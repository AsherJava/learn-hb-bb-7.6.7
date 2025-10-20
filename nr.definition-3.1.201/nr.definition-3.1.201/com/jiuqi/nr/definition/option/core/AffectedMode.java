/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

public enum AffectedMode {
    VISIBLE(1),
    EDITABLE(2);

    private final int value;

    private AffectedMode(int intValue) {
        this.value = intValue;
    }

    public int getValue() {
        return this.value;
    }
}

