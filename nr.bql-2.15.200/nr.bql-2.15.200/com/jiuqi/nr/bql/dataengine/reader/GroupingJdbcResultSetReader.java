/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.bql.dataengine.reader;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.bql.dataengine.reader.JdbcResultSetDataReader;

public class GroupingJdbcResultSetReader
extends JdbcResultSetDataReader {
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

