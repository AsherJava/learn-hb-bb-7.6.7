/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.provider.MappingResource
 *  com.jiuqi.nvwa.mapping.provider.PluginInfo
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.nvwa.mapping.provider.MappingResource;
import com.jiuqi.nvwa.mapping.provider.PluginInfo;

public class NrMappingResource
extends MappingResource {
    private double order = 40.0;

    public NrMappingResource() {
    }

    public NrMappingResource(String code, String title, PluginInfo plugin) {
        super(code, title, plugin);
    }

    public NrMappingResource(String code, String title, PluginInfo plugin, double order) {
        super(code, title, plugin);
        this.order = order;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }
}

