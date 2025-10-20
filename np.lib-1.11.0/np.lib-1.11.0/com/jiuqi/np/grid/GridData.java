/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid.BIFF;
import com.jiuqi.np.grid.BitList;
import com.jiuqi.np.grid.CellConvertor;
import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.CellPostion;
import com.jiuqi.np.grid.CellValue;
import com.jiuqi.np.grid.ExtendedDatas;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridCellProperty;
import com.jiuqi.np.grid.GridConsts;
import com.jiuqi.np.grid.GridFieldList;
import com.jiuqi.np.grid.GridFontNames;
import com.jiuqi.np.grid.IntList;
import com.jiuqi.np.grid.KeyValues;
import com.jiuqi.np.grid.LabelItems;
import com.jiuqi.np.grid.Matrix;
import com.jiuqi.np.grid.PropList;
import com.jiuqi.np.grid.ValueList;
import com.jiuqi.np.grid.WordList;
import com.jiuqi.np.util.LogHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public final class GridData
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -6870771225688462791L;
    private IntList cellColors;
    private IntList fontColors;
    private IntList edgeColors;
    private GridFontNames fontNames;
    private IntList fontSizes;
    private IntList colWidths;
    private IntList rowHeights;
    private BitList colVisible;
    private BitList rowVisible;
    private BitList hideDataRow;
    private BitList hideDataCol;
    private BitList colAutoSize;
    private BitList rowAutoSize;
    private Matrix values;
    private Matrix cells;
    private GridFieldList rects;
    private GridCell cell;
    private PropList propList;
    private Matrix objects;
    private int scrollTopCol = -1;
    private int scrollTopRow = -1;
    private int scrollBottomCol = -1;
    private int scrollBottomRow = -1;
    private int curCol = -1;
    private int curRow = -1;
    private boolean useFontSize;
    private LabelItems labels = new LabelItems();
    private Matrix cellCSS = null;
    private String gridDataClass;
    private String referenceBase;
    private Matrix cellScript = null;
    private int horzOffset;
    private int vertOffset;
    private boolean horzCenter;
    private boolean vertCenter;
    private ExtendedDatas extDatas;
    private int options;
    private int colCountPerPage = 0;
    private int rowCountPerPage = 0;
    private boolean rowPagingFirst = true;
    public static final int GRID_AUTOMERGE = 1;
    public static final int GRID_AUTOSTYLE = 2;
    public static final int GRID_DYNAMICSTORE = 4;
    public static final int GRID_AUTOADJUST = 8;
    public static final int GRID_COPYWITHOUTOBJ = 16;

    public GridData() {
        GridCell cell;
        int i;
        this.cellColors = GridConsts.create64ColorsPallette();
        this.fontColors = GridConsts.create64ColorsPallette();
        this.edgeColors = GridConsts.create16ColorsPallette();
        this.fontNames = new GridFontNames(64);
        this.fontNames.addFont("\u5b8b\u4f53");
        this.fontSizes = new IntList();
        this.fontSizes.add(9);
        this.colVisible = new BitList(true);
        this.rowVisible = new BitList(true);
        this.colAutoSize = new BitList(false);
        this.rowAutoSize = new BitList(false);
        this.hideDataRow = new BitList(false);
        this.hideDataCol = new BitList(false);
        this.colWidths = new IntList();
        this.rowHeights = new IntList();
        this.values = new Matrix();
        this.cells = new Matrix();
        this.objects = new Matrix();
        this.rects = new GridFieldList();
        this.cell = new GridCell();
        this.propList = new PropList(20);
        this.propList.add(GridConsts.DEF_CELL_PROP);
        this.extDatas = new ExtendedDatas();
        this.setColCount(5);
        this.setRowCount(5);
        for (i = 0; i < 5; ++i) {
            cell = this.getCell(i, 0);
            this.setCell(i, 0, cell);
        }
        for (i = 0; i < 5; ++i) {
            cell = this.getCell(0, i);
            this.setCell(0, i, cell);
        }
        this.options = 15;
        this.setOptions(this.options);
    }

    public GridData(int colCount, int rowCount) {
        this();
        this.setColCount(colCount);
        this.setRowCount(rowCount);
    }

    public GridData(int options) {
        this();
        this.setOptions(options);
    }

    public GridData(int colCount, int rowCount, int options) {
        this(colCount, rowCount);
        this.setOptions(options);
    }

    private void createCellCssMatrix() {
        if (this.cellCSS == null) {
            this.cellCSS = new Matrix();
            this.cellCSS.setSize(this.getColCount(), this.getRowCount());
        }
    }

    private void createCellScriptMatrix() {
        if (this.cellScript == null) {
            this.cellScript = new Matrix();
            this.cellScript.setSize(this.getColCount(), this.getRowCount());
        }
    }

    private boolean checkCol(int col, boolean isInsert) {
        if (isInsert) {
            return col > 0 && col <= this.getColCount();
        }
        return col >= 0 && col < this.getColCount();
    }

    private boolean checkRow(int row, boolean isInsert) {
        if (isInsert) {
            return row > 0 && row <= this.getRowCount();
        }
        return row >= 0 && row < this.getRowCount();
    }

    private void rebuildPropIndex(int col, int row, int oldIndex) {
        if (oldIndex >= 0 && !this.cells.hasElement(new Integer(oldIndex))) {
            this.propList.remove(oldIndex);
            this.updateProperties(oldIndex);
        }
    }

    private void updateProperties(int oldIndex) {
        for (int x = 0; x < this.cells.getXCount(); ++x) {
            for (int y = 0; y < this.cells.getYCount(); ++y) {
                Integer index = (Integer)this.cells.getElement(x, y);
                if (index == null || index <= oldIndex) continue;
                index = new Integer(index - 1);
                this.cells.setElement(x, y, index);
            }
        }
    }

    public String getDataClass() {
        return this.gridDataClass;
    }

    public void setDataClass(String value) {
        this.gridDataClass = value;
    }

    public String getReferenceBase() {
        return this.referenceBase;
    }

    public void setReferenceBase(String value) {
        this.referenceBase = value;
    }

    public String getCellData(int col, int row) {
        return (String)this.values.getElement(col, row);
    }

    public String getCellData(String pos) {
        CellPostion p = new CellPostion(pos);
        return this.getCellData(p.col(), p.row());
    }

    public void setCellData(int col, int row, String value) {
        this.values.setElement(col, row, value);
    }

    public void setCellData(String pos, String value) {
        CellPostion p = new CellPostion(pos);
        this.setCellData(p.col(), p.row(), value);
    }

    public String getCellClass(int col, int row) {
        return this.cellCSS == null ? null : (String)this.cellCSS.getElement(col, row);
    }

    public void setCellClass(int col, int row, String value) {
        if (this.cellCSS == null) {
            if (value == null || value.length() == 0) {
                return;
            }
            this.createCellCssMatrix();
        }
        this.cellCSS.setElement(col, row, value);
    }

    public boolean getUseFontSize() {
        return this.useFontSize;
    }

    public void setUserFontSize(boolean value) {
        this.useFontSize = value;
    }

    public String getCellScript(int col, int row) {
        return this.cellScript == null ? null : (String)this.cellScript.getElement(col, row);
    }

    public void setCellScript(int col, int row, String value) {
        if (this.cellScript == null) {
            if (value == null || value.length() == 0) {
                return;
            }
            this.createCellScriptMatrix();
        }
        this.cellScript.setElement(col, row, value);
    }

    public int getCellStyleIndex(int col, int row) {
        Integer index = (Integer)this.cells.getElement(col, row);
        return index == null ? 0 : index;
    }

    public GridCell[] getCellSytles() {
        if (this.propList.count() > 0) {
            GridCell[] ret = new GridCell[this.propList.count()];
            for (int i = 0; i < ret.length; ++i) {
                ret[i] = new GridCell();
                ret[i].init(this, 0, 0, this.propList.get(i));
            }
            return ret;
        }
        return new GridCell[0];
    }

    public GridCell[] getCellStyles(int left, int top, int right, int bottom, boolean fullMode) {
        if (this.propList.count() == 0) {
            return new GridCell[0];
        }
        GridCell[] ret = new GridCell[this.propList.count()];
        for (int col = left; col <= right; ++col) {
            for (int row = top; row <= bottom; ++row) {
                int index;
                if (!StringUtils.isEmpty((String)this.getCellClass(col, row)) || ret[index = this.getCellStyleIndex(col, row)] != null) continue;
                ret[index] = new GridCell();
                if (fullMode) {
                    ret[index].initRaw(this, col, row);
                    continue;
                }
                ret[index].init(this, 0, 0, this.propList.get(index));
            }
        }
        return ret;
    }

    public GridCell[] getCellStyles(int left, int top, int right, int bottom) {
        if (this.propList.count() == 0) {
            return new GridCell[0];
        }
        GridCell[] ret = new GridCell[this.propList.count()];
        for (int col = left; col <= right; ++col) {
            for (int row = top; row <= bottom; ++row) {
                int index;
                if (!StringUtils.isEmpty((String)this.getCellClass(col, row)) || ret[index = this.getCellStyleIndex(col, row)] != null) continue;
                ret[index] = new GridCell();
                ret[index].initRaw(this, col, row);
            }
        }
        return ret;
    }

    public byte[] getCellPropData(int col, int row) {
        CellField cf = this.getCellField(col, row);
        return cf == null ? this.internalGetCellPropData(col, row) : this.internalGetCellPropData(cf.left, cf.top);
    }

    final byte[] internalGetCellPropData(int col, int row) {
        Integer index = (Integer)this.cells.getElement(col, row);
        if (index != null) {
            return this.propList.get(index);
        }
        if (col == 0 || row == 0) {
            return GridConsts.DEF_HEADCELL_PROP;
        }
        return GridConsts.DEF_CELL_PROP;
    }

    public GridCell getCell(int col, int row) {
        this.cell.init(this, col, row);
        return this.cell;
    }

    public GridCell getCell(String pos) {
        CellPostion p = new CellPostion(pos);
        return this.getCell(p.col(), p.row());
    }

    public GridCell getCellEx(int col, int row) {
        GridCell result = new GridCell();
        result.init(this, col, row);
        return result;
    }

    final GridCell internalGetCell(int col, int row) {
        GridCell result = new GridCell();
        result.internalInit(this, col, row, this.internalGetCellPropData(col, row));
        return result;
    }

    public GridCell getCellEx(String pos) {
        CellPostion p = new CellPostion(pos);
        return this.getCellEx(p.col(), p.row());
    }

    public GridCellProperty getCellForChange(int col, int row) {
        if (!this.checkCol(col, false) || !this.checkRow(row, false)) {
            return null;
        }
        GridCellProperty result = new GridCellProperty();
        result.init(this, col, row);
        return result;
    }

    public GridCellProperty getCellForChange(String pos) {
        CellPostion p = new CellPostion(pos);
        return this.getCellForChange(p.col(), p.row());
    }

    public void setCell(int col, int row, GridCell value) {
        CellField cf = this.getCellField(col, row);
        if (cf == null) {
            this.internalSetCell(col, row, value);
        } else if (cf.left == col && cf.top == row) {
            GridCell dCell;
            byte[] curData = this.getCellPropData(col, row);
            boolean curSilver = GridCell.getSilverHead(curData);
            byte[] newData = value.getPropData();
            Boolean silverHead = null;
            if (curSilver != value.getSilverHead()) {
                silverHead = curSilver ? Boolean.FALSE : Boolean.TRUE;
            }
            byte rEdge = newData[9];
            byte bEdge = newData[10];
            if (cf.right > cf.left) {
                int r = cf.top;
                GridCell rCell = this.internalGetCell(cf.right, r);
                if (silverHead != null) {
                    rCell.setSilverHead(silverHead);
                }
                rCell.getPropData()[9] = newData[9];
                this.internalSetCell(cf.right, r, rCell);
                newData[9] = curData[9];
            }
            if (cf.bottom > cf.top) {
                int c = cf.left;
                GridCell bCell = this.internalGetCell(c, cf.bottom);
                if (silverHead != null) {
                    bCell.setSilverHead(silverHead);
                }
                bCell.getPropData()[10] = newData[10];
                this.internalSetCell(c, cf.bottom, bCell);
                newData[10] = curData[10];
            }
            this.internalSetCell(col, row, value);
            if (cf.bottom > cf.top) {
                byte[] sData = this.internalGetCellPropData(cf.left - 1, cf.top);
                for (int r = cf.top + 1; r <= cf.bottom; ++r) {
                    dCell = this.internalGetCell(cf.left - 1, r);
                    dCell.getPropData()[9] = sData[9];
                    this.internalSetCell(cf.left - 1, r, dCell);
                }
            }
            if (cf.right > cf.left) {
                byte[] sData = this.internalGetCellPropData(cf.left, cf.top - 1);
                for (int c = cf.left + 1; c <= cf.right; ++c) {
                    dCell = this.internalGetCell(c, cf.top - 1);
                    dCell.getPropData()[10] = sData[10];
                    this.internalSetCell(c, cf.top - 1, dCell);
                }
            }
            if (cf.bottom > cf.top) {
                byte[] sData = this.internalGetCellPropData(cf.right, cf.top);
                for (int r = cf.top + 1; r <= cf.bottom; ++r) {
                    dCell = this.internalGetCell(cf.right, r);
                    if (silverHead != null) {
                        dCell.setSilverHead(silverHead);
                    }
                    dCell.getPropData()[9] = sData[9];
                    this.internalSetCell(cf.right, r, dCell);
                }
            }
            if (cf.right > cf.left) {
                byte[] sData = this.internalGetCellPropData(cf.left, cf.bottom);
                for (int c = cf.left + 1; c <= cf.right; ++c) {
                    dCell = this.internalGetCell(c, cf.bottom);
                    if (silverHead != null) {
                        dCell.setSilverHead(silverHead);
                    }
                    dCell.getPropData()[10] = sData[10];
                    this.internalSetCell(c, cf.bottom, dCell);
                }
            }
            newData[9] = rEdge;
            newData[10] = bEdge;
            if (silverHead != null) {
                for (int c = cf.left; c < cf.right; ++c) {
                    for (int r = cf.top; r < cf.bottom; ++r) {
                        if (c == cf.left && r == cf.top) continue;
                        dCell = this.internalGetCell(c, r);
                        dCell.setSilverHead(silverHead);
                        this.internalSetCell(dCell);
                    }
                }
            }
        }
    }

    final void internalSetCell(int col, int row, GridCell value) {
        this.internalSetCell(col, row, value.getPropData());
    }

    final void internalSetCell(int col, int row, byte[] propData) {
        int index = this.propList.indexOf(propData);
        if (index < 0) {
            index = this.propList.add(propData);
        }
        boolean dynamicStore = (this.options & 4) != 0;
        Integer oldIndex = dynamicStore ? (Integer)this.cells.getElement(col, row) : null;
        this.cells.setElement(col, row, new Integer(index));
        if (dynamicStore && oldIndex != null && oldIndex != index) {
            this.rebuildPropIndex(col, row, oldIndex);
        }
    }

    final void internalSetCell(GridCell value) {
        this.internalSetCell(value.getColNum(), value.getRowNum(), value);
    }

    public void setCell(GridCell value) {
        this.setCell(value.getColNum(), value.getRowNum(), value);
    }

    public void setCell(int left, int top, int right, int bottom, GridCell value) {
        this.setCell(new CellField(left, top, right, bottom), value);
    }

    public void setCell(CellField field, GridCell value) {
        int index = this.propList.indexOf(value.getPropData());
        if (index == -1) {
            index = this.propList.add(value.getPropData());
        }
        Integer p = new Integer(index);
        for (int col = field.left; col <= field.right; ++col) {
            for (int row = field.top; row <= field.bottom; ++row) {
                CellField f = this.rects.getMergeRect(col, row);
                if (f != null && (f.left != col || f.top != row)) continue;
                this.cells.setElement(col, row, p);
            }
        }
    }

    public void setCell(CellField[] fields, GridCell value) {
        if (fields == null || fields.length == 0) {
            return;
        }
        int index = this.propList.indexOf(value.getPropData());
        if (index == -1) {
            index = this.propList.add(value.getPropData());
        }
        Integer p = new Integer(index);
        for (int i = 0; i < fields.length; ++i) {
            for (int col = fields[i].left; col <= fields[i].right; ++col) {
                for (int row = fields[i].top; row <= fields[i].bottom; ++row) {
                    CellField f = this.rects.getMergeRect(col, row);
                    if (f != null && (f.left != col || f.top != row)) continue;
                    this.cells.setElement(col, row, p);
                }
            }
        }
    }

    public Object getObj(int col, int row) {
        return this.objects.getElement(col, row);
    }

    public void setObj(int col, int row, Object obj) {
        this.objects.setElement(col, row, obj);
    }

    public boolean getColAutoSize(int col) {
        return this.colAutoSize.get(col);
    }

    public void setColAutoSize(int col, boolean value) {
        this.colAutoSize.set(col, value);
    }

    public boolean getColVisible(int col) {
        return this.colVisible.count() > 0 ? this.colVisible.get(col) : true;
    }

    public void setColVisible(int col, boolean value) {
        if (!value || col < this.colVisible.count()) {
            this.colVisible.set(col, value);
        }
    }

    public int getColCount() {
        return this.colWidths.count();
    }

    public int getColWidths(int col) {
        return this.colWidths.get(col);
    }

    public void setColWidths(int col, int value) {
        this.colWidths.set(col, value);
    }

    public boolean getRowAutoSize(int row) {
        return this.rowAutoSize.get(row);
    }

    public void setRowAutoSize(int row, boolean value) {
        this.rowAutoSize.set(row, value);
    }

    public boolean getRowVisible(int row) {
        return this.rowVisible.count() > 0 ? this.rowVisible.get(row) : true;
    }

    public void setRowVisible(int row, boolean value) {
        if (!value || row < this.getRowCount()) {
            this.rowVisible.set(row, value);
        }
    }

    public boolean getHideDataRow(int row) {
        return this.hideDataRow.get(row);
    }

    public void setHideDataRow(int row, boolean value) {
        if (!value || row < this.getRowCount()) {
            this.hideDataRow.set(row, value);
        }
    }

    public boolean getHideDataCol(int col) {
        return this.hideDataCol.get(col);
    }

    public int[][] getHideDataCols() {
        int[][] result = null;
        int count = 0;
        boolean state = false;
        for (int i = 1; i < this.getColCount(); ++i) {
            if (!state && this.getHideDataCol(i)) {
                ++count;
                state = true;
            }
            if (!state || this.getHideDataCol(i)) continue;
            state = false;
        }
        if (count > 0) {
            result = new int[2][count];
            state = false;
            int k = 0;
            for (int i = 1; i < this.getColCount(); ++i) {
                if (!state && this.getHideDataCol(i)) {
                    result[0][k] = i;
                    state = true;
                }
                if (!state || this.getHideDataCol(i)) continue;
                state = false;
                result[1][k] = i - 1;
                ++k;
            }
            if (state) {
                result[1][count - 1] = this.getColCount() - 1;
            }
        }
        return result;
    }

    public int[][] getHideDataRows() {
        int[][] result = null;
        int count = 0;
        boolean state = false;
        for (int i = 1; i < this.getRowCount(); ++i) {
            if (!state && this.getHideDataRow(i)) {
                ++count;
                state = true;
            }
            if (!state || this.getHideDataRow(i)) continue;
            state = false;
        }
        if (count > 0) {
            result = new int[2][count];
            state = false;
            int k = 0;
            for (int i = 1; i < this.getRowCount(); ++i) {
                if (!state && this.getHideDataRow(i)) {
                    result[0][k] = i;
                    state = true;
                }
                if (!state || this.getHideDataRow(i)) continue;
                state = false;
                result[1][k] = i - 1;
                ++k;
            }
            if (state) {
                result[1][count - 1] = this.getRowCount() - 1;
            }
        }
        return result;
    }

    public void setHideDataCol(int col, boolean value) {
        if (!value || col < this.getColCount()) {
            this.hideDataCol.set(col, value);
        }
    }

    public int getRowCount() {
        return this.rowHeights.count();
    }

    public void setColCount(int value) {
        int colCount = this.getColCount();
        if (value > 0 && value < colCount) {
            int delCount = colCount - value;
            this.colVisible.delete(value, delCount);
            this.colAutoSize.delete(value, delCount);
            this.colWidths.delete(value, delCount);
            this.values.xDelete(value, delCount);
            this.cells.xDelete(value, delCount);
            this.objects.xDelete(value, delCount);
            if (this.cellCSS != null) {
                this.cellCSS.xDelete(value, delCount);
            }
            if (this.cellScript != null) {
                this.cellScript.xDelete(value, delCount);
            }
            this.rects.delCols(value, delCount);
        } else if (value > colCount) {
            int insCount = value - colCount;
            this.colVisible.insert(colCount, insCount);
            this.colAutoSize.insert(colCount, insCount);
            this.colWidths.insert(colCount, insCount, colCount == 0 ? 100 : this.colWidths.get(colCount - 1));
            if (colCount == 0) {
                this.colWidths.set(0, 50);
            }
            this.values.xInsert(colCount, insCount);
            this.cells.xInsert(colCount, insCount);
            this.objects.xInsert(colCount, insCount);
            if (this.cellCSS != null) {
                this.cellCSS.xInsert(colCount, insCount);
            }
            if (this.cellScript != null) {
                this.cellScript.xInsert(colCount, insCount);
            }
            this.rects.insertCols(colCount, insCount);
            if (this.getRowCount() > 0) {
                for (int i = colCount; i < colCount + insCount; ++i) {
                    GridCell cell = this.getCell(i, 0);
                    this.setCell(i, 0, cell);
                }
            }
        }
    }

    public void setRowCount(int value) {
        int rowCount = this.getRowCount();
        if (value < rowCount) {
            int delCount = rowCount - value;
            this.rowVisible.delete(value, delCount);
            this.rowAutoSize.delete(value, delCount);
            this.rowHeights.delete(value, delCount);
            this.values.yDelete(value, delCount);
            this.cells.yDelete(value, delCount);
            this.objects.yDelete(value, delCount);
            if (this.cellCSS != null) {
                this.cellCSS.yDelete(value, delCount);
            }
            if (this.cellScript != null) {
                this.cellScript.yDelete(value, delCount);
            }
            this.rects.delRows(value, delCount);
        } else if (value > this.getRowCount()) {
            int insCount = value - rowCount;
            this.rowVisible.insert(rowCount, insCount);
            this.rowAutoSize.insert(rowCount, insCount);
            this.rowHeights.insert(rowCount, insCount, rowCount == 0 ? 20 : this.rowHeights.get(rowCount - 1));
            this.values.yInsert(rowCount, insCount);
            this.objects.yInsert(rowCount, insCount);
            this.cells.yInsert(rowCount, insCount);
            if (this.cellCSS != null) {
                this.cellCSS.yInsert(rowCount, insCount);
            }
            if (this.cellScript != null) {
                this.cellScript.yInsert(rowCount, insCount);
            }
            this.rects.insertRows(rowCount, insCount);
            if (this.getColCount() > 0) {
                for (int i = rowCount; i < rowCount + insCount; ++i) {
                    GridCell cell = this.getCell(0, i);
                    this.setCell(0, i, cell);
                }
            }
        }
    }

    public void insertCol(int index, int count) {
        this.insertCol(index, count, (this.options & 2) == 0);
    }

    private void insertCol(int index, int count, boolean fastMode) {
        if (!this.checkCol(index, true)) {
            return;
        }
        this.colVisible.insert(index, count);
        if (index > 0) {
            this.colAutoSize.insert(index, count, this.colAutoSize.get(index - 1));
        } else {
            this.colAutoSize.insert(index, count);
        }
        this.colWidths.insert(index, count, this.getColCount() == 0 || index == 1 ? 100 : this.colWidths.get(index - 1));
        this.values.xInsert(index, count);
        this.cells.xInsert(index, count);
        this.objects.xInsert(index, count);
        if (this.cellCSS != null) {
            this.cellCSS.xInsert(index, count);
        }
        if (this.cellScript != null) {
            this.cellScript.xInsert(index, count);
        }
        this.rects.insertCols(index, count);
        if (this.getRowCount() > 0 && !fastMode) {
            int row;
            for (row = 1; row < this.getRowCount(); ++row) {
                GridCell cell;
                if (index == 1) {
                    cell = this.cell;
                    cell.init(this, -1, -1, GridConsts.DEF_CELL_PROP);
                } else {
                    cell = this.getCell(index - 1, row);
                }
                for (int col = index; col < index + count; ++col) {
                    this.setCell(col, row, cell);
                }
            }
            if (index == 1) {
                for (row = 1; row < this.getRowCount(); ++row) {
                    GridCell leftCell = this.getCell(0, row);
                    GridCell rightCell = this.getCellEx(index + count - 1, row);
                    rightCell.setREdge(leftCell.getREdgeColor(), leftCell.getREdgeStyle());
                    leftCell.setREdge(0x808080, 0);
                    this.setCell(leftCell);
                    this.setCell(rightCell);
                }
            } else {
                GridCell cell = this.getCellEx(index - 1, 0);
                for (int col = index; col < index + count; ++col) {
                    GridCell destCell = this.getCell(col, 0);
                    destCell.setBEdge(cell.getBEdgeColor(), cell.getBEdgeStyle());
                    this.setCell(destCell);
                }
            }
        }
        if (this.scrollTopCol >= index) {
            this.scrollTopCol += count;
        }
        if (this.scrollBottomCol + 1 >= index) {
            this.scrollBottomCol += count;
        }
    }

    public void deleteCol(int index, int count) {
        if (!this.checkCol(index, false)) {
            return;
        }
        if (index + count > this.getColCount()) {
            count = this.getColCount() - index;
        }
        this.mergeStylesBeforeColDel(index, count);
        this.colVisible.delete(index, count);
        this.colAutoSize.delete(index, count);
        this.colWidths.delete(index, count);
        this.values.xDelete(index, count);
        this.cells.xDelete(index, count);
        this.objects.xDelete(index, count);
        if (this.cellCSS != null) {
            this.cellCSS.xDelete(index, count);
        }
        if (this.cellScript != null) {
            this.cellScript.xDelete(index, count);
        }
        this.rects.delCols(index, count);
        if (this.scrollTopCol >= index) {
            this.scrollTopCol = Math.max(index - 1, this.scrollTopCol - count);
        }
        if (this.scrollBottomCol >= index) {
            this.scrollBottomCol = Math.max(index - 1, this.scrollBottomCol - count);
        }
    }

    private void mergeStylesBeforeColDel(int index, int count) {
        if (index + count >= this.getColCount()) {
            return;
        }
        for (int row = 1; row < this.getRowCount(); ++row) {
            CellField cf = this.rects.getMergeRect(index + count, row);
            if (cf == null || cf.top != row || cf.left < index || cf.left >= index + count) continue;
            byte[] prop = this.internalGetCellPropData(cf.left, cf.top);
            this.internalSetCell(index + count, row, prop);
        }
    }

    public void insertRow(int index, int count) {
        this.insertRow(index, count, (this.options & 2) == 0);
    }

    private void insertRow(int index, int count, boolean fastMode) {
        if (!this.checkRow(index, true)) {
            return;
        }
        this.rowVisible.insert(index, count);
        if (index > 0) {
            this.rowAutoSize.insert(index, count, this.rowAutoSize.get(index - 1));
        } else {
            this.rowAutoSize.insert(index, count);
        }
        this.rowHeights.insert(index, count, this.getRowCount() == 0 || index == 1 ? 20 : this.rowHeights.get(index - 1));
        this.values.yInsert(index, count);
        this.cells.yInsert(index, count);
        this.objects.yInsert(index, count);
        if (this.cellCSS != null) {
            this.cellCSS.yInsert(index, count);
        }
        if (this.cellScript != null) {
            this.cellScript.yInsert(index, count);
        }
        this.rects.insertRows(index, count);
        if (this.getColCount() > 0 && !fastMode) {
            int col;
            for (col = 1; col < this.getColCount(); ++col) {
                GridCell cell;
                if (index == 1) {
                    cell = this.cell;
                    cell.init(this, -1, -1, GridConsts.DEF_CELL_PROP);
                } else {
                    cell = this.getCell(col, index - 1);
                }
                for (int row = index; row < index + count; ++row) {
                    this.setCell(col, row, cell);
                }
            }
            if (index == 1) {
                for (col = 1; col < this.getColCount(); ++col) {
                    GridCell topCell = this.getCell(col, 0);
                    GridCell bottomCell = this.getCellEx(col, index + count - 1);
                    bottomCell.setBEdge(topCell.getBEdgeColor(), topCell.getBEdgeStyle());
                    topCell.setBEdge(0x808080, 0);
                    this.setCell(topCell);
                    this.setCell(bottomCell);
                }
            } else {
                GridCell cell = this.getCellEx(0, index - 1);
                for (int row = index; row < index + count; ++row) {
                    GridCell destCell = this.getCell(0, row);
                    destCell.setREdge(cell.getREdgeColor(), cell.getREdgeStyle());
                    this.setCell(destCell);
                }
            }
        }
        if (this.scrollTopRow >= index) {
            this.scrollTopRow += count;
        }
        if (this.scrollBottomRow + 1 >= index) {
            this.scrollBottomRow += count;
        }
    }

    public boolean duplicateCols(int fromIndex, int toIndex, int duplicateCount, int insertIndex) {
        if (!this.checkCol(fromIndex, false)) {
            return false;
        }
        if (!this.checkCol(toIndex, false)) {
            return false;
        }
        if (!this.checkCol(insertIndex, true)) {
            return false;
        }
        if (toIndex < fromIndex || duplicateCount <= 0 || insertIndex > fromIndex && insertIndex <= toIndex) {
            return false;
        }
        int insertCount = (toIndex - fromIndex + 1) * duplicateCount;
        this.insertCol(insertIndex, insertCount, true);
        if (insertIndex <= fromIndex) {
            fromIndex += insertCount;
            toIndex += insertCount;
        }
        List merges = fromIndex < toIndex ? this.rects.findFieldsInCols(fromIndex, toIndex) : null;
        for (int i = 0; i < duplicateCount; ++i) {
            int j;
            int offset = insertIndex + (toIndex - fromIndex + 1) * i - fromIndex;
            for (j = fromIndex; j <= toIndex; ++j) {
                this.colWidths.set(offset + j, this.colWidths.get(j));
                this.values.xCopy(j, offset + j);
                if (this.cellCSS != null) {
                    this.cellCSS.xCopy(j, offset + j);
                }
                if (this.cellScript != null) {
                    this.cellScript.xCopy(j, offset + j);
                }
                this.cells.xCopy(j, offset + j);
                if (this.colVisible.get(j)) continue;
                this.colVisible.set(j + offset, false);
            }
            if (merges == null || merges.isEmpty()) continue;
            for (j = 0; j < merges.size(); ++j) {
                CellField cf = (CellField)merges.get(j);
                this.mergeCells(cf.left + offset, cf.top, cf.right + offset, cf.bottom);
            }
        }
        return true;
    }

    public boolean duplicateRows(int fromIndex, int toIndex, int duplicateCount, int insertIndex) {
        if (!this.checkRow(fromIndex, false)) {
            return false;
        }
        if (!this.checkRow(toIndex, false)) {
            return false;
        }
        if (!this.checkRow(insertIndex, true)) {
            return false;
        }
        if (toIndex < fromIndex || duplicateCount <= 0 || insertIndex > fromIndex && insertIndex <= toIndex) {
            return false;
        }
        int insertCount = (toIndex - fromIndex + 1) * duplicateCount;
        this.insertRow(insertIndex, insertCount, true);
        if (insertIndex <= fromIndex) {
            fromIndex += insertCount;
            toIndex += insertCount;
        }
        List merges = fromIndex < toIndex ? this.rects.findFieldsInRows(fromIndex, toIndex) : null;
        for (int i = 0; i < duplicateCount; ++i) {
            int j;
            int offset = insertIndex + (toIndex - fromIndex + 1) * i - fromIndex;
            for (j = fromIndex; j <= toIndex; ++j) {
                this.rowHeights.set(offset + j, this.rowHeights.get(j));
                this.values.yCopy(j, offset + j);
                if (this.cellCSS != null) {
                    this.cellCSS.yCopy(j, offset + j);
                }
                if (this.cellScript != null) {
                    this.cellScript.yCopy(j, offset + j);
                }
                this.cells.yCopy(j, offset + j);
                if (this.rowVisible.get(j)) continue;
                this.rowVisible.set(j + offset, false);
            }
            if (merges == null || merges.isEmpty()) continue;
            for (j = 0; j < merges.size(); ++j) {
                CellField cf = (CellField)merges.get(j);
                this.mergeCells(cf.left, cf.top + offset, cf.right, cf.bottom + offset);
            }
        }
        return true;
    }

    public void deleteRow(int index, int count) {
        if (!this.checkRow(index, false)) {
            return;
        }
        if (index + count > this.getRowCount()) {
            count = this.getRowCount() - index;
        }
        this.mergeStylesBeforeRowDel(index, count);
        this.rowVisible.delete(index, count);
        this.rowAutoSize.delete(index, count);
        this.rowHeights.delete(index, count);
        this.values.yDelete(index, count);
        this.cells.yDelete(index, count);
        if (this.cellCSS != null) {
            this.cellCSS.yDelete(index, count);
        }
        if (this.cellScript != null) {
            this.cellScript.yDelete(index, count);
        }
        this.objects.yDelete(index, count);
        this.rects.delRows(index, count);
        if (this.scrollTopRow >= index) {
            this.scrollTopRow = Math.max(index - 1, this.scrollTopRow - count);
        }
        if (this.scrollBottomRow >= index) {
            this.scrollBottomRow = Math.max(index - 1, this.scrollBottomRow - count);
        }
    }

    private void mergeStylesBeforeRowDel(int index, int count) {
        if (index + count >= this.getRowCount()) {
            return;
        }
        for (int col = 1; col < this.getColCount(); ++col) {
            CellField cf = this.rects.getMergeRect(col, index + count);
            if (cf == null || cf.left != col || cf.top < index || cf.top >= index + count) continue;
            byte[] prop = this.internalGetCellPropData(cf.left, cf.top);
            this.internalSetCell(col, index + count, prop);
        }
    }

    public int getRowHeights(int row) {
        return this.rowHeights.get(row);
    }

    public void setRowHeights(int row, int value) {
        this.rowHeights.set(row, value);
    }

    public int getScrollTopCol() {
        return this.scrollTopCol;
    }

    public void setScrollTopCol(int value) {
        this.scrollTopCol = value;
    }

    public int getScrollTopRow() {
        return this.scrollTopRow;
    }

    public void setScrollTopRow(int value) {
        this.scrollTopRow = value;
    }

    public int getScrollBottomCol() {
        return this.scrollBottomCol;
    }

    public void setScrollBottomCol(int value) {
        this.scrollBottomCol = value;
    }

    public int getScrollBottomRow() {
        return this.scrollBottomRow;
    }

    public void setScrollBottomRow(int value) {
        this.scrollBottomRow = value;
    }

    public int lockTopCol() {
        return this.scrollTopCol;
    }

    public void setLockTopCol(int value) {
        this.scrollTopCol = value;
    }

    public int lockTopRow() {
        return this.scrollTopRow;
    }

    public void setLockTopRow(int value) {
        this.scrollTopRow = value;
    }

    public int lockBottomCol() {
        return this.scrollBottomCol;
    }

    public void setLockBottomCol(int value) {
        this.scrollBottomCol = value;
    }

    public int lockBottomRow() {
        return this.scrollBottomRow;
    }

    public void setLockBottomRow(int value) {
        this.scrollBottomRow = value;
    }

    public int curCol() {
        return this.curCol;
    }

    public void setCurCol(int value) {
        this.curCol = value;
    }

    public int curRow() {
        return this.curRow;
    }

    public void setCurRow(int value) {
        this.curRow = value;
    }

    public GridFieldList merges() {
        return this.rects;
    }

    public IntList cellColors() {
        return this.cellColors;
    }

    public IntList fontColors() {
        return this.fontColors;
    }

    public IntList edgeColors() {
        return this.edgeColors;
    }

    public GridFontNames fontNames() {
        return this.fontNames;
    }

    public IntList fontSizes() {
        return this.fontSizes;
    }

    public boolean cellMerged(int col, int row) {
        return this.rects.getMergeRect(col, row) != null;
    }

    public CellField expandCell(int col, int row) {
        CellField rt = this.rects.getMergeRect(col, row);
        return rt == null ? new CellField(col, row, col, row) : (CellField)rt.clone();
    }

    CellField getCellField(int col, int row) {
        return this.rects.getMergeRect(col, row);
    }

    public boolean fastMergeCells(int left, int top, int right, int bottom) {
        if (left == right && top == bottom) {
            return true;
        }
        CellField cf = new CellField(left, top, right, bottom);
        return this.rects.addMergeRect(cf);
    }

    public boolean mergeCells(int left, int top, int right, int bottom) {
        byte[] dest;
        byte[] src;
        if (left == right && top == bottom) {
            return true;
        }
        byte[] srcCell = this.getCellEx(left, top).getPropData();
        if (!this.fastMergeCells(left, top, right, bottom)) {
            return false;
        }
        if (bottom > top) {
            src = this.internalGetCellPropData(left - 1, top);
            for (int r = top + 1; r <= bottom; ++r) {
                dest = this.internalGetCellPropData(left - 1, r);
                dest = (byte[])dest.clone();
                dest[9] = src[9];
                this.internalSetCell(left - 1, r, dest);
            }
        }
        if (right > left) {
            src = this.internalGetCellPropData(left, top - 1);
            for (int c = left + 1; c <= right; ++c) {
                dest = this.internalGetCellPropData(c, top - 1);
                dest = (byte[])dest.clone();
                dest[10] = src[10];
                this.internalSetCell(c, top - 1, dest);
            }
        }
        for (int r = top; r <= bottom; ++r) {
            byte[] dest2 = this.internalGetCellPropData(right, r);
            dest2 = (byte[])dest2.clone();
            dest2[9] = srcCell[9];
            this.internalSetCell(right, r, dest2);
        }
        for (int c = left; c <= right; ++c) {
            byte[] dest3 = this.internalGetCellPropData(c, bottom);
            dest3 = (byte[])dest3.clone();
            dest3[10] = srcCell[10];
            this.internalSetCell(c, bottom, dest3);
        }
        return true;
    }

    public void unmergeCells(int left, int top, int right, int bottom) {
        this.rects.removeMergeRect(new CellField(left, top, right, bottom));
    }

    public boolean inSameRegion(int col1, int row1, int col2, int row2) {
        CellField f1 = this.rects.getMergeRect(col1, row1);
        if (f1 == null) {
            return false;
        }
        return f1 == this.rects.getMergeRect(col2, row2);
    }

    public LabelItems getLabels() {
        return this.labels;
    }

    private void clear() {
        this.scrollTopCol = -1;
        this.scrollTopRow = -1;
        this.scrollBottomCol = -1;
        this.scrollBottomRow = -1;
        this.curCol = -1;
        this.curRow = -1;
        this.labels.clear();
        this.extDatas.clear();
        this.cellCSS = null;
        this.cellScript = null;
    }

    public void loadFromStream(Stream stream) throws StreamException {
        ValueList valueList = new ValueList();
        ValueList cellCSSList = new ValueList();
        ValueList cellScriptList = new ValueList();
        WordList propIndexs = new WordList();
        KeyValues dataProps = new KeyValues();
        BIFF b = new BIFF();
        b.data().setUseEncode(stream.getUseEncode());
        b.data().setCharset(stream.getCharset());
        long p = stream.getPosition();
        BIFF.readBIFF(1, b, stream);
        String signFlag = b.data().readString(4);
        if (!"BIFF".equals(signFlag)) {
            stream.setPosition(p);
            BIFF.readBIFF(2, b, stream);
            signFlag = b.data().readString(4);
            if (!"BIFF".equals(signFlag)) {
                throw new StreamException("\u9519\u8bef\u7684\u6587\u4ef6\u683c\u5f0f");
            }
        }
        int ver = b.data().readInt();
        int fileVer = b.data().getPosition() < b.data().getSize() ? b.data().readInt() : 0x1000000;
        this.clear();
        this.useFontSize = false;
        while (b.ident != 127) {
            BIFF.readBIFF(ver, b, stream);
            switch (b.ident) {
                case 28: {
                    String charset = b.data().readStringBySize();
                    if (StringUtils.isEmpty((String)charset)) break;
                    stream.setUseEncode(true);
                    stream.setCharset(charset);
                    b.data().setUseEncode(true);
                    b.data().setCharset(charset);
                    break;
                }
                case 25: {
                    dataProps.loadFromStream(b.data());
                    break;
                }
                case 18: {
                    this.useFontSize = true;
                    break;
                }
                case 6: {
                    this.fontNames.loadFromStream(b.data());
                    break;
                }
                case 8: {
                    this.fontSizes.loadFromStream(b.data(), b.size);
                    break;
                }
                case 7: {
                    this.fontColors.loadFromStream(b.data(), b.size);
                    break;
                }
                case 10: {
                    this.cellColors.loadFromStream(b.data(), b.size);
                    break;
                }
                case 9: {
                    this.edgeColors.loadFromStream(b.data(), b.size);
                    break;
                }
                case 2: {
                    this.colWidths.loadFromStream(b.data(), b.size);
                    break;
                }
                case 13: {
                    this.colAutoSize.loadFromStream(b.data(), b.size);
                    break;
                }
                case 12: {
                    this.colVisible.loadFromStream(b.data(), b.size);
                    break;
                }
                case 21: {
                    this.hideDataCol.loadFromStream(b.data(), b.size);
                    break;
                }
                case 22: {
                    this.hideDataRow.loadFromStream(b.data(), b.size);
                    break;
                }
                case 3: {
                    this.rowHeights.loadFromStream(b.data(), b.size);
                    break;
                }
                case 15: {
                    this.rowAutoSize.loadFromStream(b.data(), b.size);
                    break;
                }
                case 14: {
                    this.rowVisible.loadFromStream(b.data(), b.size);
                    break;
                }
                case 4: {
                    valueList.loadFromStream(b.data());
                    break;
                }
                case 11: {
                    this.propList.loadFromStreamEx(b.data(), 0, 12);
                    break;
                }
                case 19: {
                    this.propList.loadFromStreamEx(b.data(), 12, 8);
                    break;
                }
                case 1: {
                    propIndexs.loadFromStream(b.data(), b.size);
                    break;
                }
                case 5: {
                    this.rects.loadFromStream(b.data(), b.size);
                    break;
                }
                case 16: {
                    this.scrollTopCol = b.data().readInt();
                    this.scrollTopRow = b.data().readInt();
                    this.scrollBottomCol = b.data().readInt();
                    this.scrollBottomRow = b.data().readInt();
                    break;
                }
                case 17: {
                    this.curCol = b.data().readInt();
                    this.curRow = b.data().readInt();
                    break;
                }
                case 20: {
                    this.labels.loadFromStream(b.data());
                    break;
                }
                case 23: {
                    this.colCountPerPage = b.data().readInt();
                    this.rowCountPerPage = b.data().readInt();
                    this.rowPagingFirst = b.data().readBool();
                    break;
                }
                case 24: {
                    cellCSSList.loadFromStream(b.data());
                    break;
                }
                case 26: {
                    cellScriptList.loadFromStream(b.data());
                    break;
                }
                case 27: {
                    this.extDatas.loadFromStream(b.data(), b.size);
                }
            }
        }
        if (fileVer == 0x1000000) {
            this.labels.ver10Tover11();
        }
        if (fileVer < 0x1020000) {
            this.propList.updateToVer12();
        }
        this.gridDataClass = dataProps.getValue("gridclass");
        this.referenceBase = dataProps.getValue("gridrefbase");
        this.horzOffset = dataProps.getAsInt("horzoffset");
        this.vertOffset = dataProps.getAsInt("vertoffset");
        this.horzCenter = dataProps.getAsBoolean("horzcenter");
        this.vertCenter = dataProps.getAsBoolean("vertcenter");
        this.setColCount(this.colWidths.count());
        this.setRowCount(this.rowHeights.count());
        this.cells.resetSize(this.colWidths.count(), this.rowHeights.count());
        this.objects.resetSize(this.colWidths.count(), this.rowHeights.count());
        this.values.resetSize(this.colWidths.count(), this.rowHeights.count());
        int i = 0;
        for (int c = 0; c < this.colWidths.count(); ++c) {
            for (int r = 0; r < this.rowHeights.count(); ++r) {
                int value = propIndexs.get(i);
                this.cells.setElement(c, r, value < 0 || value >= 65535 ? null : new Integer(value));
                ++i;
            }
        }
        for (i = 0; i < valueList.count(); ++i) {
            this.values.setElement(valueList.col(i), valueList.row(i), valueList.get(i));
        }
        for (i = 0; i < cellCSSList.count(); ++i) {
            this.setCellClass(cellCSSList.col(i), cellCSSList.row(i), cellCSSList.get(i));
        }
        for (i = 0; i < cellScriptList.count(); ++i) {
            this.setCellScript(cellScriptList.col(i), cellScriptList.row(i), cellScriptList.get(i));
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        ValueList valueList = new ValueList();
        ValueList cellCSSList = new ValueList();
        ValueList cellScriptList = new ValueList();
        WordList propIndexs = new WordList();
        KeyValues dataProps = new KeyValues();
        dataProps.setValue("gridclass", this.gridDataClass);
        dataProps.setValue("gridrefbase", this.referenceBase);
        dataProps.setAsInt("horzoffset", this.horzOffset);
        dataProps.setAsInt("vertoffset", this.vertOffset);
        dataProps.setAsBoolean("horzcenter", this.horzCenter);
        dataProps.setAsBoolean("vertcenter", this.vertCenter);
        for (int c = 0; c < this.colWidths.count(); ++c) {
            for (int r = 0; r < this.rowHeights.count(); ++r) {
                CellValue cv;
                Integer propIndex;
                String cell = (String)this.values.getElement(c, r);
                if (cell != null && cell.length() > 0) {
                    CellValue cv2 = new CellValue();
                    cv2.col = c;
                    cv2.row = r;
                    cv2.value = cell;
                    valueList.add(cv2);
                }
                if ((propIndex = (Integer)this.cells.getElement(c, r)) == null) {
                    byte[] propData = c == 0 || r == 0 ? GridConsts.DEF_HEADCELL_PROP : GridConsts.DEF_CELL_PROP;
                    int pi = this.propList.indexOf(propData);
                    if (pi == -1) {
                        pi = this.propList.add(propData);
                    }
                    propIndex = new Integer(pi);
                }
                propIndexs.add(propIndex);
                cell = this.getCellClass(c, r);
                if (cell != null && cell.length() > 0) {
                    cv = new CellValue();
                    cv.col = c;
                    cv.row = r;
                    cv.value = cell;
                    cellCSSList.add(cv);
                }
                if ((cell = this.getCellScript(c, r)) == null || cell.length() == 0) continue;
                cv = new CellValue();
                cv.col = c;
                cv.row = r;
                cv.value = cell;
                cellScriptList.add(cv);
            }
        }
        BIFF b = new BIFF();
        b.data().setUseEncode(stream.getUseEncode());
        b.data().setCharset(stream.getCharset());
        b.ident = 0;
        b.data().writeString("BIFF");
        b.data().writeInt(2);
        b.data().writeInt(0x1020000);
        BIFF.writeBIFF(1, b, stream);
        b.reset();
        if (stream.getUseEncode()) {
            b.ident = (short)28;
            b.data().writeStringWithSize(stream.getCharset());
            BIFF.writeBIFF(2, b, stream);
        }
        b.reset();
        b.ident = (short)25;
        dataProps.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)18;
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)6;
        this.fontNames.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)8;
        this.fontSizes.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)7;
        this.fontColors.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)10;
        this.cellColors.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)9;
        this.edgeColors.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)2;
        this.colWidths.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)13;
        this.colAutoSize.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)12;
        this.colVisible.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)21;
        this.hideDataCol.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)3;
        this.rowHeights.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)15;
        this.rowAutoSize.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)14;
        this.rowVisible.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)22;
        this.hideDataRow.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)4;
        valueList.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)11;
        this.propList.saveToStreamEx(b.data(), 0, 12);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)19;
        this.propList.saveToStreamEx(b.data(), 12, 8);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = 1;
        propIndexs.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)5;
        this.rects.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)16;
        b.data().writeInt(this.scrollTopCol);
        b.data().writeInt(this.scrollTopRow);
        b.data().writeInt(this.scrollBottomCol);
        b.data().writeInt(this.scrollBottomRow);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)17;
        b.data().writeInt(this.curCol);
        b.data().writeInt(this.curRow);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)20;
        this.labels.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)23;
        b.data().writeInt(this.colCountPerPage);
        b.data().writeInt(this.rowCountPerPage);
        b.data().writeBool(this.rowPagingFirst);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)24;
        cellCSSList.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)26;
        cellScriptList.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)27;
        this.extDatas.saveToStream(b.data());
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)127;
        BIFF.writeBIFF(2, b, stream);
    }

    public void loadFromStream(InputStream inStream) throws StreamException {
        MemStream stream = new MemStream();
        try {
            stream.loadFromStream(inStream);
        }
        catch (IOException e) {
            throw new StreamException((Throwable)e);
        }
        stream.setPosition(0L);
        this.loadFromStream((Stream)stream);
    }

    public void saveToStream(OutputStream outStream) throws StreamException {
        MemStream stream = new MemStream();
        this.saveToStream((Stream)stream);
        try {
            stream.saveToStream(outStream);
        }
        catch (IOException e) {
            throw new StreamException((Throwable)e);
        }
    }

    public void loadFromFile(String fileName) throws StreamException {
        MemStream stream = new MemStream();
        try {
            stream.loadFromFile(fileName);
        }
        catch (IOException e) {
            throw new StreamException((Throwable)e);
        }
        stream.setPosition(0L);
        this.loadFromStream((Stream)stream);
    }

    public void saveToFile(String fileName) throws StreamException {
        MemStream stream = new MemStream();
        this.saveToStream((Stream)stream);
        try {
            stream.saveToFile(fileName);
        }
        catch (IOException e) {
            throw new StreamException((Throwable)e);
        }
    }

    public GridData makeCopy() throws StreamException {
        MemStream s = new MemStream();
        this.saveToStream((Stream)s);
        s.setPosition(0L);
        GridData gd = new GridData();
        gd.loadFromStream((Stream)s);
        gd.setOptions(this.getOptions());
        return gd;
    }

    public Object clone() {
        try {
            return this.makeCopy();
        }
        catch (StreamException e) {
            return null;
        }
    }

    public boolean copyFrom(GridData srcData, int fromLeft, int fromTop, int fromRight, int fromBottom, int toLeft, int toTop) {
        GridCell srcCell;
        CellField field;
        int y;
        int xx;
        int x;
        CellField dest = new CellField(toLeft, toTop, toLeft + fromRight - fromLeft, toTop + fromBottom - fromTop);
        if (!this.checkCol(dest.left, false)) {
            return false;
        }
        if (!this.checkCol(dest.right, false)) {
            return false;
        }
        if (!this.checkRow(dest.top, false)) {
            return false;
        }
        if (!this.checkRow(dest.bottom, false)) {
            return false;
        }
        if (!srcData.checkCol(fromLeft, false)) {
            return false;
        }
        if (!srcData.checkCol(fromRight, false)) {
            return false;
        }
        if (!srcData.checkRow(fromTop, false)) {
            return false;
        }
        if (!srcData.checkRow(fromBottom, false)) {
            return false;
        }
        this.rects.removeMergeRect(dest);
        GridCopyConvertor convertor = this == srcData || (this.options & 8) == 0 ? null : new GridCopyConvertor(srcData);
        boolean needObjects = (this.options & 0x10) == 0;
        for (x = fromLeft; x <= fromRight; ++x) {
            for (int y2 = fromTop; y2 <= fromBottom; ++y2) {
                CellField field2 = srcData.expandCell(x, y2);
                String text = srcData.getCellData(x, y2);
                GridCell cell = srcData.internalGetCell(x, y2);
                if (field2.left < fromLeft) {
                    field2.left = fromLeft;
                }
                if (field2.right > fromRight) {
                    field2.right = fromRight;
                }
                if (field2.top < fromTop) {
                    field2.top = fromTop;
                }
                if (field2.bottom > fromBottom) {
                    field2.bottom = fromBottom;
                }
                int xx2 = toLeft + x - fromLeft;
                int yy = toTop + y2 - fromTop;
                if (convertor != null) {
                    convertor.convert(cell);
                }
                this.internalSetCell(xx2, yy, cell);
                this.setCellData(xx2, yy, text);
                this.setCellClass(xx2, yy, srcData.getCellClass(x, y2));
                if (needObjects) {
                    this.setObj(xx2, yy, srcData.getObj(x, y2));
                }
                if (x != field2.left || y2 != field2.top) continue;
                this.fastMergeCells(xx2, yy, toLeft + field2.right - fromLeft, toTop + field2.bottom - fromTop);
            }
        }
        if (dest.top == 1 && dest.bottom == this.getRowCount() - 1) {
            for (x = fromLeft; x <= fromRight; ++x) {
                xx = toLeft + x - fromLeft;
                this.setHideDataCol(xx, srcData.getHideDataCol(x));
                this.setColAutoSize(xx, srcData.getColAutoSize(x));
                this.setColWidths(xx, srcData.getColWidths(x));
                this.setColVisible(xx, srcData.getColVisible(x));
            }
        }
        if (dest.left == 1 && dest.right == this.getColCount() - 1) {
            for (y = fromTop; y <= fromBottom; ++y) {
                int yy = toTop + y - fromTop;
                this.setHideDataRow(yy, srcData.getHideDataRow(y));
                this.setRowAutoSize(yy, srcData.getRowAutoSize(y));
                this.setRowHeights(yy, srcData.getRowHeights(y));
                this.setRowVisible(yy, srcData.getRowVisible(y));
            }
        }
        for (x = fromLeft; x <= fromRight; ++x) {
            xx = toLeft + x - fromLeft;
            int yy = toTop - 1;
            if (this == srcData && xx >= fromLeft && xx <= fromRight && yy >= fromTop && yy <= fromBottom) continue;
            field = srcData.expandCell(x, fromTop);
            if (field.left != x || field.top != fromTop) continue;
            srcCell = srcData.internalGetCell(x, fromTop - 1);
            GridCell destCell = this.internalGetCell(xx, yy);
            destCell.setBEdgeColor(srcCell.getBEdgeColor());
            destCell.setBEdgeStyle(srcCell.getBEdgeStyle());
            this.internalSetCell(destCell);
        }
        for (y = fromTop; y <= fromBottom; ++y) {
            xx = toLeft - 1;
            int yy = toTop + y - fromTop;
            if (this == srcData && xx >= fromLeft && xx <= fromRight && yy >= fromTop && yy <= fromBottom) continue;
            field = srcData.expandCell(fromLeft, y);
            if (field.left != fromLeft || field.top != y) continue;
            srcCell = srcData.internalGetCell(fromLeft - 1, y);
            GridCell destCell = this.internalGetCell(xx, yy);
            destCell.setREdgeColor(srcCell.getREdgeColor());
            destCell.setREdgeStyle(srcCell.getREdgeStyle());
            this.internalSetCell(destCell);
        }
        return true;
    }

    public int getColCountPerPage() {
        return this.colCountPerPage;
    }

    public void setColCountPerPage(int value) {
        this.colCountPerPage = value;
    }

    public int getRowCountPerPage() {
        return this.rowCountPerPage;
    }

    public void setRowCountPerPage(int value) {
        this.rowCountPerPage = value;
    }

    public boolean getRowPagingFirst() {
        return this.rowPagingFirst;
    }

    public void setRowPagingFirst(boolean value) {
        this.rowPagingFirst = value;
    }

    public int getColPageCount() {
        if (this.colCountPerPage <= 0) {
            return 1;
        }
        int colCount = this.getColCount() - 1;
        if (this.scrollBottomCol > 0 && this.scrollBottomCol > this.scrollTopCol) {
            colCount = this.scrollBottomCol;
        }
        if (this.scrollTopCol > 0) {
            colCount -= this.scrollTopCol - 1;
        }
        return (colCount - 1) / this.colCountPerPage + 1;
    }

    public int getRowPageCount() {
        if (this.rowCountPerPage <= 0) {
            return 1;
        }
        int rowCount = this.getRowCount() - 1;
        if (this.scrollBottomRow > 0 && this.scrollBottomRow > this.scrollTopRow) {
            rowCount = this.scrollBottomRow;
        }
        if (this.scrollTopRow > 0) {
            rowCount -= this.scrollTopRow - 1;
        }
        return (rowCount - 1) / this.rowCountPerPage + 1;
    }

    public int getPageCount() {
        return this.getColPageCount() * this.getRowPageCount();
    }

    public GridData getPagedGridData(int pageIndex) {
        int px = 0;
        int py = 0;
        if (this.rowPagingFirst) {
            int count = this.getRowPageCount();
            px = pageIndex / count;
            py = pageIndex % count;
        } else {
            int count = this.getColPageCount();
            px = pageIndex % count;
            py = pageIndex / count;
        }
        return this.getPagedGridData(px, py);
    }

    public GridData getPagedGridData(int colPageIndex, int rowPageIndex) {
        int i;
        int dataColCount;
        int rowCountPerPage;
        int colCountPerPage;
        if (colPageIndex < 0 || rowPageIndex < 0) {
            return null;
        }
        int colHead = this.scrollTopCol - 1;
        int colTail = this.scrollBottomCol + 1;
        int rowHead = this.scrollTopRow - 1;
        int rowTail = this.scrollBottomRow + 1;
        if (colHead < 0) {
            colHead = 0;
        }
        if (colTail <= colHead) {
            colTail = this.getColCount();
        }
        if (rowHead < 0) {
            rowHead = 0;
        }
        if (rowTail <= rowHead) {
            rowTail = this.getRowCount();
        }
        if ((colCountPerPage = this.colCountPerPage) <= 0) {
            colCountPerPage = colTail - colHead - 1;
        }
        if ((rowCountPerPage = this.rowCountPerPage) <= 0) {
            rowCountPerPage = rowTail - rowHead - 1;
        }
        if ((dataColCount = Math.min(colCountPerPage, colTail - colPageIndex * colCountPerPage - colHead - 1)) <= 0 && colPageIndex != 0) {
            return null;
        }
        int fromDataCol = colHead + 1 + colPageIndex * colCountPerPage;
        int dataRowCount = Math.min(rowCountPerPage, rowTail - rowPageIndex * rowCountPerPage - rowHead - 1);
        if (dataRowCount <= 0 && rowPageIndex != 0) {
            return null;
        }
        int fromDataRow = rowHead + 1 + rowPageIndex * rowCountPerPage;
        if (1 + colHead + dataColCount == colTail && 1 + rowHead + dataRowCount == rowTail) {
            return this;
        }
        GridData result = this.protoCreate();
        result.setDataClass(this.getDataClass());
        result.setColCount(1 + colHead + dataColCount + this.getColCount() - colTail);
        result.setRowCount(1 + rowHead + dataRowCount + this.getRowCount() - rowTail);
        result.setLockTopCol(colHead + 1);
        result.setLockTopRow(rowHead + 1);
        result.setLockBottomCol(colHead + dataColCount);
        result.setLockBottomRow(rowHead + dataRowCount);
        for (i = 1; i <= colHead; ++i) {
            result.setColWidths(i, this.getColWidths(i));
            result.setColAutoSize(i, this.getColAutoSize(i));
            result.setColVisible(i, this.getColVisible(i));
            result.setHideDataCol(i, this.getHideDataCol(i));
        }
        for (i = 0; i < dataColCount; ++i) {
            result.setColWidths(colHead + 1 + i, this.getColWidths(fromDataCol + i));
            result.setColAutoSize(colHead + 1 + i, this.getColAutoSize(fromDataCol + i));
            result.setColVisible(colHead + 1 + i, this.getColVisible(fromDataCol + i));
            result.setHideDataCol(colHead + 1 + i, this.getHideDataCol(fromDataCol + i));
        }
        for (i = colTail; i < this.getColCount(); ++i) {
            result.setColWidths(colHead + 1 + dataColCount + i - colTail, this.getColWidths(i));
            result.setColAutoSize(colHead + 1 + dataColCount + i - colTail, this.getColAutoSize(i));
            result.setColVisible(colHead + 1 + dataColCount + i - colTail, this.getColVisible(i));
            result.setHideDataCol(colHead + 1 + dataColCount + i - colTail, this.getHideDataCol(i));
        }
        for (i = 1; i <= rowHead; ++i) {
            result.setRowHeights(i, this.getRowHeights(i));
            result.setRowAutoSize(i, this.getRowAutoSize(i));
            result.setRowVisible(i, this.getRowVisible(i));
            result.setHideDataRow(i, this.getHideDataRow(i));
        }
        for (i = 0; i < dataRowCount; ++i) {
            result.setRowHeights(rowHead + 1 + i, this.getRowHeights(fromDataRow + i));
            result.setRowAutoSize(rowHead + 1 + i, this.getRowAutoSize(fromDataRow + i));
            result.setRowVisible(rowHead + 1 + i, this.getRowVisible(fromDataRow + i));
            result.setHideDataRow(rowHead + 1 + i, this.getHideDataRow(fromDataRow + i));
        }
        for (i = rowTail; i < this.getRowCount(); ++i) {
            result.setRowHeights(rowHead + 1 + dataRowCount + i - rowTail, this.getRowHeights(i));
            result.setRowAutoSize(rowHead + 1 + dataRowCount + i - rowTail, this.getRowAutoSize(i));
            result.setRowVisible(rowHead + 1 + dataRowCount + i - rowTail, this.getRowVisible(i));
            result.setHideDataRow(rowHead + 1 + dataRowCount + i - rowTail, this.getHideDataRow(i));
        }
        if (colHead > 0 && rowHead > 0) {
            result.copyFrom(this, 1, 1, colHead, rowHead, 1, 1);
        }
        if (dataColCount > 0 && rowHead > 0) {
            result.copyFrom(this, fromDataCol, 1, fromDataCol + dataColCount - 1, rowHead, colHead + 1, 1);
        }
        if (colTail < this.getColCount() && rowHead > 0) {
            result.copyFrom(this, colTail, 1, this.getColCount() - 1, rowHead, colHead + 1 + dataColCount, 1);
        }
        if (colHead > 0 && dataRowCount > 0) {
            result.copyFrom(this, 1, fromDataRow, colHead, fromDataRow + dataRowCount - 1, 1, rowHead + 1);
        }
        if (dataColCount > 0 && dataRowCount > 0) {
            result.copyFrom(this, fromDataCol, fromDataRow, fromDataCol + dataColCount - 1, fromDataRow + dataRowCount - 1, colHead + 1, rowHead + 1);
        }
        if (colTail < this.getColCount() && dataRowCount > 0) {
            result.copyFrom(this, colTail, fromDataRow, this.getColCount() - 1, fromDataRow + dataRowCount - 1, colHead + 1 + dataColCount, rowHead + 1);
        }
        if (colHead > 0 && rowTail < this.getRowCount()) {
            result.copyFrom(this, 1, rowTail, colHead, this.getRowCount() - 1, 1, rowHead + 1 + dataRowCount);
        }
        if (dataColCount > 0 && rowTail < this.getRowCount()) {
            result.copyFrom(this, fromDataCol, rowTail, fromDataCol + dataColCount - 1, this.getRowCount() - 1, colHead + 1, rowHead + 1 + dataRowCount);
        }
        if (colTail < this.getColCount() && rowTail < this.getRowCount()) {
            result.copyFrom(this, colTail, rowTail, this.getColCount() - 1, this.getRowCount() - 1, colHead + 1 + dataColCount, rowHead + 1 + dataRowCount);
        }
        return result;
    }

    public GridData protoCreate() {
        GridData result = new GridData(this.getOptions());
        result.cellColors = (IntList)this.cellColors.clone();
        result.edgeColors = (IntList)this.edgeColors.clone();
        result.fontNames = (GridFontNames)this.fontNames.clone();
        result.fontColors = (IntList)this.fontColors.clone();
        result.fontSizes = (IntList)this.fontSizes.clone();
        return result;
    }

    public int getDataRowCount() {
        int top = this.scrollTopRow > 0 ? this.scrollTopRow : 1;
        int bottom = this.scrollBottomRow > 0 ? this.scrollBottomRow : this.getRowCount() - 1;
        int count = 0;
        for (int i = top; i <= bottom; ++i) {
            if (!this.getRowVisible(i)) continue;
            ++count;
        }
        return count;
    }

    public int getDataColCount() {
        int left = this.scrollTopCol > 0 ? this.scrollTopCol : 1;
        int right = this.scrollBottomCol > 0 ? this.scrollBottomCol : this.getColCount() - 1;
        int count = 0;
        for (int i = left; i <= right; ++i) {
            if (!this.getColVisible(i)) continue;
            ++count;
        }
        return count;
    }

    public ExtendedDatas getExtDatas() {
        return this.extDatas;
    }

    public int getHorzOffset() {
        return this.horzOffset;
    }

    public void setHorzOffset(int offset) {
        this.horzOffset = offset;
    }

    public int getVertOffset() {
        return this.vertOffset;
    }

    public void setVertOffset(int offset) {
        this.vertOffset = offset;
    }

    public boolean getHorzCenter() {
        return this.horzCenter;
    }

    public void setHorzCenter(boolean value) {
        this.horzCenter = value;
    }

    public boolean getVertCenter() {
        return this.vertCenter;
    }

    public void setVertCenter(boolean value) {
        this.vertCenter = value;
    }

    private byte toEdgeByte(int edgeStyle, int edgeColor) {
        int v = edgeStyle & 0xF;
        int bgr = GridCell.RGB2BGR(edgeColor);
        int index = this.edgeColors.indexOf(bgr);
        if (index < 0) {
            index = this.edgeColors.count() < 15 ? this.edgeColors.add(bgr) : 0;
        }
        return (byte)(v |= index << 4 & 0xF0);
    }

    public void setInsideVertEdge(int col, int row, int edgeStyle, int edgeColor) {
        CellField cf = this.getCellField(col, row);
        if (cf == null || cf.left == cf.right) {
            return;
        }
        byte edge = this.toEdgeByte(edgeStyle, edgeColor);
        for (int c = cf.left; c < cf.right; ++c) {
            for (int r = cf.top; r <= cf.bottom; ++r) {
                byte[] cellData = this.internalGetCellPropData(c, r);
                cellData = (byte[])cellData.clone();
                cellData[9] = edge;
                this.internalSetCell(c, r, cellData);
            }
        }
    }

    public void setInsideHorzEdge(int col, int row, int edgeStyle, int edgeColor) {
        CellField cf = this.getCellField(col, row);
        if (cf == null || cf.top == cf.bottom) {
            return;
        }
        byte edge = this.toEdgeByte(edgeStyle, edgeColor);
        for (int r = cf.top; r < cf.bottom; ++r) {
            for (int c = cf.left; c <= cf.right; ++c) {
                byte[] cellData = this.internalGetCellPropData(c, r);
                cellData = (byte[])cellData.clone();
                cellData[10] = edge;
                this.internalSetCell(c, r, cellData);
            }
        }
    }

    public static GridData bytesToGrid(byte[] data) {
        if (data == null) {
            return null;
        }
        GridData gd = new GridData();
        try {
            com.jiuqi.np.util.MemStream s = new com.jiuqi.np.util.MemStream();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            gd.loadFromStream(s);
        }
        catch (Exception ex) {
            LogHelper.error(ex);
            gd = null;
        }
        return gd;
    }

    public static GridData bytesToGrid(byte[] data, String encodeCharset) {
        if (data == null) {
            return null;
        }
        GridData gd = new GridData();
        try {
            com.jiuqi.np.util.MemStream s = new com.jiuqi.np.util.MemStream();
            s.setUseEncode(true);
            s.setCharset(encodeCharset);
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            gd.loadFromStream(s);
        }
        catch (Exception ex) {
            LogHelper.error(ex);
            gd = null;
        }
        return gd;
    }

    public static byte[] gridToBytes(GridData grid) {
        if (grid == null) {
            return null;
        }
        MemStream store = new MemStream();
        try {
            grid.saveToStream((Stream)store);
            return store.getBytes();
        }
        catch (StreamException ex) {
            LogHelper.error((Exception)((Object)ex));
            return null;
        }
    }

    public static byte[] gridToBytes(GridData grid, String encodeCharset) {
        if (grid == null) {
            return null;
        }
        com.jiuqi.np.util.MemStream store = new com.jiuqi.np.util.MemStream();
        try {
            store.setUseEncode(true);
            store.setCharset(encodeCharset);
            grid.saveToStream(store);
            return store.getBytes();
        }
        catch (StreamException ex) {
            LogHelper.error((Exception)((Object)ex));
            return null;
        }
    }

    public static GridData base64ToGrid(String base64Str) {
        byte[] data = Base64.base64ToByteArray((String)base64Str);
        return GridData.bytesToGrid(data);
    }

    public static GridData base64ToGrid(String base64Str, String encodeCharset) {
        byte[] data = Base64.base64ToByteArray((String)base64Str);
        return GridData.bytesToGrid(data, encodeCharset);
    }

    public static String gridToBase64(GridData grid) {
        byte[] data = GridData.gridToBytes(grid);
        return Base64.byteArrayToBase64((byte[])data);
    }

    public static String gridToBase64(GridData grid, String encodeCharset) {
        byte[] data = GridData.gridToBytes(grid, encodeCharset);
        return Base64.byteArrayToBase64((byte[])data);
    }

    public void convertData(CellConvertor convertor) {
        for (int col = 0; col < this.getColCount(); ++col) {
            for (int row = 0; row < this.getRowCount(); ++row) {
                GridCell cell;
                CellField cf = this.expandCell(col, row);
                if (cf.left != col || cf.top != row || !convertor.convert(this, cell = this.getCellEx(col, row))) continue;
                this.setCell(col, row, cell);
            }
        }
    }

    public int getOptions() {
        return this.options;
    }

    public void setOptions(int value) {
        this.options = value;
        this.rects.setAutoMerge((value & 1) != 0);
    }

    private final class GridCopyConvertor {
        GridData src;
        int[] fontNameMaps;
        int[] fontSizeMaps;
        int[] fontColorMaps;
        int[] cellColorMaps;
        int[] edgeColorMaps;
        private Stack freeFontNames;
        private Stack freeFontSizes;
        private Stack freeFontColors;
        private Stack freeCellColors;
        private Stack freeEdgeColors;

        public GridCopyConvertor(GridData src) {
            this.src = src;
            this.mergeFontNames(src.fontNames);
            this.fontSizeMaps = this.mergeList(src.fontSizes, GridData.this.fontSizes);
            this.fontColorMaps = this.mergeList(src.fontColors, GridData.this.fontColors);
            this.cellColorMaps = this.mergeList(src.cellColors, GridData.this.cellColors);
            this.edgeColorMaps = this.mergeList(src.edgeColors, GridData.this.edgeColors);
        }

        private void mergeFontNames(GridFontNames srcFontNames) {
            boolean diff = false;
            int[] maps = new int[srcFontNames.count()];
            for (int i = 0; i < srcFontNames.count(); ++i) {
                int index;
                if (i < GridData.this.fontNames.count() && GridData.this.fontNames.getFont(i).equals(srcFontNames.getFont(i))) {
                    maps[i] = i;
                    continue;
                }
                maps[i] = index = GridData.this.fontNames.indexOfFont(srcFontNames.getFont(i));
                if (i == index) continue;
                diff = true;
            }
            this.fontNameMaps = (int[])(diff ? maps : null);
        }

        private int[] mergeList(IntList srcList, IntList destList) {
            boolean diff = false;
            int[] maps = new int[srcList.count()];
            for (int i = 0; i < srcList.count(); ++i) {
                int index;
                if (i < destList.count() && destList.get(i) == srcList.get(i)) {
                    maps[i] = i;
                    continue;
                }
                maps[i] = index = destList.indexOf(srcList.get(i));
                if (i == index) continue;
                diff = true;
            }
            return (int[])(diff ? maps : null);
        }

        public void convert(GridCell cell) {
            int newIndex;
            int index;
            byte[] data = cell.getPropData();
            if (this.fontNameMaps != null) {
                index = data[4] & 0x3F;
                newIndex = this.mapFontName(index);
                data[4] = (byte)(data[4] & 0xC0 | newIndex & 0x3F);
            }
            if (this.fontSizeMaps != null) {
                index = data[5] & 0x3F;
                newIndex = this.mapFontSize(index);
                data[5] = (byte)(data[5] & 0xC0 | newIndex & 0x3F);
            }
            if (this.fontColorMaps != null) {
                index = data[6] >> 0 & 0x3F;
                newIndex = this.mapFontColor(index);
                data[6] = (byte)(data[6] & 0xC0 | newIndex & 0x3F);
            }
            if (this.cellColorMaps != null) {
                index = data[0];
                newIndex = this.mapCellColor(index);
                data[0] = (byte)newIndex;
            }
            if (this.edgeColorMaps != null) {
                index = data[9] >> 4 & 0xF;
                newIndex = this.mapEdgeColor(index);
                data[9] = (byte)(data[9] & 0xF | newIndex << 4 & 0xF0);
                index = data[10] >> 4 & 0xF;
                newIndex = this.mapEdgeColor(index);
                data[10] = (byte)(data[10] & 0xF | newIndex << 4 & 0xF0);
            }
        }

        private int mapCellColor(int index) {
            int newIndex;
            if (this.cellColorMaps[index] == -1) {
                if (this.freeCellColors().isEmpty()) {
                    if (GridData.this.cellColors.count() >= 64) {
                        newIndex = GridData.this.cellColors().indexOf(0xFFFFFF);
                        if (newIndex == -1) {
                            newIndex = 0;
                        }
                    } else {
                        newIndex = GridData.this.cellColors.add(this.src.cellColors.get(index));
                    }
                } else {
                    newIndex = (Integer)this.freeCellColors().pop();
                    GridData.this.cellColors.set(newIndex, this.src.cellColors.get(index));
                }
                this.cellColorMaps[index] = newIndex;
            } else {
                newIndex = this.cellColorMaps[index];
            }
            return newIndex;
        }

        private int mapFontColor(int index) {
            int newIndex;
            if (this.fontColorMaps[index] == -1) {
                if (this.freeFontColors().isEmpty()) {
                    newIndex = GridData.this.fontColors.count() >= 64 ? 0 : GridData.this.fontColors.add(this.src.fontColors.get(index));
                } else {
                    newIndex = (Integer)this.freeFontColors().pop();
                    GridData.this.fontColors.set(newIndex, this.src.fontColors.get(index));
                }
                this.fontColorMaps[index] = newIndex;
            } else {
                newIndex = this.fontColorMaps[index];
            }
            return newIndex;
        }

        private int mapFontSize(int index) {
            int newIndex;
            if (this.fontSizeMaps[index] == -1) {
                if (this.freeFontSizes().isEmpty()) {
                    newIndex = GridData.this.fontSizes.count() >= 64 ? 0 : GridData.this.fontSizes.add(this.src.fontSizes.get(index));
                } else {
                    newIndex = (Integer)this.freeFontSizes().pop();
                    GridData.this.fontSizes.set(newIndex, this.src.fontSizes.get(index));
                }
                this.fontSizeMaps[index] = newIndex;
            } else {
                newIndex = this.fontSizeMaps[index];
            }
            return newIndex;
        }

        private int mapFontName(int index) {
            int newIndex;
            if (this.fontNameMaps[index] == -1) {
                if (this.freeFontNames().isEmpty()) {
                    newIndex = GridData.this.fontNames.count() >= 64 ? 0 : GridData.this.fontNames.addFont(this.src.fontNames.getFont(index));
                } else {
                    newIndex = (Integer)this.freeFontNames().pop();
                    GridData.this.fontNames.setFont(newIndex, this.src.fontNames.getFont(index));
                }
                this.fontNameMaps[index] = newIndex;
            } else {
                newIndex = this.fontNameMaps[index];
            }
            return newIndex;
        }

        private int mapEdgeColor(int index) {
            int newIndex;
            if (this.edgeColorMaps[index] == -1) {
                if (this.freeEdgeColors().isEmpty()) {
                    newIndex = GridData.this.edgeColors.count() >= 64 ? 0 : GridData.this.edgeColors.add(this.src.edgeColors.get(index));
                } else {
                    newIndex = (Integer)this.freeEdgeColors().pop();
                    GridData.this.edgeColors.set(newIndex, this.src.edgeColors.get(index));
                }
                this.edgeColorMaps[index] = newIndex;
            } else {
                newIndex = this.edgeColorMaps[index];
            }
            return newIndex;
        }

        private Stack freeFontNames() {
            if (this.freeFontNames == null) {
                int i;
                boolean[] flags = new boolean[GridData.this.fontNames.count()];
                for (i = 0; i < GridData.this.propList.count(); ++i) {
                    byte[] data = GridData.this.propList.get(i);
                    int index = data[4] & 0x3F;
                    flags[index] = true;
                }
                this.freeFontNames = new Stack();
                for (i = 0; i < flags.length; ++i) {
                    if (flags[i]) continue;
                    this.freeFontNames.push(new Integer(i));
                }
            }
            return this.freeFontNames;
        }

        private Stack freeFontSizes() {
            if (this.freeFontSizes == null) {
                int i;
                boolean[] flags = new boolean[GridData.this.fontSizes.count()];
                for (i = 0; i < GridData.this.propList.count(); ++i) {
                    byte[] data = GridData.this.propList.get(i);
                    int index = data[5] & 0x3F;
                    flags[index] = true;
                }
                this.freeFontSizes = new Stack();
                for (i = 0; i < flags.length; ++i) {
                    if (flags[i]) continue;
                    this.freeFontSizes.push(new Integer(i));
                }
            }
            return this.freeFontSizes;
        }

        private Stack freeFontColors() {
            if (this.freeFontColors == null) {
                int i;
                boolean[] flags = new boolean[GridData.this.fontColors.count()];
                for (i = 0; i < GridData.this.propList.count(); ++i) {
                    byte[] data = GridData.this.propList.get(i);
                    int index = data[6] >> 0 & 0x3F;
                    flags[index] = true;
                }
                this.freeFontColors = new Stack();
                for (i = 0; i < flags.length; ++i) {
                    if (flags[i]) continue;
                    this.freeFontColors.push(new Integer(i));
                }
            }
            return this.freeFontColors;
        }

        private Stack freeCellColors() {
            if (this.freeCellColors == null) {
                int i;
                boolean[] flags = new boolean[GridData.this.cellColors.count()];
                for (i = 0; i < GridData.this.propList.count(); ++i) {
                    byte[] data = GridData.this.propList.get(i);
                    flags[data[0]] = true;
                }
                this.freeCellColors = new Stack();
                for (i = 0; i < flags.length; ++i) {
                    if (flags[i]) continue;
                    this.freeCellColors.push(new Integer(i));
                }
            }
            return this.freeCellColors;
        }

        private Stack freeEdgeColors() {
            if (this.freeEdgeColors == null) {
                int i;
                boolean[] flags = new boolean[GridData.this.edgeColors.count()];
                for (i = 0; i < GridData.this.propList.count(); ++i) {
                    byte[] data = GridData.this.propList.get(i);
                    int index = data[9] >> 4 & 0xF;
                    flags[index] = true;
                    index = data[10] >> 4 & 0xF;
                    flags[index] = true;
                }
                this.freeEdgeColors = new Stack();
                for (i = 0; i < flags.length; ++i) {
                    if (flags[i]) continue;
                    this.freeEdgeColors.push(new Integer(i));
                }
            }
            return this.freeEdgeColors;
        }
    }
}

