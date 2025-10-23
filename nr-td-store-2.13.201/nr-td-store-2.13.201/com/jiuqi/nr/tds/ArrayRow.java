/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds;

import com.jiuqi.nr.tds.TdRowData;
import java.util.Collection;

public class ArrayRow
implements TdRowData {
    private final Object[] values;

    @Override
    public int length() {
        return this.values.length;
    }

    @Override
    public boolean isNull(int index) {
        return this.values[index] == null;
    }

    @Override
    public boolean isNull(String name) {
        return false;
    }

    @Override
    public Object getValue(int index) {
        return this.values[index];
    }

    @Override
    public Object getValue(String name) {
        return null;
    }

    public ArrayRow(int length) {
        this.values = new Object[length];
    }

    public ArrayRow(Object ... values) {
        this.values = values;
    }

    public ArrayRow(Collection<?> values) {
        this.values = values.toArray();
    }

    public static ArrayRow of(Object ... values) {
        return new ArrayRow(values);
    }

    public static ArrayRow of(Collection<?> values) {
        return new ArrayRow(values);
    }

    public void setValue(int index, Object value) {
        this.values[index] = value;
    }

    @Override
    public Object[] toArray() {
        return this.values;
    }
}

