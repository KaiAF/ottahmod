package net.livzmc.ottah.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.livzmc.ottah.Config;
import net.livzmc.ottah.OttahMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class OttahItems {

    public static final Item OTTER_SPAWN_EGG = registerItem("otter_spawn_egg", new SpawnEggItem(OttahMod.OTTER, 0x866a67, 0xb3a28e,(new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, "otter_spawn_egg"))))));



    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(OTTER_SPAWN_EGG);
        });
    }
}
