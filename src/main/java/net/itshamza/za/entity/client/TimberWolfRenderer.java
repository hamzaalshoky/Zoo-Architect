package net.itshamza.za.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.itshamza.za.ZooArchitect;
import net.itshamza.za.entity.custom.TimberWolfEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TimberWolfRenderer extends GeoEntityRenderer<TimberWolfEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("za:textures/entity/timber_wolf/timber_wolf.png");
    private static final ResourceLocation SLEEPING = new ResourceLocation("za:textures/entity/timber_wolf/timber_wolf_dark.png");
    private static final ResourceLocation LIGHT = new ResourceLocation("za:textures/entity/timber_wolf/timber_wolf_light.png");

    public TimberWolfRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TimberWolfModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(TimberWolfEntity instance) {
        return TEXTURE;
    }


    @Override
    public RenderType getRenderType(TimberWolfEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()){
            stack.scale(0.5F, 0.5F, 0.5F);
        }else{
            stack.scale(1F, 1F, 1F);
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
