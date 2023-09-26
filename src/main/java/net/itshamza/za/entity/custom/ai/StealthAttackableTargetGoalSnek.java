package net.itshamza.za.entity.custom.ai;

import net.itshamza.za.entity.custom.JaguarEntity;
import net.itshamza.za.entity.custom.ViperEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class StealthAttackableTargetGoalSnek extends NearestAttackableTargetGoal {
    ViperEntity jag;

    public StealthAttackableTargetGoalSnek(Mob p_26060_, Class p_26061_, boolean p_26062_, ViperEntity jag) {
        super(p_26060_, p_26061_, p_26062_);
        this.jag = jag;
    }

    @Override
    protected AABB getTargetSearchArea(double p_26069_) {
        return this.mob.getBoundingBox().inflate(p_26069_, 16.0D, p_26069_);
    }

    @Override
    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        jag.setStealth(true);
        super.start();
    }
}
