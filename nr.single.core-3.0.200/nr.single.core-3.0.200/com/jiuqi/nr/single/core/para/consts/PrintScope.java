/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum PrintScope {
    PS_ALL_PAGE(0),
    PS_ODD_PAGES(1),
    PS_BACK_PAGES(2),
    PS_NONE(3);

    private int intValue;
    private static HashMap<Integer, PrintScope> mappings;

    private static synchronized HashMap<Integer, PrintScope> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private PrintScope(int value) {
        this.intValue = value;
        PrintScope.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static PrintScope forValue(int value) {
        return PrintScope.getMappings().get(value);
    }
}

