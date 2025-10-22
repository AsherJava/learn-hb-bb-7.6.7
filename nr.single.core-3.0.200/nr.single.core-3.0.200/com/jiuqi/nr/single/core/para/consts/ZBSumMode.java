/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

public enum ZBSumMode {
    SUM_ADD,
    SUM_NONE;


    public int getValue() {
        return this.ordinal();
    }

    public static ZBSumMode forValue(int value) {
        return ZBSumMode.values()[value];
    }
}

