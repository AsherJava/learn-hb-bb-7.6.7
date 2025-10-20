/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

public class Holder<T> {
    private T value;

    public Holder(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}

