/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

public enum EnumFieldType {
    DATA_ENUM(1),
    ENTITY_ENUM(2),
    ENTITY_INFO_ENUM(3);

    private int intValue;

    private EnumFieldType(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }
}

