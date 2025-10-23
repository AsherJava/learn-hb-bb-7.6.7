/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum ZbType {
    GENERAL_ZB(0, "\u666e\u901a\u6307\u6807"),
    CALCULATE_ZB(1, "\u5206\u6790\u6307\u6807");

    private final int value;
    private final String title;
    public static final Map<Integer, ZbType> mappings;
    public static final Map<String, ZbType> title_mappings;

    private ZbType(int value, String title) {
        this.value = value;
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
    public static ZbType forValue(int value) {
        return mappings.get(value);
    }

    public static ZbType forTitle(String title) {
        return title_mappings.get(title);
    }

    static {
        mappings = new HashMap<Integer, ZbType>();
        title_mappings = new HashMap<String, ZbType>();
        for (ZbType type : ZbType.values()) {
            mappings.put(type.getValue(), type);
            title_mappings.put(type.getTitle(), type);
        }
    }
}

