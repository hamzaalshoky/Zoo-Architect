package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.FennecFoxEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FennecFoxRenderer extends GeoEntityRenderer<FennecFoxEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/fennec_fox/fennec_fox.png");
    private static final ResourceLocation FOXY = new ResourceLocation("za:textures/entity/fennec_fox/fennec_foxy.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/fennec_fox/fennec_fox_sleep.png");

    public FennecFoxRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FennecFoxModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(FennecFoxEntity instance) {
        return instance.isFoxy() ? FOXY : instance.isSleeping() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(FennecFoxEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()){
            stack.scale(0.5F, 0.5F, 0.5F);
        }else{
            stack.scale(1.1F, 1.1F, 1.1F);
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

}
