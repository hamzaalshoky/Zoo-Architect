package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.CobraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CobraRenderer extends GeoEntityRenderer<CobraEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/snake/cobra.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/snake/cobra.png");

    public CobraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CobraModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CobraEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(CobraEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
