/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.provider.PluginInfo;

public class ResultItemPMVO {
    private String mcscheme;
    private PluginInfo plugin;
    private String runConfig;
    private String entryView;

    public String getMcscheme() {
        return this.mcscheme;
    }

    public void setMcscheme(String mcscheme) {
        this.mcscheme = mcscheme;
    }

    public PluginInfo getPlugin() {
        return this.plugin;
    }

    public void setPlugin(PluginInfo plugin) {
        this.plugin = plugin;
    }

    public String getRunConfig() {
        return this.runConfig;
    }

    public void setRunConfig(String runConfig) {
        this.runConfig = runConfig;
    }

    public String getEntryView() {
        return this.entryView;
    }

    public void setEntryView(String entryView) {
        this.entryView = entryView;
    }
}

