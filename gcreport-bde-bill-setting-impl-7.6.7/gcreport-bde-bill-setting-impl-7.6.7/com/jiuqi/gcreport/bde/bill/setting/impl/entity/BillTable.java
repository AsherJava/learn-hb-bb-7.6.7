/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.entity;

import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillField;
import java.util.Map;

public class BillTable {
    private String tableName;
    private String tableTitle;
    private Map<String, BillField> fields;

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

    public Map<String, BillField> getFields() {
        return this.fields;
    }

    public void setFields(Map<String, BillField> fields) {
        this.fields = fields;
    }
}

