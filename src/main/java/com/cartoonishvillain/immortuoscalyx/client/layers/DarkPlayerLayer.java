package com.cartoonishvillain.immortuoscalyx.client.layers;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DarkPlayerLayer extends RenderLayer<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> {
    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/darkenedskin.png");

    public DarkPlayerLayer(RenderLayerParent<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, InfectedPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entitylivingbaseIn.isInvisible()){
            VertexConsumer bb = bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE));
            this.getParentModel().hat.visible = false;
            this.getParentModel().renderToBuffer(matrixStackIn, bb, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}
