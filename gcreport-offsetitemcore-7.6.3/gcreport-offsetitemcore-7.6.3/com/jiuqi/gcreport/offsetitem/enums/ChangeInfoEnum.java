/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

public enum ChangeInfoEnum {
    STATECHANGE("STATECHANGE", "\u72b6\u6001\u53d8\u52a8"),
    DATACHANGE("DATACHANGE", "\u6570\u636e\u53d8\u52a8");

    private String code;
    private String title;

    private ChangeInfoEnum(String code, String title) {
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

