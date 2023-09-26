package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.FlamingoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FlamingoRenderer extends GeoEntityRenderer<FlamingoEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/flamingo/flamingo.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/flamingo/flamingo_baby.png");

    public FlamingoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FlamingoModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(FlamingoEntity instance) {
        return instance.isBaby() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(FlamingoEntity animatable, float partialTicks, PoseStack stack,
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
