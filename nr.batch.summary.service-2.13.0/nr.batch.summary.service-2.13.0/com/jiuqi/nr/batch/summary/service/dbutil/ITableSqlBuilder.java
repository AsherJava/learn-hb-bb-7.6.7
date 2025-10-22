/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ITableSqlBuilder {
    protected final StringBuffer sqlTemp = new StringBuffer();

    protected void where() {
        this.sqlTemp.append(" WHERE ");
    }

    protected void on() {
        this.sqlTemp.append(" ON ");
    }

    protected void and() {
        this.sqlTemp.append(" AND ");
    }

    protected void or() {
        this.sqlTemp.append(" OR ");
    }

    protected void equalSign() {
        this.sqlTemp.append("=");
    }

    protected void bracketsStart() {
        this.sqlTemp.append(" (");
    }

    protected void bracketsEnd() {
        this.sqlTemp.append(")");
    }

    protected void aliasColName(String alias, String colName) {
        this.sqlTemp.append(alias).append(".").append(colName);
    }

    protected void aliasColValue(String value) {
        this.sqlTemp.append("'").append(value).append("'");
    }

    protected void aliasColValue(List<String> values) {
        this.bracketsStart();
        this.sqlTemp.append(values.stream().map(e -> "'" + e + "'").collect(Collectors.joining(",")));
        this.bracketsEnd();
    }

    protected void assignCond(String alias, String colName, String value) {
        this.aliasColName(alias, colName);
        this.equalSign();
        this.aliasColValue(value);
    }

    protected void assignCond(String colName, String value) {
        this.sqlTemp.append(colName);
        this.equalSign();
        this.aliasColValue(value);
    }

    public void concat(ITableSqlBuilder sqlBuilder) {
        this.sqlTemp.append(sqlBuilder.toString());
    }

    public void end() {
    }

    public String toString() {
        return this.sqlTemp.toString();
    }
}

