/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util;

import java.util.ArrayList;

public class Matrix {
    private int colCount = 0;
    private int rowCount = 0;
    private ArrayList rowList = new ArrayList(0);

    public Matrix() {
    }

    public Matrix(int colCount, int rowCount) {
        this.reset(colCount, rowCount);
    }

    public void clear() {
        this.rowList.clear();
        this.colCount = 0;
        this.rowCount = 0;
    }

    public void reset(int newColCount, int newRowCount) {
        this.clear();
        this.setColCount(newColCount);
        this.setRowCount(newRowCount);
    }

    public final int getColCount() {
        return this.colCount;
    }

    public final void setColCount(int newColCount) {
        if (this.colCount == newColCount) {
            return;
        }
        int count = this.colCount <= newColCount ? this.colCount : newColCount;
        for (int i = 0; i < this.rowCount; ++i) {
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[newColCount];
            for (int j = 0; j < count; ++j) {
                newRow[j] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        this.colCount = newColCount;
    }

    public final int getRowCount() {
        return this.rowCount;
    }

    public final void setRowCount(int newRowCount) {
        int i;
        if (this.rowCount == newRowCount) {
            return;
        }
        for (i = this.rowCount - 1; i >= newRowCount; --i) {
            this.rowList.remove(i);
        }
        for (i = this.rowCount; i < newRowCount; ++i) {
            this.rowList.add(null);
        }
        this.rowCount = newRowCount;
    }

    public final Object getItem(int col, int row) {
        Object[] aRow = (Object[])this.rowList.get(row);
        return aRow == null ? null : aRow[col];
    }

    public final void setItem(int col, int row, Object value) {
        Object[] aRow = (Object[])this.rowList.get(row);
        if (aRow == null) {
            if (value == null) {
                return;
            }
            aRow = new Object[this.colCount];
            this.rowList.set(row, aRow);
        }
        aRow[col] = value;
    }

    public final void insertCol(int index) {
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[this.colCount + 1];
            for (j = 0; j < index; ++j) {
                newRow[j] = oldRow[j];
            }
            for (j = index; j < this.colCount; ++j) {
                newRow[j + 1] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        ++this.colCount;
    }

    public final void insertCol(int index, int count) {
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[this.colCount + count];
            for (j = 0; j < index; ++j) {
                newRow[j] = oldRow[j];
            }
            for (j = index; j < this.colCount; ++j) {
                newRow[j + count] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        this.colCount += count;
    }

    public final void insertCol(int index, int count, Object[] rowItems) {
        this.insertCol(index, count);
        if (rowItems == null || rowItems.length == 0) {
            return;
        }
        int len = Math.min(this.rowCount, rowItems.length);
        if (len == 0) {
            return;
        }
        for (int i = 0; i < count; ++i) {
            for (int row = 0; row < len; ++row) {
                this.setItem(index + i, row, rowItems[row]);
            }
        }
    }

    public final void duplicateCol(int index, int count) {
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[this.colCount + count];
            for (j = 0; j <= index; ++j) {
                newRow[j] = oldRow[j];
            }
            for (j = 1; j <= count; ++j) {
                newRow[index + j] = oldRow[index];
            }
            for (j = index + 1; j < this.colCount; ++j) {
                newRow[j + count] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        this.colCount += count;
    }

    public final void deleteCol(int index) {
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[this.colCount - 1];
            for (j = 0; j < index; ++j) {
                newRow[j] = oldRow[j];
            }
            for (j = index + 1; j < this.colCount; ++j) {
                newRow[j - 1] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        --this.colCount;
    }

    public final void deleteCol(int index, int count) {
        int c = index + count > this.colCount ? this.colCount - index : count;
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] oldRow = (Object[])this.rowList.get(i);
            if (oldRow == null) continue;
            Object[] newRow = new Object[this.colCount - 1];
            for (j = 0; j < index; ++j) {
                newRow[j] = oldRow[j];
            }
            for (j = index + c; j < this.colCount; ++j) {
                newRow[j - c] = oldRow[j];
            }
            this.rowList.set(i, newRow);
        }
        this.colCount -= c;
    }

    public final void exchangeCol(int index1, int index2) {
        for (int i = 0; i < this.rowCount; ++i) {
            Object[] aRow = (Object[])this.rowList.get(i);
            Object T = aRow[index1];
            aRow[index1] = aRow[index2];
            aRow[index2] = T;
        }
    }

    public final void moveCol(int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        for (int i = 0; i < this.rowCount; ++i) {
            int j;
            Object[] aRow = (Object[])this.rowList.get(i);
            if (aRow == null) continue;
            Object T = aRow[index1];
            if (index1 > index2) {
                for (j = index1 - 1; j >= index2; --j) {
                    aRow[j + 1] = aRow[j];
                }
            } else if (index1 < index2) {
                for (j = index1 + 1; j < index2; ++j) {
                    aRow[j - 1] = aRow[j];
                }
            }
            aRow[index2] = T;
        }
    }

    public final void insertRow(int index) {
        this.rowList.add(index, null);
        ++this.rowCount;
    }

    public final void insertRow(int index, int count) {
        for (int i = 0; i < count; ++i) {
            this.rowList.add(index, null);
        }
        this.rowCount += count;
    }

    public final void insertRow(int index, int count, Object[] colItems) {
        if (colItems == null || colItems.length == 0) {
            this.insertRow(index, count);
            return;
        }
        int len = Math.min(this.colCount, colItems.length);
        for (int i = 0; i < count; ++i) {
            Object[] newRow = new Object[this.colCount];
            for (int j = 0; j < len; ++j) {
                newRow[j] = colItems[j];
            }
            this.rowList.add(index, newRow);
        }
        this.rowCount += count;
    }

    public final void duplicateRow(int index, int count) {
        for (int i = 0; i < count; ++i) {
            Object[] oldRow = (Object[])this.rowList.get(index);
            Object[] newRow = null;
            if (oldRow != null) {
                newRow = new Object[this.colCount];
                for (int j = 0; j < this.colCount; ++j) {
                    newRow[j] = oldRow[j];
                }
            }
            this.rowList.add(index + 1, newRow);
        }
        this.rowCount += count;
    }

    public final void deleteRow(int index) {
        this.rowList.remove(index);
        --this.rowCount;
    }

    public final void deleteRow(int index, int count) {
        int c = index + count > this.rowCount ? this.rowCount - index : count;
        for (int row = index + c - 1; row >= index; --row) {
            this.rowList.remove(row);
        }
        this.rowCount -= c;
    }

    public final void exchangeRow(int index1, int index2) {
        Object[] aRow = (Object[])this.rowList.get(index1);
        this.rowList.set(index1, this.rowList.get(index2));
        this.rowList.set(index2, aRow);
    }

    public final void moveRow(int index1, int index2) {
        Object[] aRow = (Object[])this.rowList.get(index1);
        if (index1 > index2) {
            for (int i = index1 - 1; i >= index2; --i) {
                this.rowList.set(i + 1, this.rowList.get(i));
            }
        } else if (index1 < index2) {
            for (int i = index1 + 1; i < index2; ++i) {
                this.rowList.set(i - 1, this.rowList.get(i));
            }
        }
        this.rowList.set(index2, aRow);
    }
}

