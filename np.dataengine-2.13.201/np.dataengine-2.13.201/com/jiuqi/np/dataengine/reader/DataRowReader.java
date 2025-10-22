/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;

public class DataRowReader
extends AbstractQueryFieldDataReader {
    private DataRowImpl dataRow;

    public DataRowReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public Object readData(QueryField queryField) throws Exception {
        int index = this.findIndex(queryField);
        return this.readData(index);
    }

    @Override
    public void reset() {
        super.reset();
        this.dataRow = null;
    }

    @Override
    public Object readData(int index) throws Exception {
        if (this.dataRow != null) {
            return this.dataRow.getValue(index).getAsObject();
        }
        return null;
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public void setDataSet(Object dataSet) {
        if (dataSet instanceof DataRowImpl) {
            this.dataRow = (DataRowImpl)dataSet;
        }
    }
}

