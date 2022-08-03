package net.danh.mmoxpaddon;

import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.Command.CMD;
import net.danh.mmoxpaddon.Event.MMDeath;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.plugin.java.JavaPlugin;

import static net.danh.dcore.DCore.RegisterDCore;
import static net.danh.dcore.Utils.File.updateFile;

public final class MMOXPAddon extends JavaPlugin {

    private static MMOXPAddon instance;

    public static MMOXPAddon getInstance() {
        return instance;
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
            DCore.dCoreLog("Settings USE_MANY_FILE was removed, edit mob in mobs.yml");
        }
    }

    @Override
    public void onDisable() {
        File.saveconfig();
    }
}