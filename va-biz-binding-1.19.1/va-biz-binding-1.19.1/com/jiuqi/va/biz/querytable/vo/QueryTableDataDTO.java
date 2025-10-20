/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.vo;

import java.util.Map;

public class QueryTableDataDTO {
    private Map<String, String> rowData;
    private Map<String, Object> assistRowDataMap;

    public Map<String, String> getRowData() {
        return this.rowData;
    }

    public void setRowData(Map<String, String> rowData) {
        this.rowData = rowData;
    }

    public Map<String, Object> getAssistRowDataMap() {
        return this.assistRowDataMap;
    }

    public void setAssistRowDataMap(Map<String, Object> assistRowDataMap) {
        this.assistRowDataMap = assistRowDataMap;
    }
}

