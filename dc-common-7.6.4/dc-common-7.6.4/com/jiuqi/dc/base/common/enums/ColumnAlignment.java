/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum ColumnAlignment {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    private String value;

    private ColumnAlignment(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

