/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.io.dataset.impl.RegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.snapshot.bean.FloatRegionCompareDifference;
import com.jiuqi.nr.snapshot.compare.AbstractCompareStrategy;

public class FloatCompareStrategy
extends AbstractCompareStrategy {
    @Override
    public FloatRegionCompareDifference compareRegionVersionData(RegionData region, TableContext tableContext, String initialDimension, String compareDimension, String dimensionName) {
        FloatRegionCompareDifference floatRegionCompareDifference = new FloatRegionCompareDifference();
        floatRegionCompareDifference.setRegionKey(region.getKey());
        floatRegionCompareDifference.setRegionName(region.getTitle());
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, tableContext, initialDimension, dimensionName);
        initialRegionDataSet.hasNext();
        initialRegionDataSet.next();
        int initialRows = initialRegionDataSet.getTotalCount();
        floatRegionCompareDifference.setInitialRows(initialRows);
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, tableContext, compareDimension, dimensionName);
        compareRegionDataSet.hasNext();
        compareRegionDataSet.next();
        int compareRows = compareRegionDataSet.getTotalCount();
        floatRegionCompareDifference.setCompareRows(compareRows);
        floatRegionCompareDifference.setDifferenceRows(compareRows - initialRows);
        if (initialRows == compareRows) {
            return null;
        }
        return floatRegionCompareDifference;
    }
}

