/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 */
package com.jiuqi.va.workflow.plugin.processdesin;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.va.biz.intf.model.PluginDefine;

public class ProcessDesignPluginDefine
implements PluginDefine {
    private String type;
    private JsonNode data;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getData() {
        return this.data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}

