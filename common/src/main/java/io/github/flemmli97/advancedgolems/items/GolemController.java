package io.github.flemmli97.advancedgolems.items;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.config.Config;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.entity.GolemState;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GolemController extends Item {

    public GolemController(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        switch (getMode(itemStack)) {
            case 0 -> list.add(new TranslatableComponent("controller.mode.0").withStyle(ChatFormatting.GOLD));
            case 1 -> list.add(new TranslatableComponent("controller.mode.1").withStyle(ChatFormatting.GOLD));
            case 2 -> list.add(new TranslatableComponent("controller.mode.2").withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        ItemStack stack = useOnContext.getItemInHand();
        if (getMode(stack) == 1 && useOnContext.getLevel() instanceof ServerLevel level) {
            UUID uuid = golemUUID(stack);
            if (uuid != null) {
                Entity e = level.getEntity(uuid);
                if (e instanceof GolemBase golem) {
                    golem.restrictTo(useOnContext.getClickedPos(), Config.homeRadius);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return super.useOn(useOnContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && player.isShiftKeyDown()) {
            ItemStack stack = player.getItemInHand(interactionHand);
            cycleModes(stack);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(level, player, interactionHand);
    }

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        if (target instanceof GolemBase golem && !player.level.isClientSide) {
            int mode = getMode(stack);
            if (mode == 0) {
                if (!player.isCreative()) {
                    golem.onControllerRemove();
                }
                golem.remove(Entity.RemovalReason.KILLED);

            } else if (mode == 1) {
                CompoundTag stackTag = stack.getOrCreateTag();
                CompoundTag tag = stackTag.getCompound(AdvancedGolems.MODID);
                tag.putUUID("SelectedEntity", golem.getUUID());
                stackTag.put(AdvancedGolems.MODID, tag);
            } else if (mode == 2) {
                golem.updateState(GolemState.getNextState(golem.getState()));
                player.sendMessage(new TranslatableComponent("golem.state." + golem.getState()).withStyle(ChatFormatting.GOLD), Util.NIL_UUID);
            }
            return true;
        }
        return false;
    }

    public static int getMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag().getCompound(AdvancedGolems.MODID);
        if (tag != null)
            return tag.getInt("Mode");
        return 0;
    }

    public static UUID golemUUID(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag().getCompound(AdvancedGolems.MODID);
        if (tag.hasUUID("SelectedEntity"))
            return tag.getUUID("SelectedEntity");
        return null;
    }

    private static void cycleModes(ItemStack stack) {
        CompoundTag stackTag = stack.getOrCreateTag();
        CompoundTag tag = stackTag.getCompound(AdvancedGolems.MODID);
        int mode = tag.getInt("Mode");
        mode++;
        if (mode > 2)
            mode = 0;
        tag.putInt("Mode", mode);
        tag.remove("SelectedEntity");
        stackTag.put(AdvancedGolems.MODID, tag);
    }

}
