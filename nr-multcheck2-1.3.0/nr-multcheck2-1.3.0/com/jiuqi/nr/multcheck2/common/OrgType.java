/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

public enum OrgType {
    ALL(0, "\u5168\u90e8\u5355\u4f4d"),
    SELECT(1, "\u9009\u62e9\u5355\u4f4d\u5217\u8868"),
    FORMULA(2, "\u516c\u5f0f\u8868\u8fbe\u5f0f");

    private final int value;
    private final String title;

    private OrgType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static OrgType fromValue(int value) {
        for (OrgType myEnum : OrgType.values()) {
            if (myEnum.value != value) continue;
            return myEnum;
        }
        return null;
    }
}

