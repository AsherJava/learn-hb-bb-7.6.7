/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.sbdata.carry.bean.BaseCarryResult;

public class FormCarryResult
extends BaseCarryResult {
    private String formCode;

    public FormCarryResult() {
    }

    public FormCarryResult(BaseCarryResult baseCarryResult) {
        this.setSuccess(baseCarryResult.isSuccess());
        this.setDataFileCheckInfo(baseCarryResult.getDataFileCheckInfo());
        this.setNoAuthDw(baseCarryResult.getNoAuthDw());
        this.setErrorMessage(baseCarryResult.getErrorMessage());
        this.setErrorRowData(baseCarryResult.getErrorRowData());
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
}

