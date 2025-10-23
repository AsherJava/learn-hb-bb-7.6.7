/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum PeriodPattern {
    NR(0, "\u62a5\u8868\u683c\u5f0f"),
    DATE(1, "\u65e5\u671f\u683c\u5f0f");

    private int value;
    private String title;
    private static final Map<Integer, PeriodPattern> VALUE_MAP;

    private PeriodPattern(int value, String title) {
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
    public static PeriodPattern valueOf(Integer value) {
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = new HashMap<Integer, PeriodPattern>();
        for (PeriodPattern type : PeriodPattern.values()) {
            VALUE_MAP.put(type.getValue(), type);
        }
    }
}

