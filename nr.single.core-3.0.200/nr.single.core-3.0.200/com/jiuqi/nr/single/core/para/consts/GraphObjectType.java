/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

public enum GraphObjectType {
    GTUNDEFINED,
    GTGROUP,
    GTLINE,
    GTPOINTS,
    GTPOLY_LINE,
    GTBEZIER_LINE,
    GTRECTANGLE,
    GTROUND_RECT,
    GTELLIPSE,
    GTTEXT,
    GTBITMAP,
    GTCUSTOM;


    public int getValue() {
        return this.ordinal();
    }

    public static GraphObjectType forValue(int value) {
        return GraphObjectType.values()[value];
    }
}

