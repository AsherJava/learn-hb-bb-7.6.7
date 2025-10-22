/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.SortMode
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.datacrud.SortMode;
import java.util.Objects;

public class FieldSort {
    private String fieldKey;
    private SortMode mode;

    public FieldSort() {
    }

    public FieldSort(String fieldKey, SortMode mode) {
        this.fieldKey = fieldKey;
        this.mode = mode;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public SortMode getMode() {
        return this.mode;
    }

    public void setMode(SortMode mode) {
        this.mode = mode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FieldSort fieldSort = (FieldSort)o;
        return Objects.equals(this.fieldKey, fieldSort.fieldKey) && this.mode == fieldSort.mode;
    }

    public int hashCode() {
        int result = Objects.hashCode(this.fieldKey);
        result = 31 * result + Objects.hashCode(this.mode);
        return result;
    }
}

