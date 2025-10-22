/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nr.splittable.bean.Header;
import com.jiuqi.nr.splittable.bean.MergedCell;
import com.jiuqi.nr.splittable.other.LinkHeap;
import com.jiuqi.nr.splittable.util.ByteToGrid2Data;
import com.jiuqi.nr.splittable.util.Grid2DataToByte;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseSheet
implements Serializable {
    private static final long serialVersionUID = -5498078174442621061L;
    private final String uuid = UUID.randomUUID().toString();
    private Header headerRow;
    private Header headerCol;
    private int sheetRow;
    private int sheetCol;
    private String sheetTitle;
    private Grid2Data grid2Data;
    private CellObj[][] newCellXY;
    private CellObj[][] oldCellXY;
    private List<CellObj> newCellObjList;
    private List<CellObj> oldCellObjList;
    private List<CellObj> tableHeadColList;
    private List<MergedCell> tempMergedCells;
    private List<MergedCell> mergedCellList;
    private LinkHeap linkHeap;

    public String getUuid() {
        return this.uuid;
    }

    public BaseSheet() {
        this(null, null, null);
    }

    public BaseSheet(String sheetTitle, Grid2Data sheetGrid, List<CellObj> oldCellObjList) {
        this.sheetTitle = sheetTitle;
        this.grid2Data = sheetGrid;
        this.oldCellObjList = oldCellObjList;
        this.headerRow = new Header();
        this.headerCol = new Header();
    }

    @JsonIgnore
    public int getHeaderRowNum() {
        if (this.headerRow == null) {
            return 0;
        }
        return this.headerRow.getRow();
    }

    public int getSheetRow() {
        this.sheetRow = this.headerRow == null ? this.headerCol.getRow() + 1 : this.headerRow.getRow() + this.headerCol.getRow() + 1;
        return this.sheetRow;
    }

    public int getSheetCol() {
        this.sheetCol = this.tableHeadColList == null ? 0 : this.tableHeadColList.size() + 1;
        return this.sheetCol;
    }

    public void updateSheet() {
        if (this.grid2Data != null) {
            this.sheetRow = this.grid2Data.getRowCount();
            this.sheetCol = this.grid2Data.getColumnCount();
        }
    }

    public Header getHeaderCol() {
        if (this.headerCol != null && this.headerCol.getRow() == 0 && this.linkHeap != null) {
            this.headerCol.setRow(this.linkHeap.getLast().getPosy() - this.linkHeap.getFirst().getPosy() + 1);
        }
        return this.headerCol;
    }

    public void setHeaderCol(Header headerCol) {
        this.headerCol = headerCol;
    }

    public Header getHeaderRow() {
        if (this.headerRow != null && this.headerRow.getColumn() == 0 && this.linkHeap != null) {
            this.headerRow.setColumn(this.linkHeap.getLast().getPosx() - this.linkHeap.getFirst().getPosx() + 1);
        }
        return this.headerRow;
    }

    public void setHeaderRow(Header headerRow) {
        this.headerRow = headerRow;
    }

    public String getSheetTitle() {
        return this.sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    @JsonSerialize(using=Grid2DataToByte.class)
    public Grid2Data getGrid2Data() {
        return this.grid2Data;
    }

    @JsonDeserialize(using=ByteToGrid2Data.class)
    public void setGrid2Data(Grid2Data grid2Data) {
        this.grid2Data = grid2Data;
    }

    public CellObj[][] getNewCellXY() {
        return this.newCellXY;
    }

    public void setNewCellXY(CellObj[][] newCellXY) {
        this.newCellXY = newCellXY;
    }

    public CellObj[][] getOldCellXY() {
        return this.oldCellXY;
    }

    public void setOldCellXY(CellObj[][] oldCellXY) {
        this.oldCellXY = oldCellXY;
    }

    public List<CellObj> getTableHeadColList() {
        return this.tableHeadColList;
    }

    public void setTableHeadColList(List<CellObj> tableHeadColList) {
        this.tableHeadColList = tableHeadColList;
    }

    public LinkHeap getLinkHeap() {
        return this.linkHeap;
    }

    public void setLinkHeap(LinkHeap linkHeap) {
        this.linkHeap = linkHeap;
    }

    public List<CellObj> getOldCellObjList() {
        return this.oldCellObjList;
    }

    public void setOldCellObjList(List<CellObj> oldCellObjList) {
        this.oldCellObjList = oldCellObjList;
    }

    public List<MergedCell> getTempMergedCells() {
        return this.tempMergedCells;
    }

    public void setTempMergedCells(List<MergedCell> tempMergedCells) {
        this.tempMergedCells = tempMergedCells;
    }

    public List<MergedCell> getMergedCellList() {
        if (this.mergedCellList == null) {
            this.mergedCellList = new ArrayList<MergedCell>();
        }
        return this.mergedCellList;
    }

    public void setMergedCellList(List<MergedCell> mergedCellList) {
        this.mergedCellList = mergedCellList;
    }
}

