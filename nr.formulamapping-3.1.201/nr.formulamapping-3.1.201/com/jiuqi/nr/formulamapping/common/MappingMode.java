/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulamapping.common;

import java.util.HashMap;
import java.util.Map;

public enum MappingMode {
    DEFAULT(0),
    AUTO(1),
    MANUAL(2);

    private int value;
    private static final Map<Integer, MappingMode> map;

    private MappingMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MappingMode valueOf(int value) {
        return map.get(value);
    }

    static {
        map = new HashMap<Integer, MappingMode>();
        for (MappingMode kind : MappingMode.values()) {
            map.put(kind.getValue(), kind);
        }
    }
}

