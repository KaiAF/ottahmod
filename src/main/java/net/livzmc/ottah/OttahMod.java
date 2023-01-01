package net.livzmc.ottah;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.livzmc.ottah.gen.OtterSpawn;
import net.livzmc.ottah.sound.OtterSounds;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OttahMod implements ModInitializer {
    public static final EntityType<OtterEntity> OTTER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Config.MOD_ID, "otter"),
            FabricEntityTypeBuilder
                    .create(SpawnGroup.CREATURE, OtterEntity::new)
                    .dimensions(EntityDimensions.changing(0.6F, 0.55F))
                    .build()
    );

    public static final Item OTTER_SPAWN_EGG = new SpawnEggItem(OTTER, 0x866a67, 0xb3a28e, new FabricItemSettings());

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(OTTER, OtterEntity.createOtterAttributes());
        new OtterSounds();
        OtterSpawn.init();

        Registry.register(Registries.ITEM, new Identifier(Config.MOD_ID,"otter_spawn_egg"), OTTER_SPAWN_EGG);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(OTTER_SPAWN_EGG));
    }
}