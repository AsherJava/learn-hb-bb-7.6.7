/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.log;

import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;

public class LogDimensionCollection {
    private ArrayDimensionValue dwDimension;
    private FixedDimensionValue periodDimension;

    public void setDw(String entityID, String ... value) {
        this.dwDimension = new ArrayDimensionValue("MDCODE", entityID, value);
    }

    public void setPeriod(String entityID, String value) {
        this.periodDimension = new FixedDimensionValue("DATAPERIOD", entityID, value);
    }

    public ArrayDimensionValue getDwDimension() {
        return this.dwDimension;
    }

    public FixedDimensionValue getPeriodDimension() {
        return this.periodDimension;
    }
}

