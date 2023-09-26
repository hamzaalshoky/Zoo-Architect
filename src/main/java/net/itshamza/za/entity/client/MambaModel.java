package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.MambaEntity;
import net.itshamza.za.entity.custom.MambaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MambaModel extends AnimatedGeoModel<MambaEntity> {

    @Override
    public ResourceLocation getModelResource(MambaEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/snake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MambaEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/snake/mamba.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MambaEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/snake.animation.json");
    }
}
