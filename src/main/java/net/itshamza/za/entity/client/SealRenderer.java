package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.SealEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SealRenderer extends GeoEntityRenderer<SealEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/seal/seal.png");

    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/seal/seal_sleep.png");

    public SealRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SealModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SealEntity instance) {
        return instance.isBasking() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(SealEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()){
            stack.scale(0.5F, 0.5F, 0.5F);
        }else{
            stack.scale(1F, 1F, 1F);
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
