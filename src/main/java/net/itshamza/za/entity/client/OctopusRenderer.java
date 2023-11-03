package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.OctopusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class OctopusRenderer extends GeoEntityRenderer<OctopusEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/octopus/octopus.png");
    private static final ResourceLocation GRAVEL = new ResourceLocation("za:textures/entity/octopus/gravel_octopus.png");
    private static final ResourceLocation STONE = new ResourceLocation("za:textures/entity/octopus/stone_octopus.png");
    private static final ResourceLocation SAND = new ResourceLocation("za:textures/entity/octopus/sand_octopus.png");
    private static final ResourceLocation CLAY = new ResourceLocation("za:textures/entity/octopus/clay_octopus.png");

    public OctopusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new OctopusModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(OctopusEntity instance) {
        return instance.isGravel() ? GRAVEL : instance.isStone() ? STONE : instance.isSand() ? SAND : instance.isClay() ? CLAY : TEXTURE;
    }


    @Override
    public RenderType getRenderType(OctopusEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
