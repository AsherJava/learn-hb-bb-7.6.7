/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;

public class FloatCopyRowData
implements Cloneable {
    protected DimensionValueSet rowKey;
    protected String keyColumnValue;
    protected AbstractData[] queryColumnValues;

    public FloatCopyRowData(int size) {
        this.queryColumnValues = new AbstractData[size];
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public void setRowKey(DimensionValueSet rowKey) {
        this.rowKey = rowKey;
    }

    public String getKeyColumnValue() {
        return this.keyColumnValue;
    }

    public void setKeyColumnValue(String keyColumnValue) {
        this.keyColumnValue = keyColumnValue;
    }

    public AbstractData getValue(int index) {
        return this.queryColumnValues[index];
    }

    public void setValue(int index, AbstractData value) {
        this.queryColumnValues[index] = value;
    }

    public int size() {
        return this.queryColumnValues.length;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.keyColumnValue == null ? 0 : this.keyColumnValue.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        FloatCopyRowData other = (FloatCopyRowData)obj;
        return !(this.keyColumnValue == null ? other.keyColumnValue != null : !this.keyColumnValue.equals(other.keyColumnValue));
    }

    protected Object clone() throws CloneNotSupportedException {
        FloatCopyRowData newRowData = new FloatCopyRowData(this.queryColumnValues.length);
        newRowData.rowKey = this.rowKey;
        newRowData.keyColumnValue = this.keyColumnValue;
        System.arraycopy(this.queryColumnValues.length, 0, newRowData.queryColumnValues, 0, this.queryColumnValues.length);
        return newRowData;
    }
}

