/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.vo;

public enum LevelType {
    ALL("ALL"),
    LEAF("LEAF"),
    MERGE("MERGE");

    private String type;

    private LevelType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static LevelType find(String type) {
        return LevelType.valueOf(type.toUpperCase());
    }
}

