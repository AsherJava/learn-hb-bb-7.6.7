/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

public class FormulaMappingObj {
    private String code;
    private String formulaSchemeTitle;
    private String formCode;
    private Integer globRow;
    private Integer globCol;

    public FormulaMappingObj() {
    }

    public FormulaMappingObj(String code, String formulaSchemeTitle, String formCode, Integer globRow, Integer globCol) {
        this.code = code;
        this.formulaSchemeTitle = formulaSchemeTitle;
        this.formCode = formCode;
        this.globRow = globRow;
        this.globCol = globCol;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(Integer globRow) {
        this.globRow = globRow;
    }

    public Integer getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(Integer globCol) {
        this.globCol = globCol;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }
}

