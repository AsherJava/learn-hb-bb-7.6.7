/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum QueryLayoutType {
    LYT_NONE("0"),
    LYT_ROW("1"),
    LYT_COL("2"),
    LYT_CONDITION("3"),
    LYT_RAC("4");

    private String name;

    private QueryLayoutType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}

