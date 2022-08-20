package net.danh.mmoxpaddon;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.Command.CMD;
import net.danh.mmoxpaddon.Event.MMDeath;
import net.danh.mmoxpaddon.Manager.Version;
import net.danh.mmoxpaddon.Resource.File;
import net.danh.mmoxpaddon.Compatible.MythicCompatible;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

import static net.danh.dcore.DCore.RegisterDCore;
import static net.danh.dcore.Utils.File.updateFile;

public final class MMOXPAddon extends JavaPlugin {

    private static MMOXPAddon instance;
    private static MythicCompatible mythicCompatible;

    public static MMOXPAddon getInstance() {
        return instance;
    }

    public static MythicCompatible getMythicCompatible() {
        return mythicCompatible;
    }

    public static SERVER_TYPE getServerType() {
        if (hasClass("com.destroystokyo.paper.PaperConfig") || hasClass("io.papermc.paper.configuration.Configuration")) {
            return SERVER_TYPE.PAPER;
        } else if (hasClass("org.spigotmc.SpigotConfig")) {
            return SERVER_TYPE.SPIGOT;
        } else {
            return SERVER_TYPE.BUKKIT;
        }
    }

    private static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        RegisterDCore(this);
        new CMD(this);
        getServer().getPluginManager().registerEvents(new MMDeath(), this);
        if (new Version().isPremium().getType()) {
            if (getServerType().equals(SERVER_TYPE.PAPER)) {
                mythicCompatible = new MythicCompatible(this);
                MythicBukkit.inst().getLogger().info("✓ Loaded custom mechanic from MMOXPAddon");
                MythicBukkit.inst().getLogger().info("✓ Loaded custom condition from MMOXPAddon");
            } else {
                mythicCompatible = null;
                MythicBukkit.inst().getLogger().log(Level.WARNING, "You need use Paper or Paper fork to enable custom mechanic and condition from MMOXPAddon");
            }
        } else {
            mythicCompatible = null;
            MythicBukkit.inst().getLogger().log(Level.WARNING, "Free version doesn't support custom mechanic and condition from MMOXPAddon");
        }
        File.createfiles();
        updateFile(this, File.getconfigfile(), "config.yml");
        if (File.getconfigfile().getBoolean("USE_MANY_FILE")) {
            DCore.dCoreLog("Settings USE_MANY_FILE was removed, edit mob in mobs.yml");
        }
        if (!new Version().isPremium().getType()) {
            getLogger().log(Level.WARNING, "You are using Free version!");
            getLogger().log(Level.WARNING, "Free version limit some features and mob from config");
        }
    }

    @Override
    public void onDisable() {
        File.saveconfig();
        File.savemob();
    }

    public enum SERVER_TYPE {
        PAPER,
        SPIGOT,
        BUKKIT
    }
}