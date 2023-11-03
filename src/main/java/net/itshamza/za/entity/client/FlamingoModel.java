package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.FlamingoEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.FlamingoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FlamingoModel extends GeoModel<FlamingoEntity> {

    @Override
    public ResourceLocation getModelResource(FlamingoEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/flamingo.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlamingoEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/flamingo/flamingo.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FlamingoEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/flamingo.animation.json");
    }

}
