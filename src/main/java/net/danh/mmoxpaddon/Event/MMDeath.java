package net.danh.mmoxpaddon.Event;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.dcore.Calculator.Calculator;
import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Manager.Mob;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static net.danh.mmoxpaddon.Manager.Debug.debug;

public class MMDeath implements Listener {
    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent e) {
        if (e.getKiller() instanceof Player p) {
            String mob_name = e.getMobType().getInternalName();
            if (File.getconfigfile().getStringList("MOBS").contains(mob_name)) {
                Mob mob = new Mob(mob_name);
                boolean use_formula = mob.useFormula();
                debug("Use Formula: " + use_formula);
                boolean use_limited_xp = mob.useLimitedXP();
                debug("Use limited xp: " + use_limited_xp);
                boolean use_command = mob.useCommand();
                debug("Use command: " + use_command);
                List<String> cmd_within_limit = mob.getCommandsWithinLimits();
                debug("Command within limits");
                for (String debug_cmd : cmd_within_limit) {
                    debug(debug_cmd);
                }
                List<String> cmd_out_of_bound = mob.getCommandsOutOfBound();
                debug("Command out of bound");
                for (String debug_cmd : cmd_out_of_bound) {
                    debug(debug_cmd);
                }
                String formula_out_of_bounds_without_papi = PlaceholderAPI.setPlaceholders(p, mob.getFormulaOutOfBoundWithoutPapi(p).replaceAll("%mob_level%", String.valueOf(e.getMobLevel())).replaceAll("%mob_xp%", String.valueOf(mob.getXPDefault())));
                debug("Formula out of bounds: " + formula_out_of_bounds_without_papi);
                String formula_out_of_bounds = Calculator.calculator(formula_out_of_bounds_without_papi, 0);
                debug("Formula of of bounds = " + formula_out_of_bounds);
                String formula_within_limits_without_papi = PlaceholderAPI.setPlaceholders(p, mob.getFormulaWithinLimitsWithoutPapi(p).replaceAll("%mob_level%", String.valueOf(e.getMobLevel())).replaceAll("%mob_xp%", String.valueOf(mob.getXPDefault())));
                debug("Formula out of bounds: " + formula_within_limits_without_papi);
                String formula_within_limits = Calculator.calculator(formula_within_limits_without_papi, 0);
                debug("Formula of of bounds = " + formula_within_limits);
                int xp_default = mob.getXPDefault();
                debug("Xp default: " + xp_default);
                int xp_max = mob.getXPMax();
                debug("Xp max: " + xp_max);
                int level_min = mob.getLevelMin();
                debug("Level min: " + level_min);
                int level_max = mob.getLevelMax();
                debug("Level max: " + level_max);
                int mob_level = (int) e.getMobLevel();
                debug("Mob level: " + mob_level);
                int player_level = PlayerData.get(p).getLevel();
                debug("Player level: " + player_level);
                if (player_level >= level_min && player_level <= level_max) {
                    if (use_command) {
                        for (String cmd : cmd_within_limit) {
                            String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                            String papi = PlaceholderAPI.setPlaceholders(p, replace);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), papi);
                                }
                            }.runTask(MMOXPAddon.getInstance());
                        }
                    }
                    if (use_formula) {
                        PlayerData.get(p).giveExperience((int) Double.parseDouble(formula_within_limits) * mob_level, EXPSource.SOURCE, p.getLocation().add(0, 2, 0), true);
                    } else {
                        PlayerData.get(p).giveExperience(xp_default * mob_level, EXPSource.SOURCE, p.getLocation().add(0, 2, 0), true);
                    }
                    if (use_limited_xp) {
                        PlayerData.get(p).giveExperience(Math.min((int) Double.parseDouble(formula_within_limits) * mob_level, xp_max), EXPSource.SOURCE, p.getLocation().add(0, 2, 0), true);
                    }
                } else {
                    if (use_command) {
                        for (String cmd : cmd_out_of_bound) {
                            String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                            String papi = PlaceholderAPI.setPlaceholders(p, replace);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), papi);
                                }
                            }.runTask(MMOXPAddon.getInstance());
                        }
                    }
                    if (use_formula) {
                        PlayerData.get(p).giveExperience(Integer.parseInt(formula_out_of_bounds) * mob_level, EXPSource.SOURCE, p.getLocation().add(0, 2, 0), true);
                    }
                    if (use_limited_xp) {
                        PlayerData.get(p).giveExperience(Math.min(Integer.parseInt(formula_out_of_bounds) * mob_level, xp_max), EXPSource.SOURCE, p.getLocation().add(0, 2, 0), true);
                    }
                }
            }
        }
    }
}
