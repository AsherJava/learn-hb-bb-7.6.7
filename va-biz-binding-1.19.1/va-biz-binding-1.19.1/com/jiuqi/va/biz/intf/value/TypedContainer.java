/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import com.jiuqi.va.biz.intf.value.TypedElement;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface TypedContainer<T extends TypedElement> {
    public Stream<T> stream();

    public int size();

    public void forEachName(BiConsumer<String, T> var1);

    public T find(String var1);

    public T get(String var1);

    public void forEachIndex(BiConsumer<Integer, T> var1);

    public T get(int var1);

    public int findIndex(Predicate<T> var1);

    public <S> S find(Class<S> var1);

    public <S> S get(Class<S> var1);
}

