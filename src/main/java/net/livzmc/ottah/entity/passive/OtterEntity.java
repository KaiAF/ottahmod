package net.livzmc.ottah.entity.passive;

import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.sound.OtterSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class OtterEntity extends AnimalEntity implements Angerable {
    private static final TrackedData<Integer> ANGER_TIME;
    private static final TrackedData<Boolean> TRUSTING;
    private static final TrackedData<String> TRUSTED;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    @Nullable
    private UUID angryAt;
    public static AnimationState WALK_ANIMATION = new AnimationState();
    public OtterEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(2, (new RevengeGoal(this)).setGroupRevenge(PlayerEntity.class));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, true));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private boolean isTrusting() {
        return this.dataTracker.get(TRUSTING);
    }

    private String getTrusted() {
        return this.dataTracker.get(TRUSTED);
    }

    @Override
    public boolean shouldAngerAt(LivingEntity entity) {
        if (entity.getUuidAsString().equals(this.getTrusted())) {
            this.setAttacker(null);
            this.setAngryAt(null);
            this.setTarget(null);
            this.setAngerTime(0);
            this.stopAnger();
            return false;
        }
        return Angerable.super.shouldAngerAt(entity);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.TROPICAL_FISH);
    }

    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (OtterEntity)OttahMod.OTTER.create(world);
    }

    public static DefaultAttributeContainer.Builder createOtterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.25F : dimensions.height - 0.075F;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(TRUSTING, false);
        this.dataTracker.startTracking(TRUSTED, "");
    }

    static {
        ANGER_TIME = DataTracker.registerData(OtterEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
        TRUSTING = DataTracker.registerData(OtterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TRUSTED = DataTracker.registerData(OtterEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));

        if (bl) {
            this.applyDamageEffects(this, target);
        }

        return bl;
    }

    private void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;

        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    private void setTrusted(String uuid) {
        this.dataTracker.set(TRUSTING, true);
        this.dataTracker.set(TRUSTED, uuid);
    }

    @Override
    public void tick() {
        if (this.world.isClient) {
            if (this.shouldWalk()) {
                this.WALK_ANIMATION.startIfNotRunning(this.age);
            } else {
                this.WALK_ANIMATION.stop();
            }
        }
        super.tick();
    }

    private boolean shouldWalk() {
        return this.onGround && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTrusting()) {
            if (itemStack.isOf(Items.COD) && !this.hasAngerTime()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.showEmoteParticle(true);
                    this.setTrusted(player.getUuid().toString());
                } else {
                    this.showEmoteParticle(false);
                }

                return ActionResult.SUCCESS;
            }
            if (itemStack.isOf(Items.TADPOLE_BUCKET) && !this.hasAngerTime()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                    player.giveItemStack(Items.WATER_BUCKET.getDefaultStack());
                }
                this.showEmoteParticle(true);
                this.setTrusted(player.getUuid().toString());
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTrusting() && this.age > 2400;
    }

    @Override
    public int getLimitPerChunk() {
        return 4;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return OtterSounds.ENTITY_OTTER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return OtterSounds.ENTITY_OTTER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0) return OtterSounds.ENTITY_OTTER_AMBIENCE;
        return null;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
}
