/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

public enum OperationFlagEnum {
    SHOW_ON_TOOLBAR("0"),
    SHOW_ON_BOTH_POSITION("1"),
    ONLY_SHOW_ON_COLUMN("2");

    private final String type;

    private OperationFlagEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}

