/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.deploy.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum DDLStatus {
    DEFAULT(0),
    GENERATE_SQL(1),
    EXECUTE_SQL(2);

    private final int value;
    private static final Map<Integer, DDLStatus> valueMap;

    private DDLStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public static DDLStatus valueOf(int value) {
        return valueMap.get(value);
    }

    static {
        valueMap = new HashMap<Integer, DDLStatus>();
        for (DDLStatus type : DDLStatus.values()) {
            valueMap.put(type.getValue(), type);
        }
    }
}

