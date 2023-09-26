package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SharkEntity;
import net.itshamza.za.entity.custom.SharkEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SharkModel extends AnimatedGeoModel<SharkEntity> {

    @Override
    public ResourceLocation getModelResource(SharkEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/shark.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SharkEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/shark/shark.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SharkEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/shark.animation.json");
    }
}
