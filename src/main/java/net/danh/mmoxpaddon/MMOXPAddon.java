package net.danh.mmoxpaddon;

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
    }

    @Override
    public void onDisable() {
        File.saveconfig();
        File.savemob();
    }
}
