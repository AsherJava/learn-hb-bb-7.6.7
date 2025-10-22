/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.util.List;

public interface Query<T> {
    public long count();

    public T first();

    public List<T> list();

    public List<T> listPage(int var1, int var2) throws IllegalArgumentException;
}

