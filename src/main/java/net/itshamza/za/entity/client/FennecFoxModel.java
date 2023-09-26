package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.FennecFoxEntity;
import net.itshamza.za.entity.custom.FennecFoxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FennecFoxModel extends AnimatedGeoModel<FennecFoxEntity> {

    @Override
    public ResourceLocation getModelResource(FennecFoxEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/fennec_fox.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FennecFoxEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/fennec_fox/fennec_fox.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FennecFoxEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/fennec_fox.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(FennecFoxEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
