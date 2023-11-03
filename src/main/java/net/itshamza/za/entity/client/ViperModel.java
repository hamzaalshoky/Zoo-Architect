package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ViperEntity;
import net.itshamza.za.entity.custom.ViperEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ViperModel extends GeoModel<ViperEntity> {

    @Override
    public ResourceLocation getModelResource(ViperEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ViperEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/snake/viper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ViperEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/snake.animation.json");
    }
}
