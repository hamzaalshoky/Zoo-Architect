package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.MantaRayEntity;
import net.itshamza.za.entity.custom.MantaRayEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MantaRayModel extends GeoModel<MantaRayEntity> {

    @Override
    public ResourceLocation getModelResource(MantaRayEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/manta_ray.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MantaRayEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/manta_ray/manta_ray.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MantaRayEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/manta_ray.animation.json");
    }
}
