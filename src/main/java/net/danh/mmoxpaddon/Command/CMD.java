package net.danh.mmoxpaddon.Command;

import net.danh.dcore.Commands.CMDBase;
import net.danh.mmoxpaddon.Resource.File;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static net.danh.dcore.Utils.Player.sendConsoleMessage;
import static net.danh.dcore.Utils.Player.sendPlayerMessage;

public class CMD extends CMDBase {

    public CMD(JavaPlugin core) {
        super(core, "mmoxpaddon");
    }

    @Override
    public void playerexecute(Player p, String[] args) {
        if (args.length == 0) {
            File.reloadfiles();
            sendPlayerMessage(p, "&aReloaded");
        }
    }

    @Override
    public void consoleexecute(ConsoleCommandSender c, String[] args) {
        if (args.length == 0) {
            File.reloadfiles();
            sendConsoleMessage(c, "&aReloaded");
        }
    }

    @Override
    public List<String> TabComplete(CommandSender commandSender, String[] strings) {
        return null;
    }
}
