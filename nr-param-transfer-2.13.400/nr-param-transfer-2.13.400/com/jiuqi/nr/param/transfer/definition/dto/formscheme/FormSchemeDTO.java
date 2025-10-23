/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormulaVariableDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.MultCheckParamDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.taskgroup.TaskLinkDTO;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormSchemeDTO
extends BaseDTO {
    private FormSchemeInfoDTO formSchemeInfo;
    private List<TaskLinkDTO> taskLinks;
    private List<FormulaVariableDTO> formulaVariables;
    private TransformReportDefine transformReportDefine;
    private List<SchemePeriodLinkDTO> periodLinks;
    private AnalysisSchemeDTO analysisSchemeDTO;
    private DesParamLanguageDTO desParamLanguageDTO;
    private List<MultCheckParamDTO> multCheckParamDTOS;

    public FormSchemeInfoDTO getFormSchemeInfo() {
        return this.formSchemeInfo;
    }

    public void setFormSchemeInfo(FormSchemeInfoDTO formSchemeInfo) {
        this.formSchemeInfo = formSchemeInfo;
    }

    public List<TaskLinkDTO> getTaskLinks() {
        return this.taskLinks;
    }

    public void setTaskLinks(List<TaskLinkDTO> taskLinks) {
        this.taskLinks = taskLinks;
    }

    public List<FormulaVariableDTO> getFormulaVariables() {
        return this.formulaVariables;
    }

    public void setFormulaVariables(List<FormulaVariableDTO> formulaVariables) {
        this.formulaVariables = formulaVariables;
    }

    public TransformReportDefine getTransformReportDefine() {
        return this.transformReportDefine;
    }

    public void setTransformReportDefine(TransformReportDefine transformReportDefine) {
        this.transformReportDefine = transformReportDefine;
    }

    public List<SchemePeriodLinkDTO> getPeriodLinks() {
        return this.periodLinks;
    }

    public void setPeriodLinks(List<SchemePeriodLinkDTO> periodLinks) {
        this.periodLinks = periodLinks;
    }

    public AnalysisSchemeDTO getAnalysisSchemeDTO() {
        return this.analysisSchemeDTO;
    }

    public void setAnalysisSchemeDTO(AnalysisSchemeDTO analysisSchemeDTO) {
        this.analysisSchemeDTO = analysisSchemeDTO;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public List<MultCheckParamDTO> getMultCheckParamDTOS() {
        return this.multCheckParamDTOS;
    }

    public void setMultCheckParamDTOS(List<MultCheckParamDTO> multCheckParamDTOS) {
        this.multCheckParamDTOS = multCheckParamDTOS;
    }

    public static FormSchemeDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (FormSchemeDTO)objectMapper.readValue(bytes, FormSchemeDTO.class);
    }
}

