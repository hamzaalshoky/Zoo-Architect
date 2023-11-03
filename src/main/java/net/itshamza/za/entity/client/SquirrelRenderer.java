package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.entity.custom.SquirrelEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SquirrelRenderer extends GeoEntityRenderer<SquirrelEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/squirrel/squirrel.png");
    private static final ResourceLocation SMAUG = new ResourceLocation("za:textures/entity/squirrel/squirrel_pikachu.png");

    public SquirrelRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SquirrelModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SquirrelEntity instance) {
        return instance.isHamza() ? SMAUG : TEXTURE;
    }


    @Override
    public RenderType getRenderType(SquirrelEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

}
