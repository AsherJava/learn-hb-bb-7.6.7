/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.ArrayList;
import java.util.List;

public class NrWrtiebackDataRegion {
    private DimensionValueSet regionKey;
    private List<Integer> resultRowIndexes = new ArrayList<Integer>();

    public NrWrtiebackDataRegion(DimensionValueSet regionKey) {
        this.regionKey = regionKey;
    }

    public DimensionValueSet getRegionKey() {
        return this.regionKey;
    }

    public List<Integer> getResultRowIndexes() {
        return this.resultRowIndexes;
    }

    public void setRegionKey(DimensionValueSet regionKey) {
        this.regionKey = regionKey;
    }
}

