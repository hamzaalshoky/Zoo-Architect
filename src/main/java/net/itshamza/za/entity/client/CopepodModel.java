package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CopepodEntity;
import net.itshamza.za.entity.custom.CopepodEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CopepodModel extends AnimatedGeoModel<CopepodEntity> {

    @Override
    public ResourceLocation getModelResource(CopepodEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/copepod.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CopepodEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/copepod/copepod.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CopepodEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/copepod.animation.json");
    }
}
