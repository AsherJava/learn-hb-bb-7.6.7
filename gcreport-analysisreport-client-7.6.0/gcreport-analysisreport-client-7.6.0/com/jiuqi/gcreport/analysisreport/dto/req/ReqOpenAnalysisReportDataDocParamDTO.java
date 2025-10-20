/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto.req;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO;
import java.util.HashMap;
import java.util.Map;

public class ReqOpenAnalysisReportDataDocParamDTO {
    private String templateId;
    private String dataId;
    private AnalysisReportParseContextDTO context;
    private Map<String, String> envData = new HashMap<String, String>();

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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

