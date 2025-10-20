/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.checkquery.enums;

public enum CheckQueryStateEnum {
    CHECK_NOTSAME(0, "\u5bf9\u8d26\u4e0d\u4e00\u81f4", "\u53cc\u65b9\u672a\u5bf9\u8d26\u6570\u91cf\u4e4b\u548c\u4e0d\u7b49\u4e8e0"),
    CHECK_ALMOSTSAME(1, "\u5bf9\u8d26\u51c6\u4e00\u81f4", "\u53cc\u65b9\u672a\u5bf9\u8d26\u6570\u91cf\u4e4b\u548c\u7b49\u4e8e0 \u4e14 \u5b58\u5728\u5dee\u5f02\u786e\u8ba4\u6570\u636e"),
    CHECK_SAME(2, "\u5bf9\u8d26\u4e00\u81f4", "\u53cc\u65b9\u672a\u5bf9\u8d26\u6570\u91cf\u4e4b\u548c=0 \u4e14 \u4e0d\u5b58\u5728\u5dee\u5f02\u786e\u8ba4\u6570\u636e");

    private Integer code;
    private String title;
    private String desc;

    private CheckQueryStateEnum(Integer code, String title, String desc) {
        this.code = code;
        this.title = title;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }
}

