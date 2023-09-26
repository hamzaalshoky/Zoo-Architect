package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.CrabEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrabRenderer extends GeoEntityRenderer<CrabEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/crab/crab.png");

    public CrabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrabModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(CrabEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
