/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.definition.common.StringUtils;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaCheckObj
extends Formula {
    private String errorMsg;
    private String schemeKey;
    private String description;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;
    private List<String> conditionCodes;

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
        if (StringUtils.isEmpty((String)this.errorMsg)) {
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

    public List<String> getConditionCodes() {
        if (this.conditionCodes == null) {
            this.conditionCodes = new ArrayList<String>();
        }
        return this.conditionCodes;
    }

    public void setConditionCodes(List<String> conditionCodes) {
        this.conditionCodes = conditionCodes;
    }
}

