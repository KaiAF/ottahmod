package net.livzmc.ottah;

import net.fabricmc.api.ModInitializer;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.livzmc.ottah.gen.OtterSpawn;
import net.livzmc.ottah.item.OttahItems;
import net.livzmc.ottah.sound.OtterSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class OttahMod implements ModInitializer {



    /*
    public static final EntityType<SnailEntity> SNAIL = Registry.register(
    Registries.ENTITY_TYPE, Identifier.of(TestMod.MOD_ID, "snail"),
    FabricEntityType.Builder.createMob(SnailEntity::new,
    SpawnGroup.CREATURE,
    mob->mob.defaultAttributes(SnailEntity::createAttributes)
    ).dimensions(0.5f, 0.5f).build());
     */



    public static final EntityType<OtterEntity> OTTER = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, "otter"),
            EntityType.Builder.of(OtterEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.55F)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, "otter")))
    );
    @Override
    public void onInitialize() {
        new OtterSounds();
        OtterSpawn.init();
        OttahItems.registerModItems();


    }
}