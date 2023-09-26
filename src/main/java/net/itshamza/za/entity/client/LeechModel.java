package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.LeechEntity;
import net.itshamza.za.entity.custom.LeechEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeechModel extends AnimatedGeoModel<LeechEntity> {

    @Override
    public ResourceLocation getModelResource(LeechEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/leech.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LeechEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/leech/leech.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LeechEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/leech.animation.json");
    }
}
