package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.ScorpionEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ScorpionRenderer extends GeoEntityRenderer<ScorpionEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/scorpion/scorpion.png");

    public ScorpionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScorpionModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(ScorpionEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(ScorpionEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
