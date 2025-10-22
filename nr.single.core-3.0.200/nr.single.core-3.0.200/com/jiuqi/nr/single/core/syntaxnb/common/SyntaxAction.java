/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.common;

import java.util.HashMap;

public enum SyntaxAction {
    SA_SYNTAX_JUDGE(0),
    SA_SYNTAX_EVALUATE(1),
    SA_SYNTAX_ATTRACT(2),
    SA_NI_JUDGE(3),
    SA_NI_EVALUATE(4),
    SA_NI_ATTRACT(5);

    private int intValue;
    private static HashMap<Integer, SyntaxAction> mappings;

    private static synchronized HashMap<Integer, SyntaxAction> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private SyntaxAction(int value) {
        this.intValue = value;
        SyntaxAction.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static SyntaxAction forValue(int value) {
        return SyntaxAction.getMappings().get(value);
    }
}

