/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBMetadata
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBMetadata;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;

public class DBQueryResultSet
implements AutoCloseable {
    private DBTable dbTable;
    private DBCursor cursor;
    private Metadata<ColumnInfo> metadata = new Metadata();

    public DBQueryResultSet(DBTable dbTable, DBCursor cursor) {
        this.dbTable = dbTable;
        this.cursor = cursor;
        DBMetadata dbMetadata = cursor.getMetadata();
        for (int i = 0; i < dbMetadata.size(); ++i) {
            this.metadata.addColumn(new Column(dbMetadata.getName(i), dbMetadata.getType(i)));
        }
    }

    public DataRow next() {
        if (this.cursor.hasNext()) {
            DBRecord record = (DBRecord)this.cursor.next();
            MemoryDataRow row = new MemoryDataRow();
            row._setBuffer(new Object[this.metadata.size()]);
            for (int index = 0; index < this.metadata.size(); ++index) {
                row.setValue(index, record.getValue(index));
            }
            return row;
        }
        return null;
    }

    public boolean hasNext() {
        return this.cursor.hasNext();
    }

    @Override
    public void close() throws Exception {
        if (this.cursor != null) {
            this.cursor.close();
            this.cursor = null;
        }
        if (this.dbTable != null) {
            this.dbTable.close();
            this.dbTable = null;
        }
    }
}

