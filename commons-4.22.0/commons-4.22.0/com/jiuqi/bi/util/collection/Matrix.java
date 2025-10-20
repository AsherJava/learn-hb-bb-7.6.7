/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.collection.AbstractMatrix;
import com.jiuqi.bi.util.collection.IMatrix;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Matrix<E>
extends AbstractMatrix<E>
implements IMatrix<E> {
    private int colSize;
    private List<E[]> rows = new ArrayList<E[]>();

    public Matrix() {
    }

    public Matrix(int colSize, int rowSize) {
        this();
        this.setColSize(colSize);
        this.setRowSize(rowSize);
    }

    public Matrix(IMatrix<E> matrix) {
        this(matrix.getColSize(), matrix.getRowSize());
        for (int col = 0; col < matrix.getColSize(); ++col) {
            for (int row = 0; row < matrix.getRowSize(); ++row) {
                E val = matrix.get(col, row);
                this.set(col, row, val);
            }
        }
    }

    @Override
    public int getColSize() {
        return this.colSize;
    }

    public void setColSize(int colSize) {
        if (this.colSize < colSize) {
            ListIterator<E[]> i = this.rows.listIterator();
            while (i.hasNext()) {
                E[] EArray = i.next();
                Object[] val = new Object[colSize];
                System.arraycopy(EArray, 0, val, 0, this.colSize);
                i.set(val);
            }
            this.colSize = colSize;
        } else if (this.colSize > colSize) {
            for (Object[] objectArray : this.rows) {
                Arrays.fill(objectArray, colSize, this.colSize, null);
            }
            this.colSize = colSize;
        }
    }

    @Override
    public int getRowSize() {
        return this.rows.size();
    }

    public void setRowSize(int rowSize) {
        block3: {
            block2: {
                if (this.rows.size() >= rowSize) break block2;
                while (this.rows.size() < rowSize) {
                    Object[] row = new Object[this.colSize];
                    this.rows.add(row);
                }
                break block3;
            }
            if (this.rows.size() <= rowSize) break block3;
            while (this.rows.size() > rowSize) {
                this.rows.remove(this.rows.size() - 1);
            }
        }
    }

    @Override
    public E get(int col, int row) {
        E[] arr = this.rows.get(row);
        return arr[col];
    }

    @Override
    public void set(int col, int row, E elem) {
        E[] arr = this.rows.get(row);
        arr[col] = elem;
    }

    @Override
    public Object[] getRow(int row) {
        E[] arr = this.rows.get(row);
        Object[] vals = new Object[this.colSize];
        System.arraycopy(arr, 0, vals, 0, this.colSize);
        return vals;
    }

    @Override
    public E[] getRow(int row, E[] arr) {
        E[] data = this.rows.get(row);
        if (arr.length < this.getColSize()) {
            arr = (Object[])Array.newInstance(arr.getClass().getComponentType(), this.getColSize());
        }
        System.arraycopy(data, 0, arr, 0, this.getColSize());
        return arr;
    }

    public Object[] removeRow(int row) {
        Object[] vals = this.getRow(row);
        this.rows.remove(row);
        return vals;
    }

    public E[] removeCol(int col) {
        if (col < 0 || col >= this.colSize) {
            throw new IllegalArgumentException("col = " + col);
        }
        Object[] vals = new Object[this.rows.size()];
        for (int row = 0; row < this.rows.size(); ++row) {
            E[] arr = this.rows.get(row);
            vals[row] = arr[col];
            for (int i = col; i < this.colSize - 1; ++i) {
                arr[i] = arr[i + 1];
            }
            arr[this.colSize - 1] = null;
        }
        --this.colSize;
        return vals;
    }
}

