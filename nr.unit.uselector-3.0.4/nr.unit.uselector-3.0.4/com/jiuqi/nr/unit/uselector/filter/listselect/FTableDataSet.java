/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.filter.listselect.FTableConditionRow;
import java.util.List;
import java.util.Map;

public class FTableDataSet {
    private int pageSize;
    private Map<String, Integer> matchCount;
    private List<FTableConditionRow> tableData;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Integer> getMatchCount() {
        return this.matchCount;
    }

    public void setMatchCount(Map<String, Integer> matchCount) {
        this.matchCount = matchCount;
    }

    public List<FTableConditionRow> getTableData() {
        return this.tableData;
    }

    public void setTableData(List<FTableConditionRow> tableData) {
        this.tableData = tableData;
    }
}

