package net.livzmc.ottah;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.livzmc.ottah.gen.OtterSpawn;
import net.livzmc.ottah.sound.OtterSounds;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OttahMod implements ModInitializer {
    public static final EntityType<OtterEntity> Otter = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("ottah", "otter"),
            FabricEntityTypeBuilder
                    .create(SpawnGroup.CREATURE, OtterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f, 0.7f))
                    .build()
    );

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(Otter, OtterEntity.createOtterAttributes());
        new OtterSounds();
        OtterSpawn.init();
    }
}