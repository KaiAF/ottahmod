package net.livzmc.ottah.mixin;

import net.livzmc.ottah.OttahMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpawnPlacements.class)
public class SpawnRestrictionMixin {
    @Shadow
    public static <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
    }

    static {
        register(OttahMod.OTTER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
