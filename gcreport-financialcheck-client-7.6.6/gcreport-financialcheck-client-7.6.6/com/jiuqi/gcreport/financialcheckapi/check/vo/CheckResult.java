/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.List;
import java.util.Map;

public class CheckResult {
    private int checkedItemCount;
    private int checkedGroupCount;
    private String errMsg;
    private AtomicDouble processRate;
    private List<String> checkedItemIds;
    private List<Map<String, Object>> checkResult;

    public void addCheckedItemCount(int num) {
        this.checkedItemCount += num;
    }

    public void addCheckedGroupCount(int num) {
        this.checkedGroupCount += num;
    }

    public void addCheckAllCount(CheckResult checkResult) {
        if (checkResult == null) {
            return;
        }
        this.checkedItemCount += checkResult.getCheckedItemCount();
        this.checkedGroupCount += checkResult.getCheckedGroupCount();
    }

    public int getCheckedItemCount() {
        return this.checkedItemCount;
    }

    public void setCheckedItemCount(int checkedItemCount) {
        this.checkedItemCount = checkedItemCount;
    }

    public int getCheckedGroupCount() {
        return this.checkedGroupCount;
    }

    public void setCheckedGroupCount(int checkedGroupCount) {
        this.checkedGroupCount = checkedGroupCount;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public AtomicDouble getProcessRate() {
        return this.processRate;
    }

    public void setProcessRate(AtomicDouble processRate) {
        this.processRate = processRate;
    }

    public List<String> getCheckedItemIds() {
        return this.checkedItemIds;
    }

    public void setCheckedItemIds(List<String> checkedItemIds) {
        this.checkedItemIds = checkedItemIds;
    }

    public List<Map<String, Object>> getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(List<Map<String, Object>> checkResult) {
        this.checkResult = checkResult;
    }
}

