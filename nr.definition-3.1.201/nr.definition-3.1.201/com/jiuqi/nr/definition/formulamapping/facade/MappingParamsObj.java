/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MappingParamsObj {
    private String schemeKey;
    private String formGroupKey;
    private String formKey;
    private String targetFormulaSchemeKey;
    private String sourceFormulaSchemeKey;
    private List<FormulaMappingObj> formulaMappings;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public List<FormulaMappingObj> getFormulaMappings() {
        return this.formulaMappings;
    }

    public void setFormulaMappings(List<FormulaMappingObj> formulaMappings) {
        this.formulaMappings = formulaMappings;
    }

    public String getTargetFormulaSchemeKey() {
        return this.targetFormulaSchemeKey;
    }

    public void setTargetFormulaSchemeKey(String targetFormulaSchemeKey) {
        this.targetFormulaSchemeKey = targetFormulaSchemeKey;
    }

    public String getSourceFormulaSchemeKey() {
        return this.sourceFormulaSchemeKey;
    }

    public void setSourceFormulaSchemeKey(String sourceFormulaSchemeKey) {
        this.sourceFormulaSchemeKey = sourceFormulaSchemeKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

