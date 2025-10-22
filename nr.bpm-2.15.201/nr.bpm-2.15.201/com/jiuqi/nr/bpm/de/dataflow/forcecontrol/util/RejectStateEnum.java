/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.util;

public enum RejectStateEnum {
    INIT(-1),
    FAIL(0),
    PASS(1);

    private int value;

    private RejectStateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

