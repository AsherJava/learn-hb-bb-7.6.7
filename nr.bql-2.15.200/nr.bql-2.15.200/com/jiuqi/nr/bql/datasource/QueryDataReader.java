/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.adhoc.engine.memory.session.BufferEnv
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.adhoc.engine.memory.session.BufferEnv;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nr.bql.common.NRBqlConsts;
import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.rowfilter.AllNullDataRowFilter;
import com.jiuqi.nr.bql.datasource.rowfilter.AllZeroDataRowFilter;
import java.util.ArrayList;
import java.util.List;

public class QueryDataReader {
    private QueryContext qContext;
    private IDataListener listener;
    private Metadata<ColumnInfo> metadata;
    private List<QueryColumnInfo> columnInfos;
    private int totalCount = 0;
    private int currentIndex = 0;
    private static final int LOG_SIZE = BufferEnv.logSize();
    private IDataRowFilter filter = null;
    private final StringBuilder logMsg = new StringBuilder();

    public QueryDataReader(QueryContext qContext, IDataListener listener, Metadata<ColumnInfo> metadata, List<QueryColumnInfo> columnInfos, String expandMode) {
        this.qContext = qContext;
        this.listener = listener;
        this.metadata = metadata;
        this.columnInfos = columnInfos;
        if (expandMode.equals(NRBqlConsts.ExpandMode.HIDE_ALLNULL.getCode())) {
            this.filter = new AllNullDataRowFilter(this.getMeasureIndexes(columnInfos));
        } else if (expandMode.equals(NRBqlConsts.ExpandMode.HIDE_ALLZERO.getCode())) {
            this.filter = new AllZeroDataRowFilter(this.getMeasureIndexes(columnInfos));
        }
        columnInfos.forEach(info -> info.initColumnReader(qContext, columnInfos));
    }

    public void start() throws AdHocEngineException {
        this.listener.start(this.metadata);
        this.logMsg.append("\u6570\u636e\u8bfb\u53d6\u5b8c\u6210,\u6253\u5370\u524d" + LOG_SIZE + "\u6761\u7ed3\u679c\uff1a\n");
        this.logMsg.append(this.metadata).append("\n");
    }

    public void finish() throws AdHocEngineException {
        this.listener.finish();
        this.logMsg.append("\u672c\u6b21\u8fd4\u56de" + this.currentIndex + "\u6761\u8bb0\u5f55\uff0c\u5171" + this.totalCount + "\u6761\u8bb0\u5f55\n");
        this.qContext.getLogger().debug(this.logMsg.toString());
    }

    public void readRowData(IDataRow dataRow) throws Exception {
        MemoryDataRow row = new MemoryDataRow(new Object[this.columnInfos.size()]);
        for (int col = 0; col < this.columnInfos.size(); ++col) {
            QueryColumnInfo columnInfo = this.columnInfos.get(col);
            Object value = columnInfo.readData(dataRow);
            if (value == null || columnInfo.isIgnoreValue()) {
                row.setNull(col);
                continue;
            }
            row.setValue(col, value);
        }
        if (this.filter != null && !this.filter.filter((DataRow)row)) {
            return;
        }
        this.listener.process((DataRow)row);
        if (this.currentIndex < LOG_SIZE) {
            this.logMsg.append(row).append("\n");
        }
        ++this.currentIndex;
    }

    public int getTotalCount() {
        if (this.totalCount == 0) {
            this.totalCount = this.currentIndex;
        }
        return this.totalCount;
    }

    private List<Integer> getMeasureIndexes(List<QueryColumnInfo> columnInfos) {
        ArrayList<Integer> measureIndexes = new ArrayList<Integer>();
        for (int i = 0; i < columnInfos.size(); ++i) {
            QueryColumnInfo info = columnInfos.get(i);
            if (!info.isMeasure()) continue;
            measureIndexes.add(i);
        }
        return measureIndexes;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

