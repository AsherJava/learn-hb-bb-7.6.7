/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum TSyntaxOption {
    SO_CODE(0),
    SO_MEAN(1),
    SO_TABLE(2),
    SO_STAR(3),
    SO_CHKPOS(4),
    SO_FINANCE(5);

    private int intValue;
    private static HashMap<Integer, TSyntaxOption> mappings;

    private static synchronized HashMap<Integer, TSyntaxOption> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TSyntaxOption(int value) {
        this.intValue = value;
        TSyntaxOption.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TSyntaxOption forValue(int value) {
        return TSyntaxOption.getMappings().get(value);
    }
}

