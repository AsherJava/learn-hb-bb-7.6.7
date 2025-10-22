/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillChildTableExtractField;
import java.util.List;

public class BillChildTableData {
    private String tableName;
    private String tableTitle;
    private List<BillChildTableExtractField> columns;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public List<BillChildTableExtractField> getColumns() {
        return this.columns;
    }

    public void setColumns(List<BillChildTableExtractField> columns) {
        this.columns = columns;
    }
}

