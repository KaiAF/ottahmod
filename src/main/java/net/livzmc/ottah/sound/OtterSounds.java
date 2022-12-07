package net.livzmc.ottah.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class OtterSounds {
    public static SoundEvent ENTITY_OTTER_HURT = register("entity.otter.hurt");
    public static SoundEvent ENTITY_OTTER_DEATH = register("entity.otter.death");
    public static SoundEvent ENTITY_OTTER_AMBIENCE = register("entity.otter.ambience");

    public void sounds() {}

    private static SoundEvent register(String name) {
        Identifier id = new Identifier("ottah", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}