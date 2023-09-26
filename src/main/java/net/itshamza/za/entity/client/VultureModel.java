package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.VultureEntity;
import net.itshamza.za.entity.custom.VultureEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class VultureModel extends AnimatedGeoModel<VultureEntity> {

    @Override
    public ResourceLocation getModelResource(VultureEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/vulture.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VultureEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/vulture/vulture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VultureEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/vulture.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(VultureEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head_only");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
    }
}
