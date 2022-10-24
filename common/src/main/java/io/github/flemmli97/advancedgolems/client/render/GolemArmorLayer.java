package io.github.flemmli97.advancedgolems.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.platform.ArmorModelHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
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
    private boolean copiedModelProperties;

    public GolemArmorLayer(RenderLayerParent<T, M> renderLayerParent, A outerModel, A innerModel) {
        super(renderLayerParent);
        this.outerModel = outerModel;
        this.innerModel = innerModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        this.copiedModelProperties = false;
        poseStack.pushPose();
        this.getParentModel().adjustModel(poseStack);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.HEAD, i, this.outerModel);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.CHEST, i, this.outerModel);
        poseStack.pushPose();
        this.getParentModel().legTransform(poseStack);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.FEET, i, this.outerModel);
        this.copiedModelProperties = false;
        this.renderArmorPiece(poseStack, multiBufferSource, entity, EquipmentSlot.LEGS, i, this.innerModel);
        poseStack.popPose();
        poseStack.popPose();
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
        if (itemStack.getItem() instanceof ArmorItem armor && armor.getSlot() == equipmentSlot) {
            if (!this.copiedModelProperties) {
                this.getParentModel().copyPropertiesTo(humanoidModel);
                this.copiedModelProperties = true;
            }
            this.setPartVisibility(humanoidModel, equipmentSlot);
            Model model = ArmorModelHandler.INSTANCE.getModel(poseStack, multiBufferSource, entity, itemStack, equipmentSlot, light, humanoidModel);
            if (model == null)
                return;
            boolean bl = equipmentSlot == EquipmentSlot.LEGS;
            boolean bl2 = itemStack.hasFoil();
            if (armor instanceof DyeableArmorItem dyeable) {
                int j = dyeable.getColor(itemStack);
                float f = (float) (j >> 16 & 0xFF) / 255.0f;
                float g = (float) (j >> 8 & 0xFF) / 255.0f;
                float h = (float) (j & 0xFF) / 255.0f;
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, f, g, h, ArmorModelHandler.INSTANCE.armorTextureForge(entity, itemStack, equipmentSlot, null, bl));
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, 1.0f, 1.0f, 1.0f, ArmorModelHandler.INSTANCE.armorTextureForge(entity, itemStack, equipmentSlot, "overlay", bl));
            } else {
                this.renderModel(poseStack, multiBufferSource, light, bl2, model, 1.0f, 1.0f, 1.0f, ArmorModelHandler.INSTANCE.armorTextureForge(entity, itemStack, equipmentSlot, null, bl));
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
                humanoidModel.rightArm.visible = true;
                humanoidModel.leftArm.visible = true;
            }
            case LEGS, FEET -> {
                humanoidModel.rightLeg.visible = true;
                humanoidModel.leftLeg.visible = true;
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean glint, Model humanoidModel, float r, float g, float b, ResourceLocation armorTexture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(armorTexture), false, glint);
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
    }
}
