/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract;

import java.util.ArrayList;
import java.util.List;

public class ExtractDataRow {
    private List<Object> values = new ArrayList<Object>();

    public int getFieldSize() {
        return this.values.size();
    }

    public Object getValue(int index) {
        return this.values.get(index);
    }

    public void addFieldValue(Object value) {
        this.values.add(value);
    }

    public String toString() {
        return this.values.toString();
    }
}

