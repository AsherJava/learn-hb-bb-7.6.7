/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.collection.SubList;
import java.util.List;
import java.util.RandomAccess;

public class RandomAccessSubList<E>
extends SubList<E>
implements RandomAccess {
    public RandomAccessSubList(List<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<E>(this, fromIndex, toIndex);
    }
}

