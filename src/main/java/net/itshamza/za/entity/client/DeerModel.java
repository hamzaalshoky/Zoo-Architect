package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.DeerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DeerModel extends AnimatedGeoModel<DeerEntity> {

    @Override
    public ResourceLocation getModelResource(DeerEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/deer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DeerEntity object) {
        return !object.hasMane() || object.isBaby() ? new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/deer/deeress.png") :
                new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/deer/deer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DeerEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/deer.animation.json");
    }
}
