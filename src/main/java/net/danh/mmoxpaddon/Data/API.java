package net.danh.mmoxpaddon.Data;

import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.danh.mcoreaddon.booster.Boosters;
import net.danh.mcoreaddon.mythicdrop.MythicXP;
import net.danh.mmoxpaddon.API.Calculator.Calculator;
import net.danh.mmoxpaddon.API.Version.Status;
import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Manager.Mobs;
import net.danh.mmoxpaddon.Manager.Version;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static net.danh.mmoxpaddon.Manager.Debug.debug;

public class API {

    public static void executeCMDWCondition(Player p, List<String> list_commands, int mob_level, int xp_default, Mobs mob) {
        for (String cmd : list_commands) {
            String replace = cmd.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
            String papi = PlaceholderAPI.setPlaceholders(p, replace);
            for (String custom_formula : mob.getListCustomFormula()) {
                if (papi.contains("#cf_")) {
                    String custom_formula_replace = mob.getCustomFormula(custom_formula).replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                    papi = papi.replaceAll("#cf_" + custom_formula + "#", String.valueOf((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))));
                }
            }
            String finalPapi = papi;
            if (finalPapi.contains(";")) {
                int random = new Random().nextInt(100);
                String[] sp = finalPapi.split(";");
                if (sp.length == 2) {
                    String commands = sp[0];
                    int chance = BigDecimal.valueOf(Double.parseDouble(sp[1].replaceAll(" ", ""))).intValue();
                    if (chance >= 100 || chance > random) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), commands);
                            }
                        }.runTask(MMOXPAddon.getInstance());
                    }
                }
                if (mob.notnullConditions()) {
                    if (sp.length == 3) {
                        String cc_c = mob.getConditions(sp[0]);
                        if (cc_c != null) {
                            boolean status = checkCondition(p, mob, cc_c, xp_default, mob_level);
                            if (status) {
                                String commands = sp[1];
                                int chance = BigDecimal.valueOf(Double.parseDouble(sp[2].replaceAll(" ", ""))).intValue();
                                if (chance >= 100 || chance > random) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            MMOXPAddon.getInstance().getServer().dispatchCommand(MMOXPAddon.getInstance().getServer().getConsoleSender(), commands);
                                        }
                                    }.runTask(MMOXPAddon.getInstance());
                                }
                            }
                        }
                    }
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

    private static boolean isStatus(String c2, int c1, int c3) {
        boolean status = false;
        if (c2.equalsIgnoreCase(">=")) {
            status = c1 >= c3;
        }
        if (c2.equalsIgnoreCase(">")) {
            status = c1 > c3;
        }
        if (c2.equalsIgnoreCase("<=")) {
            status = c1 <= c3;
        }
        if (c2.equalsIgnoreCase("<")) {
            status = c1 < c3;
        }
        return status;
    }

    public static boolean checkCondition(Player p, Mobs mob, String condition, int xp_default, int mob_level) {
        debug(p.getDisplayName() + " " + condition + " " + xp_default + " " + mob_level);
        String[] condition_split = condition.split(";");
        String placeholder = condition_split[0];
        String compare_type = condition_split[1];
        String result = condition_split[2];
        String compare = condition_split[3];
        if (condition_split.length == 4) {
            if (compare.equalsIgnoreCase("NUMBER")) {
                int c1 = BigDecimal.valueOf((Math.abs(Double.parseDouble(Calculator.calculator(placeholder, 0))))).intValue();
                int c3 = BigDecimal.valueOf((Math.abs(Double.parseDouble(Calculator.calculator(result, 0))))).intValue();
                if (compare_type.equalsIgnoreCase(">=")) {
                    return c1 >= c3;
                }
                if (compare_type.equalsIgnoreCase(">")) {
                    return c1 > c3;
                }
                if (compare_type.equalsIgnoreCase("<=")) {
                    return c1 <= c3;
                }
                if (compare_type.equalsIgnoreCase("<")) {
                    return c1 < c3;
                }
                if (compare_type.equalsIgnoreCase("==")) {
                    return c1 == c3;
                }
            }
            if (compare.equalsIgnoreCase("BOOLEAN")) {
                boolean c1 = Boolean.parseBoolean(placeholder);
                boolean c3 = Boolean.parseBoolean(result);
                return c1 && c3;
            }
            if (compare.equalsIgnoreCase("STRING")) {
                String c1 = String.valueOf(placeholder);
                String c3 = String.valueOf(result);
                return c1.equalsIgnoreCase(c3);
            }
            if (compare.equalsIgnoreCase("COMPARE_CONDITIONS")) {
                String placeholder_s = mob.getConditions(placeholder);
                String compare_type_s = mob.getConditions(compare_type);
                String result_s = mob.getConditions(result);
                if (placeholder_s != null && compare_type_s != null) {
                    String placeholder_condition = placeholder_s.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String compare_type_condition = compare_type_s.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    String result_condition;
                    if (result_s != null) {
                        result_condition = result_s.replaceAll("%mob_level%", String.valueOf(mob_level)).replaceAll("%mob_xp%", String.valueOf(xp_default));
                    } else {
                        result_condition = result;
                    }
                    String papi_conditions = PlaceholderAPI.setPlaceholders(p, placeholder_condition);
                    String compare_type_conditions = PlaceholderAPI.setPlaceholders(p, compare_type_condition);
                    String re_conditions = PlaceholderAPI.setPlaceholders(p, result_condition);
                    if (papi_conditions.contains(";") && compare_type_conditions.contains(";")) {
                        String[] pp_c = papi_conditions.split(";");
                        String[] ct_c = compare_type_conditions.split(";");
                        if (re_conditions.contains(";")) {
                            String[] r_c = re_conditions.split(";");
                            if (pp_c.length == 4 && ct_c.length == 4 && r_c.length == 4) {
                                boolean placeholder_condition_checked = checkCondition(p, mob, papi_conditions, xp_default, mob_level);
                                boolean compare_type_condition_checked = checkCondition(p, mob, compare_type_conditions, xp_default, mob_level);
                                boolean result_condition_checked = checkCondition(p, mob, re_conditions, xp_default, mob_level);
                                debug(condition + " " + placeholder_condition_checked + " " + compare_type_condition_checked + " " + result_condition_checked);
                                return (placeholder_condition_checked && compare_type_condition_checked && result_condition_checked) || (!placeholder_condition_checked && !compare_type_condition_checked && !result_condition_checked);
                            }
                        } else {
                            if (pp_c.length == 4 && ct_c.length == 4) {
                                boolean placeholder_condition_checked = checkCondition(p, mob, papi_conditions, xp_default, mob_level);
                                boolean compare_type_condition_checked = checkCondition(p, mob, compare_type_conditions, xp_default, mob_level);
                                boolean result_condition_checked = Boolean.parseBoolean(result);
                                debug(condition + " " + placeholder_condition_checked + " " + compare_type_condition_checked + " " + result_condition_checked);
                                return (placeholder_condition_checked && compare_type_condition_checked && result_condition_checked) || (!placeholder_condition_checked && !compare_type_condition_checked && !result_condition_checked);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void giveEXP(Player p, Mobs mob, int xp_default, int mob_level, String formula_without_papi_replaced, Location location) {
        for (String custom_formula : mob.getListCustomFormula()) {
            if (formula_without_papi_replaced.contains("#cf_")) {
                String custom_formula_replace = mob.getCustomFormula(custom_formula).replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
                String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                formula_without_papi_replaced = formula_without_papi_replaced
                        .replace("#cf_" + custom_formula + "#", String.valueOf(
                                Math.abs((int) Double.parseDouble(
                                        Calculator.calculator(
                                                custom_formula_papi
                                                , 0)))));
            }
        }
        if (mob.notnullConditions() && formula_without_papi_replaced.contains(";")) {
            String[] strings = formula_without_papi_replaced.split(";");
            if (strings.length == 3) {
                String cc_c = mob.getConditions(strings[0]);
                if (cc_c != null) {
                    String conditions = cc_c.replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
                    String papi_conditions = PlaceholderAPI.setPlaceholders(p, conditions);
                    String[] pp_c = papi_conditions.split(";");
                    if (pp_c.length == 4) {
                        boolean status = checkCondition(p, mob, papi_conditions, xp_default, mob_level);
                        debug(String.valueOf(status));
                        String in = strings[1];
                        String out = strings[2];
                        for (String custom_formula : mob.getListCustomFormula()) {
                            debug(custom_formula.toUpperCase());
                            debug(in);
                            if (in.contains("#cf_")) {
                                debug(in);
                                String custom_formula_replace = mob.getCustomFormula(custom_formula.toUpperCase()).replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
                                debug(custom_formula_replace);
                                String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                                debug(custom_formula_papi);
                                in = in.replace("#cf_" + custom_formula.toUpperCase() + "#", String.valueOf((Math.abs((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))))));
                                debug(in);
                            }
                            debug(out);
                            if (out.contains("#cf_")) {
                                debug(out);
                                String custom_formula_replace = mob.getCustomFormula(custom_formula.toUpperCase()).replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
                                debug(custom_formula_replace);
                                String custom_formula_papi = PlaceholderAPI.setPlaceholders(p, custom_formula_replace);
                                debug(custom_formula_papi);
                                out = out.replace("#cf_" + custom_formula.toUpperCase() + "#", String.valueOf((Math.abs((int) Double.parseDouble(Calculator.calculator(custom_formula_papi, 0))))));
                                debug(out);
                            }
                        }
                        debug("Formula (in) = " + in);
                        debug("Formula (out) = " + out);
                        if (status) {
                            giveExperience(p, BigDecimal.valueOf((Math.abs(Double.parseDouble(in)))).intValue(), location.add(0, 1.5, 0));
                        } else {
                            giveExperience(p, BigDecimal.valueOf((Math.abs(Double.parseDouble(out)))).intValue(), location.add(0, 1.5, 0));
                        }
                    }
                }
            }
        } else {
            String formula_within_limits = Calculator.calculator(formula_without_papi_replaced, 0);
            debug("Formula = " + formula_within_limits);
            giveExperience(p, (Math.abs((int) Double.parseDouble(formula_within_limits))), location.add(0, 1.5, 0));
        }
    }

    public static String isRegisteredMythicCompatible() {
        if (MMOXPAddon.getMythicCompatible() != null) {
            return Status.TRUE.getSymbol();
        } else {
            return Status.FALSE.getSymbol();
        }
    }

    @Nullable
    public static String checkMobs(String InternalName) {
        if (File.getMob().contains(InternalName)) {
            if (File.getMob().getString(InternalName + ".same_as") != null) {
                return File.getMob().getString(InternalName + ".same_as");
            } else return InternalName;
        } else return null;
    }

    public static void KillMythicMobs(Player p, Mobs mob, Integer mob_level, Location location) {
        debug("Mob level: " + mob_level);
        int xp_default = (int) Math.abs(Double.parseDouble(Calculator.calculator(PlaceholderAPI.setPlaceholders(p, mob.getXPDefault().replace("%mob_level%", String.valueOf(mob_level))), 0)));
        debug("Xp default: " + xp_default);
        int level_min = (int) Math.abs(Double.parseDouble(Calculator.calculator(PlaceholderAPI.setPlaceholders(p, mob.getLevelMin().replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))), 0)));
        debug("Level min: " + level_min);
        int level_max = (int) Math.abs(Double.parseDouble(Calculator.calculator(PlaceholderAPI.setPlaceholders(p, mob.getLevelMax().replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))), 0)));
        debug("Level max: " + level_max);
        int level_end = (int) Math.abs(Double.parseDouble(Calculator.calculator(PlaceholderAPI.setPlaceholders(p, mob.getLevelEnd().replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))), 0)));
        debug("Level end: " + level_end);
        boolean use_formula = mob.useFormula();
        debug("Use Formula: " + use_formula);
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
        List<String> formula_out_of_bounds_without_papi_higher = mob.getFormulaOutOfBoundWithoutPapiHigher(p);
        debug("Formula out of bounds higher: " + formula_out_of_bounds_without_papi_higher);
        List<String> formula_out_of_bounds_without_papi_replaced_higher = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaOutOfBoundWithoutPapiHigher(p)).stream().map(s -> s.replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))).collect(Collectors.toList()));
                /*.replace("%mob_level%", String.valueOf(mob_level))
                .replace("%mob_xp%", String.valueOf(mob.getXPDefault())));*/
        debug("Formula out of bounds replaced higher: " + formula_out_of_bounds_without_papi_replaced_higher);
        List<String> formula_out_of_bounds_without_papi_lower = mob.getFormulaOutOfBoundWithoutPapiLower(p);
        debug("Formula out of bounds lower: " + formula_out_of_bounds_without_papi_lower);
        List<String> formula_out_of_bounds_without_papi_replaced_lower = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaOutOfBoundWithoutPapiLower(p)).stream().map(s -> s.replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))).collect(Collectors.toList()));
        debug("Formula out of bounds replaced lower: " + formula_out_of_bounds_without_papi_replaced_lower);
        List<String> formula_within = mob.getFormulaWithinLimitsWithoutPapi(p);
        debug("Formula within limits : " + formula_within);
        List<String> formula_within_limits_without_papi_replaced = PlaceholderAPI.setPlaceholders(p, Objects.requireNonNull(mob.getFormulaWithinLimitsWithoutPapi(p)).stream().map(s -> s.replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(mob.getXPDefault()))).collect(Collectors.toList()));
        debug("Formula within limits replaced: " + formula_within_limits_without_papi_replaced);
        int player_level = PlayerData.get(p).getLevel();
        debug("Player level: " + player_level);
        debug("Custom formula without placeholder");
        for (String formula : mob.getListCustomFormula()) {
            debug(mob.getCustomFormula(formula));
        }
        debug("Custom formula");
        for (String formula : mob.getListCustomFormula()) {
            String replace = mob.getCustomFormula(formula).replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
            String papi = PlaceholderAPI.setPlaceholders(p, replace);
            debug(papi);
        }
        if (mob.notnullConditions()) {
            debug("Custom Conditions without placeholder");
            for (String conditions : mob.getListConditions()) {
                debug(mob.getConditions(conditions));
            }
            debug("Conditions");
            for (String conditions : mob.getListConditions()) {
                String cc_c = mob.getConditions(conditions);
                if (cc_c != null) {
                    String cdt = cc_c.replace("%mob_level%", String.valueOf(mob_level)).replace("%mob_xp%", String.valueOf(xp_default));
                    String papi = PlaceholderAPI.setPlaceholders(p, cdt);
                    debug(papi);
                }
            }
        }
        if (use_level_end) {
            if (player_level >= level_end) {
                if (new Version().isPremium().getType()) {
                    return;
                } else {
                    debug("Only premium can use LEVEL_END feature");
                }
            }
        }

        if (player_level >= level_min && player_level <= level_max) {
            if (use_command) {
                executeCMDWCondition(p, cmd_within_limit, mob_level, xp_default, mob);
            }
            if (use_formula) {
                formula_within_limits_without_papi_replaced.forEach(s -> giveEXP(p, mob, xp_default, mob_level, s, location));
            }
            if (!use_formula) {
                giveExperience(p, xp_default, location.add(0, 1.5, 0));
            }
        }
        if (new Version().isPremium().getType()) {
            if (player_level < level_min && player_level < level_max) {
                if (use_command) {
                    executeCMDWCondition(p, cmd_out_of_bound, mob_level, xp_default, mob);
                }
                if (use_formula) {
                    formula_out_of_bounds_without_papi_replaced_lower.forEach(s -> giveEXP(p, mob, xp_default, mob_level, s, location));
                }
                if (!use_formula) {
                    giveExperience(p, xp_default, location.add(0, 1.5, 0));
                }
            }
            if (player_level > level_max && player_level > level_min) {
                if (use_command) {
                    executeCMDWCondition(p, cmd_out_of_bound, mob_level, xp_default, mob);
                }
                if (use_formula) {
                    formula_out_of_bounds_without_papi_replaced_higher.forEach(s -> giveEXP(p, mob, xp_default, mob_level, s, location));
                }
                if (!use_formula) {
                    giveExperience(p, xp_default, location.add(0, 1.5, 0));
                }
            }
        } else {
            debug("Only premium version can use OUT_OF_BOUNDS features");
        }
    }

    private static void giveExperience(Player p, int xp, Location location) {
        if (MMOXPAddon.getInstance().getServer().getPluginManager().getPlugin("MCoreAddon") != null) {
            if (File.getConfig().getString("BOOSTER_OPTIONS") != null) {
                if (Objects.requireNonNull(File.getConfig().getString("BOOSTER_OPTIONS")).equalsIgnoreCase("MCoreAddon")) {
                    if (MythicXP.booster.containsKey(p)) {
                        if (MythicXP.booster.get(p) > 1d) {
                            Boosters.giveExp(p, xp, location);
                        } else {
                            PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, location, true);
                        }
                    } else {
                        PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, location, true);
                    }
                } else {
                    PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, location, true);
                }
            } else {
                PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, location, true);
            }
        } else {
            PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, location, true);
        }
    }
}
