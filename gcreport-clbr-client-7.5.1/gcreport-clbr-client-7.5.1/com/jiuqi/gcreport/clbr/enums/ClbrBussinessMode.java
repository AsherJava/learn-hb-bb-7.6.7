/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrBussinessMode {
    MANAGE_MODE("0", "\u7ba1\u7406\u5458\u6a21\u5f0f"),
    BUSINESS_MODE("1", "\u4e1a\u52a1\u4eba\u5458\u6a21\u5f0f");

    private String code;
    private String title;

    private ClbrBussinessMode(String code, String title) {
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

