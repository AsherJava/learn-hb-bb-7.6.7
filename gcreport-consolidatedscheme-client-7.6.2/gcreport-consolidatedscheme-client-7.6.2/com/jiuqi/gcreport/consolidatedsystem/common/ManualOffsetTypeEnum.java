/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.common;

public enum ManualOffsetTypeEnum {
    NOT_SUPPORT_MULTI_UNIT("0", "\u4e0d\u652f\u6301"),
    SUPPORT_MULTI_UNIT("1", "\u652f\u6301-\u751f\u6210\u660e\u7ec6\u5206\u5f55"),
    SUPPORT_BASE_TO_DIFFERENCE("2", "\u652f\u6301-\u751f\u6210\u672c\u90e8\u5bf9\u5dee\u989d\u5206\u5f55");

    private final String value;
    private final String title;

    private ManualOffsetTypeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

