package net.itshamza.za.potion;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, ZooArchitect.MOD_ID);

    public static RegistryObject<Potion> TOXIN_POTION = POTIONS.register("toxin_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.TOXIN.get(), 500, 0)));

    public static RegistryObject<Potion> VENOM_POTION = POTIONS.register("venom_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.VENOM.get(), 250, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
