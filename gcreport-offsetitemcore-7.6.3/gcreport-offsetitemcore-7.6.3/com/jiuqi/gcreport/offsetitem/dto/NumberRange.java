/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.dto;

public class NumberRange {
    private final long start;
    private int count;

    public NumberRange(long start, int count) {
        this.start = start;
        this.count = count;
    }

    public long getStart() {
        return this.start;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public NumberRange decrement() {
        if (this.count > 0) {
            return new NumberRange(this.start + 1L, this.count - 1);
        }
        return null;
    }
}

