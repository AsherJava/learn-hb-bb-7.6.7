/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableSqlBuilder;
import java.util.List;

public class ITableDeleteSqlBuilder
extends ITableSqlBuilder {
    public ITableDeleteSqlBuilder(String tableName) {
        this.sqlTemp.append(" DELETE FROM ").append(tableName);
    }

    public void where(String colName, String value) {
        this.where();
        this.assignCond(colName, value);
    }

    public void inWhereCondition(String colName, List<String> values) {
        this.sqlTemp.append(colName);
        this.sqlTemp.append(" IN ");
        this.aliasColValue(values);
    }

    public void whereInCondition(String colName, List<String> values) {
        this.where();
        this.sqlTemp.append(colName);
        this.sqlTemp.append(" IN ");
        this.aliasColValue(values);
    }

    public void andWhereCondition(String colName, String value) {
        this.and();
        this.assignCond(colName, value);
    }
}

