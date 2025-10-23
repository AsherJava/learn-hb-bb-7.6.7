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

public enum DataSchemeType {
    NR(0, "\u62a5\u8868\u6570\u636e\u65b9\u6848"),
    QUERY(1, "\u67e5\u8be2\u6570\u636e\u65b9\u6848");

    private int value;
    private String title;
    private static final Map<Integer, DataSchemeType> VALUE_MAP;

    private DataSchemeType(int value, String title) {
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
    public static DataSchemeType valueOf(Integer value) {
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = new HashMap<Integer, DataSchemeType>();
        for (DataSchemeType type : DataSchemeType.values()) {
            VALUE_MAP.put(type.getValue(), type);
        }
    }
}

