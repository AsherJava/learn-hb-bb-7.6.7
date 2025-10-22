/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;

public class QueryColumnReader {
    protected QueryColumnInfo columnInfo;
    protected QueryContext qContext;
    protected int dataType;

    public QueryColumnReader(QueryContext qContext, QueryColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
        this.qContext = qContext;
        this.dataType = columnInfo.getMetaColumn().getDataType();
    }

    public Object readData(IDataRow dataRow) {
        int fieldIndex = this.columnInfo.getFieldIndex();
        Object value = dataRow.getValue(fieldIndex, this.dataType);
        return value;
    }
}

