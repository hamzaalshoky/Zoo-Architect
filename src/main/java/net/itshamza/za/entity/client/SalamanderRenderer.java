package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.SalamanderEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SalamanderRenderer extends GeoEntityRenderer<SalamanderEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/salamander/salamander.png");
    private static final ResourceLocation SMAUG = new ResourceLocation("za:textures/entity/salamander/smaug.png");

    public SalamanderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SalamanderModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SalamanderEntity instance) {
        return instance.isSmaug() ? SMAUG : TEXTURE;
    }


    @Override
    public RenderType getRenderType(SalamanderEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

}
