/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.topic.extend.report.bean;

import com.jiuqi.nr.topic.extend.report.bean.ReportConfig;

public class ReportResult {
    private boolean success;
    private String errorMsg;
    ReportConfig reportConfig;

    public ReportResult() {
    }

    public ReportResult(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public ReportResult(boolean success, ReportConfig reportConfig) {
        this.success = success;
        this.reportConfig = reportConfig;
    }

    public ReportResult(boolean success, String errorMsg, ReportConfig reportConfig) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.reportConfig = reportConfig;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ReportConfig getReportConfig() {
        return this.reportConfig;
    }

    public void setReportConfig(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }
}

