/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

public class UnitTreeGroupField {
    private boolean ownRefer;
    private String referEntityId;
    private String ownFieldCode;
    private UnitTreeGroupField parentGroupField;

    public UnitTreeGroupField(String referEntityId, String ownFieldCode) {
        this.referEntityId = referEntityId;
        this.ownFieldCode = ownFieldCode;
    }

    public String getReferEntityId() {
        return this.referEntityId;
    }

    public boolean isOwnRefer() {
        return this.ownRefer;
    }

    public void setOwnRefer(boolean ownRefer) {
        this.ownRefer = ownRefer;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }

    public String getOwnFieldCode() {
        return this.ownFieldCode;
    }

    public void setOwnFieldCode(String ownFieldCode) {
        this.ownFieldCode = ownFieldCode;
    }

    public UnitTreeGroupField getParentGroupField() {
        return this.parentGroupField;
    }

    public void setParentGroupField(UnitTreeGroupField parentGroupField) {
        this.parentGroupField = parentGroupField;
    }
}

