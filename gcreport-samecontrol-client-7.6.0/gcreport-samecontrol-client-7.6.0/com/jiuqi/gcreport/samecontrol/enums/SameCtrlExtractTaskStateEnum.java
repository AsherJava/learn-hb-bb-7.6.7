/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlExtractTaskStateEnum {
    EXECUTING("EXECUTING", "\u6267\u884c\u4e2d"),
    SUCCESS("SUCCESS", "\u6210\u529f"),
    ERROR("ERROR", "\u9519\u8bef"),
    NONE("NONE", "\u65e0\u65e5\u5fd7\u4fe1\u606f");

    private String code;
    private String title;

    private SameCtrlExtractTaskStateEnum(String code, String title) {
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

