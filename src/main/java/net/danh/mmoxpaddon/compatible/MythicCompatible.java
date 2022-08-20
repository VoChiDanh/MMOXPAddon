package net.danh.mmoxpaddon.compatible;

import net.danh.mmoxpaddon.compatible.MythicMobs.Events.CReload;
import net.danh.mmoxpaddon.compatible.MythicMobs.Events.MReload;
import net.danh.mmoxpaddon.compatible.MythicMobs.Events.PReload;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicCompatible {

    public MythicCompatible(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MReload(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CReload(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PReload(), plugin);
    }

}
