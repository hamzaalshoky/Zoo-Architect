package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.PenguinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/penguin/penguin.png");

    public PenguinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PenguinModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(PenguinEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(PenguinEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
