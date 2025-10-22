/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.enums;

public enum InputDataSrcTypeEnum {
    MANUALENTRY(0, "\u5f55\u5165"),
    EXCELIMPORT(1, "EXCEL\u5bfc\u5165"),
    EFDCFETCH(2, "EFDC\u63d0\u53d6"),
    ETLFETCH(3, "ETL\u63d0\u53d6"),
    INTERFACEIMPORT(4, "\u63a5\u53e3\u5bfc\u5165"),
    CONVERSION(5, "\u5916\u5e01\u6298\u7b97"),
    BATCHCOPY(6, "\u6279\u91cf\u590d\u5236"),
    DATA_SYNC(7, "\u6570\u636e\u540c\u6b65"),
    GCEFDCFETCH(2, "GCEFDC\u63d0\u53d6");

    private int value;
    private String title;

    private InputDataSrcTypeEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

