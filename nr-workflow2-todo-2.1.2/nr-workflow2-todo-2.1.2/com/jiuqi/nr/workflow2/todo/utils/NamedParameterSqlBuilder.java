/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NamedParameterSqlBuilder {
    private final String tableName;
    private StringBuilder sqlTemp = new StringBuilder();

    public NamedParameterSqlBuilder(String tableName) {
        this.tableName = tableName;
    }

    public NamedParameterSqlBuilder insertSQL(String ... columns) {
        this.sqlTemp = new StringBuilder();
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

    public NamedParameterSqlBuilder updateSQL(String ... columns) {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" UPDATE ").append(this.tableName);
        this.sqlTemp.append(" SET ");
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + " = :" + e).collect(Collectors.joining(",")));
        return this;
    }

    public NamedParameterSqlBuilder selectSQL(String ... columns) {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        this.sqlTemp.append(" FROM ").append(this.tableName);
        return this;
    }

    public NamedParameterSqlBuilder deleteSQL() {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" DELETE FROM ");
        this.sqlTemp.append(this.tableName);
        return this;
    }

    public NamedParameterSqlBuilder countSQL(String countColumn, String ... columns) {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", columns));
        if (columns.length > 0) {
            this.sqlTemp.append(",");
        }
        this.sqlTemp.append(" COUNT(*) AS ");
        this.sqlTemp.append(countColumn);
        this.sqlTemp.append(" FROM ").append(this.tableName);
        return this;
    }

    public NamedParameterSqlBuilder where() {
        this.sqlTemp.append(" WHERE ");
        return this;
    }

    public NamedParameterSqlBuilder adaptiveWhere() {
        this.sqlTemp.append(" WHERE 1=1 ");
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

    public NamedParameterSqlBuilder inColumn(String column) {
        this.sqlTemp.append(column).append(" IN (:").append(column).append(")");
        return this;
    }

    public NamedParameterSqlBuilder and() {
        this.sqlTemp.append(" AND ");
        return this;
    }

    public NamedParameterSqlBuilder andColumn(String column) {
        this.sqlTemp.append(column).append(" = :").append(column);
        return this;
    }

    public NamedParameterSqlBuilder andMoreThanColumn(String column) {
        this.sqlTemp.append(column).append(" > :").append(column);
        return this;
    }

    public NamedParameterSqlBuilder andMoreThanOrEqualColumn(String column) {
        this.sqlTemp.append(column).append(" >= :").append(column);
        return this;
    }

    public NamedParameterSqlBuilder andLessThanColumn(String column) {
        this.sqlTemp.append(column).append(" < :").append(column);
        return this;
    }

    public NamedParameterSqlBuilder andLessThanOrEqualColumn(String column) {
        this.sqlTemp.append(column).append(" <= :").append(column);
        return this;
    }

    public NamedParameterSqlBuilder andColumns(String ... columns) {
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + " = :" + e).collect(Collectors.joining(" AND ")));
        return this;
    }

    public NamedParameterSqlBuilder or() {
        this.sqlTemp.append(" OR ");
        return this;
    }

    public NamedParameterSqlBuilder orColumns(String ... columns) {
        this.sqlTemp.append(Arrays.stream(columns).map(e -> e + " = :" + e).collect(Collectors.joining(" OR ")));
        return this;
    }

    public NamedParameterSqlBuilder orderByColumns(String ... columns) {
        this.sqlTemp.append(" ORDER BY ").append(String.join((CharSequence)",", columns));
        return this;
    }

    public NamedParameterSqlBuilder orderBy(String column) {
        this.sqlTemp.append(" ORDER BY ").append(column);
        return this;
    }

    public NamedParameterSqlBuilder orderByDesc(String column) {
        this.sqlTemp.append(" ORDER BY ").append(column).append(" DESC");
        return this;
    }

    public NamedParameterSqlBuilder selectJoinOn(String[] selectColumns, String joinTableName, String originalColumn, String joinTableColumn) {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", selectColumns));
        this.sqlTemp.append(" FROM ").append(this.tableName);
        this.sqlTemp.append(" INNER JOIN ").append(joinTableName);
        this.sqlTemp.append(" ON ");
        this.sqlTemp.append(this.tableName).append(".").append(originalColumn);
        this.sqlTemp.append(" = ");
        this.sqlTemp.append(joinTableName).append(".").append(joinTableColumn);
        return this;
    }

    public NamedParameterSqlBuilder innerJoin(String joinTableName) {
        this.sqlTemp.append(" INNER JOIN ").append(joinTableName);
        return this;
    }

    public NamedParameterSqlBuilder on() {
        this.sqlTemp.append(" ON ");
        return this;
    }

    public NamedParameterSqlBuilder onColumn(String originalTableColumn, String joinTableColumn) {
        this.sqlTemp.append(originalTableColumn).append(" = ").append(joinTableColumn);
        return this;
    }

    public NamedParameterSqlBuilder selectLeftJoinOn(String[] selectColumns, String joinTableName, String originalColumn, String joinTableColumn) {
        this.sqlTemp = new StringBuilder();
        this.sqlTemp.append(" SELECT ");
        this.sqlTemp.append(String.join((CharSequence)",", selectColumns));
        this.sqlTemp.append(" FROM ").append(this.tableName);
        this.sqlTemp.append(" LEFT JOIN ").append(joinTableName);
        this.sqlTemp.append(" ON ");
        this.sqlTemp.append(this.tableName).append(".").append(originalColumn);
        this.sqlTemp.append(" = ");
        this.sqlTemp.append(joinTableName).append(".").append(joinTableColumn);
        return this;
    }

    public NamedParameterSqlBuilder groupBy(String ... columns) {
        this.sqlTemp.append(" GROUP BY ").append(String.join((CharSequence)",", columns));
        return this;
    }

    public NamedParameterSqlBuilder limit(int offset, int pageSize) {
        this.sqlTemp.append(" LIMIT ").append(offset).append(", ").append(pageSize);
        return this;
    }

    public String toString() {
        return this.sqlTemp.toString();
    }
}

