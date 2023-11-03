package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class JaguarModel extends GeoModel<JaguarEntity> {
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
}
