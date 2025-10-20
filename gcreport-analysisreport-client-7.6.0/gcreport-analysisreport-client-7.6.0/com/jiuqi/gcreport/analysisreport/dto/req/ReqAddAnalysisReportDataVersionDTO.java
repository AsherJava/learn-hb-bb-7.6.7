/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto.req;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDataDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO;

public class ReqAddAnalysisReportDataVersionDTO {
    private String versionName;
    private AnalysisReportDataDTO originData;
    private AnalysisReportParseContextDTO context;
    private boolean confirmOnPartTemplateReportData;

    public AnalysisReportDataDTO getOriginData() {
        return this.originData;
    }

    public void setOriginData(AnalysisReportDataDTO originData) {
        this.originData = originData;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String value) {
        this.versionName = value;
    }

    public AnalysisReportParseContextDTO getContext() {
        return this.context;
    }

    public void setContext(AnalysisReportParseContextDTO context) {
        this.context = context;
    }

    public boolean isConfirmOnPartTemplateReportData() {
        return this.confirmOnPartTemplateReportData;
    }

    public void setConfirmOnPartTemplateReportData(boolean confirmOnPartTemplateReportData) {
        this.confirmOnPartTemplateReportData = confirmOnPartTemplateReportData;
    }
}

