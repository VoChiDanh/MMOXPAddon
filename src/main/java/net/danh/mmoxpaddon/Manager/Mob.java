package net.danh.mmoxpaddon.Manager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.danh.mmoxpaddon.MMOXPAddon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public class Mob {
    private final String name;
    private File file;
    private FileConfiguration config;

    public Mob(String name) {
        this.name = name;
        this.file = new File(MMOXPAddon.getInstance().getDataFolder() + File.separator + "Mobs", name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void load() {
        File folder = new File(MMOXPAddon.getInstance().getDataFolder(), "Mobs");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        this.file = new File(folder, this.name + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (file.length() == 0) {
            try {
                List<String> command_a = new ArrayList<>();
                List<String> command_b = new ArrayList<>();
                config.set(name + ".USE.FORMULA", true);
                config.set(name + ".USE.LIMITED_XP", false);
                config.set(name + ".USE.COMMAND", false);
                config.set(name + ".USE.LEVEL_END", true);
                config.set(name + ".CUSTOM_FORMULA.EXAMPLE1", "%mob_xp% * 2");
                config.set(name + ".CUSTOM_FORMULA.EXAMPLE2", "%mob_xp% * 5");
                command_a.add("eco give %player_name% #cf_example1#");
                command_b.add("eco give %player_name% 10");
                config.set(name + ".COMMAND.OUT_OF_BOUNDS", command_b);
                config.set(name + ".COMMAND.WITHIN_LIMITS", command_a);
                config.set(name + ".FORMULA.OUT_OF_BOUNDS.HIGHER", "%mob_xp% * 4");
                config.set(name + ".FORMULA.OUT_OF_BOUNDS.LOWER", "%mob_xp%");
                config.set(name + ".FORMULA.WITHIN_LIMITS", "%mob_xp% * 2");
                config.set(name + ".XP.DEFAULT", 1);
                config.set(name + ".XP.MAX", 1);
                config.set(name + ".LEVEL.MAX", 5);
                config.set(name + ".LEVEL.MIN", 1);
                config.set(name + ".LEVEL.END", 1);
                config.save(file);
                MMOXPAddon.getInstance().getLogger().log(Level.INFO, "Create new file mob " + name + " complete!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getLevelEnd() {
        return getConfig().getInt(name + ".LEVEL.END");
    }

    public int getLevelMax() {
        return getConfig().getInt(name + ".LEVEL.MAX");
    }

    public int getLevelMin() {
        return getConfig().getInt(name + ".LEVEL.MIN");
    }

    public int getXPMax() {
        return getConfig().getInt(name + ".XP.MAX");
    }

    public int getXPDefault() {
        return getConfig().getInt(name + ".XP.DEFAULT");
    }

    public String getFormulaWithinLimitsWithoutPapi(Player p) {
        String calculator = getConfig().getString(name + ".FORMULA.WITHIN_LIMITS");
        if (calculator == null) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public String getFormulaOutOfBoundWithoutPapiHigher(Player p) {
        String calculator = getConfig().getString(name + ".FORMULA.OUT_OF_BOUNDS.HIGHER");
        if (calculator == null) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public Set<String> getListCustomFormula() {
        return Objects.requireNonNull(net.danh.mmoxpaddon.Resource.File.getmobfile().getConfigurationSection(name + ".CUSTOM_FORMULA")).getKeys(false);
    }

    public String getCustomFormula(String formula) {
        return net.danh.mmoxpaddon.Resource.File.getmobfile().getString(name + ".CUSTOM_FORMULA." + formula);
    }

    public String getFormulaOutOfBoundWithoutPapiLower(Player p) {
        String calculator = getConfig().getString(name + ".FORMULA.OUT_OF_BOUNDS.LOWER");
        if (calculator == null) {
            return null;
        }
        return PlaceholderAPI.setPlaceholders(p, calculator);
    }

    public List<String> getCommandsOutOfBound() {
        return getConfig().getStringList(name + ".COMMAND.OUT_OF_BOUNDS");
    }

    public List<String> getCommandsWithinLimits() {
        return getConfig().getStringList(name + ".COMMAND.WITHIN_LIMITS");
    }

    public boolean useCommand() {
        return getConfig().getBoolean(name + ".USE.COMMAND");
    }

    public boolean useLimitedXP() {
        return getConfig().getBoolean(name + ".USE.LIMITED_XP");
    }

    public boolean useFormula() {
        return getConfig().getBoolean(name + ".USE.FORMULA");
    }

    public boolean useLevelEnd() {
        return getConfig().getBoolean(name + ".USE.LEVEL_END");
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
