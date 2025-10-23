/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import java.util.Map;

public class VariableInfo {
    public static final String PROPERTY_KEY_CLASS = "class";
    public static final String PROPERTY_KEY_DATA_TITLE = "data-title";
    public static final String PROPERTY_KEY_DATA_CODE = "data-code";
    private int index_start;
    private int index_end;
    private Map<String, String> properties;

    public int getIndex_start() {
        return this.index_start;
    }

    public void setIndex_start(int index_start) {
        this.index_start = index_start;
    }

    public int getIndex_end() {
        return this.index_end;
    }

    public void setIndex_end(int index_end) {
        this.index_end = index_end;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}

