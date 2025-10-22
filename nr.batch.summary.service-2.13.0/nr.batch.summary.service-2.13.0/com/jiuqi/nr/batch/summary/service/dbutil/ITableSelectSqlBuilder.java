/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.nr.batch.summary.service.dbutil.ITableSqlBuilder;
import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;

public class ITableSelectSqlBuilder
extends ITableSqlBuilder {
    public ITableSelectSqlBuilder() {
        this.sqlTemp.append(" SELECT ");
    }

    public void addSelectColumn(String colName, String alias) {
        this.sqlTemp.append(alias).append(".").append(colName).append(" ").append(colName).append(",");
    }

    public void addSelectColumn(String colName, String alias, SummaryFunction function) {
        if (SummaryFunction.DISTINCT_COUNT == function) {
            this.sqlTemp.append(SummaryFunction.COUNT.name).append("(").append(SummaryFunction.DISTINCT_COUNT.name).append(" ");
        } else {
            this.sqlTemp.append(function.name).append("(");
        }
        this.sqlTemp.append(alias).append(".").append(colName).append(")").append(" ").append(colName).append(",");
    }

    public void addSelectColumnMinValue(String colName, String value, SummaryFunction function) {
        this.sqlTemp.append(function.name).append("(").append("'").append(value).append("'").append(") ").append(colName).append(",");
    }

    public void from(String tableName) {
        int i = this.sqlTemp.lastIndexOf(",");
        if (i != -1) {
            this.sqlTemp.deleteCharAt(i);
        }
        this.sqlTemp.append(" FROM ").append(tableName);
    }

    public void rightJoin(String tableName) {
        this.sqlTemp.append(" RIGHT JOIN ").append(tableName);
    }

    public void joinOnCondition(String lAlias, String lColName, String rAlias, String rColName) {
        this.on();
        this.aliasColName(lAlias, lColName);
        this.equalSign();
        this.aliasColName(rAlias, rColName);
    }

    public void joinAndCondition(String lAlias, String lColName, String rAlias, String rColName) {
        this.and();
        this.aliasColName(lAlias, lColName);
        this.equalSign();
        this.aliasColName(rAlias, rColName);
    }

    public void groupBy(String alias, String colName) {
        this.sqlTemp.append(" GROUP BY ");
        this.aliasColName(alias, colName);
    }

    public void addGroupByColumn(String alias, String colName) {
        this.sqlTemp.append(", ");
        this.aliasColName(alias, colName);
    }

    public void where(String alias, String colName, String value) {
        this.where();
        this.assignCond(alias, colName, value);
    }

    public void andWhereCondition(String alias, String colName, String value) {
        this.and();
        this.assignCond(alias, colName, value);
    }

    public void orWhereCondition(String alias, String colName, String value) {
        this.or();
        this.assignCond(alias, colName, value);
    }
}

