/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IDataQueryService {
    public IRegionDataSet queryRegionData(IQueryInfo var1) throws CrudException;

    public IRegionDataSet queryRegionData(IQueryInfo var1, RegionGradeInfo var2) throws CrudException;

    public int queryRegionDataCount(IQueryInfo var1) throws CrudException;

    public int queryRegionDataCount(IQueryInfo var1, RegionGradeInfo var2) throws CrudException;

    public int queryDataIndex(IQueryInfo var1, String var2) throws CrudException;

    public IRegionDataSet dataLocate(IQueryInfo var1, String var2) throws CrudException;

    public IRegionDataSet dataLocate(IQueryInfo var1, String var2, int var3) throws CrudException;

    public int queryDataIndex(IQueryInfo var1, DimensionCombination var2) throws CrudException;

    public IRegionDataSet dataLocate(IQueryInfo var1, DimensionCombination var2) throws CrudException;

    public IRegionDataSet dataLocate(IQueryInfo var1, RegionGradeInfo var2, DimensionCombination var3) throws CrudException;

    public IRegionDataSet dataLocate(IQueryInfo var1, DimensionCombination var2, int var3) throws CrudException;

    public MultiDimensionalDataSet queryMultiDimRegionData(IQueryInfo var1) throws CrudException;

    public MultiDimensionalDataSet queryMultiDimRegionData(IQueryInfo var1, RegionGradeInfo var2) throws CrudException;
}

