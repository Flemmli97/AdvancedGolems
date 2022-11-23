package io.github.flemmli97.advancedgolems.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.GsonHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lang implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> data = new LinkedHashMap<>();
    private final DataGenerator gen;
    private final String modid;
    private final String locale;

    public Lang(DataGenerator gen) {
        this.gen = gen;
        this.modid = AdvancedGolems.MODID;
        this.locale = "en_us";
    }

    protected void addTranslations() {
        this.add("item.advancedgolems." + PlatformUtils.INSTANCE.items().getIDFrom(ModItems.GOLEM_SPAWNER.get()).getPath(), "Summon Golem");
        this.add("item.advancedgolems." + PlatformUtils.INSTANCE.items().getIDFrom(ModItems.GOLEM_CONTROLLER.get()).getPath(), "Golem Controller");
        this.add("item.advancedgolems." + PlatformUtils.INSTANCE.items().getIDFrom(ModItems.GOLEM_BELL.get()).getPath(), "Golem Bell");

        this.add("controller.mode.0", "Mode: Remove");
        this.add("controller.mode.1", "Mode: Home");
        this.add("controller.mode.2", "Mode: Behaviour");
        this.add("golem.state.AGGRESSIVE", "Golem is now aggressive");
        this.add("golem.state.AGGRESSIVESTAND", "Golem is now waiting");
        this.add("golem.state.PASSIVE", "Golem is now passive");
        this.add("golem.state.PASSIVESTAND", "Golem is now standing");

        this.add("golem.owner.wrong", "You don't own this golem");
        this.add("golem.owner.wrong.owner", "This is %s's golem");

        this.add("config.title.advancedgolems", "Advanced Golems Config");
        this.add(ModEntities.golem.get().getDescriptionId(), "Golem");
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        this.addTranslations();
        if (!this.data.isEmpty())
            this.save(cache, this.gen.getOutputFolder().resolve("assets/" + this.modid + "/lang/" + this.locale + ".json"));
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
