/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseFloatRegionDataStrategy;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FloatGroupRegionDataStrategy
extends BaseFloatRegionDataStrategy<IGroupingQuery>
implements IRegionDataSetStrategy {
    public FloatGroupRegionDataStrategy(RegionDataSetStrategyFactory factory) {
        super(factory);
    }

    protected boolean addGroupBy() {
        Iterator<String> groupItr = this.queryInfo.groupItr();
        if (groupItr == null) {
            return false;
        }
        boolean flag = false;
        while (groupItr.hasNext()) {
            DataField dataField;
            String linkKey = groupItr.next();
            MetaData byLink = this.relation.getMetaDataByLink(linkKey);
            if (byLink == null || (dataField = byLink.getDataField()) == null) continue;
            DataFieldType dataFieldType = dataField.getDataFieldType();
            if (dataFieldType != DataFieldType.STRING && dataFieldType != DataFieldType.INTEGER && dataFieldType != DataFieldType.DATE && dataFieldType != DataFieldType.BOOLEAN && dataFieldType != DataFieldType.DATE_TIME && dataFieldType != DataFieldType.UUID && dataFieldType != DataFieldType.BIGDECIMAL) {
                return false;
            }
            ((IGroupingQuery)this.dataQuery).addGroupColumn(byLink.getIndex());
            flag = true;
        }
        return flag;
    }

    @Override
    protected IGroupingQuery getDataQuery(IQueryInfo queryInfo, RegionRelation relation) {
        return this.dataEngineService.getGroupingQuery(relation);
    }

    @Override
    public IRegionDataSet queryData(IQueryInfo queryInfo, RegionRelation relation) {
        IGroupingTable table;
        PageInfo pageInfo;
        super.initDataQuery(queryInfo, relation);
        Iterator<String> selectLinkItr = queryInfo.selectLinkItr();
        List<MetaData> metaData = relation.getMetaData(selectLinkItr);
        RegionDataSet set = new RegionDataSet(metaData, Collections.emptyList());
        set.setRegionKey(queryInfo.getRegionKey());
        DimensionCombination dimensionCombination = queryInfo.getDimensionCombination();
        set.setMasterDimension(dimensionCombination);
        if (metaData.isEmpty()) {
            return set;
        }
        super.addQueryCol(metaData);
        super.addFilter();
        this.addOrder();
        boolean gr = this.addGroupBy();
        if (!gr) {
            return set;
        }
        this.addPage();
        int begin = -1;
        int end = -1;
        if (this.isIgnoreDbPage() && (pageInfo = queryInfo.getPageInfo()) != null) {
            begin = pageInfo.getPageIndex() * pageInfo.getRowsPerPage();
            end = (pageInfo.getPageIndex() + 1) * pageInfo.getRowsPerPage();
        }
        try {
            table = ((IGroupingQuery)this.dataQuery).executeReader(this.context);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", e);
        }
        List<IRowData> rowData = this.buildData((IReadonlyTable)table, metaData, begin, end);
        set.setRows(rowData);
        set.setTotalCount(table.getTotalCount());
        return set;
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo queryInfo, RegionRelation relation) {
        return null;
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        return -1;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        return null;
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        return -1;
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        return null;
    }
}

