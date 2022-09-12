package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.danh.mmoxpaddon.Data.API;
import net.danh.mmoxpaddon.Manager.Debug;
import net.danh.mmoxpaddon.Manager.Mobs;
import net.danh.mmoxpaddon.Manager.Version;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class MMDeath implements Listener {
    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        if (e.getKiller() instanceof Player p) {
            if (!p.hasMetadata("NPC")) {
                if (new Version().isPremium().getType()) {
                    if (File.getconfigfile().getStringList("MOBS").contains(e.getMobType().getInternalName())) {
                        Debug.debug("Mob " + e.getMobType().getInternalName() + " in list");
                        Mobs mobs = new Mobs(e.getMobType().getInternalName());
                        if (mobs.getFormulaWithinLimitsWithoutPapi(p) != null) {
                            API.KillMythicMobs(e, p, mobs);
                        } else {
                            Debug.debug("Mob " + e.getMobType().getInternalName() + " in list but not in mobs.yml");
                        }
                    } else {
                        Debug.debug("Mob " + e.getMobType().getInternalName() + " not in list");
                    }
                } else {
                    List<String> mob_list = File.getconfigfile().getStringList("MOBS");
                    String mob_name = e.getMobType().getInternalName();
                    if (mob_list.size() <= 10) {
                        for (int i = 0; i <= 10; i++) {
                            if (mob_list.get(i).equalsIgnoreCase(mob_name)) {
                                API.KillMythicMobs(e, p, new Mobs(mob_name));
                            } else {
                                Debug.debug("You are using Free version, so you just can config 10 mob name in MOBS (config.yml)");
                                Debug.debug("You have configured more than 10 mobs &c(" + mob_list.size() + ")");
                            }
                        }
                    } else {
                        Debug.debug("You are using Free version, so you just can config 10 mob name in MOBS (config.yml)");
                        Debug.debug("You have configured more than 10 mobs &c(" + mob_list.size() + ")");
                    }
                }
            }
        }
    }
}