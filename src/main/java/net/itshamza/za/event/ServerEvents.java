package net.itshamza.za.event;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ZooArchitect.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public void onEntityJoinWorld(MobSpawnEvent event) {
        try {
            if (event.getEntity() != null && event.getEntity() instanceof Husk) {
                Husk spider = (Husk) event.getEntity();
                spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal(spider, FennecFoxEntity.class, 1, true, false, null));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Fox) {
                Fox spider = (Fox) event.getEntity();
                spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal(spider, MouseEntity.class, 1, true, false, null));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Fox) {
                Fox spider = (Fox) event.getEntity();
                spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal(spider, SquirrelEntity.class, 1, true, false, null));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Wolf) {
                Wolf spider = (Wolf) event.getEntity();
                spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal(spider, SquirrelEntity.class, 1, true, false, null));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Cat) {
                Cat spider = (Cat) event.getEntity();
                spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal(spider, MouseEntity.class, 1, true, false, null));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Creeper) {
                Creeper creeper = (Creeper) event.getEntity();
                creeper.targetSelector.addGoal(3, new AvoidEntityGoal<>(creeper, JaguarEntity.class, 6.0F, 1.0D, 1.2D));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Rabbit) {
                Rabbit creeper = (Rabbit) event.getEntity();
                creeper.targetSelector.addGoal(3, new AvoidEntityGoal<>(creeper, FennecFoxEntity.class, 6.0F, 1.0D, 1.2D));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Monster) {
                Monster creeper = (Monster) event.getEntity();
                creeper.targetSelector.addGoal(0, new AvoidEntityGoal<>(creeper, FrilledLizardEntity.class, 6.0F, 1.0D, 1.2D));
            }
            if (event.getEntity() != null && event.getEntity() instanceof Bee) {
                Bee creeper = (Bee) event.getEntity();
                creeper.targetSelector.addGoal(1, new AvoidEntityGoal<>(creeper, FrilledLizardEntity.class, 6.0F, 1.0D, 1.2D));
            }
        } catch (Exception e) {
            ZooArchitect.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
        }
    }
}
