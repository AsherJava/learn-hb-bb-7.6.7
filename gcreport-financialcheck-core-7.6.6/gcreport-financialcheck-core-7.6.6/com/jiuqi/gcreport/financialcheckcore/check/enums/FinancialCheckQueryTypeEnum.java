/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.enums;

public enum FinancialCheckQueryTypeEnum {
    OFFSETED("\u5df2\u62b5\u9500"),
    CHECKED("\u672a\u62b5\u9500"),
    CHECK("\u5df2\u5bf9\u8d26"),
    UNCHECKED_ALL("\u672a\u5bf9\u8d26-\u5168\u90e8"),
    UNCHECKED("\u672a\u5bf9\u8d26 "),
    UNCHECKED_DIFF("\u672a\u5bf9\u8d26\u6709\u5dee\u989d"),
    DATAINPUT_ALL("\u6570\u636e\u5f55\u5165-\u5168\u90e8"),
    DATAINPUT_CHECKED("\u6570\u636e\u5f55\u5165-\u5df2\u5bf9\u8d26"),
    DATAINPUT_UNCHECK("\u6570\u636e\u5f55\u5165-\u672a\u5bf9\u8d26"),
    DATAINPUT_DFDATA("\u6570\u636e\u5f55\u5165-\u5bf9\u65b9\u6570\u636e");

    private String des;

    private FinancialCheckQueryTypeEnum(String des) {
        this.des = des;
    }

    public String getDes() {
        return this.des;
    }

    public static FinancialCheckQueryTypeEnum fromName(String name) {
        for (FinancialCheckQueryTypeEnum c : FinancialCheckQueryTypeEnum.values()) {
            if (!c.name().equalsIgnoreCase(name)) continue;
            return c;
        }
        return null;
    }

    public String toString() {
        return this.name();
    }
}

