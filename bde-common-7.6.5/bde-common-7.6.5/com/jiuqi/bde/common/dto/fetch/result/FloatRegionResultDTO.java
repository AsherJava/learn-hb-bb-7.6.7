/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.result;

import java.util.List;
import java.util.Map;

public class FloatRegionResultDTO {
    private Map<String, Integer> floatColumns;
    private List<Object[]> rowDatas;

    public FloatRegionResultDTO() {
    }

    public FloatRegionResultDTO(Map<String, Integer> floatColumns, List<Object[]> rowDatas) {
        this.floatColumns = floatColumns;
        this.rowDatas = rowDatas;
    }

    public Map<String, Integer> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(Map<String, Integer> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

