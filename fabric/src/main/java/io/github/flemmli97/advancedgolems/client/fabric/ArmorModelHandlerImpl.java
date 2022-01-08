package io.github.flemmli97.advancedgolems.client.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.mixin.HumanoidArmorLayerMixin;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ArmorModelHandlerImpl {

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity, A extends HumanoidModel<T>> HumanoidModel<T> getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel, Consumer<HumanoidModel<T>> setup) {
        ArmorRenderer renderer = ArmorRendererRegistryImpl.get(itemStack.getItem());
        setup.accept(humanoidModel);
        if (renderer != null) {
            renderer.render(poseStack, multiBufferSource, itemStack, entity, equipmentSlot, light, (HumanoidModel<LivingEntity>) humanoidModel);
            return null;
        }
        return humanoidModel;
    }

    public static ResourceLocation armorTextureForge(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type, boolean inner) {
        ArmorItem armorItem = (ArmorItem) stack.getItem();
        String string2 = "textures/models/armor/" + armorItem.getMaterial().getName() + "_layer_" + (inner ? 2 : 1) + (type == null ? "" : "_" + type) + ".png";
        return HumanoidArmorLayerMixin.armorResCache().computeIfAbsent(string2, ResourceLocation::new);
    }
}
