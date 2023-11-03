package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.OpossumEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OpossumModel extends GeoModel<OpossumEntity> {

    @Override
    public ResourceLocation getModelResource(OpossumEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/opossum.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OpossumEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/opossum/opossum.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OpossumEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/opossum.animation.json");
    }
}
