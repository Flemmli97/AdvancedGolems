package io.github.flemmli97.advancedgolems.items;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.registry.ModDataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
            golem.setOwner(ctx.getPlayer());
            if (stack.getOrDefault(ModDataComponents.SHUTDOWN.get(), false))
                golem.shutDownGolem(true);
            world.addFreshEntity(golem);
            if (!ctx.getPlayer().isCreative()) {
                stack.shrink(1);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static ItemStack withFrozenGolem(ItemStack stack) {
        stack.set(ModDataComponents.SHUTDOWN.get(), true);
        return stack;
    }

    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayer player) {
        return Items.VILLAGER_SPAWN_EGG;
    }
}
