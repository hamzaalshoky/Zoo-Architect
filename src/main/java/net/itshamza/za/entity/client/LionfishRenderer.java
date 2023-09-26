package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.LionfishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LionfishRenderer extends GeoEntityRenderer<LionfishEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/lionfish/lionfish.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/lionfish/lionfish.png");

    public LionfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LionfishModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(LionfishEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(LionfishEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
