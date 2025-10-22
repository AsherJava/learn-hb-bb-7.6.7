/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.enumeration;

public enum RangeFormType {
    ALL("\u5168\u90e8", 1),
    CUSTOM("\u81ea\u5b9a\u4e49", 2);

    public final int value;
    public final String name;

    private RangeFormType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static RangeFormType valueOf(Integer value) {
        if (value != null) {
            for (RangeFormType t : RangeFormType.values()) {
                if (t.value != value) continue;
                return t;
            }
        }
        return null;
    }
}

