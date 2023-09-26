package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.LionfishEntity;
import net.itshamza.za.entity.custom.LionfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LionfishModel extends AnimatedGeoModel<LionfishEntity> {

    @Override
    public ResourceLocation getModelResource(LionfishEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/lionfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LionfishEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/lionfish/lionfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LionfishEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/lionfish.animation.json");
    }
}
