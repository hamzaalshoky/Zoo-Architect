package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ShrimpEntity;
import net.itshamza.za.entity.custom.ShrimpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShrimpModel extends AnimatedGeoModel<ShrimpEntity> {

    @Override
    public ResourceLocation getModelResource(ShrimpEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/shrimp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShrimpEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/shrimp/shrimp.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShrimpEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/shrimp.animation.json");
    }
}
