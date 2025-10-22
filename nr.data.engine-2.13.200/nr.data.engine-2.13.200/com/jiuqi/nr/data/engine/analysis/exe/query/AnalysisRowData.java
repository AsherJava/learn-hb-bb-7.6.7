/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.analysis.exe.query;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisMemoryDataSetReader;
import java.util.Arrays;
import java.util.List;

public class AnalysisRowData
implements Comparable<AnalysisRowData> {
    protected DimensionValueSet rowKey;
    protected Object[] datas;
    protected int group_flag = -1;
    private List<Object> groupFieldValues;
    protected AnalysisMemoryDataSetReader ownner;
    private int keyOrderIndex = -1;
    private String groupFieldValueStr;

    public AnalysisRowData(DimensionValueSet rowKey, int columnCount, AnalysisMemoryDataSetReader ownner) {
        this.rowKey = rowKey;
        this.datas = new Object[columnCount];
        this.ownner = ownner;
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public Object readValue(int index) {
        return this.datas[index];
    }

    public void writeValue(int index, Object value) {
        this.datas[index] = value;
    }

    public int getGroup_flag() {
        return this.group_flag;
    }

    public void setRowKey(DimensionValueSet rowKey) {
        this.rowKey = rowKey;
    }

    public void setGroup_flag(int group_flag) {
        this.group_flag = group_flag;
    }

    public int size() {
        return this.datas.length;
    }

    public AnalysisMemoryDataSetReader getOwnner() {
        return this.ownner;
    }

    public List<Object> getGroupFieldValues() {
        return this.groupFieldValues;
    }

    public void setGroupFieldValues(List<Object> groupFieldValues) {
        this.groupFieldValues = groupFieldValues;
    }

    public String toString() {
        return "MemoryRowData [rowKey=" + this.rowKey + ", datas=" + Arrays.toString(this.datas) + "]";
    }

    @Override
    public int compareTo(AnalysisRowData o) {
        int result = 0;
        if (this.groupFieldValues != null) {
            for (int i = 0; i < this.groupFieldValues.size(); ++i) {
                Object value2;
                Comparable value1 = (Comparable)this.groupFieldValues.get(i);
                result = value1.compareTo(value2 = o.groupFieldValues.get(i));
                if (result == 0) continue;
                return result;
            }
        } else if (this.keyOrderIndex >= 0) {
            result = this.keyOrderIndex - o.keyOrderIndex;
        }
        return result;
    }

    public int getKeyOrderIndex() {
        return this.keyOrderIndex;
    }

    public void setKeyOrderIndex(int keyOrderIndex) {
        this.keyOrderIndex = keyOrderIndex;
    }

    public String getGroupFieldValueStr() {
        if (this.groupFieldValueStr == null) {
            this.groupFieldValueStr = "";
            if (this.groupFieldValues != null) {
                for (Object obj : this.groupFieldValues) {
                    this.groupFieldValueStr = this.groupFieldValueStr + obj.toString();
                }
            }
        }
        return this.groupFieldValueStr;
    }
}

