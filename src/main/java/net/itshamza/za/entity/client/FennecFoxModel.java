package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.FennecFoxEntity;
import net.itshamza.za.entity.custom.FennecFoxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FennecFoxModel extends GeoModel<FennecFoxEntity> {

    @Override
    public ResourceLocation getModelResource(FennecFoxEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/fennec_fox.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FennecFoxEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/fennec_fox/fennec_fox.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FennecFoxEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/fennec_fox.animation.json");
    }

}
