/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum TabSelectEnum {
    ALL_PAGE("ALLPAGE", 1, "\u672c\u7ea7-\u5168\u90e8"),
    OFFSET_PAGE("OFFSETPAGE", 2, "\u672c\u7ea7-\u5df2\u62b5\u9500"),
    NOT_OFFSET_PAGE("NOT_OFFSETPAGE", 3, "\u672c\u7ea7-\u672a\u62b5\u9500"),
    NOT_OFFSET_PARENT_PAGE("NOT_OFFSET_PARENTPAGE", 4, "\u4e0a\u7ea7-\u672a\u62b5\u9500");

    private String code;
    private int value;
    private String title;

    private TabSelectEnum(String code, int value, String title) {
        this.code = code;
        this.value = value;
        this.title = title;
    }

    public static TabSelectEnum fromCode(String code) {
        for (TabSelectEnum tab : TabSelectEnum.values()) {
            if (!tab.getCode().equals(code)) continue;
            return tab;
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

