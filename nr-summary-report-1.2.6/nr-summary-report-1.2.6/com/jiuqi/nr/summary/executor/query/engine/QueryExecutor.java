/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.engine.DataEngineExecutor;
import com.jiuqi.nr.summary.executor.query.engine.EngineQueryParam;
import com.jiuqi.nr.summary.executor.query.engine.QueryResultExecutor;
import com.jiuqi.nr.summary.model.PageInfo;

class QueryExecutor {
    private DataEngineExecutor executor = new DataEngineExecutor();

    QueryExecutor() {
    }

    public MemoryDataSet<ColumnInfo> query(SummaryDSModel dsModel, DimensionValueSet dimensionValueSet, PageInfo pageInfo) throws Exception {
        EngineQueryParam param = this.getEngineQueryParam(dsModel, pageInfo);
        IReadonlyTable dbTableValue = this.queryFromDB(dimensionValueSet, param);
        QueryResultExecutor dataProcessor = new QueryResultExecutor(dsModel);
        MemoryDataSet<ColumnInfo> result = dataProcessor.process(dbTableValue, param);
        if (pageInfo != null) {
            pageInfo.setRecordSize(dbTableValue.getTotalCount());
        }
        return result;
    }

    private EngineQueryParam getEngineQueryParam(SummaryDSModel dsModel, PageInfo pageInfo) {
        EngineQueryParam queryParam = new EngineQueryParam(dsModel);
        queryParam.setPagerInfo(pageInfo);
        return queryParam;
    }

    private IReadonlyTable queryFromDB(DimensionValueSet dim, EngineQueryParam rangeDataQueryInfo) {
        IReadonlyTable readOnlyResult = null;
        try {
            readOnlyResult = this.executor.getRangeData(dim, rangeDataQueryInfo);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return readOnlyResult;
    }
}

