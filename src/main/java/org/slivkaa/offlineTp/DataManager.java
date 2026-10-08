// Everything in here is credited to Gemini AI unfortunately

package org.slivkaa.offlineTp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataManager {
    private final JavaPlugin plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String filename;

    public DataManager(JavaPlugin plugin, String filename) {
        this.plugin = plugin;
        this.filename = filename;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), filename);
        }
        dataConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaults = plugin.getResource(filename);
        if (defaults != null) {
            dataConfig.setDefaults(YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaults, StandardCharsets.UTF_8)));
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null) return;
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save " + filename + "!");
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), filename);
        }
        if (!configFile.exists()) {
            plugin.saveResource(filename, false);
        }
    }
}
