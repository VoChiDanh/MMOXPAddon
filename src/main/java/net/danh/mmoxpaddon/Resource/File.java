package net.danh.mmoxpaddon.Resource;

import net.danh.mmoxpaddon.MMOXPAddon;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class File {

    private static java.io.File configFile;
    private static FileConfiguration config;

    public static void createfiles() {
        configFile = new java.io.File(MMOXPAddon.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) MMOXPAddon.getInstance().saveResource("config.yml", false);
        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfigfile() {
        return config;
    }

    public static void reloadfiles() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

}
