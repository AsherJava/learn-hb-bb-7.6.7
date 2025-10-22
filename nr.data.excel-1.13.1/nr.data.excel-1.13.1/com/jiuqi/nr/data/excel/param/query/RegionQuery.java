/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 */
package com.jiuqi.nr.data.excel.param.query;

import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;

public class RegionQuery {
    private IRegionDataSet regionDataSet;
    private QueryInfoBuilder queryInfoBuilder;

    public RegionQuery() {
    }

    public RegionQuery(IRegionDataSet regionDataSet, QueryInfoBuilder queryInfoBuilder) {
        this.regionDataSet = regionDataSet;
        this.queryInfoBuilder = queryInfoBuilder;
    }

    public IRegionDataSet getRegionDataSet() {
        return this.regionDataSet;
    }

    public void setRegionDataSet(IRegionDataSet regionDataSet) {
        this.regionDataSet = regionDataSet;
    }

    public QueryInfoBuilder getQueryInfoBuilder() {
        return this.queryInfoBuilder;
    }

    public void setQueryInfoBuilder(QueryInfoBuilder queryInfoBuilder) {
        this.queryInfoBuilder = queryInfoBuilder;
    }

    public IRegionDataSet nextPage(IDataQueryService dataQueryService, PageInfo page) {
        this.queryInfoBuilder.setPage(page);
        return dataQueryService.queryRegionData(this.queryInfoBuilder.build());
    }
}

