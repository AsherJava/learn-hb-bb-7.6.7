/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.model;

import com.jiuqi.nr.data.access.model.ColumnInfo;
import com.jiuqi.nr.data.access.model.IndexInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogicTableInfo {
    private String tableName;
    private String title;
    private String description;
    private List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    private List<ColumnInfo> primaryColumns = new ArrayList<ColumnInfo>();
    private List<IndexInfo> indexInfos = new ArrayList<IndexInfo>();

    public LogicTableInfo(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ColumnInfo> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public List<IndexInfo> getIndexInfos() {
        return this.indexInfos;
    }

    public void setIndexInfos(List<IndexInfo> indexInfos) {
        this.indexInfos = indexInfos;
    }

    public List<ColumnInfo> getPrimaryColumns() {
        return this.primaryColumns;
    }

    public void setPrimaryColumns(List<ColumnInfo> primaryColumns) {
        this.primaryColumns = primaryColumns;
    }

    public void addColumns(List<ColumnInfo> columns) {
        this.columns.addAll(columns.stream().map(c -> {
            c.setKeyField(false);
            return c;
        }).collect(Collectors.toList()));
    }

    public void addPrimaryColumns(List<ColumnInfo> primaryColumns) {
        this.columns.addAll(primaryColumns.stream().map(c -> {
            c.setKeyField(true);
            return c;
        }).collect(Collectors.toList()));
    }
}

