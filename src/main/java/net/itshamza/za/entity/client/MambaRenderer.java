package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.MambaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MambaRenderer extends GeoEntityRenderer<MambaEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/snake/mamba.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/snake/mamba.png");

    public MambaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MambaModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(MambaEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(MambaEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.3F, 1.3F, 1.3F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
