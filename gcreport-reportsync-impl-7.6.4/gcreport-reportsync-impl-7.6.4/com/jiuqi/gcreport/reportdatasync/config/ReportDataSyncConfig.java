/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.jiuqi.gcreport.reportdatasync")
public class ReportDataSyncConfig {
    private Boolean paramAllModal;
    private Boolean allowFetchReportData;

    public Boolean getParamAllModal() {
        return this.paramAllModal;
    }

    public void setParamAllModal(Boolean paramAllModal) {
        this.paramAllModal = paramAllModal;
    }

    public Boolean getAllowFetchReportData() {
        return this.allowFetchReportData;
    }

    public void setAllowFetchReportData(Boolean allowFetchReportData) {
        this.allowFetchReportData = allowFetchReportData;
    }
}

