/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.definition.facade.FieldDefine;

public class GroupingJdbcResultSetReader
extends DataSetReader {
    public GroupingJdbcResultSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    protected Object convertIntValue(Object dataValue) {
        return dataValue;
    }

    @Override
    protected String convertStringValue(FieldDefine fieldDefine, Object dataValue) {
        return dataValue.toString();
    }
}

