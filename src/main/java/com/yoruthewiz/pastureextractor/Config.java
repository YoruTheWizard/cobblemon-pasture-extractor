package com.yoruthewiz.pastureextractor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final Gson GSON =new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("pastureextractor.json");

    private Map<String, Float> tierChances = new HashMap<>();
    private int cooldown;
    private String[] itemBlacklist;
    private boolean ignoreFriendship;

    private Config() {
        this.tierChances = Defaults.TIER_CHANCES;
        this.cooldown = Defaults.COOLDOWN;
        this.itemBlacklist = Defaults.ITEM_BLACKLIST;
        this.ignoreFriendship = Defaults.IGNORE_FRIENDSHIP;
    }

    public static class Defaults {
        public static final Map<String, Float> TIER_CHANCES = new HashMap<>();
        static {
            TIER_CHANCES.put("iron", 0.15F);
            TIER_CHANCES.put("gold", 0.3F);
            TIER_CHANCES.put("diamond", 0.5F);
        }
        public static final int COOLDOWN = 200;
        public static final String[] ITEM_BLACKLIST = {};
        public static final boolean IGNORE_FRIENDSHIP = false;
    }

    public float getTierChance(String tier) {
        return tierChances.get(tier);
    }

    public int getCooldown() {
        return cooldown;
    }

    public String[] getItemBlacklist() {
        return itemBlacklist;
    }

    public boolean isBlacklisted(String item) {
        for (String s : itemBlacklist)
            if (s.equals(item)) return true;
        return false;
    }

    public boolean isIgnoreFriendship() {
        return ignoreFriendship;
    }

    public static Config load() {
        Config config = new Config();
        try {
            if (!Files.exists(CONFIG_PATH)) {
                config.save();
                return config;
            }
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                JsonObject baseDropChances = json.getAsJsonObject("baseDropChances");
                for (var entry : baseDropChances.entrySet()) {
                    config.tierChances.put(entry.getKey(), entry.getValue().getAsFloat());
                }

                config.cooldown = json.get("extractorCooldown").getAsInt();
                config.itemBlacklist = GSON.fromJson(json.get("itemBlacklist"), String[].class);
                config.ignoreFriendship = json.get("ignoreFriendship").getAsBoolean();
            }
        } catch (IOException e) {
            config = new Config();
            config.save();
        }
        return config;
    }

    private void save() {
        try {
            JsonObject json = new JsonObject();
            json.add("baseDropChances", GSON.toJsonTree(tierChances));
            json.addProperty("extractorCooldown", cooldown);
            json.add("itemBlacklist", GSON.toJsonTree(itemBlacklist));
            json.addProperty("ignoreFriendship", ignoreFriendship);

            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {}
    }
}
