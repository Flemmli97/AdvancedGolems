package io.github.flemmli97.advancedgolems.fabric;

import io.github.flemmli97.advancedgolems.fabric.config.ConfigSpecs;
import io.github.flemmli97.advancedgolems.fabric.registry.ModEntities;
import io.github.flemmli97.advancedgolems.fabric.registry.ModItems;
import io.github.flemmli97.advancedgolems.items.GolemController;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class AdvancedGolemsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
        ConfigSpecs.init();
        AttackEntityCallback.EVENT.register(AdvancedGolemsFabric::attackCallback);
    }

    public static InteractionResult attackCallback(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == ModItems.golemControl) {
            if (((GolemController) stack.getItem()).onLeftClickEntity(stack, player, entity))
                return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
