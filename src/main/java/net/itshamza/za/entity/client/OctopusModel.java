package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.OctopusEntity;
import net.itshamza.za.entity.custom.OctopusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OctopusModel extends AnimatedGeoModel<OctopusEntity> {
    
    @Override
    public ResourceLocation getModelResource(OctopusEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/octopus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OctopusEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/octopus/octopus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OctopusEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/octopus.animation.json");
    }
}
