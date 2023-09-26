package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.FrilledLizardEntity;
import net.itshamza.za.entity.custom.FrilledLizardEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FrilledLizardModel extends AnimatedGeoModel<FrilledLizardEntity> {

    @Override
    public ResourceLocation getModelResource(FrilledLizardEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/frilled_lizard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FrilledLizardEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/frilled_lizard/frilled_lizard.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FrilledLizardEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/frilled_lizard.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(FrilledLizardEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
            IBone head = this.getAnimationProcessor().getBone("head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
    }
}
