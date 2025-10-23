/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.provider;

public class PluginInfo {
    private String appName;
    private String prodLine;
    private String expose;

    public PluginInfo(String prodLine, String appName) {
        this.prodLine = prodLine;
        this.appName = appName;
    }

    public PluginInfo(String prodLine, String appName, String expose) {
        this.prodLine = prodLine;
        this.appName = appName;
        this.expose = expose;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProdLine() {
        return this.prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public String getExpose() {
        return this.expose;
    }

    public void setExpose(String expose) {
        this.expose = expose;
    }
}

