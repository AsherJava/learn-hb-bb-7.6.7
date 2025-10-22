/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableSqlBuilder;
import java.util.List;

public class ITableInsertSqlBuilder
extends ITableSqlBuilder {
    public ITableInsertSqlBuilder(String tableName) {
        this.sqlTemp.append(" INSERT INTO ").append(tableName);
    }

    public void addInsertColumns(List<String> columns) {
        this.bracketsStart();
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        this.bracketsEnd();
    }

    public void addValues(List<String> values) {
        this.sqlTemp.append(" VALUES ");
        this.bracketsStart();
        this.sqlTemp.append(String.join((CharSequence)",", values));
        this.bracketsEnd();
    }
}

