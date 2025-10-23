/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.dataextract;

import java.util.Arrays;

public class ExtractDataRow {
    private Object[] values;

    public ExtractDataRow(int columnCount) {
        this.values = new Object[columnCount];
    }

    public int getFieldSize() {
        return this.values.length;
    }

    public Object getValue(int index) {
        return this.values[index];
    }

    public void setFieldValue(int index, Object value) {
        this.values[index] = value;
    }

    public String toString() {
        return Arrays.toString(this.values);
    }
}

