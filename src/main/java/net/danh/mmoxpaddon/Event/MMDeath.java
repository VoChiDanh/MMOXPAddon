package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.danh.mmoxpaddon.Data.API;
import net.danh.mmoxpaddon.Manager.Mobs;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MMDeath implements Listener {
    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        if (e.getKiller() instanceof Player p) {
            if (p.hasMetadata("NPC")) {
                return;
            }
            String mob_name = e.getMobType().getInternalName();
            if (File.getconfigfile().getStringList("MOBS").contains(mob_name)) {
                API.KillMythicMobs(e, p, new Mobs(mob_name));
            }
        }
    }
}