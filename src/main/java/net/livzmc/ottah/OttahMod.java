package net.livzmc.ottah;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.livzmc.ottah.gen.OtterSpawn;
import net.livzmc.ottah.sound.OtterSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OttahMod implements ModInitializer {
    public static final EntityType<OtterEntity> Otter = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("ottah", "otter"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OtterEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.7f)).build()
    );

    // create an instance of SpawnEggItem
    public static final Item OTTER_SPAWN_EGG = new SpawnEggItem(Otter, 0x866a67, 0xb3a28e, new FabricItemSettings().group(ItemGroup.MISC));

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(Otter, OtterEntity.createOtterAttributes());
        new OtterSounds();
        OtterSpawn.init();

        // register the item OTTER_SPAWN_EGG and adds the item OTTER_SPAWN_EGG to the vanilla "Spawn Eggs" creative tab
        Registry.register(Registry.ITEM, new Identifier("ottah","otter_spawn_egg"), OTTER_SPAWN_EGG);
    }
}
