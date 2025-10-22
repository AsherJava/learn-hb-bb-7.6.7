/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.dto.DestDimsParamDTO;
import com.jiuqi.nr.form.analysis.dto.SrcDimsParamDTO;

public class AnalysisSchemeParamDTO {
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String srcTaskKey;
    private String srcFormSchemeKey;
    private SrcDimsParamDTO srcDims;
    private DestDimsParamDTO distDims;
    private String condition;

    public String getSrcTaskKey() {
        return this.srcTaskKey;
    }

    public void setSrcTaskKey(String srcTaskKey) {
        this.srcTaskKey = srcTaskKey;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public SrcDimsParamDTO getSrcDims() {
        return this.srcDims;
    }

    public void setSrcDims(SrcDimsParamDTO srcDims) {
        this.srcDims = srcDims;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public DestDimsParamDTO getDistDims() {
        return this.distDims;
    }

    public void setDistDims(DestDimsParamDTO distDims) {
        this.distDims = distDims;
    }
}

