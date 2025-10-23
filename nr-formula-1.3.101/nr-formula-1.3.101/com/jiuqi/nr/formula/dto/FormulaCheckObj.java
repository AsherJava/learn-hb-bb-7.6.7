/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.np.dataengine.definitions.Formula;
import org.springframework.util.StringUtils;

public class FormulaCheckObj
extends Formula {
    private String errorMsg;
    private String schemeKey;
    private String description;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;

    public boolean isUseBalance() {
        return this.useBalance;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void addErrorMsg(String errorMsg) {
        if (StringUtils.isEmpty(this.errorMsg)) {
            this.errorMsg = errorMsg;
        } else if (this.errorMsg.indexOf(errorMsg) == -1) {
            this.errorMsg = this.errorMsg + "\n\r" + errorMsg;
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || ((Object)((Object)this)).getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FormulaCheckObj that = (FormulaCheckObj)((Object)o);
        return this.getFormula().equals(that.getFormula()) && this.getCode().equals(that.getCode()) && this.getReportName().equals(that.getReportName());
    }

    public int hashCode() {
        return super.hashCode();
    }
}

