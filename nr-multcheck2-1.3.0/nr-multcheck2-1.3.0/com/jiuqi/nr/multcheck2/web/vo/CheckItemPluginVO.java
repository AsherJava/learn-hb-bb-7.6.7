/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.multcheck2.provider.PluginInfo;

public class CheckItemPluginVO {
    private String type;
    private String title;
    private PluginInfo pluginInfo;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PluginInfo getPluginInfo() {
        return this.pluginInfo;
    }

    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }
}

