/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryResultSet;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import java.util.Iterator;

public class MemorySteamLoader
implements AutoCloseable {
    private int memeryStartIndex;
    private int rowKeyStartIndex;
    private int bizKeyOrderIndex;
    private QuerySqlBuilder builder;
    private DBResultSet<QueryField> dataSet;
    private QueryContext context;
    private Iterator<DataRow> it;
    private SqlQueryHelper sqlHelper;
    private DBQueryResultSet dbQueryResultSet;

    public void doInit(QueryContext qContext, QuerySqlBuilder builder, SqlQueryHelper sqlHelper, DBResultSet<QueryField> dataSet) throws Exception {
        this.doInit(qContext, builder);
        this.dataSet = dataSet;
        this.sqlHelper = sqlHelper;
        this.it = dataSet.iterator();
    }

    public void doInit(QueryContext qContext, QuerySqlBuilder builder) throws Exception {
        this.memeryStartIndex = builder.getMemoryStartIndex();
        this.rowKeyStartIndex = builder.getRowKeyFieldStartIndex() - 1;
        this.bizKeyOrderIndex = builder.getBizkeyOrderFieldIndex() - 1;
        this.builder = builder;
        this.context = qContext;
    }

    public DataRow next() {
        if (this.it != null && this.it.hasNext()) {
            return this.it.next();
        }
        if (this.dbQueryResultSet != null) {
            return this.dbQueryResultSet.next();
        }
        return null;
    }

    public int getMemeryStartIndex() {
        return this.memeryStartIndex;
    }

    public int getRowKeyStartIndex() {
        return this.rowKeyStartIndex;
    }

    public QuerySqlBuilder getBuilder() {
        return this.builder;
    }

    public QueryContext getContext() {
        return this.context;
    }

    public void setDbQueryResultSet(DBQueryResultSet dbQueryResultSet) {
        this.dbQueryResultSet = dbQueryResultSet;
    }

    public int getBizKeyOrderIndex() {
        return this.bizKeyOrderIndex;
    }

    @Override
    public void close() throws Exception {
        if (this.dataSet != null) {
            this.dataSet.close();
            this.sqlHelper.close();
            this.dataSet = null;
            this.sqlHelper = null;
        } else if (this.dbQueryResultSet != null) {
            this.dbQueryResultSet.close();
            this.dbQueryResultSet = null;
        }
    }
}

