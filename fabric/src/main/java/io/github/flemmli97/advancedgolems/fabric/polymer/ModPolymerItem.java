package io.github.flemmli97.advancedgolems.fabric.polymer;

import eu.pb4.polymer.api.item.PolymerItem;
import eu.pb4.polymer.api.item.PolymerItemUtils;
import eu.pb4.polymer.api.utils.PolymerUtils;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({GolemSpawnItem.class, GolemController.class})
public abstract class ModPolymerItem implements PolymerItem {

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, @Nullable ServerPlayer player) {
        ItemStack stack = PolymerItemUtils.createItemStack(itemStack, player);
        if (itemStack.getItem() instanceof GolemController) {
            stack.getOrCreateTag().put("SkullOwner", PolymerUtils.createSkullOwner(GolemController.skullValues[GolemController.getMode(itemStack)]));
        } else {
            stack.enchant(Enchantments.UNBREAKING, 1);
            stack.hideTooltipPart(ItemStack.TooltipPart.ENCHANTMENTS);
        }
        return stack;
    }
}
