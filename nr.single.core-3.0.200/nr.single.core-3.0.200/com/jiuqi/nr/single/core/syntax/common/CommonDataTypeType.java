/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum CommonDataTypeType {
    CD_NIL_TYPE(0),
    CD_INT_TYPE(1),
    CD_REAL_TYPE(2),
    CD_STRING_TYPE(3),
    CD_BOOL_TYPE(4),
    CD_DATE_TYPE(5),
    CD_BLOB_TYPE(6),
    CD_TEXT_TYPE(7),
    CD_BLOB2_TYPE(8);

    private int intValue;
    private static HashMap<Integer, CommonDataTypeType> mappings;

    private static synchronized HashMap<Integer, CommonDataTypeType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private CommonDataTypeType(int value) {
        this.intValue = value;
        CommonDataTypeType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static CommonDataTypeType forValue(int value) {
        return CommonDataTypeType.getMappings().get(value);
    }
}

