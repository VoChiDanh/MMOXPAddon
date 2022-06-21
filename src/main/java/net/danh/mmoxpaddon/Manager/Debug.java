package net.danh.mmoxpaddon.Manager;

import net.danh.dcore.DCore;
import net.danh.mmoxpaddon.Resource.File;

public class Debug {

    public static void debug(String msg) {
        if (File.getconfigfile().getBoolean("DEBUG")) {
            DCore.dCoreLog(msg);
        }
    }

}
