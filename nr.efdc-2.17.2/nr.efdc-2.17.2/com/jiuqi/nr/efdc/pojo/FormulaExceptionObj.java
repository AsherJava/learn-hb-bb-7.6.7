/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.pojo;

public class FormulaExceptionObj {
    private String code;
    private String formula;
    private String efdcformula;
    private boolean isfloat;
    private String errorMsg;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getEfdcformula() {
        return this.efdcformula;
    }

    public void setEfdcformula(String efdcformula) {
        this.efdcformula = efdcformula;
    }

    public boolean isfloat() {
        return this.isfloat;
    }

    public void setIsfloat(boolean isfloat) {
        this.isfloat = isfloat;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

