package net.livzmc.ottah.gen;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.livzmc.ottah.OttahMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.function.Predicate;

public class OtterSpawn {
    private final static int SPAWN_RATE = 100;

    public static void addSpawn(Predicate<BiomeSelectionContext> BiomeSelector, MobCategory spawnGroup, MobSpawnSettings.SpawnerData se) {
        Preconditions.checkArgument(se.type.getCategory() != MobCategory.MISC, "MISC spawns pigs");

        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(se.type);
        Preconditions.checkState(id != Registries.ENTITY_TYPE.registry(), "Unregistered entity type: %s");
        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, BiomeSelector, context -> context.getSpawnSettings().addSpawn(spawnGroup, se));
    }

    public static void init() {
        Predicate<BiomeSelectionContext> biomeSelector = tag(BiomeTags.IS_BEACH);
        addSpawn(
                biomeSelector.and(BiomeSelectors.foundInOverworld()),
                OttahMod.OTTER.getCategory(),
                new MobSpawnSettings.SpawnerData(OttahMod.OTTER, SPAWN_RATE, 2, 4)
        );
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
    }
}
