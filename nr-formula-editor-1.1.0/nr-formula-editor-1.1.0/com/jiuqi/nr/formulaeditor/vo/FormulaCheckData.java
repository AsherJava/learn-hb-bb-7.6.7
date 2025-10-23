/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.definition.common.StringUtils;
import java.util.List;

public class FormulaCheckData
extends Formula {
    private String errorMsg;
    private String schemeKey;
    private String description;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;
    private List<String> conditionCodes;

    public String getErrorMsg() {
        return this.errorMsg;
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

    public List<String> getConditionCodes() {
        return this.conditionCodes;
    }

    public void setConditionCodes(List<String> conditionCodes) {
        this.conditionCodes = conditionCodes;
    }

    public void addErrorMsg(String errorMsg) {
        if (StringUtils.isEmpty((String)this.errorMsg)) {
            this.errorMsg = errorMsg;
        } else if (this.errorMsg.indexOf(errorMsg) == -1) {
            this.errorMsg = this.errorMsg + "\n\r" + errorMsg;
        }
    }
}

