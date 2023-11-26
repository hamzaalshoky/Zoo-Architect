package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.BassEntity;
import net.itshamza.za.entity.custom.TimberWolfEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TimberWolfModel extends AnimatedGeoModel<TimberWolfEntity> {

    @Override
    public ResourceLocation getModelResource(TimberWolfEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/timber_wolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TimberWolfEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/timber_wolf/timber_wolf.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TimberWolfEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/timber_wolf.animation.json");
    }
}
