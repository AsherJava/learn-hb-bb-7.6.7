/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.group;

public enum GroupDirection {
    ROW(0),
    COL(1);

    private final int value;

    private GroupDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

