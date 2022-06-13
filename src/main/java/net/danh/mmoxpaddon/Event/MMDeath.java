package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
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
        BukkitAPIHelper bukkitAPIHelper = new BukkitAPIHelper();
        ActiveMob mobs = bukkitAPIHelper.getMythicMobInstance(event.getEntity());
        int player_level = PlayerData.get(p).getLevel();
        int mob_level_max = File.getconfigfile().getInt("MOBS." + mobs.getType().getInternalName().toUpperCase() + ".LEVEL.MAX");
        int mob_level_min = File.getconfigfile().getInt("MOBS." + mobs.getType().getInternalName().toUpperCase() + ".LEVEL.MIN");
        MythicMob mob = event.getMobType();
        int level = (int) event.getMobLevel();
        int xp = Math.max(File.getconfigfile().getInt("XP-MIN"), File.getconfigfile().getInt("MOBS." + mob + ".XP"));
        if (player_level > mob_level_min && player_level < mob_level_max) {
            PlayerData.get(p).giveExperience(level * xp, EXPSource.OTHER);
        } else {
            String c = (Objects.requireNonNull(File.getconfigfile().getString("FORMULA")).replaceAll("%player_level%", String.valueOf(player_level)).replaceAll("%mob_level%", String.valueOf(level)).replaceAll("%mob_xp%", String.valueOf(xp)));
            String c2 = PlaceholderAPI.setPlaceholders(p, c);
            double formula = Double.parseDouble(Calculator.calculator(c2, -1));
            int xpf = (int) formula;
            PlayerData.get(p).giveExperience(xpf, EXPSource.OTHER);
        }

    }
}
