/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.Map;

public class HistoricalAttachmentClearingContext
extends NRContext {
    private String task;
    private Map<String, DimensionValue> dimensionSet;
    private String periodRegionInfo;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getPeriodRegionInfo() {
        return this.periodRegionInfo;
    }

    public void setPeriodRegionInfo(String periodRegionInfo) {
        this.periodRegionInfo = periodRegionInfo;
    }
}

