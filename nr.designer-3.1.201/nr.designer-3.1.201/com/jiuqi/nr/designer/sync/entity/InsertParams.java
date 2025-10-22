/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.sync.entity;

public class InsertParams {
    private int count;
    private boolean forward;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isForward() {
        return this.forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public String toString() {
        return "InsertParams [count=" + this.count + ", forward=" + this.forward + "]";
    }
}

