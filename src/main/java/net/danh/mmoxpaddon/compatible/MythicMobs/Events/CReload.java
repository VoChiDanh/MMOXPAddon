package net.danh.mmoxpaddon.compatible.MythicMobs.Events;

import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import net.danh.mmoxpaddon.compatible.MythicMobs.Condition;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CReload implements Listener {

    @EventHandler
    public void onMechanicReload(MythicConditionLoadEvent e) {
        if (e.getConditionName().equalsIgnoreCase("mxa-condition")) {
            e.register(new Condition(e.getConfig()));
        }
    }
}
