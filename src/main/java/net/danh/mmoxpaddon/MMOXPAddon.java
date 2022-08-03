package net.danh.mmoxpaddon;

import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.Command.CMD;
import net.danh.mmoxpaddon.Event.MMDeath;
import net.danh.mmoxpaddon.Manager.Mob;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

import static net.danh.dcore.DCore.RegisterDCore;
import static net.danh.dcore.Utils.File.updateFile;

public final class MMOXPAddon extends JavaPlugin {

    public static final String VERSION = MMOXPAddon.getInstance().getDescription().getVersion();
    public static final String DEV_BUILD_VERSION = "Build 36";
    public static final Boolean IS_DEV_BUILD = true;
    public static final Boolean IS_PREMIUM = true;

    private static MMOXPAddon instance;

    public static MMOXPAddon getInstance() {
        return instance;
    }

    public static void loadMobs() {
        DCore.dCoreLog("In next version, settings USE_MANY_FILE will be dropped and you can't use multi file anymore");
        for (String mob_name : File.getconfigfile().getStringList("MOBS")) {
            Mob mob = new Mob(mob_name);
            mob.save();
            mob.load();
        }
        MMOXPAddon.getInstance().getLogger().log(Level.INFO, "Loaded " + net.danh.mmoxpaddon.Resource.File.getconfigfile().getStringList("MOBS").size() + " Mob(s)");
    }

    @Override
    public void onEnable() {
        instance = this;
        RegisterDCore(this);
        new CMD(this);
        getServer().getPluginManager().registerEvents(new MMDeath(), this);
        File.createfiles();
        updateFile(this, File.getconfigfile(), "config.yml");
        if (File.getconfigfile().getBoolean("USE_MANY_FILE")) {
            loadMobs();
        }
    }

    @Override
    public void onDisable() {
        File.saveconfig();
    }
}
