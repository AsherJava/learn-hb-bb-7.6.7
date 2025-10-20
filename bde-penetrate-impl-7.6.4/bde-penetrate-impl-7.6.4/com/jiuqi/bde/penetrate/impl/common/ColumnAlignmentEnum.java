/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.impl.common;

public enum ColumnAlignmentEnum {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    private final String code;

    private ColumnAlignmentEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

