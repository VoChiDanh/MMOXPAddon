package net.danh.mmoxpaddon.Data;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.dcore.Calculator.Calculator;
import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Manager.Mobs;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static net.danh.mmoxpaddon.Manager.Debug.debug;

public class API {

    public static void KillMythicMobs(MythicMobDeathEvent e, Player p, Mobs mob) {
        boolean use_formula = mob.useFormula();
        debug("Use Formula: " + use_formula);
        boolean use_limited_xp = mob.useLimitedXP();
        debug("Use limited xp: " + use_limited_xp);
        boolean use_level_end = mob.useLevelEnd();
        debug("Use level end: " + use_level_end);
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
        String formula_out_of_bounds_without_papi_higher = mob.getFormulaOutOfBoundWithoutPapiHigher(p);
        debug("Formula out of bounds higher: " + formula_out_of_bounds_without_papi_higher);
        String formula_out_of_bounds_without_papi_replaced_higher = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaOutOfBoundWithoutPapiHigher(p)).replaceAll("%mob_level%", String.valueOf(e.getMobLevel())).replaceAll("%mob_xp%", String.valueOf(mob.getXPDefault())));
        debug("Formula out of bounds replaced higher: " + formula_out_of_bounds_without_papi_replaced_higher);
        String formula_out_of_bounds_higher = Calculator.calculator(formula_out_of_bounds_without_papi_replaced_higher, 0);
        debug("Formula out of bounds higher = " + formula_out_of_bounds_higher);
        String formula_out_of_bounds_without_papi_lower = mob.getFormulaOutOfBoundWithoutPapiLower(p);
        debug("Formula out of bounds lower: " + formula_out_of_bounds_without_papi_lower);
        String formula_out_of_bounds_without_papi_replaced_lower = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaOutOfBoundWithoutPapiLower(p)).replaceAll("%mob_level%", String.valueOf(e.getMobLevel())).replaceAll("%mob_xp%", String.valueOf(mob.getXPDefault())));
        debug("Formula out of bounds replaced lower: " + formula_out_of_bounds_without_papi_replaced_lower);
        String formula_out_of_bounds_lower = Calculator.calculator(formula_out_of_bounds_without_papi_replaced_lower, 0);
        debug("Formula out of bounds lower = " + formula_out_of_bounds_lower);
        String formula_within = mob.getFormulaWithinLimitsWithoutPapi(p);
        debug("Formula within limits : " + formula_within);
        String formula_within_limits_without_papi_replaced = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaWithinLimitsWithoutPapi(p)).replaceAll("%mob_level%", String.valueOf(e.getMobLevel())).replaceAll("%mob_xp%", String.valueOf(mob.getXPDefault())));
        debug("Formula within limits replaced: " + formula_within_limits_without_papi_replaced);
        String formula_within_limits = Calculator.calculator(formula_within_limits_without_papi_replaced, 0);
        debug("Formula within limits = " + formula_within_limits);
        int xp_default = mob.getXPDefault();
        debug("Xp default: " + xp_default);
        int xp_max = mob.getXPMax();
        debug("Xp max: " + xp_max);
        int level_min = mob.getLevelMin();
        debug("Level min: " + level_min);
        int level_max = mob.getLevelMax();
        debug("Level max: " + level_max);
        int level_end = mob.getLevelEnd();
        debug("Level end: " + level_end);
        int mob_level = (int) e.getMobLevel();
        debug("Mob level: " + mob_level);
        int player_level = PlayerData.get(p).getLevel();
        debug("Player level: " + player_level);
        debug("Custom formula without placeholder");
        for (String formula : mob.getListCustomFormula()) {
            debug(mob.getCustomFormula(formula));
        }
        debug("Custom formula");
        for (String formula : mob.getListCustomFormula()) {
            String replace = mob.getCustomFormula(formula).replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
            String papi = PlaceholderAPI.setPlaceholders(p, replace);
            debug(papi);
        }
        if (use_level_end) {
            if (player_level >= level_end) {
                return;
            }
        }
        if (player_level >= level_min && player_level <= level_max) {
            if (use_command) {
                for (String cmd : cmd_within_limit) {
                    String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String papi = PlaceholderAPI.setPlaceholders(p, replace);
                    for (String custom_formula : mob.getListCustomFormula()) {
                        if (papi.contains("#cf_")) {
                            String custom_formula_replace = mob.getCustomFormula(custom_formula).replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                            String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                            papi = papi.replaceAll("#cf_" + custom_formula + "#", String.valueOf((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))));
                        } else {
                            break;
                        }
                    }
                    String finalPapi = papi;
                    if (finalPapi.contains(";")) {
                        int random = new Random().nextInt(100);
                        String[] sp = finalPapi.split(";");
                        String commands = sp[0];
                        int chance = BigDecimal.valueOf(Long.parseLong(sp[1])).intValue();
                        if (chance > random) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), commands);
                                }
                            }.runTask(MMOXPAddon.getInstance());
                        }
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), finalPapi);
                            }
                        }.runTask(MMOXPAddon.getInstance());
                    }
                }
            }

            if (use_formula) {
                PlayerData.get(p).giveExperience((int) Double.parseDouble(formula_within_limits) * mob_level, EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            } else {
                PlayerData.get(p).giveExperience(xp_default * mob_level, EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
            if (use_limited_xp) {
                PlayerData.get(p).giveExperience(Math.min((int) Double.parseDouble(formula_within_limits) * mob_level, xp_max), EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
        }
        if (player_level < level_min && player_level < level_max) {
            if (use_command) {
                for (String cmd : cmd_out_of_bound) {
                    String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String papi = PlaceholderAPI.setPlaceholders(p, replace);
                    for (String custom_formula : mob.getListCustomFormula()) {
                        if (papi.contains("#cf_")) {
                            String custom_formula_replace = mob.getCustomFormula(custom_formula).replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                            String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                            papi = papi.replaceAll("#cf_" + custom_formula + "#", String.valueOf((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))));
                        } else {
                            break;
                        }
                    }
                    String finalPapi = papi;
                    if (finalPapi.contains(";")) {
                        int random = new Random().nextInt(100);
                        String[] sp = finalPapi.split(";");
                        String commands = sp[0];
                        int chance = BigDecimal.valueOf(Long.parseLong(sp[1])).intValue();
                        if (chance > random) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), commands);
                                }
                            }.runTask(MMOXPAddon.getInstance());
                        }
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), finalPapi);
                            }
                        }.runTask(MMOXPAddon.getInstance());
                    }
                }
            }
            if (use_formula) {
                PlayerData.get(p).giveExperience((int) Double.parseDouble(formula_out_of_bounds_lower) * mob_level, EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
            if (use_limited_xp) {
                PlayerData.get(p).giveExperience(Math.min((int) Double.parseDouble(formula_out_of_bounds_lower) * mob_level, xp_max), EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
        }
        if (player_level > level_max && player_level > level_min) {
            if (use_command) {
                for (String cmd : cmd_out_of_bound) {
                    String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String papi = PlaceholderAPI.setPlaceholders(p, replace);
                    for (String custom_formula : mob.getListCustomFormula()) {
                        if (papi.contains("#cf_")) {
                            String custom_formula_replace = mob.getCustomFormula(custom_formula).replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                            String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                            papi = papi.replaceAll("#cf_" + custom_formula + "#", String.valueOf((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))));
                        } else {
                            break;
                        }
                    }
                    String finalPapi = papi;
                    if (finalPapi.contains(";")) {
                        int random = new Random().nextInt(100);
                        String[] sp = finalPapi.split(";");
                        String commands = sp[0];
                        int chance = BigDecimal.valueOf(Long.parseLong(sp[1])).intValue();
                        if (chance > random) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), commands);
                                }
                            }.runTask(MMOXPAddon.getInstance());
                        }
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), finalPapi);
                            }
                        }.runTask(MMOXPAddon.getInstance());
                    }
                }
            }
            if (use_formula) {
                PlayerData.get(p).giveExperience((int) Double.parseDouble(formula_out_of_bounds_higher) * mob_level, EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
            if (use_limited_xp) {
                PlayerData.get(p).giveExperience(Math.min((int) Double.parseDouble(formula_out_of_bounds_higher) * mob_level, xp_max), EXPSource.SOURCE, e.getEntity().getLocation().add(0, 1.5, 0), true);
            }
        }
    }
}
