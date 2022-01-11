package io.github.flemmli97.advancedgolems.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.flemmli97.advancedgolems.client.ArmorModelHandler;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class GolemArmorLayer<T extends GolemBase, M extends GolemModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final A outerModel, innerModel;

    public GolemArmorLayer(RenderLayerParent<T, M> renderLayerParent, A outerModel, A innerModel) {
        super(renderLayerParent);
        this.outerModel = outerModel;
        this.innerModel = innerModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.7f);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.CHEST, i, this.outerModel);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.HEAD, i, this.outerModel);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.scale(0.6f, 0.2f, 0.85f);
        poseStack.translate(0, 8 / 16f, 0);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.LEGS, i, this.innerModel);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.FEET, i, this.outerModel);
        poseStack.popPose();
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
        if (itemStack.getItem() instanceof ArmorItem armor && armor.getSlot() == equipmentSlot) {
            HumanoidModel<T> model = ArmorModelHandler.getModel(poseStack, multiBufferSource, entity, itemStack, equipmentSlot, light, humanoidModel, theModel -> {
                this.getParentModel().copyPropertiesTo(theModel);
                this.setPartVisibility(theModel, equipmentSlot);
            });
            if (model == null)
                return;
            boolean bl = equipmentSlot == EquipmentSlot.LEGS;
            boolean bl2 = itemStack.hasFoil();
            if (armor instanceof DyeableArmorItem dyeable) {
                int j = dyeable.getColor(itemStack);
                float f = (float) (j >> 16 & 0xFF) / 255.0f;
                float g = (float) (j >> 8 & 0xFF) / 255.0f;
                float h = (float) (j & 0xFF) / 255.0f;
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, bl, f, g, h, ArmorModelHandler.armorTextureForge(entity, itemStack, equipmentSlot, null, bl));
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, bl, 1.0f, 1.0f, 1.0f, ArmorModelHandler.armorTextureForge(entity, itemStack, equipmentSlot, "overlay", bl));
            } else {
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, bl, 1.0f, 1.0f, 1.0f, ArmorModelHandler.armorTextureForge(entity, itemStack, equipmentSlot, null, bl));
            }
        }
    }

    protected void setPartVisibility(HumanoidModel<?> humanoidModel, EquipmentSlot equipmentSlot) {
        humanoidModel.setAllVisible(false);
        switch (equipmentSlot) {
            case HEAD -> {
                humanoidModel.head.visible = true;
                humanoidModel.hat.visible = true;
            }
            case CHEST -> {
                humanoidModel.body.visible = true;
                humanoidModel.rightArm.visible = true;
                humanoidModel.leftArm.visible = true;
            }
            case LEGS, FEET -> {
                humanoidModel.rightLeg.visible = true;
                humanoidModel.leftLeg.visible = true;
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean bl, EntityModel<?> humanoidModel, boolean bl2, float f, float g, float h, ResourceLocation armorTexture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(armorTexture), false, bl);
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, f, g, h, 1.0f);
    }
}
