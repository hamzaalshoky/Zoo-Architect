package net.itshamza.za.sound;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.platform.CommonPlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ZooArchitect.MOD_ID);

    public static final Supplier<SoundEvent> JAGUAR_AMBIENT = CommonPlatformHelper.registerSoundEvent("jaguar_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "jaguar_ambient")));
    public static final Supplier<SoundEvent> JAGUAR_HURT = CommonPlatformHelper.registerSoundEvent("jaguar_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "jaguar_hurt")));
    public static final Supplier<SoundEvent> JAGUAR_DEATH = CommonPlatformHelper.registerSoundEvent("jaguar_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "jaguar_death")));
    //public static final Supplier<SoundEvent> BOSCAGE = CommonPlatformHelper.registerSoundEvent("record_boscage", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "record_boscage")));
    public static final Supplier<SoundEvent> RATTLE = CommonPlatformHelper.registerSoundEvent("rattle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "rattle")));
    public static final Supplier<SoundEvent> HISS = CommonPlatformHelper.registerSoundEvent("hiss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "hiss")));
    public static final Supplier<SoundEvent> SNAKE_HURT = CommonPlatformHelper.registerSoundEvent("snake_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "snake_hurt")));
    public static final Supplier<SoundEvent> SNAKE_DIE = CommonPlatformHelper.registerSoundEvent("snake_die", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "snake_die")));
    //public static final Supplier<SoundEvent> PARCH = CommonPlatformHelper.registerSoundEvent("record_parch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "record_parch")));
    public static final Supplier<SoundEvent> TAMARIN_AMBIENT = CommonPlatformHelper.registerSoundEvent("tamarin_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "tamarin_ambient")));
    public static final Supplier<SoundEvent> SEAL_AMBIENT = CommonPlatformHelper.registerSoundEvent("seal_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZooArchitect.MOD_ID, "seal_ambient")));
}
