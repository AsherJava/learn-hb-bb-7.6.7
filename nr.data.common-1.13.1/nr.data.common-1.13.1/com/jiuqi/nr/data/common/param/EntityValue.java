/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.param;

public enum EntityValue {
    KEY(0),
    CODE(1);

    private final int value;

    private EntityValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

