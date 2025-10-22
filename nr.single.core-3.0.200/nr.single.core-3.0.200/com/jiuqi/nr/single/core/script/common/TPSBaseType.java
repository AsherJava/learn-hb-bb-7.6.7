/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.common;

import java.util.HashMap;

public enum TPSBaseType {
    BT_RETURN_ADDRESS(0),
    BT_U8(1),
    BT_S8(2),
    BT_U16(3),
    BT_S16(4),
    BT_U32(5),
    BT_S32(6),
    BT_SINGLE(7),
    BT_DOUBLE(8),
    BT_EXTENDED(9),
    BT_STRING(10),
    BT_RECORD(11),
    BT_ARRAY(12),
    BT_POINTER(13),
    BT_PCHAR(14),
    BT_RESOURCE_POINTER(15),
    BT_VARIANT(16),
    BT_S64(17),
    BT_CHAR(18),
    BT_WIDE_STRING(19),
    BT_WIDE_CHAR(20),
    BT_PROC_PTR(21),
    BT_STATIC_ARRAY(22),
    BT_SET(23),
    BT_CURRENCY(24),
    BT_CLASS(25),
    BT_INTERFACE(26),
    BT_NOTIFICATION_VARIANT(27),
    BT_TYPE(130),
    BT_ENUM(131),
    BT_EXTCLASS(130),
    BT_NONE(-1);

    private int intValue;
    private static HashMap<Integer, TPSBaseType> mappings;

    private static synchronized HashMap<Integer, TPSBaseType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSBaseType(int value) {
        this.intValue = value;
        TPSBaseType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSBaseType forValue(int value) {
        return TPSBaseType.getMappings().get(value);
    }
}

