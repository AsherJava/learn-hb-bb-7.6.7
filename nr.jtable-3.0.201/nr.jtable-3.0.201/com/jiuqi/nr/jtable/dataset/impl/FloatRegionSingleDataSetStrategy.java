/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IndexItem
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IndexItem;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionSingleQueryTabeStrategy;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.ArrayList;
import java.util.List;

public class FloatRegionSingleDataSetStrategy
extends AbstractRegionDataSetStrategy {
    public FloatRegionSingleDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, FloatRegionSingleQueryTabeStrategy regionQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
        super(regionRelationEvn, regionQueryTableStrategy, dataFormaterCache, regionQueryInfo);
    }

    public RegionSingleDataSet getLocatDataByFloatOrder(String floatOrder, int offset) {
        RegionSingleDataSet regionSingleDataSet = new RegionSingleDataSet();
        this.dataFormaterCache.init(regionSingleDataSet);
        List<String> cells = this.regionQueryTableStrategy.getCells();
        regionSingleDataSet.getCells().put(this.regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (cells.isEmpty()) {
            return regionSingleDataSet;
        }
        IReadonlyTable regionQueryTable = this.regionQueryTableStrategy.getRegionLocatQueryTable(floatOrder, offset);
        if (regionQueryTable != null && regionQueryTable.getCount() > 0) {
            IDataRow dataRow = regionQueryTable.getItem(0);
            List<Object> rowData = this.getRowData(dataRow, new ArrayList<Integer>());
            int bizKeyIndex = cells.indexOf("ID");
            DimensionValueSet locateDimensionValueSet = this.getRowDimensionValueSet(rowData.get(bizKeyIndex).toString());
            IndexItem indexItem = this.regionQueryTableStrategy.getRowIndex(locateDimensionValueSet);
            PagerInfo pagerInfo = regionSingleDataSet.getPagerInfo();
            pagerInfo.setOffset(indexItem.getCurrentIndex(locateDimensionValueSet));
            pagerInfo.setLimit(1);
            pagerInfo.setTotal(indexItem.getTotalCount());
            regionSingleDataSet.getData().add(rowData);
        }
        return regionSingleDataSet;
    }

    public RegionSingleDataSet getLocatDataByID(String dataId) {
        RegionSingleDataSet regionSingleDataSet = new RegionSingleDataSet();
        this.dataFormaterCache.init(regionSingleDataSet);
        List<String> cells = this.regionQueryTableStrategy.getCells();
        regionSingleDataSet.getCells().put(this.regionRelationEvn.getRegionData().getKey().toString(), cells);
        if (cells.isEmpty()) {
            return regionSingleDataSet;
        }
        DimensionValueSet locateDimensionValueSet = this.getRowDimensionValueSet(dataId);
        IReadonlyTable regionQueryTable = this.regionQueryTableStrategy.getRegionLocatQueryTable(locateDimensionValueSet);
        if (regionQueryTable != null && regionQueryTable.getCount() > 0) {
            IDataRow dataRow = regionQueryTable.getItem(0);
            List<Object> rowData = this.getRowData(dataRow, new ArrayList<Integer>());
            IndexItem indexItem = this.regionQueryTableStrategy.getRowIndex(locateDimensionValueSet);
            PagerInfo pagerInfo = regionSingleDataSet.getPagerInfo();
            pagerInfo.setOffset(indexItem.getCurrentIndex(locateDimensionValueSet));
            pagerInfo.setLimit(1);
            pagerInfo.setTotal(indexItem.getTotalCount());
            regionSingleDataSet.getData().add(rowData);
        }
        return regionSingleDataSet;
    }
}

