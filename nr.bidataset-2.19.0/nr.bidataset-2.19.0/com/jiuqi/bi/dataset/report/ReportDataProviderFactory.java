/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.bi.dataset.report;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.provider.ReportDSProvider;
import com.jiuqi.bi.dataset.report.query.ReportQueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDataProviderFactory
extends DSDataProviderFactory {
    @Autowired
    private ReportQueryExecutor executor;

    public IDataSetProvider createDataSetProvider(DSModel dsModel) {
        return new ReportDSProvider(this.executor, (ReportDSModel)dsModel);
    }

    public String getType() {
        return "ReportDataSet";
    }
}

