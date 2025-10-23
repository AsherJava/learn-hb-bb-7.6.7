/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface TdRowData
extends Iterable<Object> {
    public int length();

    public boolean isNull(int var1);

    public boolean isNull(String var1);

    public Object getValue(int var1);

    public Object getValue(String var1);

    default public int getInt(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0 : value.intValue();
    }

    default public int getInt(String name) {
        Number value = (Number)this.getValue(name);
        return value == null ? 0 : value.intValue();
    }

    default public long getLong(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0L : value.longValue();
    }

    default public long getLong(String name) {
        Number value = (Number)this.getValue(name);
        return value == null ? 0L : value.longValue();
    }

    default public double getDouble(int index) {
        Number value = (Number)this.getValue(index);
        return value == null ? 0.0 : value.doubleValue();
    }

    default public double getDouble(String name) {
        Number value = (Number)this.getValue(name);
        return value == null ? 0.0 : value.doubleValue();
    }

    default public BigDecimal getBigDecimal(int index) {
        Object value = this.getValue(index);
        if (value == null) {
            return null;
        }
        return value instanceof BigDecimal ? (BigDecimal)value : BigDecimal.valueOf(((Number)value).doubleValue());
    }

    default public BigDecimal getBigDecimal(String name) {
        Object value = this.getValue(name);
        if (value == null) {
            return null;
        }
        return value instanceof BigDecimal ? (BigDecimal)value : BigDecimal.valueOf(((Number)value).doubleValue());
    }

    default public String getString(int index) {
        Object value = this.getValue(index);
        return value == null ? null : value.toString();
    }

    default public String getString(String name) {
        Object value = this.getValue(name);
        return value == null ? null : value.toString();
    }

    default public boolean getBoolean(int index) {
        Object value = this.getValue(index);
        return value instanceof Boolean ? (Boolean)value : Boolean.parseBoolean(value.toString());
    }

    default public boolean getBoolean(String name) {
        Object value = this.getValue(name);
        return value instanceof Boolean ? (Boolean)value : Boolean.parseBoolean(value.toString());
    }

    default public Date getDate(int index) {
        Object value = this.getValue(index);
        return value instanceof Date ? (Date)value : new Date(((Number)value).longValue());
    }

    default public Date getDate(String name) {
        Object value = this.getValue(name);
        return value instanceof Date ? (Date)value : new Date(((Number)value).longValue());
    }

    default public Object[] toArray() {
        Object[] arr = new Object[this.length()];
        for (int i = 0; i < this.length(); ++i) {
            arr[i] = this.getValue(i);
        }
        return arr;
    }

    @Override
    default public Spliterator<Object> spliterator() {
        return Spliterators.spliterator(this.iterator(), (long)this.length(), 0);
    }

    default public Stream<Object> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    default public Iterator<Object> iterator() {
        return new Iterator<Object>(){
            private int index;

            @Override
            public Object next() {
                return TdRowData.this.getValue(this.index++);
            }

            @Override
            public boolean hasNext() {
                return this.index < TdRowData.this.length();
            }
        };
    }
}

