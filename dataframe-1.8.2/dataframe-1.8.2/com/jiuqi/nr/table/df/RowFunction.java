/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import java.util.List;

public interface RowFunction<I, O> {
    public List<List<O>> apply(List<I> var1);
}

