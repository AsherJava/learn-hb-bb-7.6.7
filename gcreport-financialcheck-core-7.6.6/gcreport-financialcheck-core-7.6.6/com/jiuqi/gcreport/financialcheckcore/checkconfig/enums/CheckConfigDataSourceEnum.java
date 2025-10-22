/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.checkconfig.enums;

public enum CheckConfigDataSourceEnum {
    FINANCIALCUBES("FINANCIALCUBES", "\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f"),
    VCHRITEM("VCHRITEM", "\u51ed\u8bc1\u5206\u5f55"),
    ORIGINACCTBALANCE("ORIGINACCTBALANCE", "\u6e90\u6838\u7b97\u4f59\u989d"),
    ORIGINACCTVCHRITEM("ORIGINACCTVCHRITEM", "\u6e90\u6838\u7b97\u51ed\u8bc1\u5206\u5f55");

    private String code;
    private String title;

    private CheckConfigDataSourceEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

