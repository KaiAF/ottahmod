package me.iris.ottah.ottah;

import me.iris.ottah.ottah.gen.OtterSpawn;
import me.iris.ottah.ottah.sound.sounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class index implements ModInitializer {
    public static final EntityType<otter> Otter = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("ottah", "otter"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, otter::new).dimensions(EntityDimensions.fixed(0.7f, 0.7f)).build()
    );

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(Otter, otter.createOtterAttributes());
        new sounds();
        OtterSpawn.init();
    }
}
