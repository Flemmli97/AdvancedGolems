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
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.DyedItemColor;

public class GolemArmorLayer<T extends GolemBase, M extends GolemModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final A outerModel, innerModel;
    private boolean copiedModelProperties;

    private final TextureAtlas armorTrimAtlas;

    public GolemArmorLayer(RenderLayerParent<T, M> renderLayerParent, A outerModel, A innerModel, ModelManager modelManager) {
        super(renderLayerParent);
        this.outerModel = outerModel;
        this.innerModel = innerModel;
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
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

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
        if (itemStack.getItem() instanceof ArmorItem armor && armor.getEquipmentSlot() == equipmentSlot) {
            if (!this.copiedModelProperties) {
                this.getParentModel().copyPropertiesTo(humanoidModel);
                this.copiedModelProperties = true;
            }
            this.setPartVisibility(humanoidModel, equipmentSlot);
            Model model = ArmorModelHandler.INSTANCE.getModel(poseStack, buffer, entity, itemStack, equipmentSlot, light, humanoidModel);
            if (model == null)
                return;
            ArmorMaterial armorMaterial = armor.getMaterial().value();
            int color = itemStack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(itemStack, -6265536) : -1;
            boolean inner = equipmentSlot == EquipmentSlot.LEGS;
            float r, g, b;
            for (ArmorMaterial.Layer layer : armorMaterial.layers()) {
                if (layer.dyeable() && color != -1) {
                    r = (float) FastColor.ARGB32.red(color) / 255.0F;
                    g = (float) FastColor.ARGB32.green(color) / 255.0F;
                    b = (float) FastColor.ARGB32.blue(color) / 255.0F;
                } else {
                    r = 1.0F;
                    g = 1.0F;
                    b = 1.0F;
                }
                this.renderModel(poseStack, buffer, light, model, r, g, b, layer.texture(inner));
            }
            ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
            if (armorTrim != null) {
                this.renderTrim(armor.getMaterial(), poseStack, buffer, light, armorTrim, model, inner);
            }
            if (itemStack.hasFoil()) {
                this.renderGlint(poseStack, buffer, light, model);
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

    private void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Model armorItem, float red, float green, float blue, ResourceLocation texture) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        armorItem.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private void renderTrim(Holder<ArmorMaterial> armorMaterial, PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorTrim trim, Model model, boolean innerTexture) {
        TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(innerTexture ? trim.innerTexture(armorMaterial) : trim.outerTexture(armorMaterial));
        VertexConsumer vertexConsumer = textureAtlasSprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(((TrimPattern) trim.pattern().value()).decal())));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Model model) {
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
