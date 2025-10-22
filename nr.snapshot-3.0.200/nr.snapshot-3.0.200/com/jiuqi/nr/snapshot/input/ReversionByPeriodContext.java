/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.consts.HistoryPeriodType;
import com.jiuqi.nr.snapshot.message.DataRange;

public class ReversionByPeriodContext {
    private DimensionCombination currentDim;
    private String formSchemeKey;
    private DataRange dataRange;
    private HistoryPeriodType historyPeriodType;
    private String historyDataName;

    public ReversionByPeriodContext() {
    }

    public ReversionByPeriodContext(DimensionCombination currentDim, String formSchemeKey, DataRange dataRange, HistoryPeriodType historyPeriodType, String historyDataName) {
        this.currentDim = currentDim;
        this.formSchemeKey = formSchemeKey;
        this.dataRange = dataRange;
        this.historyPeriodType = historyPeriodType;
        this.historyDataName = historyDataName;
    }

    public DimensionCombination getCurrentDim() {
        return this.currentDim;
    }

    public void setCurrentDim(DimensionCombination currentDim) {
        this.currentDim = currentDim;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DataRange getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(DataRange dataRange) {
        this.dataRange = dataRange;
    }

    public HistoryPeriodType getHistoryPeriodType() {
        return this.historyPeriodType;
    }

    public void setHistoryPeriodType(HistoryPeriodType historyPeriodType) {
        this.historyPeriodType = historyPeriodType;
    }

    public String getHistoryDataName() {
        return this.historyDataName;
    }

    public void setHistoryDataName(String historyDataName) {
        this.historyDataName = historyDataName;
    }
}

