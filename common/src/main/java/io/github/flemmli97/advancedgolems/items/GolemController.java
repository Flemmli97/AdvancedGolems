package io.github.flemmli97.advancedgolems.items;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.config.Config;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.entity.GolemState;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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
            case 0 -> list.add(Component.translatable("controller.mode.0").withStyle(ChatFormatting.GOLD));
            case 1 -> list.add(Component.translatable("controller.mode.1").withStyle(ChatFormatting.GOLD));
            case 2 -> list.add(Component.translatable("controller.mode.2").withStyle(ChatFormatting.GOLD));
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
                    if (golem.getOwnerUUID() == null || useOnContext.getPlayer() == null || golem.getOwnerUUID().equals(useOnContext.getPlayer().getUUID())) {
                        golem.restrictTo(useOnContext.getClickedPos(), Config.homeRadius);
                        return InteractionResult.CONSUME;
                    }
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
        if (target instanceof GolemBase golem && !player.level().isClientSide) {
            if (golem.getOwnerUUID() == null || golem.getOwnerUUID().equals(player.getUUID())) {
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
                    player.sendSystemMessage(Component.translatable("golem.state." + golem.getState()).withStyle(ChatFormatting.GOLD));
                }
                return true;
            }
            player.sendSystemMessage(Component.translatable("golem.owner.wrong").withStyle(ChatFormatting.DARK_RED));
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (isSelected && AdvancedGolems.polymer && entity.tickCount % 2 == 0 && entity instanceof ServerPlayer player && GolemController.getMode(stack) == 1) {
            UUID uuid = GolemController.golemUUID(stack);
            if (uuid != null) {
                level.getNearbyEntities(GolemBase.class, TargetingConditions.DEFAULT, player, new AABB(-16, -16, -16, 16, 16, 16).move(entity.position()))
                        .forEach(golem -> {
                            if (golem.getUUID().equals(uuid)) {
                                player.serverLevel().sendParticles(player, ParticleTypes.FLAME, true,
                                        golem.getRestrictCenter().getX() + 0.5, golem.getRestrictCenter().getY() + 1.5, golem.getRestrictCenter().getZ() + 0.5,
                                        1, 0, 0, 0, 0);
                            }
                        });
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
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

    public static String[] skullValues = new String[]{
            //Mode 0
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg4MzJjMTQ2NmM4NDFjYzc5ZDVmMTAyOTVkNDY0Mjc5OTY3OTc1YTI0NTFjN2E1MzNjNzk5Njg5NzQwOGJlYSJ9fX0=",
            //Mode 1
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I1NmU0OTA4NWY1NWQ1ZGUyMTVhZmQyNmZjNGYxYWZlOWMzNDMxM2VmZjk4ZTNlNTgyNDVkZWYwNmU1ODU4YyJ9fX0=",
            //Mode 2
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY3NWE5YjdjZTU0Y2YzYmExMjg4YWIxNmQ5MTk1ODM1ODM3NDNmOWQ1NWZlNDJhZDVlODU5NjExOWZlNzU1ZSJ9fX0="
    };

    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayer player) {
        return Items.PLAYER_HEAD;
    }
}
