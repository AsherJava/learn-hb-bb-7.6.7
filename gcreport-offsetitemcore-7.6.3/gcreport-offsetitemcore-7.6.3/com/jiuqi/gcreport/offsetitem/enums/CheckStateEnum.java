/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum CheckStateEnum {
    UNCHECKED("UNCHECKED", "\u672a\u5bf9\u8d26"),
    CHECKED("CHECKED", "\u5df2\u5bf9\u8d26"),
    DIFFCHECKED("DIFFCHECKED", "\u5dee\u5f02\u786e\u8ba4");

    private String code;
    private String title;

    private CheckStateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getTitleForCode(String code) {
        for (CheckStateEnum c : CheckStateEnum.values()) {
            if (!c.code.equalsIgnoreCase(code)) continue;
            return c.title;
        }
        return "";
    }

    public static CheckStateEnum fromCode(String code) {
        for (CheckStateEnum c : CheckStateEnum.values()) {
            if (!c.code.equalsIgnoreCase(code)) continue;
            return c;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static boolean isUncheck(CheckStateEnum checkState) {
        return checkState == UNCHECKED;
    }
}

