/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetImpl
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.nr.summary.executor.query.test.case1;

import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nr.summary.executor.query.engine.EngineQueryParam;
import com.jiuqi.nr.summary.executor.query.engine.QueryResultExecutor;
import com.jiuqi.nr.summary.executor.query.engine.QuickReportExecuteListener;
import com.jiuqi.nr.summary.executor.query.test.case1.TestEngine;

public class TestResultExecutor {
    public BIDataSetImpl testTransBiResult() throws Exception {
        TestEngine engine = new TestEngine();
        EngineQueryParam queryParam = engine.getQueryParam();
        MemoryDataSet<ColumnInfo> dataRows = engine.testReadResult(queryParam);
        QueryResultExecutor executor = new QueryResultExecutor(queryParam.getDsModel());
        MemoryDataSet<ColumnInfo> newSet = executor.resetCols(dataRows, queryParam);
        executor.processRefData(newSet, queryParam.getDsModel(), null);
        QuickReportExecuteListener listener = new QuickReportExecuteListener(null, null, null);
        return listener.buildBIDataset(queryParam.getDsModel(), newSet);
    }
}

