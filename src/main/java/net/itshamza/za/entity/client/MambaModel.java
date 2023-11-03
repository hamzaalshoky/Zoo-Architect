package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.MambaEntity;
import net.itshamza.za.entity.custom.MambaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MambaModel extends GeoModel<MambaEntity> {

    @Override
    public ResourceLocation getModelResource(MambaEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MambaEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/snake/mamba.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MambaEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/snake.animation.json");
    }
}
