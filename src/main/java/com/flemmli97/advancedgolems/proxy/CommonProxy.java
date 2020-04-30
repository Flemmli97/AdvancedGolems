package com.flemmli97.advancedgolems.proxy;

import java.io.File;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.entity.EntityGolemBase;
import com.flemmli97.advancedgolems.handler.ConfigHandler;
import com.flemmli97.advancedgolems.handler.EventHandler;
import com.flemmli97.advancedgolems.init.ModEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(AdvancedGolems.MODID);

    public void preInit(FMLPreInitializationEvent e) {
        ModEntities.init();
        ConfigHandler.loadConfig(new Configuration(new File(e.getModConfigurationDirectory(), "advancedgolems.cfg")));
        dispatcher.registerMessage(GolemMsg.Handler.class, GolemMsg.class, 0, Side.CLIENT);
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public static final void sendToPlayer(IMessage message, EntityPlayerMP player) {
        dispatcher.sendTo(message, player);
    }

    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

    public static EntityGolemBase fromUUID(ItemStack stack, World world) {
        if (stack.hasTagCompound())
            for (Entity e : world.loadedEntityList)
                if (e instanceof EntityGolemBase && e.getCachedUniqueIdString().equals(stack.getTagCompound().getString("SelectedEntity")))
                    return (EntityGolemBase) e;
        return null;
    }
}
