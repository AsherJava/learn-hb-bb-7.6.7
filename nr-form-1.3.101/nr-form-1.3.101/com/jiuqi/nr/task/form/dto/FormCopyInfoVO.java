/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

public class FormCopyInfoVO {
    private String srcFormKey;
    private String srcFormTitle;
    private String srcFormCode;
    private String existFormKey;
    private String existFormCode;
    private int checkResultKey;
    private String checkResult;
    private int opsResultKey;
    private String desFormCode;

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }

    public String getSrcFormTitle() {
        return this.srcFormTitle;
    }

    public void setSrcFormTitle(String srcFormTitle) {
        this.srcFormTitle = srcFormTitle;
    }

    public String getSrcFormCode() {
        return this.srcFormCode;
    }

    public void setSrcFormCode(String srcFormCode) {
        this.srcFormCode = srcFormCode;
    }

    public String getExistFormCode() {
        return this.existFormCode;
    }

    public void setExistFormCode(String existFormCode) {
        this.existFormCode = existFormCode;
    }

    public int getCheckResultKey() {
        return this.checkResultKey;
    }

    public void setCheckResultKey(int checkResultKey) {
        this.checkResultKey = checkResultKey;
    }

    public int getOpsResultKey() {
        return this.opsResultKey;
    }

    public void setOpsResultKey(int opsResultKey) {
        this.opsResultKey = opsResultKey;
    }

    public String getDesFormCode() {
        if (this.desFormCode == null) {
            return "";
        }
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

    public String getExistFormKey() {
        return this.existFormKey;
    }

    public void setExistFormKey(String existFormKey) {
        this.existFormKey = existFormKey;
    }
}

