/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.common;

import java.util.HashMap;

public enum SetKindType {
    SA_SYNTAX_JUDGE(0),
    SA_SYNTAX_EVALUATE(1),
    SA_SYNTAX_ATTRACT(2),
    SA_NI_JUDGE(3),
    SA_NI_EVALUATE(4),
    SA_NI_ATTRACT(5);

    private int intValue;
    private static HashMap<Integer, SetKindType> mappings;

    private static synchronized HashMap<Integer, SetKindType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private SetKindType(int value) {
        this.intValue = value;
        SetKindType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static SetKindType forValue(int value) {
        return SetKindType.getMappings().get(value);
    }
}

