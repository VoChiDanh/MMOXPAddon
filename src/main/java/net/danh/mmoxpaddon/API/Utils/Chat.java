package net.danh.mmoxpaddon.API.Utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {


    public static void sendCommandSenderMessage(CommandSender c, String... msg) {
        for (String string : msg) {
            c.sendMessage(Chat.colorize(string));
        }
    }

    public static void sendCommandSenderMessage(CommandSender c, List<String> msg) {
        for (String string : msg) {
            c.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param p   Player
     * @param msg Message... send to player
     */
    public static void sendPlayerMessage(org.bukkit.entity.Player p, String... msg) {
        for (String string : msg) {
            p.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param p   Player
     * @param msg List Message send to player
     */
    public static void sendPlayerMessage(org.bukkit.entity.Player p, List<String> msg) {
        for (String string : msg) {
            p.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param c   ConsoleCommandSender
     * @param msg Message... send to console without [DCore]
     */
    public static void sendConsoleMessage(ConsoleCommandSender c, String... msg) {
        for (String string : msg) {
            c.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param c   ConsoleCommandSender
     * @param msg List Message send to console without [DCore]
     */
    public static void sendConsoleMessage(ConsoleCommandSender c, List<String> msg) {
        for (String string : msg) {
            c.sendMessage(Chat.colorize(string));
        }
    }

    /**
     * @param p    Player
     * @param type CHAT, ACTION_BAR
     * @param msg  Message send to player
     */
    public static void sendPlayerMessageType(org.bukkit.entity.Player p, ChatMessageType type, String msg) {
        p.spigot().sendMessage(type, new TranslatableComponent(Chat.colorize(msg)));
    }


    public static String colorize(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

