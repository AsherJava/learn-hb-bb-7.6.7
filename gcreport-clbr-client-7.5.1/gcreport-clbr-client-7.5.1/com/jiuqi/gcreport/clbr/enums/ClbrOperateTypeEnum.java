/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrOperateTypeEnum {
    CONFIRM("confirm", "\u624b\u5de5\u534f\u540c\u786e\u8ba4"),
    CHECK("check", "\u68c0\u9a8c\u751f\u6210\u51ed\u8bc1"),
    CANCEL("cancel", "\u534f\u540c\u53d6\u6d88"),
    CANCELCHECK("cancelCheck", "\u534f\u540c\u53d6\u6d88\u6821\u9a8c");

    private String code;
    private String title;

    private ClbrOperateTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

