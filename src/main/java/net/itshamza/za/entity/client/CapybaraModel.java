package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CapybaraModel extends AnimatedGeoModel<CapybaraEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation("za:geo/capybara.geo.json");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("za:textures/entity/capybara/capybara_saddle_layer.png");

    @Override
    public ResourceLocation getModelResource(CapybaraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/capybara.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CapybaraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/capybara/capybara.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CapybaraEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/capybara.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(CapybaraEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
