/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.impl.util;

public enum MonitorShowModeEnum {
    LINK("1", "\u8d85\u94fe\u63a5"),
    COLOR("0", "\u7ea2\u7eff\u706f"),
    ERROR("-1", "NONE");

    private String code;
    private String title;

    private MonitorShowModeEnum(String code, String title) {
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

