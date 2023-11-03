package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SquirrelEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SquirrelModel extends GeoModel<SquirrelEntity> {

    @Override
    public ResourceLocation getModelResource(SquirrelEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/squirrel.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SquirrelEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/squirrel/squirrel.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SquirrelEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/squirrel.animation.json");
    }
    
}
