package net.danh.mmoxpaddon.Compatible.MythicMobs.Events;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import net.danh.mmoxpaddon.Compatible.MythicMobs.Mechanics;
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
