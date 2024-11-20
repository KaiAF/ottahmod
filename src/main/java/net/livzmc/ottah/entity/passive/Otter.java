package net.livzmc.ottah.entity.passive;

import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.entity.goals.OtterBreedGoal;
import net.livzmc.ottah.entity.goals.OtterFindLandGoal;
import net.livzmc.ottah.sound.OtterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Otter extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANGER_TIME;
    private static final EntityDataAccessor<Boolean> TRUSTING;
    private static final EntityDataAccessor<String> TRUSTED;
    private static final UniformInt ANGER_TIME_RANGE;
    @Nullable
    private UUID angryAt;
    public static AnimationState WALK_ANIMATION = new AnimationState();
    public Otter(EntityType<? extends Otter> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(3, new OtterBreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new OtterFindLandGoal(this, 1.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers(Player.class));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    private boolean isTrusting() {
        return this.entityData.get(TRUSTING);
    }

    private String getTrusted() {
        return this.entityData.get(TRUSTED);
    }

    @Override
    public boolean isAngryAt(LivingEntity entity, ServerLevel world) {
        if (entity.getStringUUID().equals(this.getTrusted())) {
            this.setLastHurtByMob(null);
            this.setPersistentAngerTarget(null);
            this.setTarget(null);
            this.setRemainingPersistentAngerTime(0);
            this.stopBeingAngry();
            return false;
        }
        return NeutralMob.super.isAngryAt(entity, world);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.TROPICAL_FISH);
    }

    @Override
    public @Nullable Otter getBreedOffspring(ServerLevel world, AgeableMob ageableMob) {
        return OttahMod.OTTER.create(world, EntitySpawnReason.BREEDING);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel serverLevel, Animal animal, @Nullable AgeableMob ageableMob) {
        super.finalizeSpawnChildFromBreeding(serverLevel, animal, ageableMob);
    }

    @Override
    public double getAttributeBaseValue(Holder<Attribute> holder) {
        return super.getAttributeBaseValue(holder);
    }

    public static AttributeSupplier.Builder defaultAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    public float sanitizeScale(float f) {
        return this.isBaby() ? 0.55F : 1.0F;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.45F : 0.85F;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANGER_TIME, 0);
        builder.define(TRUSTING, false);
        builder.define(TRUSTED, "");
    }

    static {
        ANGER_TIME = SynchedEntityData.defineId(Otter.class, EntityDataSerializers.INT);
        ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
        TRUSTING = SynchedEntityData.defineId(Otter.class, EntityDataSerializers.BOOLEAN);
        TRUSTED = SynchedEntityData.defineId(Otter.class, EntityDataSerializers.STRING);
    }



    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        boolean bl = target.hurtServer(world, this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));

        if (bl) {
            this.setLastHurtMob(target);
        }

        return bl;
    }

    private void showEmoteParticle(boolean positive) {
        ParticleOptions particleEffect = ParticleTypes.HEART;

        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleEffect, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
        }
    }

    private void setTrusted(String uuid) {
        this.entityData.set(TRUSTING, true);
        this.entityData.set(TRUSTED, uuid);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) {
            if (this.shouldWalk()) {
                WALK_ANIMATION.startIfStopped(this.age);
            } else {
                WALK_ANIMATION.stop();
            }
        }
        super.tick();
    }

    private boolean shouldWalk() {
        return this.onGround() && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWaterOrBubble();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.isTrusting()) {
            if (itemStack.is(Items.COD) && !this.isAngry()) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.showEmoteParticle(true);
                    this.setTrusted(player.getStringUUID());
                } else {
                    this.showEmoteParticle(false);
                }

                return InteractionResult.SUCCESS;
            }
            if (itemStack.is(Items.TADPOLE_BUCKET) && !this.isAngry()) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                    player.addItem(Items.WATER_BUCKET.getDefaultInstance());
                }
                this.showEmoteParticle(true);
                this.setTrusted(player.getStringUUID());
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }


    @Override
    public void checkDespawn() {
        if (this.level().getNearestPlayer(this, 48) == null
                && !this.isTrusting()
                && this.age > 2400
        ) this.discard();
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
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int angerTime) {
        this.entityData.set(ANGER_TIME, angerTime);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angryAt;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }

    @Override
    public @Nullable LeashData getLeashData() {
        return super.getLeashData();
    }

    @Override
    public void setInLove(@Nullable Player player) {
        super.setInLove(player);
        this.inLove = 12000;
    }

    public void clearStates() {
//        this.setIsInterested(false);
//        this.setIsCrouching(false);
//        this.setSitting(false);
//        this.setSleeping(false);
//        this.setDefending(false);
//        this.setFaceplanted(false);
    }
}
