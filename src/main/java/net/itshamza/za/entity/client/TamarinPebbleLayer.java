package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class TamarinPebbleLayer<E extends GoldenTamarinEntity> extends GeoRenderLayer<E> {

    private static final ResourceLocation LAYER = new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/golden_tamarin/golden_tamarin_pebble.png");

    public TamarinPebbleLayer(GeoRenderer<GoldenTamarinEntity> entityRendererIn) {
        super((GeoRenderer<E>) entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GoldenTamarinEntity animatable, BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer, float partialTicks,
                       int packedLightIn, int packedOverlay) {
        if (animatable.isDiaper()) {
            RenderType renderLayer = RenderType.entityCutoutNoCull(LAYER);
            getRenderer().reRender(getDefaultBakedModel((E) animatable), poseStack, bufferSource, (E) animatable, renderLayer, bufferSource.getBuffer(renderLayer), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
