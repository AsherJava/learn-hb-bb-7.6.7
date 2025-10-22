/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.logic.api.param.cksr;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatus;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CheckStatusRecord {
    private String recordKey;
    private String checkActionID;
    private ActionEnum checkActionType;
    private String formulaSchemeKey;
    private String formKey;
    private DimensionValueSet dimensionValueSet = new DimensionValueSet();
    private CheckStatus checkStatus;
    private Instant checkTime;
    private Map<Integer, Integer> errorCount = new HashMap<Integer, Integer>();

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getCheckActionID() {
        return this.checkActionID;
    }

    public void setCheckActionID(String checkActionID) {
        this.checkActionID = checkActionID;
    }

    public ActionEnum getCheckActionType() {
        return this.checkActionType;
    }

    public void setCheckActionType(ActionEnum checkActionType) {
        this.checkActionType = checkActionType;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public CheckStatus getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Instant getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Instant checkTime) {
        this.checkTime = checkTime;
    }

    public Map<Integer, Integer> getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Map<Integer, Integer> errorCount) {
        this.errorCount = errorCount;
    }
}

