/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.vo;

public class AnalysisInsertResult {
    private String formKey;
    private String formCode;
    private String formTitle;
    private String type;
    private String message;
    private boolean success;

    public AnalysisInsertResult() {
    }

    public AnalysisInsertResult(String formKey, String formCode, String formTitle, String type, String message, boolean success) {
        this.formKey = formKey;
        this.formCode = formCode;
        this.formTitle = formTitle;
        this.type = type;
        this.message = message;
        this.success = success;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

