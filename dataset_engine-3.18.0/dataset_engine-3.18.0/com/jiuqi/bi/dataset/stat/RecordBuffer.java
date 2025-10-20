/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.dataset.stat;

import com.jiuqi.bi.syntax.DataType;
import java.util.Arrays;
import java.util.List;

public class RecordBuffer
implements Comparable<RecordBuffer>,
Cloneable {
    private int[] firstRowInDs;
    private Object[] buffer;
    private List<Integer> destKeyDimList;
    private String min_timekey;
    private String max_timekey;
    private int destKeyDimSize;

    public RecordBuffer(int colSize, List<Integer> destKeyDimList) {
        this.buffer = new Object[colSize];
        this.destKeyDimList = destKeyDimList;
        this.destKeyDimSize = destKeyDimList.size();
    }

    public void setMin_timekey(String min_timekey) {
        this.min_timekey = min_timekey;
    }

    public void setMax_timekey(String max_timekey) {
        this.max_timekey = max_timekey;
    }

    public String getMax_timekey() {
        return this.max_timekey;
    }

    public String getMin_timekey() {
        return this.min_timekey;
    }

    public void setFirstRowInDs(int[] firstRowInDs) {
        this.firstRowInDs = firstRowInDs;
    }

    public int[] getFirstRowInDs() {
        return this.firstRowInDs;
    }

    public Object[] getBuffer() {
        return this.buffer;
    }

    public Object getValue(int index) {
        return this.buffer[index];
    }

    public void setValue(int index, Object value) {
        this.buffer[index] = value;
    }

    @Override
    public int compareTo(RecordBuffer o) {
        for (int i = 0; i < this.destKeyDimSize; ++i) {
            int col = this.destKeyDimList.get(i);
            int cv = DataType.compareObject((Object)this.buffer[col], (Object)o.buffer[col]);
            if (cv == 0) continue;
            return cv;
        }
        return 0;
    }

    public RecordBuffer clone() {
        try {
            RecordBuffer result = (RecordBuffer)super.clone();
            result.buffer = new Object[this.buffer.length];
            for (int i = 0; i < this.buffer.length; ++i) {
                result.buffer[i] = this.buffer[i];
            }
            return result;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return this.buffer == null ? "[]" : Arrays.toString(this.buffer);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RecordBuffer)) {
            return false;
        }
        return this.compareTo((RecordBuffer)obj) == 0;
    }

    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < this.destKeyDimSize; ++i) {
            int col = this.destKeyDimList.get(i);
            hash = hash * 31 + (this.buffer[col] == null ? 0 : this.buffer[col].hashCode());
        }
        return hash;
    }
}

