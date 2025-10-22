/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

public enum FormRegionType {
    MAP_TYPE_FLOAT,
    MAP_TYPE_FIXED;


    public int getValue() {
        return this.ordinal();
    }

    public static FormRegionType forValue(int value) {
        return FormRegionType.values()[value];
    }
}

