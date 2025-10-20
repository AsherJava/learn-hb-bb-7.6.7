/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.collection.AbstractMatrix;
import com.jiuqi.bi.util.collection.IMatrix;
import com.jiuqi.bi.util.collection.VirtualArray;
import java.util.Iterator;
import java.util.List;

public class FixedSparseMatrix<E>
extends AbstractMatrix<E>
implements IMatrix<E> {
    private VirtualArray<VirtualArray<E>> rows;
    private int colSize;
    private int pageSize;

    public FixedSparseMatrix(int colSize, int rowSize, int pageSize) {
        this.rows = new VirtualArray(rowSize);
        this.colSize = colSize;
        this.pageSize = pageSize;
    }

    public FixedSparseMatrix(int colSize, int rowSize) {
        this(colSize, rowSize, 1024);
    }

    @Override
    public int getColSize() {
        return this.colSize;
    }

    @Override
    public int getRowSize() {
        return this.rows.size();
    }

    @Override
    public E get(int col, int row) {
        List rec = this.rows.get(row);
        return rec == null ? null : (E)rec.get(col);
    }

    @Override
    public void set(int col, int row, E elem) {
        VirtualArray<E> rec = this.rows.get(row);
        if (rec == null) {
            if (elem != null) {
                rec = new VirtualArray(this.colSize, this.pageSize);
                rec.set(col, elem);
                this.rows.set(row, rec);
            }
        } else {
            rec.set(col, elem);
        }
    }

    @Override
    public Object[] getRow(int row) {
        Object[] elems = new Object[this.colSize];
        VirtualArray<E> rec = this.rows.get(row);
        if (rec != null) {
            elems = rec.toArray(elems);
        }
        return elems;
    }

    @Override
    public Object[] getCol(int col) {
        Object[] elems = new Object[this.rows.size()];
        for (int row = 0; row < this.rows.size(); ++row) {
            VirtualArray<E> rec = this.rows.get(row);
            if (rec == null) continue;
            elems[row] = rec.get(col);
        }
        return elems;
    }

    @Override
    public void fill(E elem) {
        if (elem == null) {
            this.rows.clear();
        } else {
            super.fill(elem);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public Iterator<E> elemIterator() {
        return new ElemItr();
    }

    private final class ElemItr
    implements Iterator<E> {
        private Iterator<VirtualArray<E>> rowItr;
        private Iterator<E> colItr;

        public ElemItr() {
            this.rowItr = FixedSparseMatrix.this.rows.elemIterator();
            this.colItr = null;
        }

        @Override
        public boolean hasNext() {
            while (true) {
                if (this.colItr == null) {
                    if (!this.rowItr.hasNext()) {
                        return false;
                    }
                    this.colItr = this.rowItr.next().iterator();
                }
                if (this.colItr.hasNext()) {
                    return true;
                }
                this.colItr = null;
            }
        }

        @Override
        public E next() {
            return this.colItr.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class Itr
    implements Iterator<E> {
        private int curRow = 0;
        private int curCol = 0;

        private Itr() {
        }

        @Override
        public boolean hasNext() {
            return this.curRow < FixedSparseMatrix.this.rows.size();
        }

        @Override
        public E next() {
            Object elem = FixedSparseMatrix.this.get(this.curCol, this.curRow);
            ++this.curCol;
            if (this.curCol >= FixedSparseMatrix.this.colSize) {
                ++this.curRow;
                this.curCol = 0;
            }
            return elem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

