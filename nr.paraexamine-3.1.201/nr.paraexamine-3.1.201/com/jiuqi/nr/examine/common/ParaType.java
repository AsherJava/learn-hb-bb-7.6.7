/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.common;

import java.util.HashMap;

public enum ParaType {
    ALL(0),
    TASK(1),
    FORMSCHEME(2),
    FORMULASCHEME(3),
    PRINTSCHEME(4),
    FORMULA(5),
    FORMGROUP(6),
    FORM(7),
    REGION(8),
    LINK(9);

    private int status;
    private static HashMap<Integer, ParaType> mappings;

    private ParaType(int status) {
        this.status = status;
        ParaType.getMappings().put(status, this);
    }

    private static synchronized HashMap<Integer, ParaType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public int getValue() {
        return this.status;
    }

    public static ParaType forValue(int value) {
        return ParaType.getMappings().get(value);
    }
}

