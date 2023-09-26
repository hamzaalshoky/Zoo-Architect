package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.NarwhalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NarwhalRenderer extends GeoEntityRenderer<NarwhalEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/narwhal/narwhal.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/narwhal/narwhal.png");

    public NarwhalRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NarwhalModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(NarwhalEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(NarwhalEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
