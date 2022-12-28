package net.danh.mmoxpaddon.Manager;

import net.danh.mmoxpaddon.API.Version.DVersion;
import net.danh.mmoxpaddon.API.Version.Status;
import net.danh.mmoxpaddon.MMOXPAddon;

public class Version extends DVersion {

    @Override
    public String getOriginalVersion() {
        return MMOXPAddon.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDevBuildVersion() {
        return "Build Beta";
    }

    @Override
    public Status isDevBuild() {
        return Status.TRUE;
    }

    @Override
    public Status isPremium() {
        return Status.TRUE;
    }

    @Override
    public String getReleaseLink() {
        return "https://www.spigotmc.org/resources/102848/";
    }
}
