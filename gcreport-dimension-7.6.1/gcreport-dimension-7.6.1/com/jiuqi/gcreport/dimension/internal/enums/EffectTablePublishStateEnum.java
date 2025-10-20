/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.enums;

public enum EffectTablePublishStateEnum {
    INIT(0, "\u672a\u53d1\u5e03"),
    SUCCESS(1, "\u53d1\u5e03\u6210\u529f"),
    PUBLISH_FAILURE(2, "\u53d1\u5e03\u540e\u5931\u8d25"),
    UPDATE_FAILURE(3, "\u4fee\u6539\u540e\u5931\u8d25");

    private Integer code;
    private String title;

    private EffectTablePublishStateEnum(Integer code, String title) {
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

