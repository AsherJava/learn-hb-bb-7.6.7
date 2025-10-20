/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.util.Iterator;
import java.util.List;

public class MutableIterator<T>
implements Iterator<T> {
    private final List<T> items;
    private int index;

    public MutableIterator(List<T> items) {
        this.items = items;
        this.index = 0;
    }

    public static <T> Iterator<T> of(List<T> items) {
        return new MutableIterator<T>(items);
    }

    @Override
    public boolean hasNext() {
        return this.index < this.items.size();
    }

    @Override
    public T next() {
        T item = this.items.get(this.index);
        ++this.index;
        return item;
    }

    @Override
    public void remove() {
        --this.index;
        this.items.remove(this.index);
    }
}

