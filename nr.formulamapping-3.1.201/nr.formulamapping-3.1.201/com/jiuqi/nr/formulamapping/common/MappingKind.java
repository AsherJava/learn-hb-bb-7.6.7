/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulamapping.common;

import java.util.HashMap;
import java.util.Map;

public enum MappingKind {
    GROUP(0),
    MAPPING(1);

    private int value;
    private static final Map<Integer, MappingKind> map;

    private MappingKind(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MappingKind valueOf(int value) {
        return map.get(value);
    }

    static {
        map = new HashMap<Integer, MappingKind>();
        for (MappingKind kind : MappingKind.values()) {
            map.put(kind.getValue(), kind);
        }
    }
}

