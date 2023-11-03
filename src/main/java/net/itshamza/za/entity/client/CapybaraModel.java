package net.itshamza.za.entity.client;

import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CapybaraModel extends GeoModel<CapybaraEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation("za:geo/capybara.geo.json");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("za:textures/entity/capybara/capybara_saddle_layer.png");

    @Override
    public ResourceLocation getModelResource(CapybaraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "geo/capybara.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CapybaraEntity object) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/capybara/capybara.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CapybaraEntity animatable) {
        return new ResourceLocation(ZooArchitect.MOD_ID, "animations/capybara.animation.json");
    }
}
