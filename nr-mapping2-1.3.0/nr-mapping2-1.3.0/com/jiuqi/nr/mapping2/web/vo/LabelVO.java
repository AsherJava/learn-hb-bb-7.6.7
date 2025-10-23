/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.vo;

public class LabelVO {
    String code;
    String title;

    public LabelVO() {
    }

    public LabelVO(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

