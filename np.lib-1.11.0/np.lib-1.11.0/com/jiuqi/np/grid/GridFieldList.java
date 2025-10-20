/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.util.Matrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class GridFieldList
implements Serializable {
    private static final long serialVersionUID = -7254874172090668587L;
    private List list;
    private GridData parent;
    private boolean autoMerge;
    private Matrix rectMaps;
    private Set tmpRects;

    public GridFieldList(GridData aParent) {
        this();
        this.parent = aParent;
    }

    public GridFieldList() {
        Comparator rectCmp = new Comparator(){

            public int compare(Object o1, Object o2) {
                CellField f1 = (CellField)o1;
                CellField f2 = (CellField)o2;
                return f1.compareTo(f2);
            }
        };
        this.list = new ArrayList();
        this.rectMaps = new Matrix();
        this.tmpRects = new TreeSet(rectCmp);
    }

    public int count() {
        return this.list.size();
    }

    public GridData getParent() {
        return this.parent;
    }

    public CellField get(int index) {
        return (CellField)this.list.get(index);
    }

    public void remove(int index) {
        CellField field = (CellField)this.list.remove(index);
        for (int col = field.left; col <= field.right; ++col) {
            for (int row = field.top; row <= field.bottom; ++row) {
                this.rectMaps.setItem(col, row, null);
            }
        }
    }

    protected boolean remove(CellField field) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (field != this.list.get(i)) continue;
            this.remove(i);
            return true;
        }
        return false;
    }

    public CellField getMergeRect(int col, int row) {
        if (col >= this.rectMaps.getColCount() || row >= this.rectMaps.getRowCount()) {
            return null;
        }
        return (CellField)this.rectMaps.getItem(col, row);
    }

    public boolean checkCanMerge(CellField field) {
        for (int col = field.left; col <= field.right; ++col) {
            for (int row = field.top; row <= field.bottom; ++row) {
                CellField f = this.getMergeRect(col, row);
                if (f == null) continue;
                switch (CellField.compareField(f, field)) {
                    case -2: 
                    case 1: {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean addMergeRect(CellField field) {
        if (this.checkCanMerge(field)) {
            this.tmpRects.clear();
            if (this.rectMaps.getColCount() <= field.right) {
                this.rectMaps.setColCount(field.right + 1);
            }
            if (this.rectMaps.getRowCount() <= field.bottom) {
                this.rectMaps.setRowCount(field.bottom + 1);
            }
            for (int col = field.left; col <= field.right; ++col) {
                for (int row = field.top; row <= field.bottom; ++row) {
                    CellField f = (CellField)this.rectMaps.getItem(col, row);
                    if (f != null && !this.tmpRects.contains(f)) {
                        this.tmpRects.add(f);
                    }
                    this.rectMaps.setItem(col, row, field);
                }
            }
            if (!this.tmpRects.isEmpty()) {
                this.list.removeAll(this.tmpRects);
                this.tmpRects.clear();
            }
            this.list.add(field);
            return true;
        }
        return false;
    }

    public int removeMergeRect(CellField field) {
        int count = 0;
        for (int col = field.left; col <= field.right; ++col) {
            for (int row = field.top; row <= field.bottom; ++row) {
                CellField f = this.getMergeRect(col, row);
                if (f == null) continue;
                this.remove(f);
                ++count;
            }
        }
        return count;
    }

    public void delRows(int startRow, int count) {
        int top = startRow;
        int bottom = startRow + count - 1;
        for (int i = this.count() - 1; i >= 0; --i) {
            int b;
            CellField f = this.get(i);
            int t = f.top < top ? top : f.top;
            int n = b = f.bottom < bottom ? f.bottom : bottom;
            if (b >= t) {
                f.bottom -= b - t + 1;
                if (f.bottom >= f.top) continue;
                this.remove(i);
                continue;
            }
            if (f.top <= bottom) continue;
            f.top -= count;
            f.bottom -= count;
        }
        if (startRow < this.rectMaps.getRowCount()) {
            this.rectMaps.deleteRow(startRow, count);
        }
    }

    public void delCols(int startCol, int count) {
        int left = startCol;
        int right = startCol + count - 1;
        for (int i = this.count() - 1; i >= 0; --i) {
            int r;
            CellField f = this.get(i);
            int l = f.left < left ? left : f.left;
            int n = r = f.right < right ? f.right : right;
            if (r >= l) {
                f.right -= r - l + 1;
            }
            if (f.right < f.left) {
                this.remove(i);
                continue;
            }
            if (f.left <= right) continue;
            f.left -= count;
            f.right -= count;
        }
        if (startCol < this.rectMaps.getColCount()) {
            this.rectMaps.deleteCol(startCol, count);
        }
    }

    public void insertRows(int startRow, int count) {
        CellField f;
        int i;
        Object[] colItems = null;
        ArrayList<CellField> adjRects = new ArrayList<CellField>();
        for (i = 0; i < this.count(); ++i) {
            f = this.get(i);
            if (startRow > f.top && startRow <= f.bottom) {
                f.bottom += count;
                if (colItems == null) {
                    colItems = new Object[this.rectMaps.getColCount()];
                }
                for (int col = f.left; col <= f.right; ++col) {
                    colItems[col] = f;
                }
                continue;
            }
            if (startRow <= f.top) {
                f.top += count;
                f.bottom += count;
                continue;
            }
            if (startRow != f.bottom + 1 || f.right <= f.left || !this.autoMerge) continue;
            adjRects.add(f);
        }
        if (this.rectMaps.getRowCount() > 0 && startRow <= this.rectMaps.getRowCount()) {
            this.rectMaps.insertRow(startRow, count, colItems);
        }
        for (i = 0; i < adjRects.size(); ++i) {
            f = (CellField)adjRects.get(i);
            for (int row = startRow; row < startRow + count; ++row) {
                this.addMergeRect(new CellField(f.left, row, f.right, row));
            }
        }
    }

    public void insertCols(int startCol, int count) {
        CellField f;
        int i;
        Object[] rowItems = null;
        ArrayList<CellField> adjRects = new ArrayList<CellField>();
        for (i = 0; i < this.count(); ++i) {
            f = this.get(i);
            if (startCol > f.left && startCol <= f.right) {
                f.right += count;
                if (rowItems == null) {
                    rowItems = new Object[this.rectMaps.getRowCount()];
                }
                for (int row = f.top; row <= f.bottom; ++row) {
                    rowItems[row] = f;
                }
                continue;
            }
            if (startCol <= f.left) {
                f.left += count;
                f.right += count;
                continue;
            }
            if (startCol != f.right + 1 || f.top >= f.bottom || !this.autoMerge) continue;
            adjRects.add(f);
        }
        if (this.rectMaps.getColCount() > 0 && startCol <= this.rectMaps.getColCount()) {
            this.rectMaps.insertCol(startCol, count, rowItems);
        }
        for (i = 0; i < adjRects.size(); ++i) {
            f = (CellField)adjRects.get(i);
            for (int col = startCol; col < startCol + count; ++col) {
                this.addMergeRect(new CellField(col, f.top, col, f.bottom));
            }
        }
    }

    public void extendRows(int startRow, int count) {
        for (int i = 0; i < this.count(); ++i) {
            CellField f = this.get(i);
            if (startRow >= f.top && startRow <= f.bottom) {
                f.bottom += count;
                continue;
            }
            if (startRow >= f.top) continue;
            f.top += count;
            f.bottom += count;
        }
        if (this.rectMaps.getRowCount() > 0 && startRow < this.rectMaps.getRowCount()) {
            this.rectMaps.duplicateRow(startRow, count);
        }
    }

    public void extendCols(int startCol, int count) {
        for (int i = 0; i < this.count(); ++i) {
            CellField f = this.get(i);
            if (startCol >= f.left && startCol <= f.right) {
                f.right += count;
                continue;
            }
            if (startCol >= f.left) continue;
            f.left += count;
            f.right += count;
        }
        if (this.rectMaps.getColCount() > 0 && startCol < this.rectMaps.getColCount()) {
            this.rectMaps.duplicateCol(startCol, count);
        }
    }

    public List findFieldsInCols(int from, int to) {
        ArrayList<CellField> result = new ArrayList<CellField>();
        for (int i = 0; i < this.count(); ++i) {
            CellField f = this.get(i);
            if (f.left < from || f.right > to) continue;
            result.add(f);
        }
        return result;
    }

    public List findFieldsInRows(int from, int to) {
        ArrayList<CellField> result = new ArrayList<CellField>();
        for (int i = 0; i < this.count(); ++i) {
            CellField f = this.get(i);
            if (f.top < from || f.bottom > to) continue;
            result.add(f);
        }
        return result;
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        this.list.clear();
        this.rectMaps.clear();
        for (int i = 0; i < size / 16; ++i) {
            CellField cf = new CellField();
            cf.left = stream.readInt();
            cf.top = stream.readInt();
            cf.right = stream.readInt();
            cf.bottom = stream.readInt();
            this.addMergeRect(cf);
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        for (int i = 0; i < this.list.size(); ++i) {
            CellField cf = (CellField)this.list.get(i);
            stream.writeInt(cf.left);
            stream.writeInt(cf.top);
            stream.writeInt(cf.right);
            stream.writeInt(cf.bottom);
        }
    }

    public boolean getAutoMerge() {
        return this.autoMerge;
    }

    public void setAutoMerge(boolean value) {
        this.autoMerge = value;
    }

    public String toString() {
        return this.list.toString();
    }
}

