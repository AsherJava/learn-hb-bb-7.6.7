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

public enum Dimension {
    MONEY(0, "\u91d1\u989d"),
    NO_DIM(1, "\u65e0\u91cf\u7eb2");

    private final int value;
    private final String title;
    public static final Map<Integer, Dimension> mappings;

    private Dimension(int value, String title) {
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
    public static Dimension forValue(int value) {
        return mappings.get(value);
    }

    static {
        mappings = new HashMap<Integer, Dimension>();
        for (Dimension type : Dimension.values()) {
            mappings.put(type.getValue(), type);
        }
    }
}

