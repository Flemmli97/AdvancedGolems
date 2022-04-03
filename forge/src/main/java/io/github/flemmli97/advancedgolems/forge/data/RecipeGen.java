package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {

    public RecipeGen(DataGenerator arg) {
        super(arg);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.golemSpawn.get())
                .define('L', Items.LAPIS_LAZULI)
                .define('S', Items.IRON_SWORD)
                .define('D', Items.PUMPKIN)
                .define('G', Items.PAPER)
                .define('I', Tags.Items.DYES_BLACK)
                .pattern("LIL")
                .pattern("GDG")
                .pattern("LSL")
                .group("golem_spawner")
                .unlockedBy("golem_spawner", RecipeProvider.has(Items.PUMPKIN))
                .save(consumer, new ResourceLocation(AdvancedGolems.MODID, "golem_spawner_alt"));
        ShapedRecipeBuilder.shaped(ModItems.golemSpawn.get())
                .define('L', Items.LAPIS_LAZULI)
                .define('S', Items.IRON_SWORD)
                .define('D', Items.PUMPKIN)
                .define('G', Items.PAPER)
                .define('I', Items.BLACK_DYE)
                .pattern("LIL")
                .pattern("GDG")
                .pattern("LSL")
                .group("golem_spawner")
                .unlockedBy("golem_spawner", RecipeProvider.has(Items.PUMPKIN))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.golemControl.get())
                .define('P', Items.PUMPKIN)
                .define('S', Items.PAPER)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .group("golem_controller")
                .unlockedBy("golem_controller", RecipeProvider.has(Items.PUMPKIN))
                .save(consumer);
    }
}
