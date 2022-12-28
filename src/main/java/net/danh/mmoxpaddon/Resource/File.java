package net.danh.mmoxpaddon.Resource;

import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Manager.Version;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

public class File {
    public static void createFiles() {
        MMOXPAddon.getConfigurationManager().build("", "config.yml");
        MMOXPAddon.getConfigurationManager().build("", "mobs.yml");
    }

    public static FileConfiguration getConfig() {
        return MMOXPAddon.getConfigurationManager().file("", "config.yml");
    }

    public static FileConfiguration getMob() {
        return MMOXPAddon.getConfigurationManager().file("", "mobs.yml");
    }

    public static void reloadFiles() {
        MMOXPAddon.getConfigurationManager().reload("", "config.yml");
        MMOXPAddon.getConfigurationManager().reload("", "mobs.yml");
        if (File.getConfig().getBoolean("USE_MANY_FILE")) {
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "Settings USE_MANY_FILE was removed, edit mob in mobs.yml");
        }
        if (!new Version().isPremium().getType()) {
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "You are using Free version!");
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "Free version limit some features and mob from config");
        }
    }

    public static void saveConfig() {
        MMOXPAddon.getConfigurationManager().save("", "config.yml");
    }

    public static void saveMob() {
        MMOXPAddon.getConfigurationManager().save("", "mobs.yml");
    }

}
