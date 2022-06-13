package net.danh.mmoxpaddon.Resource;

import net.danh.mmoxpaddon.MMOXPAddon;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class File {

    private static java.io.File configFile, mobFile;
    private static FileConfiguration config, mob;

    public static void createfiles() {
        configFile = new java.io.File(MMOXPAddon.getInstance().getDataFolder(), "config.yml");
        mobFile = new java.io.File(MMOXPAddon.getInstance().getDataFolder(), "mobs.yml");
        if (!configFile.exists()) MMOXPAddon.getInstance().saveResource("config.yml", false);
        if (!mobFile.exists()) MMOXPAddon.getInstance().saveResource("mobs.yml", false);
        config = new YamlConfiguration();
        mob = new YamlConfiguration();

        try {
            config.load(configFile);
            mob.load(mobFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfigfile() {
        return config;
    }

    public static FileConfiguration getmobfile() {
        return mob;
    }

    public static void reloadfiles() {
        config = YamlConfiguration.loadConfiguration(configFile);
        mob = YamlConfiguration.loadConfiguration(mobFile);
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

    public static void savemob() {
        try {
            mob.save(mobFile);
        } catch (IOException ignored) {
        }
    }

}
