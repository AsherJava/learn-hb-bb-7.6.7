/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.LinkSort
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.SortMode
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import java.util.List;

public class FloatOrderCalc {
    private JtableContext jtableContext;
    private RegionRelation regionRelation;
    private IDataQueryService dataQueryService;
    private String regionKey;
    private double maxFloatOrder = -1.0;

    public void setMaxFloatOrder(double maxFloatOrder) {
        this.maxFloatOrder = maxFloatOrder;
    }

    public FloatOrderCalc(JtableContext jtableContext, String regionKey, RegionRelation regionRelation) {
        this.jtableContext = jtableContext;
        this.regionRelation = regionRelation;
        this.regionKey = regionKey;
        this.dataQueryService = (IDataQueryService)BeanUtil.getBean(IDataQueryService.class);
    }

    public double getMaxFloatOrder() {
        if (this.maxFloatOrder > 0.0) {
            this.maxFloatOrder += 1000.0;
        } else {
            QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(this.jtableContext, this.regionKey, this.regionRelation);
            LinkSort linkSort = new LinkSort();
            linkSort.setLinkKey("FLOATORDER");
            linkSort.setMode(SortMode.DESC);
            queryInfoBuilder.orderBy(linkSort);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageIndex(0);
            pageInfo.setRowsPerPage(1);
            queryInfoBuilder.setPage(pageInfo);
            queryInfoBuilder.setEnableEnumFill(false);
            IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
            if (iRegionDataSet.getRowCount() > 0) {
                List rowData = iRegionDataSet.getRowData();
                IRowData lasetRow = (IRowData)rowData.get(0);
                IDataValue dataValue = lasetRow.getDataValueByLink("FLOATORDER");
                if (dataValue != null) {
                    double floatOrderValue = dataValue.getAsFloat();
                    this.maxFloatOrder = floatOrderValue + 1000.0;
                } else {
                    this.maxFloatOrder = 1000.0;
                }
            } else {
                this.maxFloatOrder = 1000.0;
            }
        }
        return this.maxFloatOrder;
    }
}

