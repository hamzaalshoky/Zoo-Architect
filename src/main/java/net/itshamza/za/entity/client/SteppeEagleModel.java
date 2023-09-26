package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SteppeEagleEntity;
import net.itshamza.za.entity.custom.SteppeEagleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SteppeEagleModel extends AnimatedGeoModel<SteppeEagleEntity> {

    @Override
    public ResourceLocation getModelResource(SteppeEagleEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/steppe_eagle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SteppeEagleEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/steppe_eagle/steppe_eagle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SteppeEagleEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/steppe_eagle.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(SteppeEagleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
    }
}
