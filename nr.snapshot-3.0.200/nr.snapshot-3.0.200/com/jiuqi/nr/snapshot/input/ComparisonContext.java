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
import java.util.List;
import java.util.Map;

public class ComparisonContext {
    private DimensionCombination currentDim;
    private String currentDataName;
    private String formSchemeKey;
    private DataRange dataRange;
    private Map<String, HistoryPeriodType> historyPeriodMap;
    private List<String> snapshotIds;

    public ComparisonContext() {
    }

    public ComparisonContext(DimensionCombination currentDim, String currentDataName, String formSchemeKey, DataRange dataRange, Map<String, HistoryPeriodType> historyPeriodMap, List<String> snapshotIds) {
        this.currentDim = currentDim;
        this.currentDataName = currentDataName;
        this.formSchemeKey = formSchemeKey;
        this.dataRange = dataRange;
        this.historyPeriodMap = historyPeriodMap;
        this.snapshotIds = snapshotIds;
    }

    public DimensionCombination getCurrentDim() {
        return this.currentDim;
    }

    public void setCurrentDim(DimensionCombination currentDim) {
        this.currentDim = currentDim;
    }

    public String getCurrentDataName() {
        return this.currentDataName;
    }

    public void setCurrentDataName(String currentDataName) {
        this.currentDataName = currentDataName;
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

    public Map<String, HistoryPeriodType> getHistoryPeriodMap() {
        return this.historyPeriodMap;
    }

    public void setHistoryPeriodMap(Map<String, HistoryPeriodType> historyPeriodMap) {
        this.historyPeriodMap = historyPeriodMap;
    }

    public List<String> getSnapshotIds() {
        return this.snapshotIds;
    }

    public void setSnapshotIds(List<String> snapshotIds) {
        this.snapshotIds = snapshotIds;
    }
}

