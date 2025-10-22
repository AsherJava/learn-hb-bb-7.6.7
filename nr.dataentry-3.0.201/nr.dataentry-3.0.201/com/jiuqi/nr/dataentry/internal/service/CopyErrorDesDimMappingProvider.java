/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.copydes.IDimMappingProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.List;

public class CopyErrorDesDimMappingProvider
implements IDimMappingProvider {
    private DimensionCollection sourceDim;
    private List<DimensionCombination> targetDim;
    private String MDCODE;

    public void setMDCODE(String MDCODE) {
        this.MDCODE = MDCODE;
    }

    public void setSourceDim(DimensionCollection sourceDim) {
        this.sourceDim = sourceDim;
    }

    public void setTargetDim(List<DimensionCombination> targetDim) {
        this.targetDim = targetDim;
    }

    @Override
    public DimensionCollection getQuerySrcDim() {
        return this.sourceDim;
    }

    @Override
    public List<DimensionCombination> getTargetMasterKey(DimensionCombination srcMasterKey) {
        ArrayList<DimensionCombination> res = new ArrayList<DimensionCombination>();
        for (DimensionCombination dimensionCombination : this.targetDim) {
            if (!dimensionCombination.getValue(this.MDCODE).equals(srcMasterKey.getValue(this.MDCODE))) continue;
            res.add(dimensionCombination);
        }
        return res;
    }
}

