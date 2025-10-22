/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryResultSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBQueryDataSetReader
extends AbstractQueryFieldDataReader {
    private static final Logger logger = LoggerFactory.getLogger(DBQueryDataSetReader.class);
    private DBQueryResultSet dataSet;
    private DataRow row = null;

    public DBQueryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public Object readData(QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        if (info == null || this.row == null) {
            return null;
        }
        return this.convertDataValue(info, this.row.getValue(info.index - 1));
    }

    @Override
    public void reset() {
        super.reset();
        try {
            if (this.dataSet != null) {
                this.dataSet.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.dataSet = null;
        this.row = null;
        this.canRead = false;
    }

    @Override
    public Object readData(int index) throws Exception {
        if (this.row != null) {
            Object value = this.row.getValue(index - 1);
            if (value instanceof BlobValue) {
                return ((BlobValue)value)._getBytes();
            }
            return value;
        }
        return null;
    }

    @Override
    public boolean next() {
        this.row = this.dataSet != null && this.dataSet.hasNext() ? this.dataSet.next() : null;
        return this.row != null;
    }

    @Override
    public void setDataSet(Object dataSet) {
        if (dataSet != null && dataSet instanceof DBQueryResultSet) {
            this.dataSet = (DBQueryResultSet)dataSet;
        }
    }

    public void setRow(DataRow row) {
        this.row = row;
    }

    public DataRow getRow() {
        return this.row;
    }
}

