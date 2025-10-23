/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.definition.dto.formula;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaSchemeInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaSchemeDTO
extends BaseDTO {
    private FormulaSchemeInfoDTO formulaSchemeInfo;
    private List<FormulaDTO> formulas;
    private List<FormulaConditionLinkDTO> formulaConditionLinks;
    private List<FormulaConditionDTO> formulaConditions;
    private DesParamLanguageDTO desParamLanguageDTO;

    public FormulaSchemeInfoDTO getFormulaSchemeInfo() {
        return this.formulaSchemeInfo;
    }

    public void setFormulaSchemeInfo(FormulaSchemeInfoDTO formulaSchemeInfo) {
        this.formulaSchemeInfo = formulaSchemeInfo;
    }

    public List<FormulaDTO> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<FormulaDTO> formulas) {
        this.formulas = formulas;
    }

    public static FormulaSchemeDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (FormulaSchemeDTO)objectMapper.readValue(bytes, FormulaSchemeDTO.class);
    }

    public List<FormulaConditionLinkDTO> getFormulaConditionLinks() {
        return this.formulaConditionLinks;
    }

    public void setFormulaConditionLinks(List<FormulaConditionLinkDTO> formulaConditionLinks) {
        this.formulaConditionLinks = formulaConditionLinks;
    }

    public List<FormulaConditionDTO> getFormulaConditions() {
        return this.formulaConditions;
    }

    public void setFormulaConditions(List<FormulaConditionDTO> formulaConditions) {
        this.formulaConditions = formulaConditions;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }
}

