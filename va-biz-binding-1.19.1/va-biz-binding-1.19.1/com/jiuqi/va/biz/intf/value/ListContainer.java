/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ListContainer<T> {
    public Stream<T> stream();

    public int size();

    public void forEach(BiConsumer<Integer, T> var1);

    public void forEachReverse(BiConsumer<Integer, T> var1);

    public T get(int var1);

    public int findIndex(Predicate<T> var1);

    public void sort(Comparator<? super T> var1);
}

