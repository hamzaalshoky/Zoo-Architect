package net.itshamza.za.entity.custom.ai;

import net.itshamza.za.entity.custom.VultureEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class VultureAIStealFromPlayers extends Goal {

    private VultureEntity seagull;
    private Vec3 fleeVec = null;
    private Player target;
    private int fleeTime = 0;

    public VultureAIStealFromPlayers(VultureEntity VultureEntity) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET));
        this.seagull = VultureEntity;
    }

    @Override
    public boolean canUse() {
        long worldTime = this.seagull.level().getGameTime() % 10;
        if (this.seagull.getNoActionTime() >= 100 && worldTime != 0) {
            return false;
        }
        if (this.seagull.getRandom().nextInt(6) != 0 && worldTime != 0 || seagull.stealCooldown > 0) {
            return false;
        }
        if(this.seagull.getMainHandItem().isEmpty()){
            Player valid = getClosestValidPlayer();
            if(valid != null){
                target = valid;
                return true;
            }
        }
        return false;
    }

    public void start(){
        this.seagull.aiItemFlag = true;
    }

    public void stop(){
        this.seagull.aiItemFlag = false;
        target = null;
        fleeVec = null;
        fleeTime = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && !target.isCreative() && (seagull.getMainHandItem().isEmpty() || fleeTime > 0);
    }

    public void tick(){
        seagull.getMoveControl().setWantedPosition(target.getX(), target.getEyeY(), target.getZ(), 1.2F);
        if(seagull.distanceTo(target) < 2F && seagull.getMainHandItem().isEmpty()){
            if(hasFoods(target)){
                ItemStack foodStack = getFoodItemFrom(target);
                if(!foodStack.isEmpty()){
                    ItemStack copy = foodStack.copy();
                    foodStack.shrink(1);
                    copy.setCount(1);
                    seagull.setItemInHand(InteractionHand.MAIN_HAND, copy);
                    fleeTime = 60;
                    seagull.stealCooldown = 1500 + seagull.getRandom().nextInt(1500);
                }else{
                    stop();
                }
            }else{
                stop();
            }
        }
        if(fleeTime > 0){
            if(fleeVec == null){
                fleeVec = seagull.getBlockInViewAway(target.position(), 4);
            }
            if(fleeVec != null){
                seagull.getMoveControl().setWantedPosition(fleeVec.x, fleeVec.y, fleeVec.z, 1.2F);
                if(seagull.distanceToSqr(fleeVec) < 5){
                    fleeVec = seagull.getBlockInViewAway(fleeVec, 4);
                }
            }
            fleeTime--;
        }
    }

    private Player getClosestValidPlayer(){
        List<Player> list = seagull.level().getEntitiesOfClass(Player.class, seagull.getBoundingBox().inflate(10, 25, 10), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        Player closest = null;
        if(!list.isEmpty()){
            for(Player player : list){
                if((closest == null || closest.distanceTo(seagull) > player.distanceTo(seagull)) && hasFoods(player)){
                    closest = player;
                }
            }
        }
        return closest;
    }

    private boolean hasFoods(Player player){
        for(int i = 0; i < 9; i++){
            ItemStack stackIn = player.getInventory().items.get(i);
            if(stackIn.isEdible()){
                return true;
            }
        }
        return false;
    }

    private ItemStack getFoodItemFrom(Player player){
        List<ItemStack> foods = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            ItemStack stackIn = player.getInventory().items.get(i);
            if(stackIn.isEdible()){
                foods.add(stackIn);
            }
        }
        if(!foods.isEmpty()){
            return foods.get(foods.size() <= 1 ? 0 : seagull.getRandom().nextInt(foods.size() - 1));
        }
        return ItemStack.EMPTY;
    }
}
