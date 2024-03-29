package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.OpossumEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OpossumRenderer extends GeoEntityRenderer<OpossumEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/opossum/opossum.png");
    private static final ResourceLocation OPOSSUM = new ResourceLocation("za:textures/entity/opossum/opossum_aggro.png");

    public OpossumRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OpossumModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(OpossumEntity instance) {
        return instance.level.isNight() ? OPOSSUM : TEXTURE;
    }


    @Override
    public RenderType getRenderType(OpossumEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()){
            stack.scale(0.6F, 0.6F, 0.6F);
        }else{
            stack.scale(1F, 1F, 1F);
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
