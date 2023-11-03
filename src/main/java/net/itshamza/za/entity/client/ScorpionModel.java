package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ScorpionEntity;
import net.itshamza.za.entity.custom.ScorpionEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ScorpionModel extends GeoModel<ScorpionEntity> {

    @Override
    public ResourceLocation getModelResource(ScorpionEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/scorpion.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ScorpionEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/scorpion/scorpion.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ScorpionEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/scorpion.animation.json");
    }
}
