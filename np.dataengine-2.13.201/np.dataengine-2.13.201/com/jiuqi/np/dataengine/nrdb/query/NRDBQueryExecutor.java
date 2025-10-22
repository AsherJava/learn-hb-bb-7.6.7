/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBMetadata
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.query.DBQuery
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutor;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryResultSet;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBMetadata;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.query.DBQuery;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;

public class NRDBQueryExecutor
extends DBQueryExecutor {
    @Override
    public int runQuery(QueryContext qContext, MemoryDataSet<QueryField> result, int rowOffSet, int rowSize) throws Exception {
        boolean dbPage;
        boolean bl = dbPage = rowSize > 0;
        if (dbPage) {
            this.dbQueryBuilder.limit(rowSize, rowOffSet);
            this.dbQueryBuilder.countable(true);
        }
        if (this.queryNothing) {
            return 0;
        }
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(this.tableInfo.getTableModelDefine());){
            DBQuery dbQuery = this.dbQueryBuilder.build();
            if (qContext.isDebug()) {
                qContext.getMonitor().debug("query " + dbTable.getName() + ":\n" + dbQuery, DataEngineConsts.DebugLogType.SQL);
            }
            try (DBCursor cursor = dbTable.query(dbQuery);){
                DBMetadata metadata = cursor.getMetadata();
                for (int i = 0; i < metadata.size(); ++i) {
                    result.getMetadata().addColumn(new Column(metadata.getName(i), metadata.getType(i)));
                }
                while (cursor.hasNext()) {
                    DBRecord record = (DBRecord)cursor.next();
                    DataRow row = result.add();
                    for (int index = 0; index < metadata.size(); ++index) {
                        row.setValue(index, record.getValue(index));
                    }
                }
                if (dbPage) {
                    int n = (int)cursor.count();
                    return n;
                }
            }
        }
        return result.size();
    }

    @Override
    public int readData(QueryContext qContext, IDataListener listener, int rowOffSet, int rowSize) throws Exception {
        boolean countable;
        boolean bl = countable = rowSize > 0;
        if (countable) {
            this.dbQueryBuilder.limit(rowSize, rowOffSet);
            this.dbQueryBuilder.countable(true);
        }
        if (this.queryNothing) {
            listener.start(new Metadata());
            listener.finish();
            return 0;
        }
        int totalCount = 0;
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(this.tableInfo.getTableModelDefine());){
            DBQuery dbQuery = this.dbQueryBuilder.build();
            if (qContext.isDebug()) {
                qContext.getMonitor().debug("query " + dbTable.getName() + ":\n" + dbQuery, DataEngineConsts.DebugLogType.SQL);
            }
            Metadata metadata = new Metadata();
            try (DBCursor cursor = dbTable.query(dbQuery);){
                DBMetadata dbMetadata = cursor.getMetadata();
                for (int i = 0; i < dbMetadata.size(); ++i) {
                    metadata.addColumn(new Column(dbMetadata.getName(i), dbMetadata.getType(i)));
                }
                listener.start(metadata);
                while (cursor.hasNext()) {
                    DBRecord record = (DBRecord)cursor.next();
                    MemoryDataRow row = new MemoryDataRow();
                    row._setBuffer(new Object[metadata.size()]);
                    for (int index = 0; index < metadata.size(); ++index) {
                        row.setValue(index, record.getValue(index));
                    }
                    listener.process((DataRow)row);
                    ++totalCount;
                }
                listener.finish();
                if (countable) {
                    int n = (int)cursor.count();
                    return n;
                }
            }
        }
        return totalCount;
    }

    @Override
    public void readToMemorySteamLoader(QueryContext qContext, MemorySteamLoader loader) throws Exception {
        if (this.queryNothing) {
            return;
        }
        DBTable dbTable = NrdbStorageManager.getInstance().openTable(this.tableInfo.getTableModelDefine());
        DBQuery dbQuery = this.dbQueryBuilder.build();
        if (qContext.isDebug()) {
            qContext.getMonitor().debug("query " + dbTable.getName() + ":\n" + dbQuery, DataEngineConsts.DebugLogType.SQL);
        }
        DBCursor cursor = dbTable.query(dbQuery);
        loader.setDbQueryResultSet(new DBQueryResultSet(dbTable, cursor));
    }
}

