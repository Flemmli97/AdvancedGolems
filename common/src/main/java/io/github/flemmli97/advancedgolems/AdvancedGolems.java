package io.github.flemmli97.advancedgolems;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedGolems {

    public static final String MODID = "advancedgolems";
    public static final Logger LOGGER = LogManager.getLogger(AdvancedGolems.MODID);

    public static boolean polymer;

    public static ResourceLocation modRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
