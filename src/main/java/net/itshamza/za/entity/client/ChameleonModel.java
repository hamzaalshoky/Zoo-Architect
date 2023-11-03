package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ChameleonEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.ChameleonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ChameleonModel extends GeoModel<ChameleonEntity> {

    @Override
    public ResourceLocation getModelResource(ChameleonEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/chameleon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChameleonEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/chameleon/chameleon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChameleonEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/chameleon.animation.json");
    }
}
