/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.util.HashMap;
import java.util.Map;

public enum ZbGatherType {
    NONE(AggrType.NONE, "\u4e0d\u6c47\u603b"),
    SUM(AggrType.SUM, "\u6c42\u548c"),
    COUNT(AggrType.COUNT, "\u8ba1\u6570"),
    AVERAGE(AggrType.AVERAGE, "\u5e73\u5747"),
    MIN(AggrType.MIN, "\u6700\u5c0f"),
    MAX(AggrType.MAX, "\u6700\u5927"),
    DISTINCT_COUNT(AggrType.DISTINCT_COUNT, "\u53bb\u91cd\u8ba1\u6570");

    private final int value;
    private final String title;
    private static final Map<Integer, ZbGatherType> mappings;
    private static final Map<String, ZbGatherType> title_mappings;

    private ZbGatherType(AggrType aggrType, String title) {
        this.value = aggrType.getValue();
        this.title = title;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    @JsonCreator
    public static ZbGatherType forValue(int value) {
        return mappings.get(value);
    }

    public static ZbGatherType forTitle(String title) {
        return title_mappings.get(title);
    }

    static {
        mappings = new HashMap<Integer, ZbGatherType>();
        title_mappings = new HashMap<String, ZbGatherType>();
        for (ZbGatherType type : ZbGatherType.values()) {
            mappings.put(type.getValue(), type);
            title_mappings.put(type.getTitle(), type);
        }
    }
}

