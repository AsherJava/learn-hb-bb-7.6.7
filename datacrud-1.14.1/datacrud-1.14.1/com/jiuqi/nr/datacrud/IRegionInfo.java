/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IRegionInfo {
    public String getRegionKey();

    public DimensionCombination getDimensionCombination();

    public RegionRelation getRegionRelation();
}

