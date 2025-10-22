/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.bean.FloatRegionCompareDifference
 */
package com.jiuqi.nr.jtable.dataversion.compare;

import com.jiuqi.nr.data.engine.bean.FloatRegionCompareDifference;
import com.jiuqi.nr.jtable.dataversion.compare.AbstractCompareStrategy;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import java.util.UUID;

public class FloatCompareStrategy
extends AbstractCompareStrategy {
    public FloatRegionCompareDifference compareRegionVersionData(RegionData region, JtableContext jtableContext, UUID initialDataVersionId, UUID compareDataVersionId) {
        FloatRegionCompareDifference floatRegionCompareDifference = new FloatRegionCompareDifference();
        floatRegionCompareDifference.setRegionKey(region.getKey());
        floatRegionCompareDifference.setRegionName(region.getTitle());
        RegionDataSet initialRegionDataSet = this.getRegionDataSet(region, jtableContext, initialDataVersionId);
        int initialRows = initialRegionDataSet.getData().size();
        floatRegionCompareDifference.setInitialRows(initialRows);
        RegionDataSet compareRegionDataSet = this.getRegionDataSet(region, jtableContext, compareDataVersionId);
        int compareRows = compareRegionDataSet.getData().size();
        floatRegionCompareDifference.setCompareRows(compareRows);
        floatRegionCompareDifference.setDifferenceRows(compareRows - initialRows);
        if (initialRows == compareRows) {
            return null;
        }
        return floatRegionCompareDifference;
    }
}

