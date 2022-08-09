package net.danh.mmoxpaddon.Resource;

import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Manager.Version;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.logging.Level;

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
        if (File.getconfigfile().getBoolean("USE_MANY_FILE")) {
            DCore.dCoreLog("Settings USE_MANY_FILE was removed, edit mob in mobs.yml");
        }
        if (!new Version().isPremium().getType()) {
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "You are using Free version!");
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "Free version limit some features and mob from config");
        }
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
