/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.provider;

import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionMetaData;
import java.util.Arrays;

public class DimensionRow {
    private String key;
    private String code;
    private String Title;
    private String parentKey;
    private Object[] values;
    private Number ordinal;
    private DimensionMetaData metaData;

    public DimensionRow(String key, String code, DimensionMetaData metaData) {
        this.key = key;
        this.code = code;
        this.metaData = metaData;
        this.values = new Object[metaData.size()];
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setValue(Object value, int index) {
        if (index >= 0 && index < this.values.length) {
            this.values[index] = value;
        }
    }

    public Object getValue(int index) {
        if (index < 0 || index >= this.values.length) {
            return null;
        }
        return this.values[index];
    }

    public Object getValue(String name) {
        DimensionColumn column = this.metaData.findColumn(name);
        if (column != null) {
            return this.getValue(column.getIndex());
        }
        return null;
    }

    public Number getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Number ordinal) {
        this.ordinal = ordinal;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
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
        DimensionRow other = (DimensionRow)obj;
        return !(this.key == null ? other.key != null : !this.key.equals(other.key));
    }

    public String toString() {
        return "DimensionRow [key=" + this.key + ", code=" + this.code + ", Title=" + this.Title + ", parentKey=" + this.parentKey + ", values=" + Arrays.toString(this.values) + "]+\n";
    }
}

