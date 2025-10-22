/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum QueryBlockType {
    QBT_GRID("0"),
    QBT_CHART("1"),
    QBT_ANALYSIS("2"),
    QBT_CUSTOMENTRY("3");

    private String name;

    private QueryBlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static QueryBlockType getType(String value) {
        if (value.equals("qbt_Grid") || value.equals("0")) {
            return QBT_GRID;
        }
        if (value.equals("qbt_Chart") || value.equals("1")) {
            return QBT_CHART;
        }
        if (value.equals("qbt_Analysis") || value.equals("2")) {
            return QBT_ANALYSIS;
        }
        if (value.equals("qbt_CustomEntry") || value.equals("3")) {
            return QBT_CUSTOMENTRY;
        }
        return QBT_GRID;
    }
}

