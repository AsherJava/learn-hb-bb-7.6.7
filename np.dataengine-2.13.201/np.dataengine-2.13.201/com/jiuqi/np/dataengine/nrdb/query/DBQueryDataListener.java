/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.DataSetReader;

public class DBQueryDataListener
implements IDataListener {
    private QueryContext qContext;
    private DataQueryBuilder dataQueryBuilder;
    private IDataRowReader dataRowReader;
    private QuerySqlBuilder querySqlBuilder;

    public DBQueryDataListener(QueryContext qContext, DataQueryBuilder dataQueryBuilder, IDataRowReader dataRowReader, QuerySqlBuilder querySqlBuilder) {
        this.qContext = qContext;
        this.dataQueryBuilder = dataQueryBuilder;
        this.dataRowReader = dataRowReader;
        this.querySqlBuilder = querySqlBuilder;
    }

    public void finish() throws AdHocEngineException {
        try {
            this.dataRowReader.finish(this.qContext);
        }
        catch (Exception e) {
            throw new AdHocEngineException(e.getMessage(), (Throwable)e);
        }
    }

    public boolean process(DataRow row) throws AdHocEngineException {
        try {
            DataSetReader reader = (DataSetReader)this.qContext.getDataReader();
            reader.setRow(row);
            DataRowImpl dataRow = this.dataQueryBuilder.loadRowData(this.qContext, this.querySqlBuilder, reader, -1, -1, this.dataRowReader);
            this.dataRowReader.readRowData(this.qContext, dataRow);
        }
        catch (Exception e) {
            throw new AdHocEngineException(e.getMessage(), (Throwable)e);
        }
        return true;
    }

    public void start(Metadata<ColumnInfo> metadata) throws AdHocEngineException {
        try {
            this.dataRowReader.start(this.qContext, this.dataQueryBuilder.getTable().getSystemFields());
        }
        catch (Exception e) {
            throw new AdHocEngineException(e.getMessage(), (Throwable)e);
        }
    }
}

