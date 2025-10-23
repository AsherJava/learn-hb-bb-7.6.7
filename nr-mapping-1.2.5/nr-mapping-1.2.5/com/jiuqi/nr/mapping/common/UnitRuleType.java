/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.common;

public enum UnitRuleType {
    IMPORT(0, "\u5bfc\u5165\u89c4\u5219"),
    EXPORT(1, "\u5bfc\u51fa\u89c4\u5219");

    private int value;
    private String title;

    private UnitRuleType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static UnitRuleType valueOf(int value) {
        for (UnitRuleType type : UnitRuleType.values()) {
            if (value != type.value()) continue;
            return type;
        }
        return null;
    }
}

