package com.flemmli97.advancedgolems.entity.render;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.entity.EntityGolemGuard;
import com.flemmli97.advancedgolems.init.ModItems;
import com.flemmli97.advancedgolems.items.ItemGolemControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

public class RenderGolem extends RenderBiped<EntityGolemGuard> {

    private static final ResourceLocation textures = new ResourceLocation(AdvancedGolems.MODID + ":textures/entity/golem.png");

    public RenderGolem(RenderManager manager) {
        super(manager, new ModelBiped(0, 0, 64, 64), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
    }

    @Override
    public void doRender(EntityGolemGuard entity, double x, double y, double z, float entityYaw, float partialTicks) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        ItemStack heldItem = player.getHeldItemMainhand();
        if (heldItem != null && heldItem.getItem() == ModItems.golemControl) {
            ItemGolemControl item = (ItemGolemControl) heldItem.getItem();
            if (item.getMetadata(heldItem) == 1 && item.getGolem(heldItem, player.world) == entity) {
                entity.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX, entity.posY + 2.3, entity.posZ, 0, 0, 0);
            }
        }
        this.updateHeldItem(entity);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void updateHeldItem(EntityGolemGuard entity) {
        ModelBiped model = (ModelBiped) this.getMainModel();
        ItemStack main = entity.getHeldItemMainhand();
        ItemStack off = entity.getHeldItemOffhand();
        model.rightArmPose = ModelBiped.ArmPose.EMPTY;
        model.leftArmPose = ModelBiped.ArmPose.EMPTY;
        if (!main.isEmpty()) {
            model.rightArmPose = ModelBiped.ArmPose.ITEM;
            if (entity.getItemInUseCount() > 0) {
                EnumAction enumaction1 = main.getItemUseAction();

                if (enumaction1 == EnumAction.BLOCK) {
                    model.rightArmPose = ModelBiped.ArmPose.BLOCK;
                } else if (enumaction1 == EnumAction.BOW) {
                    model.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }
        if (!off.isEmpty()) {
            model.leftArmPose = ModelBiped.ArmPose.ITEM;
            if (entity.getItemInUseCount() > 0) {
                EnumAction enumaction1 = off.getItemUseAction();

                if (enumaction1 == EnumAction.BLOCK) {
                    model.leftArmPose = ModelBiped.ArmPose.BLOCK;
                } else if (enumaction1 == EnumAction.BOW) {
                    model.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGolemGuard entity) {
        return textures;
    }

}
