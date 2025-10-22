/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import com.jiuqi.nvwa.dataengine.common.Convert;

public class CountColumnReader
extends QueryColumnReader {
    public CountColumnReader(QueryContext qContext, QueryColumnInfo columnInfo) {
        super(qContext, columnInfo);
    }

    @Override
    public Object readData(IDataRow dataRow) {
        Object value = super.readData(dataRow);
        return Convert.toDouble((Object)value);
    }
}

