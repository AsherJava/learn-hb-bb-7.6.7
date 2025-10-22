/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.bean;

import java.util.List;
import java.util.Map;

public class MidstoreReturnInfo {
    private boolean isPull;
    private Map<String, List<String>> unAccessForms;
    private Map<String, List<String>> unAccessUnits;
    private int allCount;
    private int successCount;
    private int failedCount;

    public int getAllCount() {
        return this.allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return this.failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public boolean isPull() {
        return this.isPull;
    }

    public void setPull(boolean pull) {
        this.isPull = pull;
    }

    public Map<String, List<String>> getUnAccessForms() {
        return this.unAccessForms;
    }

    public void setUnAccessForms(Map<String, List<String>> unAccessForms) {
        this.unAccessForms = unAccessForms;
    }

    public Map<String, List<String>> getUnAccessUnits() {
        return this.unAccessUnits;
    }

    public void setUnAccessUnits(Map<String, List<String>> unAccessUnits) {
        this.unAccessUnits = unAccessUnits;
    }
}

