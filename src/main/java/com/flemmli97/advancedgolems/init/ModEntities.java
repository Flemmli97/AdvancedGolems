package com.flemmli97.advancedgolems.init;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.entity.EntityGolemAngel;
import com.flemmli97.advancedgolems.entity.EntityGolemGuard;
import com.flemmli97.advancedgolems.entity.EntitySpecialArrow;
import com.flemmli97.advancedgolems.entity.render.RenderAngel;
import com.flemmli97.advancedgolems.entity.render.RenderGolem;

import net.minecraft.client.renderer.entity.RenderTippedArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(AdvancedGolems.MODID, "golemGuard"), EntityGolemGuard.class, "golemGuard", 0, AdvancedGolems.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AdvancedGolems.MODID, "golemAngel"), EntityGolemAngel.class, "golemAngel", 1, AdvancedGolems.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AdvancedGolems.MODID, "specialArrow"), EntitySpecialArrow.class, "specialArrow", 2, AdvancedGolems.instance, 64, 10, true);

    }

    @SideOnly(value = Side.CLIENT)
    public static void initRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGolemGuard.class, RenderGolem::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGolemAngel.class, RenderAngel::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpecialArrow.class, RenderTippedArrow::new);
    }

}
