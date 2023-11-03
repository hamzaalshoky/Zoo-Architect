package net.itshamza.za.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.ManateeEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class ManateeRenderer extends GeoEntityRenderer<ManateeEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/manatee/manatee.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/manatee/manatee_sleep.png");

    public ManateeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ManateeModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(ManateeEntity instance) {
        return instance.isSleeping() ? SLEEPING : TEXTURE;
    }


    @Override
    public RenderType getRenderType(ManateeEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
