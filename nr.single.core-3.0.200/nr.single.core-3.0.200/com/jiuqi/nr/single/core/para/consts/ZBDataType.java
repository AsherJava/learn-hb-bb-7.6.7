/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum ZBDataType {
    STRING(1),
    INTEGER(2),
    NUMERIC(3),
    DATE(4),
    REMARK(5),
    ATTATCHMENT(6),
    DOUBLE(7),
    BOOLEAN(8),
    PICTURE(17),
    UUID(39),
    UNKNOWN(9);

    private int intValue;
    private static HashMap<Integer, ZBDataType> mappings;

    private static synchronized HashMap<Integer, ZBDataType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private ZBDataType(int value) {
        this.intValue = value;
        ZBDataType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static ZBDataType forValue(int value) {
        return ZBDataType.getMappings().get(value);
    }
}

