package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class JaguarModel extends AnimatedGeoModel<JaguarEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation("za:geo/jaguar.geo.json");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("za:textures/entity/jaguar/jaguar_glow.png");

    @Override
    public ResourceLocation getModelResource(JaguarEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/jaguar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JaguarEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/jaguar/jaguar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JaguarEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/jaguar.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(JaguarEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
