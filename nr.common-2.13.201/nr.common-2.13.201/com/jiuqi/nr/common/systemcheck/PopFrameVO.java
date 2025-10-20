/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.systemcheck;

public class PopFrameVO {
    private String appName;
    private String entry;
    private String title;
    private boolean fullScreen;
    private int width;
    private int height;
    private int vueVersion;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEntry() {
        return this.entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFullScreen() {
        return this.fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVueVersion() {
        return this.vueVersion;
    }

    public void setVueVersion(int vueVersion) {
        this.vueVersion = vueVersion;
    }
}

