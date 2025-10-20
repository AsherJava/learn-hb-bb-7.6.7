/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.fetch.vo;

import java.util.LinkedHashMap;
import java.util.List;

public class FetchQueryResultVO {
    private LinkedHashMap<String, Integer> floatColumns;
    private List<String[]> rowDatas;

    public LinkedHashMap<String, Integer> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(LinkedHashMap<String, Integer> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public List<String[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<String[]> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

