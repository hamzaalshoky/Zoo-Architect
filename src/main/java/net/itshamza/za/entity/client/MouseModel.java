package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.MouseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MouseModel extends AnimatedGeoModel<MouseEntity> {

    @Override
    public ResourceLocation getModelResource(MouseEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/mouse.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MouseEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/mouse/mouse.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MouseEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/mouse.animation.json");
    }
}
