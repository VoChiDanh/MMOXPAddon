package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.danh.mmoxpaddon.Data.API;
import net.danh.mmoxpaddon.Manager.Debug;
import net.danh.mmoxpaddon.Manager.Mobs;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MMDeath implements Listener {
    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        if (e.getKiller() instanceof Player p) {
            if (!p.hasMetadata("NPC")) {
                if (File.getconfigfile().getStringList("MOBS").contains(e.getMobType().getInternalName())) {
                    Debug.debug("Mob " + e.getMobType().getInternalName() + " in list");
                    API.KillMythicMobs(e, p, new Mobs(e.getMobType().getInternalName()));
                } else {
                    Debug.debug("Mob " + e.getMobType().getInternalName() + " not in list");
                }
            }
        }
    }
}