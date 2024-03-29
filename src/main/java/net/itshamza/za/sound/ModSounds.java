package net.itshamza.za.sound;

import net.itshamza.za.ZooArchitect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ZooArchitect.MOD_ID);

    public static RegistryObject<SoundEvent> LION_AMBIENT
            = registerSoundEvents("lion_ambient");

    public static RegistryObject<SoundEvent> LION_HURT
            = registerSoundEvents("lion_hurt");

    public static RegistryObject<SoundEvent> LION_DEATH
            = registerSoundEvents("lion_death");

    public static RegistryObject<SoundEvent> BOSCAGE
            = registerSoundEvents("record_boscage");

    public static RegistryObject<SoundEvent> RATTLE
            = registerSoundEvents("rattle");

    public static RegistryObject<SoundEvent> HISS
            = registerSoundEvents("hiss");

    public static RegistryObject<SoundEvent> SNAKE_HURT
            = registerSoundEvents("snake_hurt");

    public static RegistryObject<SoundEvent> SNAKE_DIE
            = registerSoundEvents("snake_die");

    public static RegistryObject<SoundEvent> PARCH
            = registerSoundEvents("record_parch");

    public static RegistryObject<SoundEvent> TAMARIN_AMBIENT
            = registerSoundEvents("tamarin_ambient");

    public static RegistryObject<SoundEvent> SEAL_AMBIENT
            = registerSoundEvents("seal_ambient");

    public static RegistryObject<SoundEvent> WOLF_HOWL
            = registerSoundEvents("wolf_howl");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(ZooArchitect.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> new SoundEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
