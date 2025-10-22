/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.sb;

public enum SBImportActuatorType {
    DB(1),
    BUF_DB(2);

    private final int value;

    private SBImportActuatorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

