package io.github.flemmli97.advancedgolems.forge.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.platform.ArmorModelHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;

public class ArmorModelHandlerImpl implements ArmorModelHandler {

    @Override
    public <T extends LivingEntity, A extends HumanoidModel<T>> Model getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        return ClientHooks.getArmorModel(entity, itemStack, equipmentSlot, humanoidModel);
    }
}
