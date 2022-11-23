package io.github.flemmli97.advancedgolems.items;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.jetbrains.annotations.Nullable;

public class GolemBell extends Item {

    public GolemBell(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            for (GolemBase golem : level.getEntities(EntityTypeTest.forClass(GolemBase.class), player.getBoundingBox().inflate(32), e -> true)) {
                golem.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, true, false));
            }
            player.getCooldowns().addCooldown(this, 20);
        }
        player.playSound(SoundEvents.BELL_BLOCK, 0.7f, 1.8f);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayer player) {
        return Items.BELL;
    }
}
