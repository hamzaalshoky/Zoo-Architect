package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CrabEntity;
import net.itshamza.za.entity.custom.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrabModel extends GeoModel<CrabEntity> {

    @Override
    public ResourceLocation getModelResource(CrabEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/crab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrabEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/crab/crab.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrabEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/crab.animation.json");
    }
}
