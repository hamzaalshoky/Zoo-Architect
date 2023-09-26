package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.NarwhalEntity;
import net.itshamza.za.entity.custom.NarwhalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NarwhalModel extends AnimatedGeoModel<NarwhalEntity> {

    @Override
    public ResourceLocation getModelResource(NarwhalEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/narwhal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NarwhalEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/narwhal/narwhal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NarwhalEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/narwhal.animation.json");
    }
}
