/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.entity;

public enum EntityQueryMode {
    DEFAULT(0),
    IGNORE_ISOLATE_CONDITION(1),
    IGNORE_VERSION(2);

    private final int value;

    private EntityQueryMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

