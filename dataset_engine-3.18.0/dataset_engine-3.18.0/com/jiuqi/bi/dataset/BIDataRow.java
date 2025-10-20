/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

public class BIDataRow {
    private int rownum;
    private final Object[] value;

    public BIDataRow(Object[] value, int rownum) {
        this.value = value;
        this.rownum = rownum;
    }

    public Object[] getBuffer() {
        return this.value;
    }

    public int getRowNum() {
        return this.rownum;
    }

    public double getDouble(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0.0 : value.doubleValue();
    }

    public int getInt(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0 : value.intValue();
    }

    public String getString(int index) {
        Object value = this.getValue(index);
        return value == null ? null : value.toString();
    }

    public Calendar getDate(int index) {
        return (Calendar)this.getValue(index);
    }

    public boolean getBoolean(int index) {
        Boolean value = (Boolean)this.getValue(index);
        return value == null ? false : value;
    }

    public BigDecimal getBigDecimal(int index) {
        Object value = this.getValue(index);
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        if (value instanceof Number) {
            Number v = (Number)value;
            return new BigDecimal(v.toString());
        }
        throw new RuntimeException("\u65e0\u6cd5\u5b9e\u73b0\u6570\u636e\u7c7b\u578b\u8f6c\u6362");
    }

    public boolean wasNull(int index) {
        return this.value[index] == null;
    }

    public Object getValue(int index) {
        return this.value[index];
    }

    public String toString() {
        return this.getRowNum() + " : " + (this.value == null ? "[]" : Arrays.toString(this.value));
    }
}

