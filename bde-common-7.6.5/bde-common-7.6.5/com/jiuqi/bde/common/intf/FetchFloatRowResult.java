/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import java.util.List;
import java.util.Map;

public class FetchFloatRowResult {
    private List<String[]> rowDatas;
    private Map<String, Integer> floatColumns;
    private Map<String, ColumnTypeEnum> floatColumnsType;

    public Map<String, Integer> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(Map<String, Integer> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public Map<String, ColumnTypeEnum> getFloatColumnsType() {
        return this.floatColumnsType;
    }

    public void setFloatColumnsType(Map<String, ColumnTypeEnum> floatColumnsType) {
        this.floatColumnsType = floatColumnsType;
    }

    public List<String[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<String[]> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

