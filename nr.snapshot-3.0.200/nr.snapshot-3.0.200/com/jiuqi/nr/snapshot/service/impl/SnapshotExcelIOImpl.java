/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider
 *  com.jiuqi.nr.data.excel.param.DataQueryPar
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider;
import com.jiuqi.nr.data.excel.param.DataQueryPar;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.snapshot.service.DataOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="snapshotRegionDataSetProvider")
public class SnapshotExcelIOImpl
implements IRegionDataSetProvider {
    @Autowired
    private DataOperationService dataOperationService;

    public IRegionDataSet getRegionDataSet(DataQueryPar par) {
        return this.dataOperationService.querySanpshotData(par.getQueryInfo(), par.getSnapshotId());
    }
}

