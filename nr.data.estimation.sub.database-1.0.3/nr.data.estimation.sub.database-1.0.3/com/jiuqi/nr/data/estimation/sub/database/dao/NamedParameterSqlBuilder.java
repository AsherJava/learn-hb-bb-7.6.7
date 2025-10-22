/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.sub.database.dao;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NamedParameterSqlBuilder {
    private final String tableName;
    private StringBuffer sqlTemp = new StringBuffer();

    public NamedParameterSqlBuilder(String tableName) {
        this.tableName = tableName;
    }

    public NamedParameterSqlBuilder insertSQL(String[] columns) {
        this.sqlTemp = new StringBuffer();
        this.sqlTemp.append(" INSERT INTO ");
        this.sqlTemp.append(this.tableName).append(" ( ");
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        this.sqlTemp.append(" ) ");
        this.sqlTemp.append(" VALUES ");
        this.sqlTemp.append(" ( ");
        this.sqlTemp.append(Arrays.stream(columns).map(e -> ":" + e).collect(Collectors.joining(",")));
        this.sqlTemp.append(" ) ");
        return this;
    }

    public NamedParameterSqlBuilder updateSQL(String[] columns) {
        this.sqlTemp = new StringBuffer();
        this.sqlTemp.append(" UPDATE ").append(this.tableName);
        this.sqlTemp.append(" SET ");
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + "=:" + e).collect(Collectors.joining(",")));
        return this;
    }

    public NamedParameterSqlBuilder selectSQL(String[] columns) {
        this.sqlTemp = new StringBuffer();
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        this.sqlTemp.append(" FROM ").append(this.tableName);
        return this;
    }

    public NamedParameterSqlBuilder deleteSQL() {
        this.sqlTemp = new StringBuffer();
        this.sqlTemp.append(" DELETE FROM ");
        this.sqlTemp.append(this.tableName);
        return this;
    }

    public NamedParameterSqlBuilder where() {
        this.sqlTemp.append(" WHERE ");
        return this;
    }

    public NamedParameterSqlBuilder andWhere(String ... columns) {
        this.where();
        this.andColumns(columns);
        return this;
    }

    public NamedParameterSqlBuilder orWhere(String ... columns) {
        this.where();
        this.orColumns(columns);
        return this;
    }

    public NamedParameterSqlBuilder inWhere(String column) {
        this.where();
        this.sqlTemp.append(column).append(" IN (:").append(column).append(")");
        return this;
    }

    public NamedParameterSqlBuilder and() {
        this.sqlTemp.append(" AND ");
        return this;
    }

    public NamedParameterSqlBuilder andColumns(String ... columns) {
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + "=:" + e).collect(Collectors.joining(" AND ")));
        return this;
    }

    public NamedParameterSqlBuilder or() {
        this.sqlTemp.append(" OR ");
        return this;
    }

    public NamedParameterSqlBuilder orColumns(String ... columns) {
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + "=:" + e).collect(Collectors.joining(" OR ")));
        return this;
    }

    public NamedParameterSqlBuilder orderBy(String ... columns) {
        this.sqlTemp.append(" ORDER BY ").append(String.join((CharSequence)",", columns));
        return this;
    }

    public NamedParameterSqlBuilder selectJoinOn(String[] columns, String otherTable, String otherColumn, String column) {
        this.sqlTemp = new StringBuffer();
        this.sqlTemp.append(" SELECT * FROM (");
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        this.sqlTemp.append(" FROM ").append(this.tableName);
        this.sqlTemp.append(",").append(otherTable);
        this.where();
        this.sqlTemp.append(this.tableName).append(".").append(column);
        this.sqlTemp.append(" = ");
        this.sqlTemp.append(otherTable).append(".").append(otherColumn);
        this.sqlTemp.append(")");
        this.sqlTemp.append(" TEMPFORM");
        return this;
    }

    public NamedParameterSqlBuilder groupBy(String ... columns) {
        this.sqlTemp.append(" GROUP BY ").append(String.join((CharSequence)",", columns));
        return this;
    }

    public String toString() {
        return this.sqlTemp.toString();
    }
}

