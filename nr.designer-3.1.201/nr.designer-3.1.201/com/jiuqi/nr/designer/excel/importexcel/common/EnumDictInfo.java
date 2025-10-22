/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import com.jiuqi.nr.designer.excel.importexcel.common.DataRow;
import java.util.List;

public class EnumDictInfo {
    private String key;
    private String tableName;
    private String tableCode;
    private List<DataRow> dataRowList;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public List<DataRow> getDataRowList() {
        return this.dataRowList;
    }

    public void setDataRowList(List<DataRow> dataRowList) {
        this.dataRowList = dataRowList;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

