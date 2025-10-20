/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum EffectTableType {
    DETAIL("DETAIL", "\u660e\u7ec6"),
    SUMMARY("SUMMARY", "\u6c47\u603b");

    private String code;
    private String name;

    private EffectTableType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static EffectTableType fromCode(String code) {
        for (EffectTableType type : EffectTableType.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        return null;
    }

    public static EffectTableType fromName(String name) {
        for (EffectTableType type : EffectTableType.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

