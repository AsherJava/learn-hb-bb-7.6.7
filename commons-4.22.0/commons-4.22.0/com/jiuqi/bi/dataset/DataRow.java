/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.DataSetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

public abstract class DataRow
implements Cloneable,
Iterable<Object> {
    private Object[] buffer;

    public DataRow() {
    }

    public DataRow(Object[] buffer) {
        this.buffer = buffer;
    }

    public DataRow(int length) {
        this.buffer = new Object[length];
    }

    public Object[] getBuffer() {
        return this.buffer;
    }

    public void _setBuffer(Object[] buffer) {
        this.buffer = buffer;
    }

    public abstract boolean commit() throws DataSetException;

    public boolean wasNull(int index) {
        return this.buffer[index] == null;
    }

    public void setNull(int index) {
        this.buffer[index] = null;
    }

    public Object getValue(int index) {
        return this.buffer[index];
    }

    public void setValue(int index, Object value) {
        this.buffer[index] = value;
    }

    public final int getInt(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0 : value.intValue();
    }

    public final long getLong(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0L : value.longValue();
    }

    public final boolean getBoolean(int index) {
        Boolean value = (Boolean)this.getValue(index);
        return value == null ? false : value;
    }

    public final Calendar getDate(int index) {
        return (Calendar)this.getValue(index);
    }

    public final double getDouble(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0.0 : value.doubleValue();
    }

    public final String getString(int index) {
        Object value = this.getValue(index);
        return value == null ? null : value.toString();
    }

    public final BlobValue getBlob(int index) {
        return (BlobValue)this.getValue(index);
    }

    public final BigDecimal getBigDecimal(int index) {
        return (BigDecimal)this.getValue(index);
    }

    public final void setInt(int index, int value) {
        this.setValue(index, value);
    }

    public final void setLong(int index, long value) {
        this.setValue(index, value);
    }

    public final void setBoolean(int index, boolean value) {
        this.setValue(index, value);
    }

    public final void setDate(int index, Calendar value) {
        this.setValue(index, value);
    }

    public final void setDouble(int index, double value) {
        this.setValue(index, value);
    }

    public final void setString(int index, String value) {
        this.setValue(index, value);
    }

    public final void setBlob(int index, BlobValue value) {
        this.setValue(index, value);
    }

    public final void setBigDecimal(int index, BigDecimal value) {
        this.setValue(index, value);
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>(){
            private int index = 0;

            @Override
            public Object next() {
                return DataRow.this.getValue(this.index++);
            }

            @Override
            public boolean hasNext() {
                return DataRow.this.buffer != null && this.index < DataRow.this.buffer.length;
            }
        };
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return this.buffer == null ? "[]" : Arrays.toString(this.buffer);
    }
}

