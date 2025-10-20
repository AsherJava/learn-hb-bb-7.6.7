/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.util.SQLPrinter;

public abstract class AbstractFilter
implements ISQLFilter {
    protected ISQLTable owner;

    public AbstractFilter() {
    }

    public AbstractFilter(ISQLTable owner) {
        this.owner = owner;
    }

    public ISQLTable owner() {
        return this.owner;
    }

    public void setOwner(ISQLTable owner) {
        this.owner = owner;
    }

    protected void printField(StringBuilder buffer, ISQLField field, IDatabase database) throws SQLModelException {
        SQLPrinter.printField(database, this.owner, field, buffer);
    }
}

