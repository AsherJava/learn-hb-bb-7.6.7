/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service.dto;

public class FormulaMp {
    private String code;
    private String formulaSchemeTitle;
    private String formCode;

    public FormulaMp() {
    }

    public FormulaMp(String code, String formCode, String formulaSchemeTitle) {
        this.code = code;
        this.formCode = formCode;
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getCode() {
        return this.code;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }
}

