package com.yoruthewiz.pastureextractor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private static final Gson GSON =new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "config/pastureextractor.json";

    private int cooldown;
    private float baseDropChance;
    private String[] itemBlacklist;

    private Config() {
        this.cooldown = Defaults.COOLDOWN;
        this.baseDropChance = Defaults.BASE_DROP_CHANCE;
        this.itemBlacklist = Defaults.ITEM_BLACKLIST;
    }

    public static class Defaults {
        public static final int COOLDOWN = 200;
        public static final float BASE_DROP_CHANCE = 0.15f;
        public static final String[] ITEM_BLACKLIST = {};
    }

    public int getCooldown() {
        return cooldown;
    }

    public float getBaseDropChance() {
        return baseDropChance;
    }

    public String[] getItemBlacklist() {
        return itemBlacklist;
    }

    public static Config load() {
        Config config = new Config();
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            config.cooldown = json.get("cooldown").getAsInt();
            config.baseDropChance = json.get("dropChance").getAsFloat();
            config.itemBlacklist = GSON.fromJson(json.get("itemBlacklist"), String[].class);
        } catch (IOException e) {
            config = new Config();
            config.save();
        }
        return config;
    }

    private void save() {
        JsonObject json = new JsonObject();
        json.addProperty("cooldown", cooldown);
        json.addProperty("dropChance", baseDropChance);
        json.add("itemBlacklist", GSON.toJsonTree(itemBlacklist));

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {}
    }
}
