package io.github.flemmli97.advancedgolems.fabric.polymer;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import io.github.flemmli97.advancedgolems.items.GolemBell;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({GolemSpawnItem.class, GolemController.class, GolemBell.class})
public abstract class ModPolymerItem implements PolymerItem {

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipFlag context, HolderLookup.Provider lookup, @Nullable ServerPlayer player) {
        ItemStack stack = PolymerItemUtils.createItemStack(itemStack, lookup, player);
        if (itemStack.getItem() instanceof GolemController) {
            stack.set(DataComponents.PROFILE, new ResolvableProfile(PolymerUtils.createSkinGameProfile("skull_dummy", GolemController.skullValues[GolemController.getMode(itemStack).ordinal()])));
        } else {
            stack.enchant(lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING), 1);
            stack.update(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY, e -> e.withTooltip(false));
        }
        return stack;
    }
}
