package net.danh.mmoxpaddon.Manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record Mobs(String name) {

    public String getLevelEnd() {
        return File.getMob().getString(name() + ".LEVEL.END");
    }

    public String getLevelMax() {
        return File.getMob().getString(name() + ".LEVEL.MAX");
    }

    public String getLevelMin() {
        return File.getMob().getString(name() + ".LEVEL.MIN");
    }

    public String getXPMax() {
        return File.getMob().getString(name() + ".XP.MAX");
    }

    public String getXPDefault() {
        return File.getMob().getString(name() + ".XP.DEFAULT");
    }

    public Set<String> getListCustomFormula() {
        return Objects.requireNonNull(File.getMob().getConfigurationSection(name + ".CUSTOM_FORMULA")).getKeys(false);
    }

    public String getCustomFormula(String formula) {
        return net.danh.mmoxpaddon.Resource.File.getMob().getString(name + ".CUSTOM_FORMULA." + formula);
    }

    public Set<String> getListConditions() {
        return Objects.requireNonNull(File.getMob().getConfigurationSection(name + ".CONDITIONS")).getKeys(false);
    }

    public boolean notnullConditions() {
        return File.getMob().getConfigurationSection(name + ".CONDITIONS") != null;
    }

    public String getConditions(String conditions) {
        if (conditions.contains("#")) {
            return net.danh.mmoxpaddon.Resource.File.getMob().getString(name + ".CONDITIONS." + conditions.replaceAll("#", ""));
        }
        return null;
    }

    public List<String> getFormulaWithinLimitsWithoutPapi(Player p) {
        List<String> calculator = File.getMob().getStringList(name() + ".FORMULA.WITHIN_LIMITS");
        if (calculator.isEmpty()) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public List<String> getFormulaOutOfBoundWithoutPapiLower(Player p) {
        List<String> calculator = File.getMob().getStringList(name() + ".FORMULA.OUT_OF_BOUNDS.LOWER");
        if (calculator.isEmpty()) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public List<String> getFormulaOutOfBoundWithoutPapiHigher(Player p) {
        List<String> calculator = File.getMob().getStringList(name() + ".FORMULA.OUT_OF_BOUNDS.HIGHER");
        if (calculator.isEmpty()) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public List<String> getCommandsOutOfBound() {
        return File.getMob().getStringList(name() + ".COMMAND.OUT_OF_BOUNDS");
    }

    public List<String> getCommandsWithinLimits() {
        return File.getMob().getStringList(name() + ".COMMAND.WITHIN_LIMITS");
    }

    public boolean useCommand() {
        return File.getMob().getBoolean(name() + ".USE.COMMAND");
    }

    public boolean useLevelEnd() {
        return File.getMob().getBoolean(name() + ".USE.LEVEL_END");
    }

    public boolean useLimitedXP() {
        return File.getMob().getBoolean(name() + ".USE.LIMITED_XP");
    }

    public boolean useFormula() {
        return File.getMob().getBoolean(name() + ".USE.FORMULA");
    }
}
