/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.DimensionValue
 */
package com.jiuqi.bde.penetrate.client.dto;

import com.jiuqi.bde.common.dto.DimensionValue;
import java.util.Map;

public class PenetratePeriodOffsetDTO {
    private String endDate;
    private String beginDate;
    private String yearOffset;
    private String periodOffset;
    private Map<String, DimensionValue> dimensionSet;

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getYearOffset() {
        return this.yearOffset;
    }

    public void setYearOffset(String yearOffset) {
        this.yearOffset = yearOffset;
    }

    public String getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

