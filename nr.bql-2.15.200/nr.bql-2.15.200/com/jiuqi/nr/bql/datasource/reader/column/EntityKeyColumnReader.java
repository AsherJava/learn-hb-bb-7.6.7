/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;

public class EntityKeyColumnReader
extends QueryColumnReader {
    public EntityKeyColumnReader(QueryContext qContext, QueryColumnInfo columnInfo) {
        super(qContext, columnInfo);
    }

    @Override
    public Object readData(IDataRow dataRow) {
        Object value = super.readData(dataRow);
        if (value == null) {
            value = dataRow.getRowKeys().getValue(this.columnInfo.getRefDimensionName());
        }
        return value;
    }
}

