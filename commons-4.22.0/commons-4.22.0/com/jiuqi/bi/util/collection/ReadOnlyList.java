/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public class ReadOnlyList<E>
extends AbstractList<E>
implements List<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = 5745152545872951383L;
    private transient Object[] elements;

    public ReadOnlyList(Collection<? extends E> c) {
        this.elements = c.toArray();
        if (this.elements.getClass() != Object[].class) {
            this.elements = Arrays.copyOf(this.elements, this.elements.length, Object[].class);
        }
    }

    @SafeVarargs
    public ReadOnlyList(E ... arr) {
        this.elements = Arrays.copyOf(arr, arr.length, Object[].class);
    }

    @Override
    public E get(int index) {
        return (E)this.elements[index];
    }

    @Override
    public int size() {
        return this.elements.length;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
}

