/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class FormCopyObj {
    private String formTitle;
    private String formCode;
    private String formGroup;
    private String copyFormKey;
    private boolean onlyCopyStyle = false;
    private String newFormKey;
    private String targetFormScheme;

    public String getTargetFormScheme() {
        return this.targetFormScheme;
    }

    public void setTargetFormScheme(String targetFormScheme) {
        this.targetFormScheme = targetFormScheme;
    }

    public String getNewFormKey() {
        return this.newFormKey;
    }

    public void setNewFormKey(String newFormKey) {
        this.newFormKey = newFormKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormGroup() {
        return this.formGroup;
    }

    public void setFormGroup(String formGroup) {
        this.formGroup = formGroup;
    }

    public String getCopyFormKey() {
        return this.copyFormKey;
    }

    public void setCopyFormKey(String copyFormKey) {
        this.copyFormKey = copyFormKey;
    }

    public boolean getOnlyCopyStyle() {
        return this.onlyCopyStyle;
    }

    public void setOnlyCopyStyle(boolean onlyCopyStyle) {
        this.onlyCopyStyle = onlyCopyStyle;
    }
}

