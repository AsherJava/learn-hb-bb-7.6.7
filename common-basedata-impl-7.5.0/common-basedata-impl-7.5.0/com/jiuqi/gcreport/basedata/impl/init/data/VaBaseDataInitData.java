/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.init.data;

import java.util.List;
import java.util.Map;

public class VaBaseDataInitData {
    private String tableName;
    private boolean allowDataExists;
    private String checkFieldNames;
    private List<Map<String, Object>> rowDatas;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isAllowDataExists() {
        return this.allowDataExists;
    }

    public void setAllowDataExists(boolean allowDataExists) {
        this.allowDataExists = allowDataExists;
    }

    public String getCheckFieldNames() {
        return this.checkFieldNames;
    }

    public void setCheckFieldNames(String checkFieldNames) {
        this.checkFieldNames = checkFieldNames;
    }

    public List<Map<String, Object>> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Map<String, Object>> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

