package io.github.flemmli97.advancedgolems.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class ArmorModelHandler {

    protected static ArmorModelHandler INSTANCE;

    public static ArmorModelHandler instance() {
        return INSTANCE;
    }

    public abstract <T extends LivingEntity, A extends HumanoidModel<T>> HumanoidModel<T> getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel, Consumer<HumanoidModel<T>> setup);

    public abstract ResourceLocation armorTextureForge(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type, boolean inner);
}
