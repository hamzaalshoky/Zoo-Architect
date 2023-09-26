package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SealEntity;
import net.itshamza.za.entity.custom.SealEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SealModel extends AnimatedGeoModel<SealEntity> {

    @Override
    public ResourceLocation getModelResource(SealEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/seal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SealEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/seal/seal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SealEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/seal.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(SealEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
    }
}
