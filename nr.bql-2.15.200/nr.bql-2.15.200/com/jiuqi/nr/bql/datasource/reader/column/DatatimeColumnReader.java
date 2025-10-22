/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatatimeColumnReader
extends QueryColumnReader {
    private final DateFormat timeKeyFormat = new SimpleDateFormat("yyyyMMdd");

    public DatatimeColumnReader(QueryContext qContext, QueryColumnInfo columnInfo) {
        super(qContext, columnInfo);
    }

    @Override
    public Object readData(IDataRow dataRow) {
        Object value = super.readData(dataRow);
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return this.getTimeKeyByDate((Date)value);
        }
        String periodCode = (String)value;
        return this.qContext.getTimeKeyByDataTime(periodCode);
    }

    private String getTimeKeyByDate(Date date) {
        return this.timeKeyFormat.format(date);
    }
}

