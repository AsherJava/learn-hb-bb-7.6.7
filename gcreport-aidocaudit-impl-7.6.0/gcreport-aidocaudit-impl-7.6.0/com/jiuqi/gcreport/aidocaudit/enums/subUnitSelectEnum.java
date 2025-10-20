/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.enums;

public enum subUnitSelectEnum {
    DIRECTCHILDREN(0, "\u76f4\u63a5\u4e0b\u7ea7"),
    ALLCHILDREN(1, "\u6240\u6709\u4e0b\u7ea7"),
    NOTDISTINGUISH(2, "\u4e0d\u533a\u5206");

    private int code;
    private String title;

    private subUnitSelectEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

