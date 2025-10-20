/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.collection.AbstractMatrix;
import com.jiuqi.bi.util.collection.IMatrix;

public class SubMatrix<E>
extends AbstractMatrix<E> {
    private IMatrix<E> rawMatrix;
    private int startCol;
    private int startRow;
    private int colSize;
    private int rowSize;

    public SubMatrix(IMatrix<E> rawMatrix, int startCol, int startRow, int colSize, int rowSize) {
        if (startCol < 0 || startCol > rawMatrix.getColSize()) {
            throw new IndexOutOfBoundsException("\u5f00\u59cb\u5217\u53f7\u9519\u8bef\uff1a" + startCol);
        }
        if (startRow < 0 || startRow > rawMatrix.getRowSize()) {
            throw new IndexOutOfBoundsException("\u5f00\u59cb\u884c\u53f7\u9519\u8bef\uff1a" + startRow);
        }
        if (colSize < 0 || colSize > rawMatrix.getColSize() - startCol) {
            throw new IllegalArgumentException("\u5b50\u77e9\u9635\u5217\u6570\u9519\u8bef\uff1a" + colSize);
        }
        if (rowSize < 0 || rowSize > rawMatrix.getRowSize() - startRow) {
            throw new IllegalArgumentException("\u5b50\u77e9\u9635\u884c\u6570\u9519\u8bef\uff1a" + rowSize);
        }
        this.rawMatrix = rawMatrix;
        this.startCol = startCol;
        this.startRow = startRow;
        this.colSize = colSize;
        this.rowSize = rowSize;
    }

    @Override
    public int getColSize() {
        return this.colSize;
    }

    @Override
    public int getRowSize() {
        return this.rowSize;
    }

    @Override
    public E get(int col, int row) {
        this.check(col, row);
        return this.rawMatrix.get(col + this.startCol, row + this.startRow);
    }

    @Override
    public void set(int col, int row, E elem) {
        this.check(col, row);
        this.rawMatrix.set(col + this.startCol, row + this.startRow, elem);
    }

    private void check(int col, int row) {
        if (col < 0 || col >= this.colSize) {
            throw new IllegalArgumentException("\u9519\u8bef\u7684\u77e9\u9635\u5217\u53f7\uff1a" + col);
        }
        if (row < 0 || row >= this.rowSize) {
            throw new IllegalArgumentException("\u9519\u8bef\u7684\u77e9\u9635\u884c\u53f7\uff1a" + row);
        }
    }
}

