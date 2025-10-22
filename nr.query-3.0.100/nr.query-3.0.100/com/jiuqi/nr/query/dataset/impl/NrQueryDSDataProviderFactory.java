/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSExecutor;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrQueryDSDataProviderFactory
extends DSDataProviderFactory {
    @Autowired
    private NrQueryDSExecutor nrQueryDSExecutor;

    public IDataSetProvider createDataSetProvider(DSModel dsModel) {
        return new NrQueryDSProvider(this.nrQueryDSExecutor, (QueryDSModel)dsModel);
    }

    public String getType() {
        return "QueryDataSet";
    }
}

