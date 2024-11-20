package net.livzmc.ottah.sound;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class OtterSounds {
    public static SoundEvent ENTITY_OTTER_HURT = register("entity.otter.hurt");
    public static SoundEvent ENTITY_OTTER_DEATH = register("entity.otter.death");
    public static SoundEvent ENTITY_OTTER_AMBIENCE = register("entity.otter.ambience");

    public void sounds() {}

    private static SoundEvent register(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath("ottah", name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}