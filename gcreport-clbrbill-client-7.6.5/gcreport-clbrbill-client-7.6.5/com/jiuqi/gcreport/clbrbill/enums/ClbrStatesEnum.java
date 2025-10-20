/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.enums;

public enum ClbrStatesEnum {
    SUBMIT("\u5df2\u63d0\u4ea4"),
    CONFIRM("\u5df2\u5224\u5b9a"),
    REJECT("\u5df2\u9a73\u56de"),
    ABANDON("\u5df2\u4f5c\u5e9f");

    private String name;

    private ClbrStatesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ClbrStatesEnum getEnumByName(String name) {
        ClbrStatesEnum[] values;
        for (ClbrStatesEnum value : values = ClbrStatesEnum.values()) {
            if (!value.name().equals(name)) continue;
            return value;
        }
        return null;
    }
}

