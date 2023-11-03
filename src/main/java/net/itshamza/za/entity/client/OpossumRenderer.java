package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.OpossumEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class OpossumRenderer extends GeoEntityRenderer<OpossumEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/opossum/opossum.png");
    private static final ResourceLocation OPOSSUM = new ResourceLocation("za:textures/entity/opossum/opossum.png");

    public OpossumRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OpossumModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(OpossumEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(OpossumEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
