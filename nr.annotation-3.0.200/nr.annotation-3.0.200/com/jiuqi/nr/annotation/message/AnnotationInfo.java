/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.annotation.message;

import com.jiuqi.nr.annotation.message.RowInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class AnnotationInfo {
    private DimensionCombination dimensionCombination;
    private List<RowInfo> rowInfos;

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public List<RowInfo> getRowInfos() {
        return this.rowInfos;
    }

    public void setRowInfos(List<RowInfo> rowInfos) {
        this.rowInfos = rowInfos;
    }
}

