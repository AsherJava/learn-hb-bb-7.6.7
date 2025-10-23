/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;

public class SummaryDimensionInfo {
    private DimensionValueSet targetDimValues;
    private DimensionValueSet sourceDimValues;
    private TempAssistantTable mdTempAssistantTable;

    public SummaryDimensionInfo(DimensionValueSet targetDimValues, DimensionValueSet sourceDimValues) {
        this.targetDimValues = targetDimValues;
        this.sourceDimValues = sourceDimValues;
    }

    public TempAssistantTable getMdTempAssistantTable() {
        return this.mdTempAssistantTable;
    }

    public void setMdTempAssistantTable(TempAssistantTable mdTempAssistantTable) {
        this.mdTempAssistantTable = mdTempAssistantTable;
    }

    public DimensionValueSet getTargetDimValues() {
        return this.targetDimValues;
    }

    public DimensionValueSet getSourceDimValues() {
        return this.sourceDimValues;
    }
}

