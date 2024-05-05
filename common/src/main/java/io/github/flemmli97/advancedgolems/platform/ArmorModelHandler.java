package io.github.flemmli97.advancedgolems.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.tenshilib.platform.InitUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ArmorModelHandler {

    ArmorModelHandler INSTANCE = InitUtil.getPlatformInstance(ArmorModelHandler.class,
            "io.github.flemmli97.advancedgolems.fabric.platform.ArmorModelHandlerImpl",
            "io.github.flemmli97.advancedgolems.forge.platform.ArmorModelHandlerImpl");

    <T extends LivingEntity, A extends HumanoidModel<T>> Model getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel);
}
