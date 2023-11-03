package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GoldenTamarinModel extends GeoModel<GoldenTamarinEntity> {
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

}
