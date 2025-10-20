/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.ExistsFilter;

public class NotExistsFilter
extends ExistsFilter {
    public NotExistsFilter() {
    }

    public NotExistsFilter(ISQLTable filterTable) {
        super(filterTable);
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("NOT ");
        super.toSQL(buffer, database, options);
    }
}

