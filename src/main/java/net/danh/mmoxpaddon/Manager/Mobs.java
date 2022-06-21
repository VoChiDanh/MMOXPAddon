package net.danh.mmoxpaddon.Manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.entity.Player;

import java.util.List;

public record Mobs(String name) {

    public int getLevelMax() {
        return File.getmobfile().getInt(name() + ".LEVEL.MAX");
    }

    public int getLevelMin() {
        return File.getmobfile().getInt(name() + ".LEVEL.MIN");
    }

    public int getXPMax() {
        return File.getmobfile().getInt(name() + ".XP.MAX");
    }

    public int getXPDefault() {
        return File.getmobfile().getInt(name() + ".XP.DEFAULT");
    }

    public String getFormulaWithinLimitsWithoutPapi(Player p) {
        String calculator = File.getmobfile().getString(name() + ".FORMULA.WITHIN_LIMITS");
        if (calculator == null) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public String getFormulaOutOfBoundWithoutPapi(Player p) {
        String calculator = File.getmobfile().getString(name() + ".FORMULA.OUT_OF_BOUNDS");
        if (calculator == null) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public List<String> getCommandsOutOfBound() {
        return File.getmobfile().getStringList(name() + ".COMMAND.OUT_OF_BOUNDS");
    }

    public List<String> getCommandsWithinLimits() {
        return File.getmobfile().getStringList(name() + ".COMMAND.WITHIN_LIMITS");
    }

    public boolean useCommand() {
        return File.getmobfile().getBoolean(name() + ".USE.COMMAND");
    }

    public boolean useLimitedXP() {
        return File.getmobfile().getBoolean(name() + ".USE.LIMITED_XP");
    }

    public boolean useFormula() {
        return File.getmobfile().getBoolean(name() + ".USE.FORMULA");
    }
}