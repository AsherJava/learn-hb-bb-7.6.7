/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.enumeration;

public enum ConditionValueType {
    UNITS("\u5355\u4f4d\u5217\u8868", 2),
    EXPRESSION("\u8868\u8fbe\u5f0f", 1),
    INVALIDINPUT("\u65e0\u6548\u8f93\u5165", 3);

    public final int value;
    public final String name;

    private ConditionValueType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static ConditionValueType valueOf(Integer value) {
        if (value != null) {
            for (ConditionValueType t : ConditionValueType.values()) {
                if (t.value != value) continue;
                return t;
            }
        }
        return null;
    }
}

