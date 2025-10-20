/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.ListContainer;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ListContainerImpl<T>
implements ListContainer<T> {
    protected final List<T> list;

    public ListContainerImpl(List<T> list) {
        this.list = list;
    }

    @Override
    public Stream<T> stream() {
        return this.list.stream();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }

    @Override
    public void forEach(BiConsumer<Integer, T> consumer) {
        for (int i = 0; i < this.list.size(); ++i) {
            consumer.accept(i, (Integer)this.list.get(i));
        }
    }

    @Override
    public void forEachReverse(BiConsumer<Integer, T> consumer) {
        for (int i = this.list.size() - 1; i >= 0; --i) {
            consumer.accept(i, (Integer)this.list.get(i));
        }
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (!predicate.test(this.list.get(i))) continue;
            return i;
        }
        return -1;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        this.list.sort(c);
    }

    public static <T> ListContainerImpl<T> create(List<T> list) {
        return new ListContainerImpl<T>(list);
    }
}

