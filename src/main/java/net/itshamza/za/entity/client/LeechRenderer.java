package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.LeechEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class LeechRenderer extends GeoEntityRenderer<LeechEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/leech/leech.png");

    public LeechRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LeechModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(LeechEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(LeechEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
