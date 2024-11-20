package net.livzmc.ottah.sound;

import net.livzmc.ottah.OttahMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class OtterSounds {
    public static final SoundEvent ENTITY_OTTER_HURT = register("entity.otter.hurt");
    public static final SoundEvent ENTITY_OTTER_DEATH = register("entity.otter.death");
    public static final SoundEvent ENTITY_OTTER_AMBIENCE = register("entity.otter.ambience");

    private static SoundEvent register(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(OttahMod.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}