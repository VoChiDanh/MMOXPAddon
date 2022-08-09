package net.danh.mmoxpaddon.Manager;

import net.danh.mmoxpaddon.MMOXPAddon;
import net.danh.mmoxpaddon.Resource.File;

import java.util.logging.Level;

public class Debug {
    public static void debug(String msg) {
        if (File.getconfigfile().getBoolean("DEBUG")) {
            MMOXPAddon.getInstance().getLogger().log(Level.WARNING, "[DEBUG] " + msg);
        }
    }
}
