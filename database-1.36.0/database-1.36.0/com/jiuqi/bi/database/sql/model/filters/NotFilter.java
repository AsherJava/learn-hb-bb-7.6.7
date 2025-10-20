/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.IInnerTableProvider;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.AndFilter;
import com.jiuqi.bi.database.sql.model.filters.OrFilter;
import java.util.ArrayList;
import java.util.List;

public class NotFilter
implements ISQLFilter,
IInnerTableProvider {
    private ISQLFilter filter;

    public NotFilter() {
    }

    public NotFilter(ISQLFilter filter) {
        this.filter = filter;
    }

    public ISQLFilter filter() {
        return this.filter;
    }

    public void setFilter(ISQLFilter filter) {
        this.filter = filter;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.filter instanceof NotFilter) {
            ISQLFilter innerFilter = ((NotFilter)this.filter).filter();
            innerFilter.toSQL(buffer, database, options);
            return;
        }
        boolean quoted = this.filter instanceof AndFilter || this.filter instanceof OrFilter;
        buffer.append("NOT ");
        if (quoted) {
            buffer.append('(');
        }
        this.filter.toSQL(buffer, database, options);
        if (quoted) {
            buffer.append(')');
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        if (this.filter instanceof IInnerTableProvider) {
            return ((IInnerTableProvider)((Object)this.filter)).getInnerTables();
        }
        return new ArrayList<ISQLTable>();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        boolean quoted = this.filter instanceof AndFilter || this.filter instanceof OrFilter;
        buffer.append("NOT ");
        if (quoted) {
            buffer.append('(');
        }
        buffer.append(this.filter);
        if (quoted) {
            buffer.append(')');
        }
        return buffer.toString();
    }
}

