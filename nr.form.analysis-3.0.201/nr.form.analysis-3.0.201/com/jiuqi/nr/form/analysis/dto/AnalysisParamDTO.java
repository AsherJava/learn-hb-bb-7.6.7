/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.dto.AnalysisFormParamDTO;
import com.jiuqi.nr.form.analysis.dto.AnalysisSchemeParamDTO;
import java.util.List;

public class AnalysisParamDTO {
    private AnalysisSchemeParamDTO analysisScheme;
    private List<AnalysisFormParamDTO> analysisForms;

    public AnalysisParamDTO(AnalysisSchemeParamDTO analysisScheme, List<AnalysisFormParamDTO> analysisForms) {
        this.analysisScheme = analysisScheme;
        this.analysisForms = analysisForms;
    }

    public AnalysisSchemeParamDTO getAnalysisScheme() {
        return this.analysisScheme;
    }

    public List<AnalysisFormParamDTO> getAnalysisForms() {
        return this.analysisForms;
    }
}

