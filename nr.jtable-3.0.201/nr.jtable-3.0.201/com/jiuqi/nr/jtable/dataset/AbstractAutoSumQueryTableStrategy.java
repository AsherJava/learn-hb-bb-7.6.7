/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.jtable.dataset.AbstractRegionGroupingQueryTableStrategy;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.dataset.impl.GroupingRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.util.SortingMethod;
import java.util.List;
import java.util.Map;

public abstract class AbstractAutoSumQueryTableStrategy
extends AbstractRegionGroupingQueryTableStrategy {
    public AbstractAutoSumQueryTableStrategy(IGroupingQuery groupingQuery, AbstractRegionRelationEvn regionRelationEvn, GroupingRelationEvn groupingRelationEvn, RegionQueryInfo regionQueryInfo) {
        super(groupingQuery, regionRelationEvn, groupingRelationEvn, regionQueryInfo);
    }

    protected void addAutoSumColumn() {
        if (!this.regionQueryInfo.getRestructureInfo().isUnitAutoSum()) {
            return;
        }
        String unitDimension = this.groupingRelationEvn.getUnitDimension();
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        if (!dataLinkFieldMap.isEmpty()) {
            FieldData field = this.regionRelationEvn.getDataLinkFieldMap().values().iterator().next();
            List dataFieldDeployInfos = this.deployInfoService.getByDataFieldKeys(new String[]{field.getFieldKey()});
            String unitFieldKey = this.groupingRelationEvn.getUnitFieldKey(((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableName());
            int unitIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, unitFieldKey);
            this.regionRelationEvn.putCellIndex(unitDimension, unitIndex);
            this.groupingLinks.add(unitDimension);
            this.jtableDataEngineService.setEntityLevelGather(this.groupingQuery, this.groupingRelationEvn.getTargetKey(), unitIndex, this.groupingRelationEvn.getEntityViewData().getKey(), this.groupingRelationEvn.getUnitLevels(), this.regionQueryInfo.getContext().getSumScheme());
            this.groupingQuery.addGroupColumn(unitIndex);
        }
    }

    @Override
    protected void addPeriodColumn() {
        if (!this.regionQueryInfo.getRestructureInfo().isPeriodAutoSum()) {
            return;
        }
        String periodDimension = this.groupingRelationEvn.getPeriodDimension();
        Map<String, FieldData> dataLinkFieldMap = this.regionRelationEvn.getDataLinkFieldMap();
        if (!dataLinkFieldMap.isEmpty()) {
            FieldData field = this.regionRelationEvn.getDataLinkFieldMap().values().iterator().next();
            List dataFieldDeployInfos = this.deployInfoService.getByDataFieldKeys(new String[]{field.getFieldKey()});
            String periodFieldKey = this.groupingRelationEvn.getPeriodFieldKey(((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableName());
            List<Integer> periodLevels = this.groupingRelationEvn.getPeriodLevels();
            if (periodLevels != null && periodLevels.size() > 0 && StringUtils.isNotEmpty((String)periodFieldKey)) {
                int periodIndex = this.jtableDataEngineService.addQueryColumn((ICommonQuery)this.groupingQuery, periodFieldKey);
                this.regionRelationEvn.putCellIndex(periodDimension, periodIndex);
                this.groupingLinks.add(periodDimension);
                this.groupingQuery.setPeriodLevelGather(periodIndex, periodLevels);
                this.groupingQuery.addGroupColumn(periodIndex);
                this.jtableDataEngineService.addOrderByItem((ICommonQuery)this.groupingQuery, periodFieldKey, false);
            }
        }
    }

    @Override
    protected void addColumnFilter(String dataLinkKey, int columnIndex) {
        if (this.regionQueryInfo != null && this.regionQueryInfo.getFilterInfo().getCellQuerys() != null) {
            List<CellQueryInfo> cellQueryInfos = this.regionQueryInfo.getFilterInfo().getCellQuerys();
            for (CellQueryInfo cellQueryInfo : cellQueryInfos) {
                if (dataLinkKey != cellQueryInfo.getCellKey()) continue;
                SortingMethod sortingMe = new SortingMethod();
                StringBuffer cellFilterBuf = new StringBuffer();
                FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(dataLinkKey);
                fieldData.setDataLinkKey(dataLinkKey);
                cellFilterBuf = sortingMe.sortingMethod(cellQueryInfo, fieldData, (ICommonQuery)this.groupingQuery, columnIndex, this.regionQueryInfo.getContext());
                if (this.filterBuf.length() == 0) {
                    this.filterBuf.append(cellFilterBuf);
                    break;
                }
                if (this.filterBuf.length() == 0 || cellFilterBuf.length() == 0) break;
                this.filterBuf.append("AND" + cellFilterBuf);
                break;
            }
        }
    }
}

