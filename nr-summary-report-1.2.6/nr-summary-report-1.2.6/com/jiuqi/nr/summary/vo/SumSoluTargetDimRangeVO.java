/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.model.soulution.TargetDimensionRange;
import java.util.List;

public class SumSoluTargetDimRangeVO {
    private TargetDimensionRange targetDimensionRange;
    private List<String> targetDimensionValues;
    private String targetDimensionFilter;

    public TargetDimensionRange getTargetDimensionRange() {
        return this.targetDimensionRange;
    }

    public void setTargetDimensionRange(TargetDimensionRange targetDimensionRange) {
        this.targetDimensionRange = targetDimensionRange;
    }

    public List<String> getTargetDimensionValues() {
        return this.targetDimensionValues;
    }

    public void setTargetDimensionValues(List<String> targetDimensionValues) {
        this.targetDimensionValues = targetDimensionValues;
    }

    public String getTargetDimensionFilter() {
        return this.targetDimensionFilter;
    }

    public void setTargetDimensionFilter(String targetDimensionFilter) {
        this.targetDimensionFilter = targetDimensionFilter;
    }
}

