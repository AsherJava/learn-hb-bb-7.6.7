/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import java.math.BigDecimal;
import java.util.Map;

public class HOrderColumnReader
extends QueryColumnReader {
    private Map<String, Integer> orderCache;

    public HOrderColumnReader(QueryContext qContext, QueryColumnInfo columnInfo) {
        super(qContext, columnInfo);
        this.orderCache = this.qContext.getOrderCache(columnInfo.getEntityDefine().getDimensionName());
    }

    @Override
    public Object readData(IDataRow dataRow) {
        String dimensionValue;
        Integer order;
        Object value = super.readData(dataRow);
        if (this.orderCache != null && (order = this.orderCache.get(dimensionValue = (String)dataRow.getRowKeys().getValue(this.columnInfo.getRefDimensionName()))) != null) {
            if (this.dataType == 10) {
                return new BigDecimal(order);
            }
            return order;
        }
        return value;
    }
}

