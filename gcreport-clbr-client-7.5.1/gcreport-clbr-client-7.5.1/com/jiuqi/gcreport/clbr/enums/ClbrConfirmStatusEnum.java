/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrConfirmStatusEnum {
    BOTH_CONFIRMED(0, "\u53cc\u65b9\u5df2\u786e\u8ba4"),
    SINGLE_CONFIRMED(1, "\u53d1\u8d77\u65b9\u5df2\u786e\u8ba4");

    private Integer code;
    private String title;

    private ClbrConfirmStatusEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

