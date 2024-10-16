package io.github.flemmli97.advancedgolems.fabric.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.platform.ArmorModelHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ArmorModelHandlerImpl implements ArmorModelHandler {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LivingEntity, A extends HumanoidModel<T>> Model getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        ArmorRenderer renderer = ArmorRendererRegistryImpl.get(itemStack.getItem());
        if (renderer != null) {
            renderer.render(poseStack, multiBufferSource, itemStack, entity, equipmentSlot, light, (HumanoidModel<LivingEntity>) humanoidModel);
            return null;
        }
        return humanoidModel;
    }
}
