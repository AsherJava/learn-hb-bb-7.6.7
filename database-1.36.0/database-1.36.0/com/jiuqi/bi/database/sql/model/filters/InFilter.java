/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.IInnerTableProvider;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.AbstractFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InFilter
extends AbstractFilter
implements ISQLFilter,
IInnerTableProvider {
    private List<ISQLField> filterFields = new ArrayList<ISQLField>(2);
    private ISQLTable filterTable;

    public ISQLField filterField() {
        return this.filterFields.size() == 1 ? this.filterFields.get(0) : null;
    }

    public void setFilterField(ISQLField filterField) {
        this.filterFields.clear();
        this.filterFields.add(filterField);
    }

    public void addFilterField(ISQLField filterField) {
        this.filterFields.add(filterField);
    }

    public List<ISQLField> filterFields() {
        return this.filterFields;
    }

    public ISQLTable getFilterTable() {
        return this.filterTable;
    }

    public void setFilterTable(ISQLTable filterTable) {
        this.filterTable = filterTable;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.filterFields.isEmpty()) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u8fc7\u6ee4\u7684\u8868\u5b57\u6bb5");
        }
        if (this.filterFields.size() > 1) {
            buffer.append('(');
        }
        this.filterFields.get(0).toSQL(buffer, database, 1);
        for (int i = 1; i < this.filterFields.size(); ++i) {
            buffer.append(',');
            this.printField(buffer, this.filterFields.get(i), database);
        }
        if (this.filterFields.size() > 1) {
            buffer.append(')');
        }
        buffer.append(" ").append(this.getOperator()).append(" (");
        this.filterTable.toSQL(buffer, database, 1);
        buffer.append(')');
    }

    protected String getOperator() {
        return "IN";
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        return Arrays.asList(this.filterTable);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.filterFields).append(" ").append(this.getOperator()).append(" ").append(this.filterTable);
        return buffer.toString();
    }
}

