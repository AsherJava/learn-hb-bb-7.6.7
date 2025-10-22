/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryDataSetReader;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryResultSet;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.dataengine.reader.SteamJoinDataSetReader;
import java.util.List;

public class TableResultSetReader
implements AutoCloseable {
    private QueryContext qContext;
    private QuerySqlBuilder sqlBuilder;
    private int[] compareColumnIndexes;
    private DBResultSet<QueryField> dataSet;
    private AbstractQueryFieldDataReader dataSetReader;
    private ArrayKey compareKey;
    private DBQueryResultSet dbQueryResultSet;
    private SqlQueryHelper sqlHelper;

    public TableResultSetReader(QueryContext qContext, QuerySqlBuilder sqlBuilder, List<String> compareDims) {
        this.sqlBuilder = sqlBuilder;
        this.qContext = qContext;
        this.initCompareIndexes(sqlBuilder, compareDims);
    }

    public ArrayKey getCompareKey() throws Exception {
        if (this.qContext.isEnableNrdb()) {
            if (this.dbQueryResultSet == null) {
                this.dbQueryResultSet = this.sqlBuilder.getDbQueryExecutor().getDBQueryResultSet(this.qContext);
                this.dataSetReader = new DBQueryDataSetReader(this.qContext);
                this.dataSetReader.setDataSet(this.dbQueryResultSet);
                this.next();
            }
        } else if (this.dataSet == null) {
            Object[] args = this.sqlBuilder.getArgValues() == null ? null : this.sqlBuilder.getArgValues().toArray();
            this.sqlHelper = DataEngineUtil.createSqlQueryHelper();
            this.dataSet = this.sqlHelper.queryDBResultSet(this.qContext.getQueryParam().getConnection(), this.sqlBuilder.getSql(), args, this.qContext.getMonitor());
            this.dataSetReader = new DataSetReader(this.qContext);
            this.dataSetReader.setDataSet(this.dataSet);
            this.next();
        }
        return this.compareKey;
    }

    public boolean next() throws Exception {
        if (this.dataSetReader != null && this.dataSetReader.next()) {
            Object[] keyValues = new Object[this.compareColumnIndexes.length];
            for (int i = 0; i < this.compareColumnIndexes.length; ++i) {
                keyValues[i] = this.dataSetReader.readData(this.compareColumnIndexes[i]);
            }
            this.compareKey = new ArrayKey(keyValues);
            return true;
        }
        this.compareKey = null;
        return false;
    }

    public boolean readDataByCompareKey(ArrayKey currentCompareKey, SteamJoinDataSetReader steamJoinDataSetReader) throws Exception {
        if (this.compareKey != null && this.compareKey.equals((Object)currentCompareKey) && this.dataSetReader != null) {
            steamJoinDataSetReader.readTableRow(this.qContext, this.sqlBuilder, this.dataSetReader);
            return true;
        }
        return false;
    }

    private void initCompareIndexes(QuerySqlBuilder sqlBuilder, List<String> compareDims) {
        this.compareColumnIndexes = new int[compareDims.size()];
        DimensionSet loopDimensions = sqlBuilder.getLoopDimensions();
        int rowKeyStartIndex = sqlBuilder.getRowKeyFieldStartIndex();
        for (int i = 0; i < compareDims.size(); ++i) {
            String compareDim = compareDims.get(i);
            this.compareColumnIndexes[i] = loopDimensions.indexOf(compareDim) + rowKeyStartIndex;
        }
    }

    public AbstractQueryFieldDataReader getDataSetReader() {
        return this.dataSetReader;
    }

    public QuerySqlBuilder getSqlBuilder() {
        return this.sqlBuilder;
    }

    @Override
    public void close() throws Exception {
        if (this.dataSet != null) {
            this.dataSet.close();
            this.dataSet = null;
            if (this.sqlHelper != null) {
                this.sqlHelper.close();
                this.sqlHelper = null;
            }
        } else if (this.dbQueryResultSet != null) {
            this.dbQueryResultSet.close();
            this.dbQueryResultSet = null;
        }
    }
}

