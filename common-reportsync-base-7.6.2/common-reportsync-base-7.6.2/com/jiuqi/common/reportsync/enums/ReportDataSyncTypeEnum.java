/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.reportsync.enums;

public enum ReportDataSyncTypeEnum {
    PARAM("param", "\u53c2\u6570"),
    DATA("business", "\u4e1a\u52a1\u6570\u636e");

    private String code;
    private String title;

    private ReportDataSyncTypeEnum(String code, String title) {
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

