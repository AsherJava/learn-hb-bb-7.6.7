/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.np.dataengine.definitions.Formula;
import java.util.Objects;

public class FormulaExtDTO
extends Formula
implements Cloneable {
    private String errorMsg;
    private String formulaSchemeKey;
    private String description;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;
    private String unit;

    public String getErrorMsg() {
        return this.errorMsg != null ? this.errorMsg.trim() : null;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = null == this.errorMsg ? (errorMsg != null ? errorMsg.trim() : null) : this.errorMsg + (errorMsg != null ? "\n\r" + errorMsg.trim() : "");
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUseCalculate() {
        return this.useCalculate;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public boolean isUseCheck() {
        return this.useCheck;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public boolean isUseBalance() {
        return this.useBalance;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Object clone() throws CloneNotSupportedException {
        FormulaExtDTO formulaExtDTO = new FormulaExtDTO();
        formulaExtDTO.setUseCheck(this.isUseCheck());
        formulaExtDTO.setUseBalance(this.isUseBalance());
        formulaExtDTO.setFormulaSchemeKey(this.getFormulaSchemeKey());
        formulaExtDTO.setId(this.getId());
        formulaExtDTO.setCode(this.getCode());
        formulaExtDTO.setFormula(this.getFormula());
        formulaExtDTO.setOrder(this.getOrder());
        formulaExtDTO.setReportName(this.getReportName());
        formulaExtDTO.setFormKey(this.getFormKey());
        formulaExtDTO.setMeanning(this.getMeanning());
        formulaExtDTO.setChecktype(this.getChecktype());
        formulaExtDTO.setAutoCalc(this.isAutoCalc());
        formulaExtDTO.setBalanceZBExp(this.getBalanceZBExp());
        formulaExtDTO.setErrorMsg(this.errorMsg);
        formulaExtDTO.setDescription(this.description);
        formulaExtDTO.setUseCalculate(this.useCalculate);
        formulaExtDTO.setUseCheck(this.useCheck);
        formulaExtDTO.setUseBalance(this.useBalance);
        return formulaExtDTO;
    }

    public String getCode() {
        return super.getCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FormulaExtDTO that = (FormulaExtDTO)o;
        return Objects.equals(this.getCode(), that.getCode());
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getCode());
    }
}

