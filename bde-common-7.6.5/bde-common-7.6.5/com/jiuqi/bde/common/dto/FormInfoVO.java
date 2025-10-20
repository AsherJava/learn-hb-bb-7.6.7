/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

public class FormInfoVO {
    private String id;
    private String formCode;
    private String title;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "FormInfoVO [id=" + this.id + ", formCode=" + this.formCode + ", title=" + this.title + "]";
    }
}

