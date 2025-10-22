/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.io.dataset.IRegionDataSet;

public interface IRegionDataSetProvider {
    public IRegionDataSet get(String var1, DimensionCombination var2);
}

