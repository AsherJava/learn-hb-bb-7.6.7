/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.ArrayList;
import java.util.List;

public class SimpleAutoSumDataSetStrategy
extends AbstractRegionGroupingDataSetStrategy {
    public SimpleAutoSumDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionGroupingQueryTableStrategy regionGroupingQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        super(regionRelationEvn, regionGroupingQueryTableStrategy, dataFormaterCache, regionQueryInfo);
    }

    public RegionDataSet getRegionDataSet() {
        RegionDataSet regionDataSet = new RegionDataSet();
        regionDataSet.setSumData(true);
        RegionQueryInfo regionQueryInfo = this.regionGroupingQueryTableStrategy.getRegionQueryInfo();
        RegionAnnotationResult regionAnnotationResult = this.regionRelationEvn.getRegionAnnotationResult(regionQueryInfo.getContext());
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.dataFormaterCache.init(regionDataSet);
        List<String> cells = this.regionGroupingQueryTableStrategy.getCells();
        regionDataSet.getCells().put(this.regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (cells.isEmpty()) {
            return regionDataSet;
        }
        IReadonlyTable regionQueryTable = this.regionGroupingQueryTableStrategy.getRegionQueryTable();
        if (regionQueryTable.getTotalCount() > 0) {
            IDataRow dataRow = regionQueryTable.getItem(0);
            regionDataSet.getData().add(this.getRowData(dataRow, new ArrayList<Integer>()));
            regionDataSet.setTotalCount(1);
        }
        return regionDataSet;
    }
}

