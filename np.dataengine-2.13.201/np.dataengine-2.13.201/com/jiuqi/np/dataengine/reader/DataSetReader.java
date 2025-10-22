/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSetReader
extends AbstractQueryFieldDataReader {
    private static final Logger logger = LoggerFactory.getLogger(DataSetReader.class);
    private DataSet<QueryField> dataSet;
    private Iterator<DataRow> iterator;
    private DataRow row = null;

    public DataSetReader(QueryContext queryContext) {
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
        this.row = this.iterator != null && this.iterator.hasNext() ? this.iterator.next() : null;
        return this.row != null;
    }

    @Override
    public void setDataSet(Object dataSet) {
        if (dataSet != null && dataSet instanceof DataSet) {
            this.dataSet = (DataSet)dataSet;
            this.iterator = this.dataSet.iterator();
        }
    }

    public void setRow(DataRow row) {
        this.row = row;
    }

    public DataRow getRow() {
        return this.row;
    }
}

