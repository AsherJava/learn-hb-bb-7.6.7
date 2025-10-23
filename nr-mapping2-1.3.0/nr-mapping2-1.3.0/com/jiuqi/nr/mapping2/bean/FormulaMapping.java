/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.bean;

public class FormulaMapping {
    private String key;
    private String mappingScheme;
    private String formulaScheme;
    private String formCode;
    private String formulaCode;
    private String mFormulaScheme;
    private String mFormulaCode;
    private String mFormulaWildcard;

    public FormulaMapping() {
    }

    public FormulaMapping(String mappingScheme, String formulaScheme, String formCode, String formulaCode, String mFormulaScheme, String mFormulaCode) {
        this.mappingScheme = mappingScheme;
        this.formulaScheme = formulaScheme;
        this.formCode = formCode;
        this.formulaCode = formulaCode;
        this.mFormulaScheme = mFormulaScheme;
        this.mFormulaCode = mFormulaCode;
    }

    public FormulaMapping(String key, String mappingScheme, String formulaScheme, String formCode, String formulaCode, String mFormulaScheme, String mFormulaCode) {
        this.key = key;
        this.mappingScheme = mappingScheme;
        this.formulaScheme = formulaScheme;
        this.formCode = formCode;
        this.formulaCode = formulaCode;
        this.mFormulaScheme = mFormulaScheme;
        this.mFormulaCode = mFormulaCode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getmFormulaWildcard() {
        return this.mFormulaWildcard;
    }

    public void setmFormulaWildcard(String mFormulaWildcard) {
        this.mFormulaWildcard = mFormulaWildcard;
    }
}

