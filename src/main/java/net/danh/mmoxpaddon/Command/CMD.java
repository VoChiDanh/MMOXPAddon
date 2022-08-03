package net.danh.mmoxpaddon.Command;

import net.danh.dcore.Commands.CMDBase;
import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.danh.dcore.Utils.Player.sendConsoleMessage;
import static net.danh.dcore.Utils.Player.sendPlayerMessage;

public class CMD extends CMDBase {

    public CMD(JavaPlugin core) {
        super(core, "mmoxpaddon");
    }

    @Override
    public void playerexecute(Player p, String[] args) {
        if (args.length == 1) {
            if (p.hasPermission("mmoxpaddon.admin")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    File.reloadfiles();
                    sendPlayerMessage(p, "&aReloaded");
                }
                if (args[0].equalsIgnoreCase("version")) {
                    sendPlayerMessage(p, "&aOriginal Version: " + MMOXPAddon.VERSION);
                    sendPlayerMessage(p, "&aDev Build Version: " + MMOXPAddon.DEV_BUILD_VERSION);
                    sendPlayerMessage(p, "&aDev Build: " + MMOXPAddon.IS_DEV_BUILD);
                    sendPlayerMessage(p, "&aPremium: " + MMOXPAddon.IS_PREMIUM);
                }
            }
        }
    }

    @Override
    public void consoleexecute(ConsoleCommandSender c, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                File.reloadfiles();
                sendConsoleMessage(c, "&aReloaded");
            }
            if (args[0].equalsIgnoreCase("version")) {
                sendConsoleMessage(c, "&aOriginal Version: " + MMOXPAddon.VERSION);
                sendConsoleMessage(c, "&aDev Build Version: " + MMOXPAddon.DEV_BUILD_VERSION);
                sendConsoleMessage(c, "&aDev Build: " + MMOXPAddon.IS_DEV_BUILD);
                sendConsoleMessage(c, "&aPremium: " + MMOXPAddon.IS_PREMIUM);
            }
        }
    }

    @Override
    public List<String> TabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (sender.hasPermission("mmoxpaddon.admin")) {
            if (args.length == 1) {
                commands.add("reload");
                commands.add("version");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
