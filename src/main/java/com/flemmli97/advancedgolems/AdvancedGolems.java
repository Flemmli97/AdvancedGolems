package com.flemmli97.advancedgolems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flemmli97.advancedgolems.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AdvancedGolems.MODID, name = AdvancedGolems.MODNAME, version = AdvancedGolems.VERSION)
public class AdvancedGolems {

    public static final String MODID = "advancedgolems";
    public static final String MODNAME = "Advanced Golems";
    public static final String VERSION = "1.0.0[1.10.2]";
    public static final Logger logger = LogManager.getLogger(AdvancedGolems.MODID);

    @Instance
    public static AdvancedGolems instance = new AdvancedGolems();

    @SidedProxy(clientSide = "com.flemmli97.advancedgolems.proxy.ClientProxy", serverSide = "com.flemmli97.advancedgolems.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
