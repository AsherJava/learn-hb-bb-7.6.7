/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.enums;

public enum DimensionPublishStateEnum {
    INIT(0, "\u672a\u53d1\u5e03"),
    SUCCESS(1, "\u6210\u529f"),
    FAILURE(2, "\u5931\u8d25");

    private Integer code;
    private String title;

    private DimensionPublishStateEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

