package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RecipeGen extends RecipeProvider {

    public RecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLEM_SPAWNER.get())
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
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLEM_CONTROLLER.get())
                .define('P', Items.PUMPKIN)
                .define('S', Items.PAPER)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .group("golem_controller")
                .unlockedBy("golem_controller", RecipeProvider.has(Items.PUMPKIN))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLEM_BELL.get())
                .define('B', Items.BELL)
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("  B")
                .pattern(" S ")
                .pattern("S  ")
                .group("golem_bell")
                .unlockedBy("golem_bell", RecipeProvider.has(Items.BELL))
                .save(output);
    }
}
