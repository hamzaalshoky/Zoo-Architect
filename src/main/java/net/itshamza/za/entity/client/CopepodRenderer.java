package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.CopepodEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CopepodRenderer extends GeoEntityRenderer<CopepodEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/copepod/copepod.png");

    public CopepodRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CopepodModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CopepodEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(CopepodEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
