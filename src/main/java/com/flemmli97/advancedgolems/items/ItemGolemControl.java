package com.flemmli97.advancedgolems.items;

import java.util.List;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.entity.EntityGolemBase;
import com.flemmli97.advancedgolems.entity.EnumState;
import com.flemmli97.advancedgolems.init.ModItems;
import com.flemmli97.advancedgolems.proxy.CommonProxy;
import com.flemmli97.advancedgolems.proxy.GolemMsg;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGolemControl extends Item {

    public ItemGolemControl() {
        super();
        this.setUnlocalizedName("golem_control");
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName(new ResourceLocation(AdvancedGolems.MODID, "golem_control"));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        switch (stack.getMetadata()) {
            case 0:
                tooltip.add(TextFormatting.GOLD + "Mode: Remove");
                break;
            case 1:
                tooltip.add(TextFormatting.GOLD + "Mode: Home");
                break;
            case 2:
                tooltip.add(TextFormatting.GOLD + "Mode: Behaviour");
                break;
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).hasTagCompound() && player.getHeldItem(hand).getMetadata() == 1) {
            CommonProxy.fromUUID(player.getHeldItem(hand), world).setHomePosAndDistance(pos, 9);
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.isSneaking()) {
                int meta = itemStack.getMetadata();
                meta++;
                if (meta > 2)
                    meta = 0;
                itemStack.setItemDamage(meta);
                this.resetEntity(itemStack);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
        if (target instanceof EntityGolemBase) {
            EntityGolemBase golem = (EntityGolemBase) target;
            if (stack.getMetadata() == 0) {
                if (!player.world.isRemote) {
                    if (!player.capabilities.isCreativeMode) {
                        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
                            ItemStack itemstack = golem.getItemStackFromSlot(entityequipmentslot);
                            if (itemstack != null)
                                golem.entityDropItem(itemstack, 0.0F);
                        }
                        golem.entityDropItem(new ItemStack(ModItems.golemSpawn), 0.0F);
                    }
                    golem.setDead();
                }
                return true;
            } else if (stack.getMetadata() == 1) {
                if (!player.world.isRemote) {
                    if (!stack.hasTagCompound())
                        stack.setTagCompound(new NBTTagCompound());
                    stack.getTagCompound().setString("SelectedEntity", golem.getCachedUniqueIdString());
                    CommonProxy.sendToPlayer(new GolemMsg(golem), (EntityPlayerMP) player);
                }
                return true;
            } else if (stack.getMetadata() == 2) {
                if (!player.world.isRemote) {
                    golem.setEntityState(EnumState.getNextState(golem.currentState()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + this.messageFromState(golem.currentState())));
                }
                return true;
            }
        }
        return false;
    }

    private String messageFromState(EnumState state) {
        String msg = "";
        switch (state) {
            case AGGRESSIVE:
                msg = "Aggressive";
                break;
            case AGGRESSIVESTAND:
                msg = "Waiting";
                break;
            case PASSIVE:
                msg = "Passive";
                break;
            case PASSIVESTAND:
                msg = "Standing";
                break;
        }
        return msg;
    }

    private void resetEntity(ItemStack stack) {
        if (stack.hasTagCompound()) {
            stack.getTagCompound().removeTag("SelectedEntity");
            if (stack.getTagCompound().hasNoTags())
                stack.setTagCompound(null);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Items.PAPER.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(Items.PAPER.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(Items.PAPER.getRegistryName(), "inventory"));
    }

    public EntityGolemBase getGolem(ItemStack stack, World world) {
        return stack.hasTagCompound() ? CommonProxy.fromUUID(stack, world) : null;
    }

}
