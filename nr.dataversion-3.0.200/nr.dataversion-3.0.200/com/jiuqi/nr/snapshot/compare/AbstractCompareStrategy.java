/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.snapshot.IRegionCompareDifference;

public abstract class AbstractCompareStrategy {
    public abstract IRegionCompareDifference compareRegionVersionData(RegionData var1, TableContext var2, String var3, String var4, String var5);

    public RegionDataSet getRegionDataSet(RegionData region, TableContext tableContext, String DimensionValue2, String dimensionName) {
        DimensionValue dataVersionDimension = new DimensionValue();
        dataVersionDimension.setName(dimensionName);
        dataVersionDimension.setValue(DimensionValue2);
        return new RegionDataSet(tableContext, region);
    }
}

