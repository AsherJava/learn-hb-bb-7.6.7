/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.compiler.type;

import java.util.HashMap;

public enum TPSSubOptType {
    T_MAIN_BEGIN(0),
    T_PROC_BEGIN(1),
    T_SUB_BEGIN(2),
    T_ONE_LINER(3),
    T_IF_ONE_LINER(4),
    T_REPEAT(5),
    T_TRY(6),
    T_TRY_END(7),
    T_UNIT_INIT(8),
    T_UNIT_FINISH(9);

    private int intValue;
    private static HashMap<Integer, TPSSubOptType> mappings;

    private static synchronized HashMap<Integer, TPSSubOptType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSSubOptType(int value) {
        this.intValue = value;
        TPSSubOptType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSSubOptType forValue(int value) {
        return TPSSubOptType.getMappings().get(value);
    }
}

