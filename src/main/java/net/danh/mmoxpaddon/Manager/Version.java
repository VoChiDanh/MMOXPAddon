package net.danh.mmoxpaddon.Manager;

import net.danh.dcore.Utils.DVersion;
import net.danh.dcore.Utils.Status;
import net.danh.mmoxpaddon.MMOXPAddon;

public class Version extends DVersion {

    @Override
    public String getOriginalVersion() {
        return MMOXPAddon.getInstance().getDescription().getVersion().split(";")[0];
    }

    @Override
    public String getDevBuildVersion() {
        return MMOXPAddon.getInstance().getDescription().getVersion().split(";")[1];
    }

    @Override
    public String isDevBuild() {
        return Status.TRUE.getStatus();
    }

    @Override
    public String isPremium() {
        return Status.TRUE.getStatus();
    }

    @Override
    public String getReleaseLink() {
        return "https://www.spigotmc.org/resources/102848/";
    }
}