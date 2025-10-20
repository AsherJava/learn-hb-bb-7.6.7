/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class FormCopyParamsVO {
    private String desFormCode;
    private String existFormKey;
    private String srcFormKey;
    private String srcFormGroupKey;
    private int opsResultKey;

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }

    public int getOpsResultKey() {
        return this.opsResultKey;
    }

    public void setOpsResultKey(int opsResultKey) {
        this.opsResultKey = opsResultKey;
    }

    public String getSrcFormGroupKey() {
        return this.srcFormGroupKey;
    }

    public void setSrcFormGroupKey(String srcFormGroupKey) {
        this.srcFormGroupKey = srcFormGroupKey;
    }

    public String getExistFormKey() {
        return this.existFormKey;
    }

    public void setExistFormKey(String existFormKey) {
        this.existFormKey = existFormKey;
    }
}

