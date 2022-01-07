package io.github.flemmli97.advancedgolems.items;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class GolemSpawnItem extends Item {

    public GolemSpawnItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        if (!world.isClientSide) {
            ItemStack stack = ctx.getItemInHand();
            GolemBase golem = new GolemBase(world, ctx.getClickedPos());
            world.addFreshEntity(golem);
            if (!ctx.getPlayer().isCreative()) {
                stack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }
}
