/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.jtable.dataset.AbstractRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.ArrayList;
import java.util.List;

public class SimpleRegionDataSetStrategy
extends AbstractRegionDataSetStrategy {
    public SimpleRegionDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionQueryTableStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        super(regionRelationEvn, regionQueryTableStrategy, dataFormaterCache, regionQueryInfo);
    }

    public RegionDataSet getRegionDataSet() {
        return this.getRegionDataSet(false);
    }

    public RegionDataSet getRegionDataSet(boolean isVersionData) {
        RegionDataSet regionDataSet = new RegionDataSet();
        RegionQueryInfo regionQueryInfo = this.regionQueryTableStrategy.getRegionQueryInfo();
        RegionAnnotationResult regionAnnotationResult = this.regionRelationEvn.getRegionAnnotationResult(regionQueryInfo.getContext());
        regionDataSet.setAnnotationResult(regionAnnotationResult);
        this.dataFormaterCache.init(regionDataSet);
        List<String> cells = this.regionQueryTableStrategy.getCells();
        regionDataSet.getCells().put(this.regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (cells.isEmpty()) {
            return regionDataSet;
        }
        if (isVersionData) {
            return regionDataSet;
        }
        IReadonlyTable regionQueryTable = this.regionQueryTableStrategy.getRegionQueryTable();
        for (int i = 0; i < regionQueryTable.getCount(); ++i) {
            IDataRow dataRow = regionQueryTable.getItem(i);
            List<Object> rowData = this.getRowData(dataRow, new ArrayList<Integer>());
            boolean empty = true;
            for (Object cellData : rowData) {
                if (cellData == null || !StringUtils.isNotEmpty((String)cellData.toString())) continue;
                empty = false;
                break;
            }
            if (empty) continue;
            regionDataSet.getData().add(rowData);
        }
        if (regionQueryTable.getCount() > 0) {
            regionDataSet.setRegionOnlyHasExtentGridData(false);
        }
        regionDataSet.setTotalCount(regionDataSet.getData().size());
        return regionDataSet;
    }
}

