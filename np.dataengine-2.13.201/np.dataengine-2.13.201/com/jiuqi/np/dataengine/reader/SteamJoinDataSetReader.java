/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.MemoryRowData;

public class SteamJoinDataSetReader
extends MemoryDataSetReader {
    public SteamJoinDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    public void readTableRow(QueryContext qContext, QuerySqlBuilder builder, AbstractQueryFieldDataReader dataSetReader) throws Exception {
        if (this.rowDatas == null) {
            this.resetRow();
            this.rowKey = new DimensionValueSet();
        }
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        int bizKeyOrderIndex = builder.getBizkeyOrderFieldIndex() - 1;
        this.loopDimensions = builder.getLoopDimensions();
        this.rowDatas.loadDataFromDataSetReader(dataSetReader, memeryStartIndex, rowKeyStartIndex, bizKeyOrderIndex);
    }

    public void readTableRow(QueryContext qContext, QuerySqlBuilder builder, DataRow row) throws Exception {
        if (this.rowDatas == null) {
            this.resetRow();
            this.rowKey = new DimensionValueSet();
        }
        int memeryStartIndex = builder.getMemoryStartIndex();
        int rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        int bizKeyOrderIndex = builder.getBizkeyOrderFieldIndex() - 1;
        this.loopDimensions = builder.getLoopDimensions();
        this.rowDatas.loadDataFromDataRow(row, memeryStartIndex, rowKeyStartIndex, bizKeyOrderIndex);
    }

    public void resetRow() {
        this.rowDatas = new MemoryRowData(null, this.columns.size());
    }
}

