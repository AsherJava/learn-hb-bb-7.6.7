/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 */
package com.jiuqi.gcreport.analysisreport.dto;

import com.jiuqi.bi.oss.ObjectInfo;

public class AnalysisReportGeneratorDocDTO {
    private ObjectInfo objectInfo;
    private String confirmMessage;

    public AnalysisReportGeneratorDocDTO() {
    }

    public AnalysisReportGeneratorDocDTO(ObjectInfo objectInfo, String confirmMessage) {
        this.objectInfo = objectInfo;
        this.confirmMessage = confirmMessage;
    }

    public ObjectInfo getObjectInfo() {
        return this.objectInfo;
    }

    public void setObjectInfo(ObjectInfo objectInfo) {
        this.objectInfo = objectInfo;
    }

    public String getConfirmMessage() {
        return this.confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }
}

