package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.PenguinEntity;
import net.itshamza.za.entity.custom.PenguinEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PenguinModel extends AnimatedGeoModel<PenguinEntity> {

    @Override
    public ResourceLocation getModelResource(PenguinEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PenguinEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PenguinEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/penguin.animation.json");
    }
}
