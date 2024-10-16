package io.github.flemmli97.advancedgolems.fabric;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.events.EventCalls;
import io.github.flemmli97.advancedgolems.fabric.config.ConfigSpecs;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.registry.ModDataComponents;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
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
        AdvancedGolems.polymer = FabricLoader.getInstance().isModLoaded("polymer");
        ConfigSpecs.init();
        ModEntities.ENTITIES.registerContent();
        ModItems.ITEMS.registerContent();
        ModDataComponents.DATA_COMPONENTS.registerContent();
        AttackEntityCallback.EVENT.register(AdvancedGolemsFabric::attackCallback);
        AdvancedGolemsFabric.registerAttributes();
        EventCalls.getPopulatedTabs().forEach((tab, cons) -> ItemGroupEvents.modifyEntriesEvent(tab).register(c -> cons.accept(i -> c.accept(i.get()))));
    }

    public static InteractionResult attackCallback(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == ModItems.GOLEM_CONTROLLER.get()) {
            if (((GolemController) stack.getItem()).onLeftClickEntity(stack, player, entity))
                return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.GOLEM.get(), GolemBase.createAttributes());
    }
}
