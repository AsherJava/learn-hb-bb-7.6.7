/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.IInnerTableProvider;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AndFilter
implements ISQLFilter,
IInnerTableProvider {
    private List<ISQLFilter> filters;

    public AndFilter() {
        this.filters = new ArrayList<ISQLFilter>();
    }

    public AndFilter(Collection<ISQLFilter> filters) {
        this.filters = new ArrayList<ISQLFilter>(filters);
    }

    public AndFilter(ISQLFilter ... filters) {
        this.filters = new ArrayList<ISQLFilter>(filters.length);
        for (ISQLFilter filter : filters) {
            this.filters.add(filter);
        }
    }

    public List<ISQLFilter> filters() {
        return this.filters;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.filters.isEmpty()) {
            throw new SQLModelException("AND\u7684\u6761\u4ef6\u5217\u8868\u4e3a\u7a7a\u3002");
        }
        if (this.filters.size() == 1) {
            this.filters.get(0).toSQL(buffer, database, options);
            return;
        }
        boolean started = false;
        buffer.append('(');
        for (ISQLFilter filter : this.filters) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            filter.toSQL(buffer, database, options);
        }
        buffer.append(')');
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> innerTables = new ArrayList<ISQLTable>();
        for (ISQLFilter filter : this.filters) {
            if (!(filter instanceof IInnerTableProvider)) continue;
            List<ISQLTable> subTables = ((IInnerTableProvider)((Object)filter)).getInnerTables();
            innerTables.addAll(subTables);
        }
        return innerTables;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        boolean started = false;
        for (ISQLFilter filter : this.filters) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            buffer.append(filter);
        }
        return buffer.toString();
    }

    public static ISQLFilter and(Collection<ISQLFilter> filters) {
        if (filters.size() == 1) {
            return filters.iterator().next();
        }
        return new AndFilter(filters);
    }
}

