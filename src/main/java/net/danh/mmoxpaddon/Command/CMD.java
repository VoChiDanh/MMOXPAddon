package net.danh.mmoxpaddon.Command;

import net.danh.mmoxpaddon.API.Command.CMDBase;
import net.danh.mmoxpaddon.API.Utils.Chat;
import net.danh.mmoxpaddon.Data.API;
import net.danh.mmoxpaddon.Manager.Version;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMD extends CMDBase {

    public CMD() {
        super("mmoxpaddon");
    }

    @Override
    public void execute(CommandSender c, String[] args) {
        if (args.length == 1) {
            if (c.hasPermission("mmoxpaddon.admin")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    File.reloadFiles();
                    Chat.sendCommandSenderMessage(c, "&aReloaded");
                }
                if (args[0].equalsIgnoreCase("version")) {
                    Chat.sendCommandSenderMessage(c, "&aOriginal Version: " + new Version().getOriginalVersion());
                    Chat.sendCommandSenderMessage(c, "&aDev Build Version: " + new Version().getDevBuildVersion());
                    Chat.sendCommandSenderMessage(c, "&aDev Build: " + new Version().isDevBuild().getSymbol());
                    Chat.sendCommandSenderMessage(c, "&aPremium: " + new Version().isPremium().getSymbol());
                    Chat.sendCommandSenderMessage(c, "&aRelease Link: " + new Version().getReleaseLink());
                    Chat.sendCommandSenderMessage(c, "&aMythicCompatible: " + API.isRegisteredMythicCompatible());
                }
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
