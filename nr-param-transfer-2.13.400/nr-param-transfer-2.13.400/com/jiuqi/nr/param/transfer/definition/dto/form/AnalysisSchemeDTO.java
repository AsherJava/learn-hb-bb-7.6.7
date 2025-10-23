/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalysisSchemeDTO {
    private AnalysisSchemeParamDefineImpl analysisScheme;

    public AnalysisSchemeParamDefineImpl getAnalysisScheme() {
        return this.analysisScheme;
    }

    public void setAnalysisScheme(AnalysisSchemeParamDefineImpl analysisScheme) {
        this.analysisScheme = analysisScheme;
    }
}

