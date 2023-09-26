package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.RattlesnakeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RattlesnakeRenderer extends GeoEntityRenderer<RattlesnakeEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/snake/rattlesnake.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/snake/rattlesnake.png");

    public RattlesnakeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RattlesnakeModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(RattlesnakeEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(RattlesnakeEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.1F, 1.1F, 1.1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
