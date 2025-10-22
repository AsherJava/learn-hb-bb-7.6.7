/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import java.util.ArrayList;
import java.util.List;

public class BlockData<E> {
    private List<List<E>> blocks;

    public BlockData() {
        this(0);
    }

    public BlockData(int rowSize) {
        this.blocks = new ArrayList<List<E>>(rowSize > 10 ? rowSize : 10);
        for (int i = 0; i < rowSize; ++i) {
            this.blocks.add(new ArrayList());
        }
    }

    public BlockData(List<List<E>> datas) {
        this.blocks = new ArrayList<List<E>>(datas.size());
        for (List<E> col : datas) {
            this.add(new ArrayList<E>(col));
        }
    }

    public void reshape(int rows, int cols) {
        for (int r = this.blocks.size(); r < rows; ++r) {
            this.add(new ArrayList(cols));
        }
        for (List<E> block : this.blocks) {
            for (int c = block.size(); c < cols; ++c) {
                block.add(null);
            }
        }
    }

    public void addCol(List<E> col) {
        for (int i = 0; i < this.size(); ++i) {
            List<E> row = this.blocks.get(i);
            if (i < col.size()) {
                row.add(col.get(i));
                continue;
            }
            row.add(null);
        }
    }

    public void add(List<E> row) {
        this.addRow(row);
    }

    public void addRow(List<E> row) {
        int len = this.length();
        for (int r = row.size(); r < len; ++r) {
            row.add(null);
        }
        this.blocks.add(row);
    }

    public void removeCol(int col) {
        if (col > this.length()) {
            throw new IndexOutOfBoundsException("Col: " + col + ", Size: " + this.length());
        }
        for (int i = 0; i < this.blocks.size(); ++i) {
            this.blocks.get(i).remove(col);
        }
    }

    public void removeRow(int row) {
        if (row > this.size()) {
            throw new IndexOutOfBoundsException("Row: " + row + ", Size: " + this.size());
        }
        this.blocks.remove(row);
    }

    public E get(int col, int row) {
        if (col > this.length() || row > this.size()) {
            return null;
        }
        return this.getRow(row).get(col);
    }

    public void set(int col, int row, E value) {
        this.getRow(row).set(col, value);
    }

    public List<E> getRow(int row) {
        return this.blocks.get(row);
    }

    public List<E> getCol(int col) {
        ArrayList<E> c = new ArrayList<E>(this.size());
        for (int i = 0; i < this.size(); ++i) {
            c.add(this.blocks.get(i).get(col));
        }
        return c;
    }

    public int colSize() {
        return this.length();
    }

    public int rowSize() {
        return this.size();
    }

    public int size() {
        return this.blocks.size();
    }

    public int length() {
        return this.blocks.isEmpty() ? 0 : this.blocks.get(0).size();
    }

    public boolean isEmpty() {
        return this.blocks.isEmpty();
    }
}

