package net.danh.mmoxpaddon;

import net.danh.mmoxpaddon.Command.CMD;
import net.danh.mmoxpaddon.Event.Damage;
import net.danh.mmoxpaddon.Event.MMDeath;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.plugin.java.JavaPlugin;

import static net.danh.dcore.DCore.RegisterDCore;

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
        getServer().getPluginManager().registerEvents(new Damage(), this);
        File.createfiles();
    }

    @Override
    public void onDisable() {
        File.saveconfig();
    }
}
