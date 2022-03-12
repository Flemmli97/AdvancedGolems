package io.github.flemmli97.advancedgolems.fabric.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.fabric.config.ConfigSpecs;
import io.github.flemmli97.tenshilib.common.config.ClothConfigScreenHelper;

import java.util.List;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ClothConfigScreenHelper.configScreenOf(parent, AdvancedGolems.MODID, List.of(ConfigSpecs.getJsonConfig()));
    }
}
