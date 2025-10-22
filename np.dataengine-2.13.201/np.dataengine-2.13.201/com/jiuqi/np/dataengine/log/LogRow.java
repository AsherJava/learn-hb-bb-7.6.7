/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.np.dataengine.log;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.log.LogType;
import java.sql.Timestamp;
import java.text.DateFormat;

public class LogRow {
    public static final String LOG_ROW_HEAD = "DATAQUERY_SQLLOG:";
    private LogType type;
    private long exeCost;
    private long totalCost;
    private int colCount;
    private int rowCount;
    private String userName;
    private Timestamp time;
    private String tableName;
    private String sql;
    private final DateFormat dFormat;

    public LogRow(LogType type, long exeCost, long totalCost, int colCount, int rowCount, String tableName, String sql, DateFormat dFormat) {
        this.type = type;
        this.exeCost = exeCost;
        this.totalCost = totalCost;
        this.colCount = colCount;
        this.rowCount = rowCount;
        this.userName = NpContextHolder.getContext().getUserName();
        this.time = new Timestamp(System.currentTimeMillis());
        this.tableName = tableName;
        this.sql = sql;
        this.dFormat = dFormat;
    }

    public LogType getType() {
        return this.type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public long getExeCost() {
        return this.exeCost;
    }

    public void setExeCost(long exeCost) {
        this.exeCost = exeCost;
    }

    public long getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public int getColCount() {
        return this.colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean isQuerySql() {
        return LogType.SELECT == this.type || LogType.STREAM == this.type;
    }

    public String toString() {
        return LOG_ROW_HEAD + this.type.name() + "," + this.exeCost + "," + this.totalCost + "," + this.colCount + "," + this.rowCount + "," + this.userName + "," + this.dFormat.format(this.time) + ",\"" + this.sql + "\"";
    }
}

