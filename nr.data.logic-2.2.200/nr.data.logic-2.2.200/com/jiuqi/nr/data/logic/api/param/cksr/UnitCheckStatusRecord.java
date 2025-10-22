/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param.cksr;

import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatus;
import java.util.Map;

public class UnitCheckStatusRecord {
    private String unitKey;
    private CheckStatus checkStatus;
    private Map<Integer, Integer> errorCount;

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
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

