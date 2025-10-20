/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.formcopy;

import com.jiuqi.nr.designer.formcopy.FormSyncParams;

public class FormCopyParams {
    private String formKey;
    private String formTitle;
    private String formCode;
    private String formGroupKey;
    private String order;
    private String srcFormKey;
    private String srcFormGroupKey;

    public FormCopyParams() {
    }

    public FormCopyParams(String srcFormKey, String srcFormGroupKey, String formGroupKey) {
        this.srcFormKey = srcFormKey;
        this.srcFormGroupKey = srcFormGroupKey;
        this.formGroupKey = formGroupKey;
    }

    public FormCopyParams(FormSyncParams formSyncParams, String formGroupKey, String srcFormGroupKey) {
        this.formKey = formSyncParams.getFormKey();
        this.formTitle = formSyncParams.getFormTitle();
        this.formCode = formSyncParams.getFormCode();
        this.formGroupKey = formGroupKey;
        this.srcFormKey = formSyncParams.getSrcFormKey();
        this.srcFormGroupKey = srcFormGroupKey;
    }

    public String getFormKey() {
        return this.formKey;
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

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }

    public String getSrcFormGroupKey() {
        return this.srcFormGroupKey;
    }

    public void setSrcFormGroupKey(String srcFormGroupKey) {
        this.srcFormGroupKey = srcFormGroupKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

