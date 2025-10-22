/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface MultiDimensionalDataSet
extends IRegionDataSet {
    public DimensionCollection getMasterDimensionCollection();

    public IRegionDataSet getRegionDataSet(DimensionCombination var1);

    public IRegionDataSet removeRegionDataSet(DimensionCombination var1);

    public boolean containsRegionDataSet(DimensionCombination var1);
}

