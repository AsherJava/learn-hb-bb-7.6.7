/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo;

public class LogInfo {
    private String actionName;
    private String logInfo;
    private BatchUploadRetrunInfo batchUploadRetrunInfo;

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public BatchUploadRetrunInfo getBatchUploadRetrunInfo() {
        return this.batchUploadRetrunInfo;
    }

    public void setBatchUploadRetrunInfo(BatchUploadRetrunInfo batchUploadRetrunInfo) {
        this.batchUploadRetrunInfo = batchUploadRetrunInfo;
    }
}

