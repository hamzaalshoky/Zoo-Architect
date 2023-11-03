package net.itshamza.za.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.JaguarEntity;
import net.itshamza.za.entity.custom.variant.JaguarVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class JaguarRenderer extends GeoEntityRenderer<JaguarEntity> {
    public static final Map<JaguarVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(JaguarVariant.class), (p_114874_) -> {
                p_114874_.put(JaguarVariant.NORMAL,
                        new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/jaguar/jaguar.png"));
                p_114874_.put(JaguarVariant.BLACK,
                        new ResourceLocation(ZooArchitect.MOD_ID, "textures/entity/jaguar/black_jaguar.png"));
            });
    
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/jaguar/jaguar_glow.png");
    
    public JaguarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JaguarModel());
        this.shadowRadius = 0.3f;
        this.addRenderLayer(new JaguarEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(JaguarEntity instance) {
        return instance.isStealth() ? TEXTURE : LOCATION_BY_VARIANT.get(instance.getVariant());
    }


    @Override
    public RenderType getRenderType(JaguarEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
