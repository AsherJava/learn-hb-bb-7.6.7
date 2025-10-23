/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.DataTableVO;
import java.util.List;

public class SummarySetVO {
    private String table;
    private List<String> keys;
    private DataTableVO tableInfo;

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public DataTableVO getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(DataTableVO tableInfo) {
        this.tableInfo = tableInfo;
    }
}

