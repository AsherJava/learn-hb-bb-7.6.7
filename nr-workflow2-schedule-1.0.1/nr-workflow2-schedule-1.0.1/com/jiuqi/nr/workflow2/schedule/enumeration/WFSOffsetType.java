/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.enumeration;

public enum WFSOffsetType {
    NATURAL_DAY(0),
    BUSINESS_DAY(1);

    final int value;

    private WFSOffsetType(int value) {
        this.value = value;
    }
}

