package net.itshamza.za.damagesource;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources {
    public static final DamageSource MAUL = (new MaulDamageSource("maul"));
    public static final DamageSource VENOM = (new MaulDamageSource("venom")).bypassArmor();
}
