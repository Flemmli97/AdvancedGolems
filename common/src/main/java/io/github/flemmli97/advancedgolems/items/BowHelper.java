package io.github.flemmli97.advancedgolems.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BowHelper extends BowItem {
    private BowHelper(Properties properties) {
        super(properties);
    }

    public static List<ItemStack> doDraw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        return draw(weapon, ammo, shooter);
    }
}
