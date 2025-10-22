/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.table;

public enum ReportTableType {
    RTT_FIXTABLE,
    RTT_ROWFLOATTABLE,
    RTT_COLFLOATTALBE,
    RTT_WORDTABLE,
    RTT_BLOBTABLE;


    public int getValue() {
        return this.ordinal();
    }

    public static ReportTableType forValue(int value) {
        return ReportTableType.values()[value];
    }
}

