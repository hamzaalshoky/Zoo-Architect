package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ManateeEntity;
import net.itshamza.za.entity.custom.ManateeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ManateeModel extends AnimatedGeoModel<ManateeEntity> {

    @Override
    public ResourceLocation getModelResource(ManateeEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/manatee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ManateeEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/manatee/manatee.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ManateeEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/manatee.animation.json");
    }
}
