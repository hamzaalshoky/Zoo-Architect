package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CardinalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CardinalModel extends GeoModel<CardinalEntity> {

    @Override
    public ResourceLocation getModelResource(CardinalEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/cardinal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CardinalEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "entity/cardinal/cardinal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CardinalEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/cardinal.animation.json");
    }
}
