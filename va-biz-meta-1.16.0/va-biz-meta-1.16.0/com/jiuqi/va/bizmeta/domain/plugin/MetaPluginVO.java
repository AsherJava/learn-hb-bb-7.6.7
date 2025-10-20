/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.plugin;

import java.util.List;
import java.util.Map;

public class MetaPluginVO {
    private List<Map<String, Object>> data;

    public MetaPluginVO(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<Map<String, Object>> getPlugins() {
        return this.data;
    }

    public void setPlugins(List<Map<String, Object>> plugins) {
        this.data = plugins;
    }
}

