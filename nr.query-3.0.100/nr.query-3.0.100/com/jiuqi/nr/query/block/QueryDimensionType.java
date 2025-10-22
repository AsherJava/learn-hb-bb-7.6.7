/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public enum QueryDimensionType {
    QDT_NULL("0"),
    QDT_ENTITY("1"),
    QDT_DICTIONARY("2"),
    QDT_FIELD("3"),
    QDT_CALIBER("4"),
    QDT_FLOATDETAIL("5"),
    QDT_STATISTIC("6"),
    QDT_CUSTOM("7"),
    QDT_GRIDFIELD("10"),
    QDT_GRIDFIELD_ROW("8"),
    QDT_GRIDFIELD_COL("9"),
    QDT_UPLOADSTATUS("10");

    private String name;

    private QueryDimensionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}

