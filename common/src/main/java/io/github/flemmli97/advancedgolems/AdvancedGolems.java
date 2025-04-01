package io.github.flemmli97.advancedgolems;

import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedGolems {

    public static final String MODID = "advancedgolems";
    public static final Logger LOGGER = LogManager.getLogger(AdvancedGolems.MODID);

    public static boolean polymer;

    public static final TagKey<Item> BLACK_DYES = PlatformUtils.INSTANCE.itemTag(new ResourceLocation("c", "black_dyes"));
    public static final TagKey<Item> RODS_WOODEN = PlatformUtils.INSTANCE.itemTag(new ResourceLocation("c", "wooden_rods"));

}
