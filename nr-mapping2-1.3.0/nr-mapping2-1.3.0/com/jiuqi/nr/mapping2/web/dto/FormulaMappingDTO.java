/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package com.jiuqi.nr.mapping2.web.dto;

import com.jiuqi.nr.definition.facade.FormulaDefine;

public class FormulaMappingDTO {
    private String key;
    private String mappingScheme;
    private String formulaScheme;
    private String formCode;
    private String formulaCode;
    private String formulaExp;
    private String mFormulaScheme;
    private String mFormulaCode;
    private String mFormulaWildcard;

    public FormulaMappingDTO(FormulaDefine f, String msKey, String formCode) {
        this.mappingScheme = msKey;
        this.formulaScheme = f.getFormulaSchemeKey();
        this.formCode = formCode;
        this.formulaCode = f.getCode();
        this.formulaExp = f.getExpression();
    }

    public FormulaMappingDTO() {
    }

    public String getMappingScheme() {
        return this.mappingScheme;
    }

    public void setMappingScheme(String mappingScheme) {
        this.mappingScheme = mappingScheme;
    }

    public String getFormulaScheme() {
        return this.formulaScheme;
    }

    public void setFormulaScheme(String formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormulaExp() {
        return this.formulaExp;
    }

    public void setFormulaExp(String formulaExp) {
        this.formulaExp = formulaExp;
    }

    public String getmFormulaScheme() {
        return this.mFormulaScheme;
    }

    public void setmFormulaScheme(String mFormulaScheme) {
        this.mFormulaScheme = mFormulaScheme;
    }

    public String getmFormulaCode() {
        return this.mFormulaCode;
    }

    public void setmFormulaCode(String mFormulaCode) {
        this.mFormulaCode = mFormulaCode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getmFormulaWildcard() {
        return this.mFormulaWildcard;
    }

    public void setmFormulaWildcard(String mFormulaWildcard) {
        this.mFormulaWildcard = mFormulaWildcard;
    }
}

