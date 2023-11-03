package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.FrilledLizardEntity;
import net.itshamza.za.entity.custom.FrilledLizardEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FrilledLizardModel extends GeoModel<FrilledLizardEntity> {

    @Override
    public ResourceLocation getModelResource(FrilledLizardEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/frilled_lizard.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FrilledLizardEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/frilled_lizard/frilled_lizard.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FrilledLizardEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/frilled_lizard.animation.json");
    }

}
