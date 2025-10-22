/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.query.ReportQueryExecutor;

public class ReportDSProvider
implements IDataSetProvider {
    private ReportQueryExecutor executor;
    private ReportDSModel dsModel;

    public ReportDSProvider(ReportQueryExecutor executor, ReportDSModel dsModel) {
        this.executor = executor;
        this.dsModel = dsModel;
    }

    public void open(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, IDSContext dsContext) throws BIDataSetException {
        this.executor.runQuery(this.dsModel, dsContext, 0, 1, memoryDataSet);
    }
}

