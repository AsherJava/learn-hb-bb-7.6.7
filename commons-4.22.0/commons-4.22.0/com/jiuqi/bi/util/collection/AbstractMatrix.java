/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.collection.IMatrix;
import com.jiuqi.bi.util.collection.SubMatrix;
import java.lang.reflect.Array;
import java.util.Iterator;

public abstract class AbstractMatrix<E>
implements IMatrix<E> {
    @Override
    public boolean isEmpty() {
        return this.getRowSize() == 0 || this.getColSize() == 0;
    }

    @Override
    public void fill(E elem) {
        for (int row = 0; row < this.getRowSize(); ++row) {
            for (int col = 0; col < this.getColSize(); ++col) {
                this.set(col, row, elem);
            }
        }
    }

    @Override
    public Object[] getCol(int col) {
        Object[] arr = new Object[this.getRowSize()];
        for (int row = 0; row < this.getRowSize(); ++row) {
            arr[row] = this.get(col, row);
        }
        return arr;
    }

    @Override
    public E[] getCol(int col, E[] arr) {
        if (arr.length < this.getRowSize()) {
            arr = (Object[])Array.newInstance(arr.getClass().getComponentType(), this.getRowSize());
        }
        for (int row = 0; row < this.getRowSize(); ++row) {
            arr[row] = this.get(col, row);
        }
        return arr;
    }

    @Override
    public Object[] getRow(int row) {
        Object[] arr = new Object[this.getColSize()];
        for (int col = 0; col < this.getColSize(); ++col) {
            arr[col] = this.get(col, row);
        }
        return arr;
    }

    @Override
    public E[] getRow(int row, E[] arr) {
        if (arr.length < this.getColSize()) {
            arr = (Object[])Array.newInstance(arr.getClass().getComponentType(), this.getColSize());
        }
        for (int col = 0; col < this.getColSize(); ++col) {
            arr[col] = this.get(col, row);
        }
        return arr;
    }

    @Override
    public boolean isColEmpty(int col) {
        for (int row = 0; row < this.getRowSize(); ++row) {
            if (this.get(col, row) == null) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isRowEmpty(int row) {
        for (int col = 0; col < this.getColSize(); ++col) {
            if (this.get(col, row) == null) continue;
            return false;
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(true);
    }

    @Override
    public Iterator<E> elemIterator() {
        return new Itr(false);
    }

    @Override
    public IMatrix<E> subMatrix(int fromCol, int fromRow, int toCol, int toRow) {
        return new SubMatrix(this, fromCol, fromRow, toCol - fromCol, toRow - fromRow);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int row = 0; row < this.getRowSize(); ++row) {
            if (row > 0) {
                buffer.append("; ").append(StringUtils.LINE_SEPARATOR);
            }
            for (int col = 0; col < this.getColSize(); ++col) {
                if (col > 0) {
                    buffer.append(", ");
                }
                buffer.append(this.get(col, row));
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    private final class Itr
    implements Iterator<E> {
        private final boolean nullable;
        private int curRow;
        private int curCol;

        public Itr(boolean nullable) {
            this.nullable = nullable;
            this.curRow = 0;
            this.curCol = 0;
            this.skipNull();
        }

        private E current() {
            return AbstractMatrix.this.get(this.curCol, this.curRow);
        }

        @Override
        public boolean hasNext() {
            return this.curRow < AbstractMatrix.this.getRowSize() && this.curCol < AbstractMatrix.this.getColSize();
        }

        @Override
        public E next() {
            Object elem = this.current();
            this.moveNext();
            this.skipNull();
            return elem;
        }

        private void moveNext() {
            ++this.curCol;
            if (this.curCol >= AbstractMatrix.this.getColSize()) {
                ++this.curRow;
                this.curCol = 0;
            }
        }

        private void skipNull() {
            if (this.nullable) {
                return;
            }
            while (this.current() == null && this.hasNext()) {
                this.moveNext();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

