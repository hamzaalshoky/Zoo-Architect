package net.itshamza.za.effects;

import net.itshamza.za.damagesource.ModDamageSources;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VenomEffect extends MobEffect {
    public VenomEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(MobEffectCategory.HARMFUL, 9786206);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.level().damageSources().magic(), 1f);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
