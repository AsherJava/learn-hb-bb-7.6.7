/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.common;

public enum TransmissonDataType {
    CHECK_MESSAGE("CHECK_MESSAGE", "\u5ba1\u6838\u8bf4\u660e"),
    ANNOTATION_MESSAGE("ANNOTATION_MESSAGE", " \u6279\u6ce8\u9009\u9879");

    private String code;
    private String title;

    private TransmissonDataType(String code, String title) {
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

