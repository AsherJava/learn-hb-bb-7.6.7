/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.checkquery.enums;

public enum CheckQueryColumnEnum {
    CHECK_STATE("CHECKSTATES", "\u5bf9\u8d26\u72b6\u6001"),
    ORIGINALCURR("ORIGINALCURR", "\u4ea4\u6613\u5e01\u79cd"),
    UNIT("UNIT", "\u672c\u65b9\u5355\u4f4d"),
    OPPUNIT("OPPUNIT", "\u5bf9\u65b9\u5355\u4f4d"),
    CHECK_PROJECT("CHECKPROJECT", "\u5bf9\u8d26\u9879\u76ee"),
    TOTAL("TOTAL", "\u5408\u8ba1"),
    COUNT("COUNT", "\u6570\u91cf"),
    ASSET_CHECKUNIT("ASSETCHECKUNIT", "\u503a\u6743\u65b9\u5bf9\u8d26\u5355\u4f4d"),
    DEBT_CHECKUNIT("DEBTCHECKUNIT", "\u503a\u52a1\u65b9\u5bf9\u8d26\u5355\u4f4d"),
    ASSET_CHECK_PROJECT("ASSETCHECKPROJECT", "\u503a\u6743\u65b9\u5bf9\u8d26\u9879\u76ee"),
    DEBT_CHECK_PROJECT("DEBTCHECKPROJECT", "\u503a\u52a1\u65b9\u5bf9\u8d26\u9879\u76ee"),
    ASSET_TOTAL("ASSETTOTAL", "\u503a\u6743\u65b9\u5408\u8ba1"),
    DEBT_TOTAL("DEBTTOTAL", "\u503a\u52a1\u65b9\u5408\u8ba1"),
    DIFF_AMOUNT("DIFFAMOUNT", "\u5dee\u5f02\u91d1\u989d"),
    UNCHECKED_COUNT("UNCHECKEDCOUNT", "\u672a\u5bf9\u8d26\u6570\u91cf");

    private String value;
    private String title;

    private CheckQueryColumnEnum(String value, String title) {
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

