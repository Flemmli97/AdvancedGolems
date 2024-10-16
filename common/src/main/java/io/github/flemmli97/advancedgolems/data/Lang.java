package io.github.flemmli97.advancedgolems.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.util.GsonHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Lang implements DataProvider {

    private final Map<String, String> data = new LinkedHashMap<>();
    private final PackOutput packOutput;
    private final String modid;
    private final String locale;

    public Lang(PackOutput output) {
        this.packOutput = output;
        this.modid = AdvancedGolems.MODID;
        this.locale = "en_us";
    }

    protected void addTranslations() {
        this.add("item.advancedgolems." + BuiltInRegistries.ITEM.getKey(ModItems.GOLEM_SPAWNER.get()).getPath(), "Summon Golem");
        this.add("item.advancedgolems." + BuiltInRegistries.ITEM.getKey(ModItems.GOLEM_CONTROLLER.get()).getPath(), "Golem Controller");
        this.add("item.advancedgolems." + BuiltInRegistries.ITEM.getKey(ModItems.GOLEM_BELL.get()).getPath(), "Golem Bell");

        this.add("controller.mode.remove", "Mode: Remove");
        this.add("controller.mode.home", "Mode: Home");
        this.add("controller.mode.behaviour", "Mode: Behaviour");
        this.add("golem.state.AGGRESSIVE", "Golem is now aggressive");
        this.add("golem.state.AGGRESSIVESTAND", "Golem is now waiting");
        this.add("golem.state.PASSIVE", "Golem is now passive");
        this.add("golem.state.PASSIVESTAND", "Golem is now standing");

        this.add("golem.owner.wrong", "You don't own this golem");
        this.add("golem.owner.wrong.owner", "This is %s's golem");

        this.add("config.title.advancedgolems", "Advanced Golems Config");
        this.add(ModEntities.GOLEM.get().getDescriptionId(), "Golem");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.runAsync(() -> {
            this.addTranslations();
            if (!this.data.isEmpty()) {
                try {
                    this.save(cache, this.packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.modid).resolve("lang").resolve(this.locale + ".json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Languages: " + this.locale;
    }

    private void save(CachedOutput cache, Path target) throws IOException {
        JsonObject json = new JsonObject();
        for (Map.Entry<String, String> pair : this.data.entrySet()) {
            json.addProperty(pair.getKey(), pair.getValue());
        }
        saveTo(cache, json, target);
    }

    public void add(String key, String value) {
        if (this.data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    private static void saveTo(CachedOutput cachedOutput, JsonElement jsonElement, Path path) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);
        OutputStreamWriter writer = new OutputStreamWriter(hashingOutputStream, StandardCharsets.UTF_8);
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setSerializeNulls(false);
        jsonWriter.setIndent("  ");
        GsonHelper.writeValue(jsonWriter, jsonElement, null);
        jsonWriter.close();
        cachedOutput.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }
}
