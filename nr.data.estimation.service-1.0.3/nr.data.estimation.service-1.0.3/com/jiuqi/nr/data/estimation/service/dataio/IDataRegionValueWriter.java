/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IDataRegionValueWriter {
    public void writeDataRegionValue(IDataRegionInfo var1, DimensionCombination var2, IDataRegionTableData var3) throws Exception;
}

