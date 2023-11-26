package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.MouseEntity;
import net.itshamza.za.entity.custom.SalamanderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MouseRenderer extends GeoEntityRenderer<MouseEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/mouse/mouse.png");
    private static final ResourceLocation SMAUG = new ResourceLocation("za:textures/entity/mouse/mouse_remy.png");

    public MouseRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MouseModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(MouseEntity instance) {
        return instance.isRemy() ? SMAUG : TEXTURE;
    }

    @Override
    public RenderType getRenderType(MouseEntity animatable, float partialTicks, PoseStack stack,
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
