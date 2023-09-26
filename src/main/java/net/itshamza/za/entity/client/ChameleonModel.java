package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ChameleonEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.ChameleonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChameleonModel extends AnimatedGeoModel<ChameleonEntity> {

    @Override
    public ResourceLocation getModelResource(ChameleonEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/chameleon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChameleonEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/chameleon/chameleon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChameleonEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/chameleon.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(ChameleonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
