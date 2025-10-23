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

public enum RelationType {
    ONE_TO_ONE(0, "\u4e00\u5bf9\u4e00"),
    ONE_TO_MANY(1, "\u4e00\u5bf9\u591a"),
    MANY_TO_ONE(2, "\u591a\u5bf9\u4e00"),
    MANY_TO_MANY(3, "\u591a\u5bf9\u591a");

    private int value;
    private String title;
    private static final Map<Integer, RelationType> VALUE_MAP;

    private RelationType(int value, String title) {
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
    public static RelationType valueOf(Integer value) {
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = new HashMap<Integer, RelationType>();
        for (RelationType type : RelationType.values()) {
            VALUE_MAP.put(type.getValue(), type);
        }
    }
}

