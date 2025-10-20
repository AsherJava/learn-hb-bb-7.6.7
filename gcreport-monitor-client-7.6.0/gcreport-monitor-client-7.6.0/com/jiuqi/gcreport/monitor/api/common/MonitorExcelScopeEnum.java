/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

public enum MonitorExcelScopeEnum {
    DIRECTCHILDREN(0, "\u76f4\u63a5\u4e0b\u7ea7"),
    ALLCHILDREN(1, "\u6240\u6709\u4e0b\u7ea7");

    private int code;
    private String title;

    private MonitorExcelScopeEnum(int code, String title) {
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

