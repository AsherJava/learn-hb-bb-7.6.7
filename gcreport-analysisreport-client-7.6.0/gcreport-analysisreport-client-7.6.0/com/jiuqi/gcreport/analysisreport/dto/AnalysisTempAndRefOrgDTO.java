/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 */
package com.jiuqi.gcreport.analysisreport.dto;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;

public class AnalysisTempAndRefOrgDTO {
    private AnalysisTemp analysisTemp;
    private AnalysisReportRefOrgDTO refOrgDTO;

    public AnalysisTempAndRefOrgDTO() {
    }

    public AnalysisTempAndRefOrgDTO(AnalysisTemp analysisTemp, AnalysisReportRefOrgDTO refOrgDTO) {
        this.analysisTemp = analysisTemp;
        this.refOrgDTO = refOrgDTO;
    }

    public AnalysisTemp getAnalysisTemp() {
        return this.analysisTemp;
    }

    public void setAnalysisTemp(AnalysisTemp analysisTemp) {
        this.analysisTemp = analysisTemp;
    }

    public AnalysisReportRefOrgDTO getRefOrgDTO() {
        return this.refOrgDTO;
    }

    public void setRefOrgDTO(AnalysisReportRefOrgDTO refOrgDTO) {
        this.refOrgDTO = refOrgDTO;
    }
}

