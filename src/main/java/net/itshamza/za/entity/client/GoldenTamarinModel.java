package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GoldenTamarinModel extends AnimatedGeoModel<GoldenTamarinEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation("za:geo/golden_tamarin.geo.json");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("za:textures/entity/golden_tamarin/golden_tamarin_pebble.png");
    public static final ResourceLocation DIAPER = new ResourceLocation("za:textures/entity/golden_tamarin/golden_tamarin_diapers.png");

    @Override
    public ResourceLocation getModelResource(GoldenTamarinEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/golden_tamarin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoldenTamarinEntity object) {
        return GoldenTamarinRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(GoldenTamarinEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/golden_tamarin.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(GoldenTamarinEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if(!entity.isSitting()){
            IBone head = this.getAnimationProcessor().getBone("head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
        }
    }
}
