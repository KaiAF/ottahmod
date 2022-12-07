package net.livzmc.ottah.gen;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.livzmc.ottah.OttahMod;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;

import java.util.function.Predicate;

public class OtterSpawn {
    private static int SpawnRate = 100;

    public static void addSpawn(Predicate<BiomeSelectionContext> BiomeSelector, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry se) {
        Preconditions.checkArgument(se.type.getSpawnGroup() != SpawnGroup.MISC, "MISC spawns pigs");

        Identifier id = Registries.ENTITY_TYPE.getId(se.type);
        Preconditions.checkState(id != Registries.ENTITY_TYPE.getDefaultId(), "Unregistered entity type: %s");
        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, BiomeSelector, context -> context.getSpawnSettings().addSpawn(spawnGroup, se));
    }

    private static void normalSpawn() {
        Predicate<BiomeSelectionContext> biomeSelector = tag(BiomeTags.IS_BEACH);
        addSpawn(biomeSelector.and(BiomeSelectors.foundInOverworld()), OttahMod.Otter.getSpawnGroup(), new SpawnSettings.SpawnEntry(OttahMod.Otter, SpawnRate, 2, 4));
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
    }

    public static void init() {
        normalSpawn();
    }
}
