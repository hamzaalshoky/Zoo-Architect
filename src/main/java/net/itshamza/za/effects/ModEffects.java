package net.itshamza.za.effects;

import net.itshamza.za.ZooArchitect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ZooArchitect.MOD_ID);

    public static final RegistryObject<MobEffect> TOXIN = MOB_EFFECTS.register("toxin",
            () -> new ToxinEffect(MobEffectCategory.HARMFUL, 7165262));

    public static final RegistryObject<MobEffect> VENOM = MOB_EFFECTS.register("venom",
            () -> new VenomEffect(MobEffectCategory.HARMFUL, 15059270));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
