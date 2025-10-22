/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingDataSetStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.RegionGradeDataLoader;
import java.util.ArrayList;
import java.util.List;

public class FloatAutoSumDataSetStrategy
extends AbstractRegionGroupingDataSetStrategy {
    public FloatAutoSumDataSetStrategy(AbstractRegionRelationEvn regionRelationEvn, AbstractRegionGroupingQueryTableStrategy regionGroupingQueryTableStrategy, DataFormaterCache dataFormaterCache, RegionQueryInfo regionQueryInfo) {
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
        String gatherType = this.regionRelationEvn.getGatherType();
        if (StringUtils.isNotEmpty((String)gatherType) && TableGatherType.TABLE_GATHER_NONE == TableGatherType.valueOf((String)gatherType)) {
            return regionDataSet;
        }
        IReadonlyTable regionQueryTable = this.regionGroupingQueryTableStrategy.getRegionQueryTable();
        regionDataSet.setTotalCount(regionQueryTable.getCount());
        RegionGradeDataLoader loader = new RegionGradeDataLoader();
        loader.loadRegionGradeData(regionQueryTable, regionDataSet);
        int startIndex = 0;
        int endIndex = regionQueryTable.getCount();
        PagerInfo pagerInfo = regionQueryInfo.getPagerInfo();
        List<List<Object>> relList = regionDataSet.getRel();
        if (pagerInfo != null && pagerInfo.getLimit() > 0 && regionQueryTable.getCount() == regionQueryTable.getTotalCount()) {
            startIndex = pagerInfo.getOffset() * pagerInfo.getLimit();
            endIndex = (pagerInfo.getOffset() + 1) * pagerInfo.getLimit();
            if (startIndex < 0) {
                startIndex = 0;
            } else if (startIndex > regionQueryTable.getCount()) {
                startIndex = regionQueryTable.getCount();
            }
            if (endIndex < 0) {
                endIndex = 0;
            } else if (endIndex > regionQueryTable.getCount()) {
                endIndex = regionQueryTable.getCount();
            }
            if (startIndex > endIndex) {
                startIndex = endIndex;
            }
        }
        ArrayList<List<Object>> pageRelList = new ArrayList<List<Object>>();
        pageRelList.addAll(relList.subList(startIndex, endIndex));
        regionDataSet.setRel(pageRelList);
        for (int rowIndex = startIndex; rowIndex < endIndex; ++rowIndex) {
            IDataRow dataRow = regionQueryTable.getItem(rowIndex);
            regionDataSet.getData().add(this.getRowData(dataRow, new ArrayList<Integer>()));
        }
        return regionDataSet;
    }
}

