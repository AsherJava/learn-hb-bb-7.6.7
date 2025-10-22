/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.memdb.api.query.DBFilter
 *  com.jiuqi.nvwa.memdb.api.query.DBFilterByExpression
 *  com.jiuqi.nvwa.memdb.api.query.DBFilterByValues
 */
package com.jiuqi.np.dataengine.nrdb;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.memdb.api.query.DBFilter;
import com.jiuqi.nvwa.memdb.api.query.DBFilterByExpression;
import com.jiuqi.nvwa.memdb.api.query.DBFilterByValues;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NrdbAccessUtils {
    public static DBFilter getColumnDBFilter(ColumnModelDefine column, Object value) {
        return NrdbAccessUtils.getColumnDBFilter(column.getName(), column.getColumnType().getValue(), value);
    }

    public static DBFilter getColumnDBFilter(String columnName, int dataType, Object value) {
        if (dataType == 6) {
            if (value instanceof List) {
                return new DBFilterByValues(columnName, (Collection)((List)value).stream().map(Object::toString).collect(Collectors.toList()));
            }
            return new DBFilterByValues(columnName, new String[]{value.toString()});
        }
        return new DBFilterByExpression(columnName + "=" + value.toString());
    }
}

