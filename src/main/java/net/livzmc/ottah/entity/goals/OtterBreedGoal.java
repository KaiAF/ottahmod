package net.livzmc.ottah.entity.goals;

import net.livzmc.ottah.entity.passive.Otter;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OtterBreedGoal extends BreedGoal {
    public OtterBreedGoal(final Otter otter, final double d) {
        super(otter, d);
        this.speedModifier = 1;
    }

    private boolean inBreedZone = false;
    private final int speedModifier;

    private final List<TagKey<Biome>> likedBreedingBiomes = List.of(BiomeTags.IS_RIVER, BiomeTags.IS_OCEAN, BiomeTags.IS_DEEP_OCEAN);

    @Override
    public void tick() {
        // Otters like to breed in water so find the closest river to breed in!
        int findBreedingBiomeRange = 32;

        AABB breedingZone = new AABB(level.findClosestBiome3d((biomeHolder -> likedBreedingBiomes.stream().anyMatch(biomeHolder::is)), this.animal.blockPosition(), findBreedingBiomeRange, findBreedingBiomeRange, findBreedingBiomeRange).getFirst());
        Vec3 findMiddleBreedingZone = breedingZone.getCenter();
        if (!inBreedZone) {
            this.animal.getNavigation().moveTo(findMiddleBreedingZone.x + 0.5, findMiddleBreedingZone.y + 1, findMiddleBreedingZone.z + 0.5, this.speedModifier);
            this.partner.getNavigation().moveTo(this.partner, this.speedModifier);
            if (breedingZone.contains(Vec3.atLowerCornerOf(this.animal.blockPosition()))) inBreedZone = true;
            return;
        }

        super.tick();
    }

    public void start() {
        ((Otter)this.animal).clearStates();
        ((Otter)this.partner).clearStates();
        super.start();
    }

    protected void breed() {
        ServerLevel serverLevel = this.level;
        Otter otter = (Otter) this.animal.getBreedOffspring(serverLevel, this.partner);
        if (otter != null) {
            ServerPlayer mainAnimalFeeder = this.animal.getLoveCause();
            ServerPlayer partnerAnimalFeeder = this.partner.getLoveCause();

            if (mainAnimalFeeder != null) {
                mainAnimalFeeder.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(mainAnimalFeeder, this.animal, this.partner, otter);
            }

            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            otter.setAge(-24000);
            otter.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
            serverLevel.addFreshEntityWithPassengers(otter);
            this.level.broadcastEntityEvent(this.animal, (byte)18);
            if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1));
            }

        }
    }
}
