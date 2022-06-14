package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.dcore.Calculator.Calculator;
import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class MMDeath implements Listener {


    private void debug(String msg) {
        if (File.getconfigfile().getBoolean("DEBUG")) {
            DCore.dCoreLog(msg);
        }
    }

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event) {
        if (event.getKiller() instanceof Player p) {
            String mobs = event.getMob().getType().getInternalName();
            int player_level = PlayerData.get(p).getLevel();
            int mob_level_max = File.getmobfile().getInt("MOBS." + mobs + ".LEVEL.MAX");
            int mob_level_min = File.getmobfile().getInt("MOBS." + mobs + ".LEVEL.MIN");
            int level = (int) event.getMobLevel();
            int xp = Math.max(File.getconfigfile().getInt("XP-MIN"), File.getmobfile().getInt("MOBS." + mobs + ".XP"));
            debug("Player level: " + player_level);
            debug("Mob level max: " + mob_level_max);
            debug("Mob level min: " + mob_level_min);
            debug("Mob level: " + level);
            debug("Mob Name: " + mobs);
            debug("XP: " + xp);
            if (player_level >= mob_level_min && player_level <= mob_level_max) {
                String c = (Objects.requireNonNull(File.getconfigfile().getString("FORMULA.WITHIN_LIMITS")).replaceAll("%player_level%", String.valueOf(player_level)).replaceAll("%mob_level%", String.valueOf(level)).replaceAll("%mob_xp%", String.valueOf(xp)));
                String c2 = PlaceholderAPI.setPlaceholders(p, c);
                debug("Formula:" + c2);
                double formula = Double.parseDouble(Calculator.calculator(c2, -1));
                debug("Final XP:" + formula);
                int xpf = (int) formula;
                debug("XP give: " + xpf);
                PlayerData.get(p).giveExperience(level * xpf, EXPSource.SOURCE, event.getEntity().getLocation().add(0, 1.5, 0), true);
            } else {
                String c = (Objects.requireNonNull(File.getconfigfile().getString("FORMULA.OUT_OF_BOUNDS")).replaceAll("%player_level%", String.valueOf(player_level)).replaceAll("%mob_level%", String.valueOf(level)).replaceAll("%mob_xp%", String.valueOf(xp)));
                String c2 = PlaceholderAPI.setPlaceholders(p, c);
                debug("Formula:" + c2);
                double formula = Double.parseDouble(Calculator.calculator(c2, 0));
                debug("Final XP:" + formula);
                int xpf = (int) formula;
                debug("XP give: " + xpf);
                PlayerData.get(p).giveExperience(xpf, EXPSource.SOURCE, event.getEntity().getLocation().add(0, 1.5, 0), true);
            }
        }
    }
}
