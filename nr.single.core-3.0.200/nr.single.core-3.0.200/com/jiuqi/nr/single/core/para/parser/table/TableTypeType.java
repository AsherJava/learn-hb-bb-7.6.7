/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.table;

public enum TableTypeType {
    TTT_JCFH,
    TTT_JCHB,
    TTT_JTHZ,
    TTT_JTCE,
    TTT_BZHZ,
    TTT_BZCE,
    TTT_YBHZ,
    TTT_FJ;


    public int getValue() {
        return this.ordinal();
    }

    public static TableTypeType forValue(int value) {
        return TableTypeType.values()[value];
    }
}

