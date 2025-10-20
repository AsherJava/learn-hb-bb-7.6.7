/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy;

public class FormSyncParams {
    private String formKey;
    private String formTitle;
    private String formCode;
    private String srcFormKey;

    public FormSyncParams() {
    }

    public FormSyncParams(String formKey, String srcFormKey) {
        this.formKey = formKey;
        this.srcFormKey = srcFormKey;
    }

    public FormSyncParams(String formKey, String formTitle, String formCode, String srcFormKey) {
        this.formKey = formKey;
        this.formTitle = formTitle;
        this.formCode = formCode;
        this.srcFormKey = srcFormKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }
}

