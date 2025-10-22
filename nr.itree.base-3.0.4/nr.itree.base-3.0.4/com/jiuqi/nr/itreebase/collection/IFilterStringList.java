/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam;
import java.util.List;

public interface IFilterStringList {
    public int size();

    public boolean isEmpty();

    public boolean contains(String var1);

    public List<String> toList();

    public List<String> subList(int var1, int var2);

    public IFilterStringList unionAll(List<String> var1);

    public IFilterStringList retainAll(List<String> var1);

    public IFilterStringList supplementaryAll(List<String> var1);

    public IFilterStringList removeAll(List<String> var1);

    public IFilterStringList clear();

    public IFilterStringList sort(IFilterStringListSortParam var1);
}

