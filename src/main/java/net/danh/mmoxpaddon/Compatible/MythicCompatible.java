package net.danh.mmoxpaddon.Compatible;

import net.danh.mmoxpaddon.Compatible.MythicMobs.Events.CReload;
import net.danh.mmoxpaddon.Compatible.MythicMobs.Events.MReload;
import net.danh.mmoxpaddon.Compatible.MythicMobs.Events.PReload;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicCompatible {

    public MythicCompatible(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MReload(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CReload(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PReload(), plugin);
    }

}
