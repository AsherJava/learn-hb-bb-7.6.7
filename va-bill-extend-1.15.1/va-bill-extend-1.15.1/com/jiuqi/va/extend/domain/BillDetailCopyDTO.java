/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.domain;

import java.util.List;
import java.util.Map;

public class BillDetailCopyDTO {
    private String tableName;
    private List<Map<String, Object>> rowData;
    private List<Map<String, Object>> copyRowData;

    public BillDetailCopyDTO() {
    }

    public BillDetailCopyDTO(String tableName, List<Map<String, Object>> rowData, List<Map<String, Object>> copyRowData) {
        this.tableName = tableName;
        this.rowData = rowData;
        this.copyRowData = copyRowData;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Map<String, Object>> getRowData() {
        return this.rowData;
    }

    public void setRowData(List<Map<String, Object>> rowData) {
        this.rowData = rowData;
    }

    public List<Map<String, Object>> getCopyRowData() {
        return this.copyRowData;
    }

    public void setCopyRowData(List<Map<String, Object>> copyRowData) {
        this.copyRowData = copyRowData;
    }
}

