package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MMDeath implements Listener {

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        Player p = (Player) e.getKiller();
        if (p == null) {
            return;
        }
        MythicMob mob = e.getMobType();
        int level = (int) e.getMobLevel();
        int xp = Math.max(File.getconfigfile().getInt("XP-MIN"), File.getconfigfile().getInt("MOBS." + mob + ".XP"));
        PlayerData.get(p).giveExperience(level * xp, EXPSource.OTHER, p.getLocation(), true);
    }
}
