/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.common.service.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterDim {
    private DimensionValueSet fixedFilterDims;
    private List<String> dynamicsFilterDims;
    private Set<String> filterDims = new HashSet<String>();

    public DimensionValueSet getFixedFilterDims() {
        return this.fixedFilterDims;
    }

    public void setFixedFilterDims(DimensionValueSet fixedFilterDims) {
        for (int i = 0; i < fixedFilterDims.getDimensionSet().size(); ++i) {
            this.filterDims.add(fixedFilterDims.getDimensionSet().get(i));
        }
        this.fixedFilterDims = fixedFilterDims;
    }

    public List<String> getDynamicsFilterDims() {
        return this.dynamicsFilterDims;
    }

    public void setDynamicsFilterDims(List<String> dynamicsFilterDims) {
        this.dynamicsFilterDims = dynamicsFilterDims;
        this.filterDims.addAll(dynamicsFilterDims);
    }

    public Set<String> getFilterDims() {
        return this.filterDims;
    }

    public void setFilterDims(Set<String> filterDims) {
        this.filterDims = filterDims;
    }

    public boolean isFilterDim() {
        return this.fixedFilterDims != null || this.dynamicsFilterDims != null;
    }
}

