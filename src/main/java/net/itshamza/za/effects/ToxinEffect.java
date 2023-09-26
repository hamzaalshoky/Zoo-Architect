package net.itshamza.za.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ToxinEffect extends MobEffect {
    public ToxinEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(MobEffectCategory.HARMFUL, 9786206);
    }

    private int lastDuration = -1;

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (lastDuration == 1) {
            entity.hurt(DamageSource.MAGIC, 50);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        lastDuration = duration;
        return duration > 0;
    }

}
