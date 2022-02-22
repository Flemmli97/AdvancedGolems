package io.github.flemmli97.advancedgolems.forge.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.mixin.HumanoidArmorLayerMixin;
import io.github.flemmli97.advancedgolems.platform.ArmorModelHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.Nullable;

public class ArmorModelHandlerImpl extends ArmorModelHandler {

    public static void init() {
        INSTANCE = new ArmorModelHandlerImpl();
    }

    @Override
    public <T extends LivingEntity, A extends HumanoidModel<T>> Model getModel(PoseStack poseStack, MultiBufferSource multiBufferSource, T entity, ItemStack itemStack, EquipmentSlot equipmentSlot, int light, A humanoidModel) {
        return ForgeHooksClient.getArmorModel(entity, itemStack, equipmentSlot, humanoidModel);
    }

    @Override
    public ResourceLocation armorTextureForge(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type, boolean inner) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(58);
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, inner ? 2 : 1, type == null ? "" : String.format("_%s", type));
        ResourceLocation resourcelocation = HumanoidArmorLayerMixin.armorResCache().get(s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type));
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            HumanoidArmorLayerMixin.armorResCache().put(s1, resourcelocation);
        }
        return resourcelocation;
    }
}
