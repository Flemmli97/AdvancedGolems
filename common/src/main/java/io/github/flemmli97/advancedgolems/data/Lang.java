package io.github.flemmli97.advancedgolems.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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
    public void run(HashCache cache) throws IOException {
        this.addTranslations();
        if (!this.data.isEmpty())
            this.save(cache, this.data, this.gen.getOutputFolder().resolve("assets/" + this.modid + "/lang/" + this.locale + ".json"));
    }

    @Override
    public String getName() {
        return "Languages: " + this.locale;
    }

    @SuppressWarnings("deprecation")
    private void save(HashCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object);
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
        String hash = DataProvider.SHA1.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(data);
            }
        }

        cache.putNew(target, hash);
    }

    public void add(String key, String value) {
        if (this.data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }
}
