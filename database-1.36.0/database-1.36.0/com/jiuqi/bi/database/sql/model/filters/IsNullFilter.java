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

public final class IsNullFilter
extends AbstractFilter
implements ISQLFilter {
    private ISQLField field;
    private boolean stringMode;

    public IsNullFilter() {
        this((ISQLField)null);
    }

    public IsNullFilter(ISQLField field) {
        this.field = field;
        this.stringMode = false;
    }

    public ISQLField field() {
        return this.field;
    }

    public boolean isStringMode() {
        return this.stringMode;
    }

    public void setStringMode(boolean stringMode) {
        this.stringMode = stringMode;
    }

    public void setField(ISQLField field) {
        this.field = field;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase dbType, int options) throws SQLModelException {
        IsNullFilter.toSQL(buffer, this.owner, this.field, dbType, this.stringMode);
    }

    @Deprecated
    public static void toSQL(StringBuilder buffer, ISQLField field, IDatabase database, boolean stringMode) throws SQLModelException {
        IsNullFilter.toSQL(buffer, null, field, database, stringMode);
    }

    public static void toSQL(StringBuilder buffer, ISQLTable printer, ISQLField field, IDatabase database, boolean stringMode) throws SQLModelException {
        if (stringMode && database.getDescriptor().supportEmptyString()) {
            buffer.append('(');
            field.toSQL(buffer, database, 1);
            buffer.append(" IS NULL OR ");
            field.toSQL(buffer, database, 1);
            buffer.append("= '')");
        } else {
            field.toSQL(buffer, database, 1);
            buffer.append(" IS NULL");
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public String toString() {
        if (this.field == null) {
            return "IsNull()";
        }
        return "IsNull(" + this.field.toString() + ")";
    }
}

