/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider;
import com.jiuqi.nr.data.excel.param.DataQueryPar;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="defaultRegionDataSetProvider")
public class DefaultRegionDataSetProvider
implements IRegionDataSetProvider {
    @Autowired
    private IDataQueryService dataQueryService;

    @Override
    public IRegionDataSet getRegionDataSet(DataQueryPar par) {
        return this.dataQueryService.queryRegionData(par.getQueryInfo(), par.getRegionGradeInfo());
    }
}

