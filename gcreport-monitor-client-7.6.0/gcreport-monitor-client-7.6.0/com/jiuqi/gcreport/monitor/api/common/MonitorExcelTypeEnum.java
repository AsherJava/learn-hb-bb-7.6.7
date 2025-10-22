/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

public enum MonitorExcelTypeEnum {
    DATASTYLE(0, "\u6570\u636e\u6a21\u5f0f"),
    WORDSTYLE(1, "\u6587\u5b57\u6a21\u5f0f"),
    COLORSTYLE(-1, "\u989c\u8272\u6a21\u5f0f");

    private int code;
    private String title;

    private MonitorExcelTypeEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

