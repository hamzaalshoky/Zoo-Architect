package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.FrilledLizardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FrilledLizardRenderer extends GeoEntityRenderer<FrilledLizardEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/frilled_lizard/frilled_lizard.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/frilled_lizard/frilled_lizard_baby.png");

    public FrilledLizardRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FrilledLizardModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(FrilledLizardEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(FrilledLizardEntity animatable, float partialTicks, PoseStack stack,
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
