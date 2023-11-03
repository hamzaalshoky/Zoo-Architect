package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.BassEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class BassModel extends GeoModel<BassEntity> {

    @Override
    public ResourceLocation getModelResource(BassEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/bass.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BassEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/bass/bass.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BassEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/bass.animation.json");
    }
}
