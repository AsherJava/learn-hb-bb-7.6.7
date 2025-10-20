/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;

public final class RestrictionDescriptor {
    public final int mode;
    public final BIDataSetFieldInfo item;
    public final int fieldIdx;
    public final Object condition;
    private int hash;
    public static final int MODE_TAG = 0;
    public static final int MODE_OFFSET = 1;
    public static final int MODE_VALUES = 2;
    public static final int MODE_EXPRESSION = 3;

    public RestrictionDescriptor(int mode, BIDataSetFieldInfo item, int fieldIdx, Object condition) {
        this.mode = mode;
        this.item = item;
        this.fieldIdx = fieldIdx;
        this.condition = condition;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[mode: ").append(this.mode).append(", item: ").append(this.item).append(", condition: ").append(this.condition).append(']');
        return buffer.toString();
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0) {
            h = this.mode * 31;
            if (this.item != null) {
                h += this.item.hashCode();
            }
            h *= 31;
            if (this.condition != null) {
                h += this.condition.hashCode();
            }
            this.hash = h;
        }
        return h;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this == null || obj == null) {
            return false;
        }
        if (!(obj instanceof RestrictionDescriptor)) {
            return false;
        }
        RestrictionDescriptor that = (RestrictionDescriptor)obj;
        boolean isEqual = this.mode == that.mode;
        isEqual &= this.item.equals(that.item);
        return isEqual &= this.condition.equals(that.condition);
    }

    public boolean isEqualMode() {
        return this.mode == 2;
    }
}

