/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TCheckType {
    CHECK_JUDGE(0),
    CHECK_EVALUATE(1),
    CHECK_ATTRACT(2);

    private int intValue;
    private static HashMap<Integer, TCheckType> mappings;

    private static synchronized HashMap<Integer, TCheckType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TCheckType(int value) {
        this.intValue = value;
        TCheckType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TCheckType forValue(int value) {
        return TCheckType.getMappings().get(value);
    }
}

