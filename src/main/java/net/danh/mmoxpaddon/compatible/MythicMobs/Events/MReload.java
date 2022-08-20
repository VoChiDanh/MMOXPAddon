package net.danh.mmoxpaddon.compatible.MythicMobs.Events;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import net.danh.mmoxpaddon.compatible.MythicMobs.Mechanics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MReload implements Listener {

    @EventHandler
    public void onMechanicReload(MythicMechanicLoadEvent e) {
        if (e.getMechanicName().equalsIgnoreCase("mxa-mechanic")) {
            e.register(new Mechanics(e.getConfig()));
        }
    }
}
