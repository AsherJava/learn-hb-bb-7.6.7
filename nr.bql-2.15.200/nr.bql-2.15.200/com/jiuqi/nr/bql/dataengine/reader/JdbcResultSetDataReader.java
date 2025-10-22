/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.query.DBResultSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 */
package com.jiuqi.nr.bql.dataengine.reader;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nr.bql.dataengine.reader.AbstractQueryFieldDataReader;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcResultSetDataReader
extends AbstractQueryFieldDataReader {
    private static final Logger logger = LoggerFactory.getLogger(JdbcResultSetDataReader.class);
    private DBResultSet<QueryField> dataSet;
    private Iterator<DataRow> iterator;
    private DataRow row = null;

    public JdbcResultSetDataReader(QueryContext queryContext) {
        super(queryContext);
    }

    public Object readData(QueryField queryField) throws Exception {
        QueryFieldInfo info = this.findQueryFieldInfo(queryField);
        if (info == null || this.row == null) {
            return null;
        }
        return this.convertDataValue(info.fieldDefine, this.row.getValue(info.index - 1));
    }

    @Override
    public void reset() {
        super.reset();
        try {
            if (this.dataSet != null) {
                this.dataSet.clear();
            }
        }
        catch (DataSetException e) {
            logger.error(e.getMessage(), e);
        }
        this.dataSet = null;
        this.row = null;
        this.canRead = false;
        this.iterator = null;
    }

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

    public boolean next() {
        this.row = this.iterator != null && this.iterator.hasNext() ? this.iterator.next() : null;
        return this.row != null;
    }

    public void setDataSet(Object dataSet) {
        if (dataSet != null && dataSet instanceof DBResultSet) {
            this.dataSet = (DBResultSet)dataSet;
            this.iterator = this.dataSet.iterator();
        }
    }
}

