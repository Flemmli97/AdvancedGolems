package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {

    public RecipeGen(DataGenerator arg) {
        super(arg);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.GOLEM_SPAWNER.get())
                .define('L', Items.LAPIS_LAZULI)
                .define('S', Items.IRON_SWORD)
                .define('D', Items.PUMPKIN)
                .define('G', Items.PAPER)
                .define('I', AdvancedGolems.blackDyes)
                .pattern("LIL")
                .pattern("GDG")
                .pattern("LSL")
                .group("golem_spawner")
                .unlockedBy("golem_spawner", RecipeProvider.has(Items.PUMPKIN))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.GOLEM_CONTROLLER.get())
                .define('P', Items.PUMPKIN)
                .define('S', Items.PAPER)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .group("golem_controller")
                .unlockedBy("golem_controller", RecipeProvider.has(Items.PUMPKIN))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.GOLEM_BELL.get())
                .define('B', Items.BELL)
                .define('S', AdvancedGolems.rodsWooden)
                .pattern("  B")
                .pattern(" S ")
                .pattern("S  ")
                .group("golem_bell")
                .unlockedBy("golem_bell", RecipeProvider.has(Items.BELL))
                .save(consumer);
    }
}
