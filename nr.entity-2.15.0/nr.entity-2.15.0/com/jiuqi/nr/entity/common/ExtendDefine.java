/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.common;

import java.util.HashMap;

public enum ExtendDefine {
    ENCODING_STRUCTURE(0),
    ORDER(1),
    GROUP(2);

    private int intValue;
    private static HashMap<Integer, ExtendDefine> mappings;

    private static synchronized HashMap<Integer, ExtendDefine> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private ExtendDefine(int value) {
        this.intValue = value;
        ExtendDefine.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static ExtendDefine forValue(int value) {
        return ExtendDefine.getMappings().get(value);
    }
}

