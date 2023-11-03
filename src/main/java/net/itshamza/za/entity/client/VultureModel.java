package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.VultureEntity;
import net.itshamza.za.entity.custom.VultureEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class VultureModel extends GeoModel<VultureEntity> {

    @Override
    public ResourceLocation getModelResource(VultureEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/vulture.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VultureEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/vulture/vulture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VultureEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/vulture.animation.json");
    }
}
