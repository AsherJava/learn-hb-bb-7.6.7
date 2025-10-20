/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.impl.intf;

import java.sql.Timestamp;

public class FetchFormStatus {
    private String runnerId;
    private String formLogId;
    private int successCount;
    private int executeCount;
    private int failedCount;
    private Timestamp endDate;

    public String getRunnerId() {
        return this.runnerId;
    }

    public String getFormLogId() {
        return this.formLogId;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public int getExecuteCount() {
        return this.executeCount;
    }

    public int getFailedCount() {
        return this.failedCount;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public void setFormLogId(String formLogId) {
        this.formLogId = formLogId;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return "FetchFormStatus [runnerId=" + this.runnerId + ", formLogId=" + this.formLogId + ", successCount=" + this.successCount + ", executeCount=" + this.executeCount + ", failedCount=" + this.failedCount + ", endDate=" + this.endDate + "]";
    }
}

