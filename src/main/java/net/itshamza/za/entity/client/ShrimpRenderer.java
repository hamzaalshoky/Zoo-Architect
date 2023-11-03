package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.ShrimpEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ShrimpRenderer extends GeoEntityRenderer<ShrimpEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/shrimp/shrimp.png");

    public ShrimpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShrimpModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(ShrimpEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(ShrimpEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
