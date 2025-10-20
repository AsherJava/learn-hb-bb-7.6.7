/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.enums;

public enum CheckShowTypeEnum {
    SCHEME("SCHEME", "\u6309\u65b9\u6848\u5206\u7ec4"),
    UNIT("UNIT", "\u6309\u5355\u4f4d\u5206\u7ec4");

    private String code;
    private String title;

    private CheckShowTypeEnum(String code, String title) {
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

