package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CobraEntity;
import net.itshamza.za.entity.custom.CobraEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CobraModel extends GeoModel<CobraEntity> {
    
    @Override
    public ResourceLocation getModelResource(CobraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CobraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/snake/cobra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CobraEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/snake.animation.json");
    }
}
