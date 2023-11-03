package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.BassEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BassRenderer extends GeoEntityRenderer<BassEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/bass/bass.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/bass/bass.png");

    public BassRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BassModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(BassEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(BassEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
