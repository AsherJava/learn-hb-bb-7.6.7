/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;

public interface IDataGatherService {
    public IRegionDataSet gradeGather(IRegionDataSet var1, RegionGradeInfo var2, RegionRelation var3);

    public IRegionDataSet gradeGather(IRegionDataSet var1, RegionRelation var2);
}

