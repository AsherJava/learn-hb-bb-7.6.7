/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.reader.AbstractQueryFieldDataReader;
import java.math.BigDecimal;
import java.util.Arrays;

public class MemoryRowData {
    protected DimensionValueSet rowKey;
    protected Object[] datas;
    private int group_flag;
    private String recKey;

    public MemoryRowData(DimensionValueSet rowKey, int columnCount) {
        this.rowKey = rowKey;
        this.datas = new Object[columnCount];
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public Object[] getDatas() {
        return this.datas;
    }

    public int getGroup_flag() {
        return this.group_flag;
    }

    public String getRecKey() {
        return this.recKey;
    }

    public void setRowKey(DimensionValueSet rowKey) {
        this.rowKey = rowKey;
    }

    public void setGroup_flag(int group_flag) {
        this.group_flag = group_flag;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public void loadData(DataRow row, boolean sumDatas, int memeryStartIndex, int rowKeyStartIndex, int bizKeyOrderIndex) {
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object originalValue;
            Object value = row.getValue(i);
            if (sumDatas && (originalValue = this.datas[memeryStartIndex + i]) != null) {
                if (value == null) {
                    value = originalValue;
                } else if (originalValue instanceof BigDecimal) {
                    value = ((BigDecimal)originalValue).add((BigDecimal)value);
                } else if (originalValue instanceof Number) {
                    value = ((Number)originalValue).doubleValue() + ((Number)value).doubleValue();
                }
            }
            this.datas[memeryStartIndex + i] = value;
        }
        if (bizKeyOrderIndex >= 0) {
            this.setRecKey((String)row.getValue(bizKeyOrderIndex));
        }
    }

    public void loadDataFromDataSetReader(AbstractQueryFieldDataReader dataSetReader, int memeryStartIndex, int rowKeyStartIndex, int bizKeyOrderIndex) throws Exception {
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object value;
            this.datas[memeryStartIndex + i] = value = dataSetReader.readData(i + 1);
        }
    }

    public void loadDataFromDataRow(DataRow row, int memeryStartIndex, int rowKeyStartIndex, int bizKeyOrderIndex) throws Exception {
        for (int i = 0; i < rowKeyStartIndex; ++i) {
            Object value;
            this.datas[memeryStartIndex + i] = value = row.getValue(i);
        }
    }

    public String toString() {
        return "MemoryRowData [rowKey=" + this.rowKey + ", datas=" + Arrays.toString(this.datas) + "]";
    }
}

