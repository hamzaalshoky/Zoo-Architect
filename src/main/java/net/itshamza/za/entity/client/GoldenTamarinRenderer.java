package net.itshamza.za.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.GoldenTamarinEntity;
import net.itshamza.za.entity.custom.variant.TamarinVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class GoldenTamarinRenderer extends GeoEntityRenderer<GoldenTamarinEntity> {
    public static final Map<TamarinVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TamarinVariant.class), (p_114874_) -> {
                p_114874_.put(TamarinVariant.GOLDEN,
                        new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/golden_tamarin/golden_tamarin.png"));
                p_114874_.put(TamarinVariant.BLACK,
                        new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/golden_tamarin/black_tamarin.png"));
            });
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/golden_tamarin/golden_tamarin.png");

    public GoldenTamarinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GoldenTamarinModel());
        this.shadowRadius = 0.3f;
        this.addRenderLayer(new TamarinPebbleLayer<>(this));
        this.addRenderLayer(new TamarinDiaperLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GoldenTamarinEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }


    @Override
    public RenderType getRenderType(GoldenTamarinEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

}
