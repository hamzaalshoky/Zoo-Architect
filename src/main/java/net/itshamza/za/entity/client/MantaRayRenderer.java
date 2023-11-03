package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.MantaRayEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class MantaRayRenderer extends GeoEntityRenderer<MantaRayEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/manta_ray/manta_ray.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/manta_ray/manta_ray.png");

    public MantaRayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MantaRayModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(MantaRayEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(MantaRayEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
