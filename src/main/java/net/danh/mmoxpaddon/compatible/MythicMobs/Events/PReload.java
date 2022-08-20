package net.danh.mmoxpaddon.compatible.MythicMobs.Events;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PReload implements Listener {

    @EventHandler
    public void onMythicReload(MythicReloadedEvent e) {
        e.getInstance().getLogger().info("✓ Loaded custom mechanic from MMOXPAddon");
        e.getInstance().getLogger().info("✓ Loaded custom condition from MMOXPAddon");
    }
}
