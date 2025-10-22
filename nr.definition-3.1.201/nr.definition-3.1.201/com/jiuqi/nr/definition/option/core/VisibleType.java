/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

public enum VisibleType {
    DEFAULT(0),
    DISABLE(1),
    HIDE(2);

    private final int value;

    private VisibleType(int intValue) {
        this.value = intValue;
    }

    public int getValue() {
        return this.value;
    }
}

