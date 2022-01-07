package io.github.flemmli97.advancedgolems.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ArmorModelHandler {

    @ExpectPlatform
    public static <T extends LivingEntity, A extends HumanoidModel<T>> HumanoidModel<T> getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation armorTextureForge(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type, boolean inner) {
        throw new AssertionError();
    }
}
