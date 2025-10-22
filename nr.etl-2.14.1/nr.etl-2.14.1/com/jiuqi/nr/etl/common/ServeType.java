/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

public enum ServeType {
    ETL(1),
    DATA_INTEGRATION(2);

    private final int value;

    private ServeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ServeType valueOf(int value) {
        if (value == ServeType.ETL.value) {
            return ETL;
        }
        if (value == ServeType.DATA_INTEGRATION.value) {
            return DATA_INTEGRATION;
        }
        return null;
    }
}

