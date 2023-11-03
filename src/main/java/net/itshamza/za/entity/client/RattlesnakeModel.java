package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.RattlesnakeEntity;
import net.itshamza.za.entity.custom.RattlesnakeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RattlesnakeModel extends GeoModel<RattlesnakeEntity> {

    @Override
    public ResourceLocation getModelResource(RattlesnakeEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RattlesnakeEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/snake/rattlesnake.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RattlesnakeEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/snake.animation.json");
    }
}
