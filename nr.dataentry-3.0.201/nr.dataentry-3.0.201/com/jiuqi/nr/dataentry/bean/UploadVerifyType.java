/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public enum UploadVerifyType {
    CALCUTE("CALCUTE", "\u8fd0\u7b97"),
    CHECK("CHECK", "\u5ba1\u6838"),
    NODECHECK("NODECHECK", "\u8282\u70b9\u68c0\u67e5");

    private String code;
    private String title;

    private UploadVerifyType(String code, String title) {
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

