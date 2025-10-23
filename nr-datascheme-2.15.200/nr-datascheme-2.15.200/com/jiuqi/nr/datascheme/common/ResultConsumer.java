/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.common;

@FunctionalInterface
public interface ResultConsumer<T, S, M> {
    public void accept(T var1, S var2, M var3);
}

