package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.dcore.Calculator.Calculator;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class MMDeath implements Listener {

    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event) {
        Player p = (Player) event.getKiller();
        if (p == null) {
            return;
        }
        String mobs = event.getMob().getType().getInternalName();
        int player_level = PlayerData.get(p).getLevel();
        int mob_level_max = File.getmobfile().getInt("MOBS." + mobs + ".LEVEL.MAX");
        int mob_level_min = File.getmobfile().getInt("MOBS." + mobs + ".LEVEL.MIN");
        int level = (int) event.getMobLevel();
        int xp = Math.max(File.getconfigfile().getInt("XP-MIN"), File.getmobfile().getInt("MOBS." + mobs + ".XP"));
        if (player_level > mob_level_min && player_level <= mob_level_max) {
            PlayerData.get(p).giveExperience(level * xp, EXPSource.SOURCE);
        } else {
            String c = (Objects.requireNonNull(File.getconfigfile().getString("FORMULA")).replaceAll("%player_level%", String.valueOf(player_level)).replaceAll("%mob_level%", String.valueOf(level)).replaceAll("%mob_xp%", String.valueOf(xp)));
            String c2 = PlaceholderAPI.setPlaceholders(p, c);
            double formula = Double.parseDouble(Calculator.calculator(c2, -1));
            int xpf = (int) formula;
            PlayerData.get(p).giveExperience(xpf, EXPSource.SOURCE);
        }

    }
}
