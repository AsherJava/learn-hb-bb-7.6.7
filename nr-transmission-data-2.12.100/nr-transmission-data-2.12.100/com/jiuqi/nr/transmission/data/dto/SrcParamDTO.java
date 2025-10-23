/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.transmission.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.transmission.data.dto.SrcParam.SrcParamDefinitionObj;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SrcParamDTO {
    private SrcParamDefinitionObj taskDefinition;
    private SrcParamDefinitionObj formSchemeDefinition;
    private List<SrcParamDefinitionObj> formulaSchemeDefinitions;
    private List<SrcParamDefinitionObj> formDefinitions;

    public SrcParamDefinitionObj getTaskDefinition() {
        return this.taskDefinition;
    }

    public void setTaskDefinition(SrcParamDefinitionObj taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public SrcParamDefinitionObj getFormSchemeDefinition() {
        return this.formSchemeDefinition;
    }

    public void setFormSchemeDefinition(SrcParamDefinitionObj formSchemeDefinition) {
        this.formSchemeDefinition = formSchemeDefinition;
    }

    public List<SrcParamDefinitionObj> getFormulaSchemeDefinitions() {
        return this.formulaSchemeDefinitions;
    }

    public void setFormulaSchemeDefinitions(List<SrcParamDefinitionObj> formulaSchemeDefinitions) {
        this.formulaSchemeDefinitions = formulaSchemeDefinitions;
    }

    public List<SrcParamDefinitionObj> getFormDefinitions() {
        return this.formDefinitions;
    }

    public void setFormDefinitions(List<SrcParamDefinitionObj> formDefinitions) {
        this.formDefinitions = formDefinitions;
    }
}

