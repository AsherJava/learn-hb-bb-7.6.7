/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.vo;

public class ReportDataSyncReceiveLogItemVO {
    private String id;
    private String receiveTaskLogId;
    private String syncLogInfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiveTaskLogId() {
        return this.receiveTaskLogId;
    }

    public void setReceiveTaskLogId(String receiveTaskLogId) {
        this.receiveTaskLogId = receiveTaskLogId;
    }

    public String getSyncLogInfo() {
        return this.syncLogInfo;
    }

    public void setSyncLogInfo(String syncLogInfo) {
        this.syncLogInfo = syncLogInfo;
    }
}

