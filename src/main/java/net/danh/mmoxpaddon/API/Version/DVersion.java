package net.danh.mmoxpaddon.API.Version;

public abstract class DVersion {

    public abstract String getOriginalVersion();

    public abstract String getDevBuildVersion();

    public abstract Status isDevBuild();

    public abstract Status isPremium();

    public abstract String getReleaseLink();

}


