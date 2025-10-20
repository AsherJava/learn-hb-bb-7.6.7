/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.common;

public class PenetratePlugin {
    private String productLine;
    private String appName;
    private String pluginName;

    public PenetratePlugin() {
    }

    public PenetratePlugin(String productLine, String appName, String pluginName) {
        this.productLine = productLine;
        this.appName = appName;
        this.pluginName = pluginName;
    }

    public String getProductLine() {
        return this.productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }
}

