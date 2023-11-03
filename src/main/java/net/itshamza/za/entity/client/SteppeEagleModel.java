package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SteppeEagleEntity;
import net.itshamza.za.entity.custom.SteppeEagleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SteppeEagleModel extends GeoModel<SteppeEagleEntity> {

    @Override
    public ResourceLocation getModelResource(SteppeEagleEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/steppe_eagle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SteppeEagleEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/steppe_eagle/steppe_eagle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SteppeEagleEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/steppe_eagle.animation.json");
    }



}
