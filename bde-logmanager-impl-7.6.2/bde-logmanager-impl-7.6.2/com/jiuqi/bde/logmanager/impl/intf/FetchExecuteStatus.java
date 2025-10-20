/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.impl.intf;

import java.sql.Timestamp;

public class FetchExecuteStatus {
    private int successCount;
    private int executeCount;
    private int failedCount;
    private Timestamp endDate;

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getExecuteCount() {
        return this.executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public int getFailedCount() {
        return this.failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getTotal() {
        return this.successCount + this.executeCount + this.failedCount;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return "FetchExecuteStatus [successCount=" + this.successCount + ", executeCount=" + this.executeCount + ", failedCount=" + this.failedCount + ", endDate=" + this.endDate + "]";
    }
}

