package net.itshamza.za.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.CardinalEntity;
import net.itshamza.za.entity.custom.variant.CardinalVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class CardinalRenderer extends GeoEntityRenderer<CardinalEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/cardinal/cardinal.png");

    public CardinalRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CardinalModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(CardinalEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(CardinalEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
