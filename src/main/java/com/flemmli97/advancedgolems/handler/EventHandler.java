package com.flemmli97.advancedgolems.handler;

import com.flemmli97.advancedgolems.entity.EntityGolemBase;
import com.flemmli97.advancedgolems.init.ModItems;
import com.flemmli97.advancedgolems.items.ItemGolemControl;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void render(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        ItemStack heldItem = player.getHeldItemMainhand();
        if (heldItem != null && heldItem.getItem() == ModItems.golemControl) {
            ItemGolemControl item = (ItemGolemControl) heldItem.getItem();
            if (item.getMetadata(heldItem) == 1 && item.getGolem(heldItem, player.world) != null) {
                EntityGolemBase golem = item.getGolem(heldItem, player.world);
                BlockPos pos = golem.getHomePosition();
                this.renderBlockOutline(player, pos, event.getPartialTicks());
                this.renderArea(player, pos, event.getPartialTicks(), golem.getMaximumHomeDistance());
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    private void renderBlockOutline(EntityPlayerSP player, BlockPos pos, float partialTicks) {
        World world = player.world;
        IBlockState state = world.getBlockState(pos);
        AxisAlignedBB aabb = state.getSelectedBoundingBox(world, pos);
        if (aabb != null) {
            this.renderBoundingBox(aabb, player, partialTicks);
        }
    }

    @SideOnly(value = Side.CLIENT)
    private void renderArea(EntityPlayerSP player, BlockPos pos, float partialTicks, float f) {
        AxisAlignedBB aabb = new AxisAlignedBB(0, 0, 0, 1, 1, 1).grow(f).offset(pos.down());
        this.renderBoundingBox(aabb, player, partialTicks);
    }

    private void renderBoundingBox(AxisAlignedBB aabb, EntityPlayerSP player, float partialTicks) {
        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(2);
        GlStateManager.depthMask(false);
        RenderGlobal.drawSelectionBoundingBox(aabb.grow(0.0020000000949949026D).offset(-d0, -d1, -d2), 1, 0.5F, 0.5F, 1);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
