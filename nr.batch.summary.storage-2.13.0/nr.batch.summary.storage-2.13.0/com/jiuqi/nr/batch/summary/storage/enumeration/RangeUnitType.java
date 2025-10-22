/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.enumeration;

public enum RangeUnitType {
    ALL("\u5168\u90e8\u5355\u4f4d", 1),
    CHECK_UNIT("\u6307\u5b9a\u5355\u4f4d", 2),
    EXPRESSION("\u6839\u636e\u6761\u4ef6\u8fc7\u6ee4", 3);

    public final int value;
    public final String name;

    private RangeUnitType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static RangeUnitType valueOf(Integer value) {
        if (value != null) {
            for (RangeUnitType t : RangeUnitType.values()) {
                if (t.value != value) continue;
                return t;
            }
        }
        return null;
    }
}

