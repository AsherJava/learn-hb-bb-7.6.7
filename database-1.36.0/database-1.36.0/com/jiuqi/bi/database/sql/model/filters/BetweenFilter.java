/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.filters.AbstractFilter;

public final class BetweenFilter
extends AbstractFilter
implements ISQLFilter {
    private ISQLField valueField;
    private ISQLField startField;
    private ISQLField endField;

    public BetweenFilter() {
    }

    public BetweenFilter(ISQLField valueField, ISQLField startField, ISQLField endField) {
        this.valueField = valueField;
        this.startField = startField;
        this.endField = endField;
    }

    public BetweenFilter(ISQLTable owner) {
        this.owner = owner;
    }

    public BetweenFilter(ISQLTable owner, ISQLField valueField, ISQLField startField, ISQLField endField) {
        this.owner = owner;
        this.valueField = valueField;
        this.startField = startField;
        this.endField = endField;
    }

    public ISQLField valueField() {
        return this.valueField;
    }

    public void setValueField(ISQLField valueField) {
        this.valueField = valueField;
    }

    public ISQLField startField() {
        return this.startField;
    }

    public void setStartField(ISQLField startField) {
        this.startField = startField;
    }

    public ISQLField endField() {
        return this.endField;
    }

    public void setEndField(ISQLField endField) {
        this.endField = endField;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.printField(buffer, this.valueField, database);
        buffer.append(" BETWEEN ");
        this.printField(buffer, this.startField, database);
        buffer.append(" AND ");
        this.printField(buffer, this.endField, database);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public String toString() {
        return this.valueField + " BETWEEN " + this.startField + " AND " + this.endField;
    }
}

