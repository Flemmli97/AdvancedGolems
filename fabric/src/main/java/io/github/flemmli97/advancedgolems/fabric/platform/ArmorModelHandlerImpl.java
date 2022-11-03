package io.github.flemmli97.advancedgolems.fabric.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.ArmorCacheGetter;
import io.github.flemmli97.advancedgolems.platform.ArmorModelHandler;
import io.github.flemmli97.tenshilib.api.item.DynamicArmorTextureItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public ResourceLocation armorTextureForge(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type, boolean inner) {
        if(stack.getItem() instanceof DynamicArmorTextureItem item)
            return ArmorCacheGetter.getOrCompute(item.getArmorTexture(stack, entity, slot, null));
        ArmorItem armorItem = (ArmorItem) stack.getItem();
        String string2 = "textures/models/armor/" + armorItem.getMaterial().getName() + "_layer_" + (inner ? 2 : 1) + (type == null ? "" : "_" + type) + ".png";
        return ArmorCacheGetter.getOrCompute(string2);
    }
}
