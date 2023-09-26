package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.SalamanderEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SalamanderModel extends AnimatedGeoModel<SalamanderEntity> {

    @Override
    public ResourceLocation getModelResource(SalamanderEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/salamander.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SalamanderEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/salamander/salamander.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SalamanderEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/salamander.animation.json");
    }
    
}
