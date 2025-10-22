/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.DataSetReader
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.nr.bql.dataengine.query.DataQueryBuilder;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;

public class DBQueryDataListener
implements IDataListener {
    private QueryContext qContext;
    private DataQueryBuilder dataQueryBuilder;
    private QuerySqlBuilder querySqlBuilder;

    public DBQueryDataListener(QueryContext qContext, DataQueryBuilder dataQueryBuilder, QuerySqlBuilder querySqlBuilder) {
        this.qContext = qContext;
        this.dataQueryBuilder = dataQueryBuilder;
        this.querySqlBuilder = querySqlBuilder;
    }

    public void finish() throws AdHocEngineException {
    }

    public boolean process(DataRow row) throws AdHocEngineException {
        try {
            DataSetReader reader = (DataSetReader)this.qContext.getDataReader();
            reader.setRow(row);
            this.dataQueryBuilder.loadRowData(this.qContext, this.querySqlBuilder, (IQueryFieldDataReader)reader, -1, -1);
        }
        catch (Exception e) {
            throw new AdHocEngineException(e.getMessage(), (Throwable)e);
        }
        return true;
    }

    public void start(Metadata<ColumnInfo> metadata) throws AdHocEngineException {
    }
}

