/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formsetting.vo;

public class FormSettingVO {
    private String id;
    private String code;
    private String title;
    private String formGroupId;
    private String formSchemeKey;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFormGroupId() {
        return this.formGroupId;
    }

    public void setFormGroupId(String formGroupId) {
        this.formGroupId = formGroupId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String toString() {
        return "FormSettingVO{id='" + this.id + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", formGroupId='" + this.formGroupId + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + '}';
    }
}

