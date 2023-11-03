package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SealEntity;
import net.itshamza.za.entity.custom.SealEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SealModel extends GeoModel<SealEntity> {

    @Override
    public ResourceLocation getModelResource(SealEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/seal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SealEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/seal/seal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SealEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/seal.animation.json");
    }
}
