/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.client.enums;

public enum DataRefParamType {
    EQUAL,
    LIKE;


    public static DataRefParamType fromName(String value) {
        if (value == null) {
            return LIKE;
        }
        for (DataRefParamType type : DataRefParamType.values()) {
            if (!type.name().equals(value)) continue;
            return type;
        }
        return LIKE;
    }
}

