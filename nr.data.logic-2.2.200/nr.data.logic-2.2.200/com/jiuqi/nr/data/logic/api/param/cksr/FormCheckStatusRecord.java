/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param.cksr;

import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatus;
import java.util.Map;

public class FormCheckStatusRecord {
    private String formKey;
    private CheckStatus checkStatus;
    private Map<Integer, Integer> errorCount;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public CheckStatus getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Map<Integer, Integer> getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Map<Integer, Integer> errorCount) {
        this.errorCount = errorCount;
    }
}

