/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 */
package com.jiuqi.nr.summary.executor.query.test.case1;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.engine.DataEngineExecutor;
import com.jiuqi.nr.summary.executor.query.engine.EngineQueryParam;
import com.jiuqi.nr.summary.executor.query.test.case1.TestDSModel1;
import com.jiuqi.nr.summary.model.PageInfo;
import java.util.Map;

public class TestEngine {
    private DimensionValueSet getTestDimension() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)"2023Y0010");
        dimensionValueSet.setValue("MD_ORG", (Object)"1000");
        return dimensionValueSet;
    }

    public EngineQueryParam getQueryParam() {
        SummaryDSModel testDsModel = new TestDSModel1().getTestDsModel();
        PageInfo pageInfo = this.getPageInfo();
        EngineQueryParam queryParam = new EngineQueryParam(testDsModel);
        queryParam.setPagerInfo(pageInfo);
        return queryParam;
    }

    private PageInfo getPageInfo() {
        PageInfo pageInfo = new PageInfo();
        return pageInfo;
    }

    public IReadonlyTable testEngineExec() throws Exception {
        EngineQueryParam queryParam = this.getQueryParam();
        return this.testEngineExec(queryParam);
    }

    public IReadonlyTable testEngineExec(EngineQueryParam queryParam) throws Exception {
        DimensionValueSet testDimension = this.getTestDimension();
        DataEngineExecutor executor = new DataEngineExecutor();
        IReadonlyTable rangeData = executor.getRangeData(testDimension, queryParam);
        return rangeData;
    }

    public MemoryDataSet<ColumnInfo> testReadResult() throws Exception {
        EngineQueryParam queryParam = this.getQueryParam();
        return this.testReadResult(queryParam);
    }

    public MemoryDataSet<ColumnInfo> testReadResult(EngineQueryParam queryParam) throws Exception {
        IReadonlyTable table = this.testEngineExec(queryParam);
        Metadata<ColumnInfo> metadata = queryParam.getMetadata();
        MemoryDataSet dataSet = new MemoryDataSet(null, metadata);
        int tableCount = table.getCount();
        for (int i = 0; i < tableCount; ++i) {
            IDataRow row = table.getItem(i);
            this.processRow((MemoryDataSet<ColumnInfo>)dataSet, row, queryParam, i);
        }
        return dataSet;
    }

    protected void processRow(MemoryDataSet<ColumnInfo> dataSet, IDataRow dataRow, EngineQueryParam param, int rowNumber) {
        Map<String, Integer> columnMap = param.getColumnMap();
        Metadata<ColumnInfo> metadata = param.getMetadata();
        DataRow row = dataSet.add();
        for (int col = 0; col < metadata.size(); ++col) {
            Column columnInfo = metadata.getColumn(col);
            int fieldIndex = columnMap.getOrDefault(columnInfo.getName(), -1);
            if (fieldIndex < 0) continue;
            AbstractData dataValue = dataRow.getValue(fieldIndex);
            if (dataValue.isNull) {
                row.setNull(col);
                continue;
            }
            row.setValue(col, dataValue.getAsObject());
        }
    }
}

