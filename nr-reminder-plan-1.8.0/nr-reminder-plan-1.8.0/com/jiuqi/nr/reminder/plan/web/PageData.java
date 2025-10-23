/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.web;

public class PageData<T> {
    private int total;
    private T data;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

