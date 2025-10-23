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

public enum DataFieldRestrictType {
    DEFAULT(0, "\u9ed8\u8ba4"),
    ZB(1, "\u6307\u6807");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataFieldRestrictType> MAP;

    private DataFieldRestrictType(int value, String title) {
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
    public static DataFieldRestrictType valueOf(int value) {
        return MAP.get(value);
    }

    static {
        MAP = new HashMap();
        for (DataFieldRestrictType value : DataFieldRestrictType.values()) {
            MAP.put(value.value, value);
        }
    }
}

