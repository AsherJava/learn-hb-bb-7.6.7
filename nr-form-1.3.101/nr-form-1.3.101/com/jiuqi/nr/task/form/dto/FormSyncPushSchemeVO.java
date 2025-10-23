/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

public class FormSyncPushSchemeVO {
    private String desTaskKey;
    private String desTaskTitle;
    private String desTaskOrder;
    private String formSchemeKey;
    private String formSchemeCode;
    private String formSchemeTitle;
    private String desFormTitle;
    private String desFormKey;
    private String desFormCode;
    private String checkResult = "\u6b63\u5e38";
    private int opsResult;

    public FormSyncPushSchemeVO() {
    }

    public FormSyncPushSchemeVO(String desTaskKey, String formSchemeKey) {
        this.desTaskKey = desTaskKey;
        this.desTaskTitle = desTaskKey;
        this.formSchemeKey = formSchemeKey;
        this.formSchemeTitle = formSchemeKey;
    }

    public String getDesTaskKey() {
        return this.desTaskKey;
    }

    public void setDesTaskKey(String desTaskKey) {
        this.desTaskKey = desTaskKey;
    }

    public String getDesTaskTitle() {
        return this.desTaskTitle;
    }

    public void setDesTaskTitle(String desTaskTitle) {
        this.desTaskTitle = desTaskTitle;
    }

    public String getDesTaskOrder() {
        return this.desTaskOrder;
    }

    public void setDesTaskOrder(String desTaskOrder) {
        this.desTaskOrder = desTaskOrder;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getDesFormTitle() {
        return this.desFormTitle;
    }

    public void setDesFormTitle(String desFormTitle) {
        this.desFormTitle = desFormTitle;
    }

    public String getDesFormKey() {
        return this.desFormKey;
    }

    public void setDesFormKey(String desFormKey) {
        this.desFormKey = desFormKey;
    }

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public String getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public int getOpsResult() {
        return this.opsResult;
    }

    public void setOpsResult(int opsResult) {
        this.opsResult = opsResult;
    }
}

