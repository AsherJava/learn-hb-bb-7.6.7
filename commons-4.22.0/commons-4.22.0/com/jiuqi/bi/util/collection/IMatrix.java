/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.util.Iterator;

public interface IMatrix<E>
extends Iterable<E> {
    public boolean isEmpty();

    public int getColSize();

    public int getRowSize();

    public E get(int var1, int var2);

    public void set(int var1, int var2, E var3);

    public Object[] getRow(int var1);

    public E[] getRow(int var1, E[] var2);

    public Object[] getCol(int var1);

    public E[] getCol(int var1, E[] var2);

    public boolean isColEmpty(int var1);

    public boolean isRowEmpty(int var1);

    @Override
    public Iterator<E> iterator();

    public Iterator<E> elemIterator();

    public void fill(E var1);

    public IMatrix<E> subMatrix(int var1, int var2, int var3, int var4);
}

