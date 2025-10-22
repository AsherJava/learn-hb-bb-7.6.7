/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.crud;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IRegionDataSetStrategy {
    public IRegionDataSet queryData(IQueryInfo var1, RegionRelation var2);

    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo var1, RegionRelation var2);

    public int queryDataCount(IQueryInfo var1, RegionRelation var2);

    public int queryDataIndex(IQueryInfo var1, RegionRelation var2, String var3);

    public IRegionDataSet regionDataLocate(IQueryInfo var1, RegionRelation var2, String var3, int var4);

    public int queryDataIndex(IQueryInfo var1, RegionRelation var2, DimensionCombination var3);

    public IRegionDataSet regionDataLocate(IQueryInfo var1, RegionRelation var2, DimensionCombination var3, int var4);
}

