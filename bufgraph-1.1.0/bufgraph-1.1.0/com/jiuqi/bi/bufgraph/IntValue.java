/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.bufgraph;

public final class IntValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int intValue() {
        return this.value;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}

