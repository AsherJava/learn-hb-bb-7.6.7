/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import java.util.HashMap;

public enum LinkType {
    LINK_TYPE_ERROR(-1),
    LINK_TYPE_FLOAT(1),
    LINK_TYPE_STRING(2),
    LINK_TYPE_INTEGER(3),
    LINK_TYPE_BOOLEAN(4),
    LINK_TYPE_DATE(5),
    LINK_TYPE_DATETIME(6),
    LINK_TYPE_TIME(19),
    LINK_TYPE_UUID(7),
    LINK_TYPE_DECIMAL(8),
    LINK_TYPE_TEXT(16),
    LINK_TYPE_PICTURE(17),
    LINK_TYPE_FILE(22),
    LINK_TYPE_ENUM(23),
    LINK_TYPE_FORMULA(50);

    private int intValue;
    private static HashMap<Integer, LinkType> mappings;

    private static synchronized HashMap<Integer, LinkType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private LinkType(int value) {
        this.intValue = value;
        LinkType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static LinkType forValue(int value) {
        return LinkType.getMappings().get(value);
    }
}

