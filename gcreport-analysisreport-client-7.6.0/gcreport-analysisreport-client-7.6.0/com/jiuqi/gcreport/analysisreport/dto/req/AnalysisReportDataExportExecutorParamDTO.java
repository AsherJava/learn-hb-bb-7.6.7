/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto.req;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnalysisReportDataExportExecutorParamDTO {
    private String currType;
    private String templateId;
    private Set<String> dataIds;
    private AnalysisReportParseContextDTO context;
    private Map<String, String> envData = new HashMap<String, String>();

    public String getCurrType() {
        return this.currType;
    }

    public void setCurrType(String currType) {
        this.currType = currType;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Set<String> getDataIds() {
        return this.dataIds;
    }

    public void setDataIds(Set<String> dataIds) {
        this.dataIds = dataIds;
    }

    public Map<String, String> getEnvData() {
        return this.envData;
    }

    public void setEnvData(Map<String, String> envData) {
        this.envData = envData;
    }

    public AnalysisReportParseContextDTO getContext() {
        return this.context;
    }

    public void setContext(AnalysisReportParseContextDTO context) {
        this.context = context;
    }
}

