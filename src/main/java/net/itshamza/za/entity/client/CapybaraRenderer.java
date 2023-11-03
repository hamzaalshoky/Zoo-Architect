package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.BassEntity;
import net.itshamza.za.entity.custom.CapybaraEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class CapybaraRenderer extends GeoEntityRenderer<CapybaraEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/capybara/capybara.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/capybara/capybara_sleep.png");

    public CapybaraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CapybaraModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CapybaraEntity instance) {
        return instance.isSleeping() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(CapybaraEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

}
