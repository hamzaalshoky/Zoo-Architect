package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.SteppeEagleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SteppeEagleRenderer extends GeoEntityRenderer<SteppeEagleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/steppe_eagle/steppe_eagle.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/steppe_eagle/steppe_eagle_baby.png");

    public SteppeEagleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SteppeEagleModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SteppeEagleEntity instance) {
        return instance.isBaby() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(SteppeEagleEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
