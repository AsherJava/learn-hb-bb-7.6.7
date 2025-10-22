/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.splittable.bean.BaseSheet;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nr.splittable.bean.RegionAndLink;
import com.jiuqi.nr.splittable.other.LinkHeap;
import com.jiuqi.nr.splittable.other.LinkObj;
import com.jiuqi.nr.splittable.util.ByteToGrid2Data;
import com.jiuqi.nr.splittable.util.Grid2DataToByte;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseBook
implements Serializable {
    private static final long serialVersionUID = 4060109044995348969L;
    private String bookKey;
    private String bookTitle;
    private Grid2Data grid2Data;
    private int rowCount;
    private int colCount;
    private List<BaseSheet> sheetList;
    private int[][] arr;
    private List<RegionAndLink> regionAndLinkList;
    private Map<String, String> sheetTitleMap;
    private List<LinkObj> allLinkObj;
    private List<LinkObj> fixedLinkObjList;
    private List<LinkObj> floatLinkObjList;
    private List<LinkHeap> allLinkHeapList;
    private List<LinkHeap> fixedLinkHeapList;
    private List<LinkHeap> floatLinkHeapList;
    private boolean hasNote;
    private int maxLinkBottom = -1;
    private int totalRow;
    private List<List<CellObj>> allOldCellObjList;

    public BaseBook() {
        this("key", "\u9ed8\u8ba4\u6807\u9898", new Grid2Data());
    }

    public BaseBook(String formKey, String title, Grid2Data grid2Data) {
        this.bookKey = formKey;
        this.bookTitle = title;
        this.grid2Data = grid2Data;
        this.rowCount = grid2Data.getRowCount();
        this.colCount = grid2Data.getColumnCount();
        this.arr = new int[this.rowCount][this.colCount];
        this.regionAndLinkList = new ArrayList<RegionAndLink>();
        this.fixedLinkObjList = new ArrayList<LinkObj>();
    }

    public String getBookKey() {
        return this.bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @JsonSerialize(using=Grid2DataToByte.class)
    public Grid2Data getGrid2Data() {
        return this.grid2Data;
    }

    @JsonDeserialize(using=ByteToGrid2Data.class)
    public void setGrid2Data(Grid2Data grid2Data) {
        this.grid2Data = grid2Data;
    }

    public List<BaseSheet> getSheetList() {
        if (this.sheetList == null) {
            this.sheetList = new ArrayList<BaseSheet>();
        }
        return this.sheetList;
    }

    public void setSheetList(List<BaseSheet> sheetList) {
        this.sheetList = sheetList;
    }

    public int[][] getArr() {
        return this.arr;
    }

    public void setArr(int[][] arr) {
        this.arr = arr;
    }

    public List<RegionAndLink> getRegionAndLinkList() {
        if (this.regionAndLinkList == null) {
            return new ArrayList<RegionAndLink>();
        }
        return this.regionAndLinkList;
    }

    public void setRegionAndLinkList(List<RegionAndLink> regionAndLinkList) {
        this.regionAndLinkList = regionAndLinkList;
    }

    public List<LinkObj> getAllLinkObj() {
        if (this.allLinkObj == null) {
            this.allLinkObj = new ArrayList<LinkObj>();
            if (this.fixedLinkObjList != null) {
                this.allLinkObj.addAll(this.fixedLinkObjList);
            }
            if (this.floatLinkObjList != null) {
                this.allLinkObj.addAll(this.floatLinkObjList);
            }
            return this.allLinkObj;
        }
        return this.allLinkObj;
    }

    public void setAllLinkObj(List<LinkObj> allLinkObj) {
        this.allLinkObj = allLinkObj;
    }

    public List<LinkObj> getFixedLinkObjList() {
        return this.fixedLinkObjList;
    }

    public void setFixedLinkObjList(List<LinkObj> fixedLinkObjList) {
        this.fixedLinkObjList = fixedLinkObjList;
    }

    public List<LinkObj> getFloatLinkObjList() {
        return this.floatLinkObjList;
    }

    public void setFloatLinkObjList(List<LinkObj> floatLinkObjList) {
        this.floatLinkObjList = floatLinkObjList;
    }

    public List<LinkHeap> getFixedLinkHeapList() {
        return this.fixedLinkHeapList;
    }

    public void setFixedLinkHeapList(List<LinkHeap> fixedLinkHeapList) {
        this.fixedLinkHeapList = fixedLinkHeapList;
    }

    public List<LinkHeap> getFloatLinkHeapList() {
        return this.floatLinkHeapList;
    }

    public void setFloatLinkHeapList(List<LinkHeap> floatLinkHeapList) {
        this.floatLinkHeapList = floatLinkHeapList;
    }

    public List<LinkHeap> getAllLinkHeapList() {
        return this.allLinkHeapList;
    }

    public void setAllLinkHeapList(List<LinkHeap> allLinkHeapList) {
        this.allLinkHeapList = allLinkHeapList;
    }

    public boolean isHasNote() {
        return this.hasNote;
    }

    public void setHasNote(boolean hasNote) {
        this.hasNote = hasNote;
    }

    public int getMaxLinkBottom() {
        return this.maxLinkBottom;
    }

    public void setMaxLinkBottom(int maxLinkBottom) {
        this.maxLinkBottom = maxLinkBottom;
    }

    public Map<String, String> getSheetTitleMap() {
        if (this.sheetTitleMap == null) {
            this.sheetTitleMap = new HashMap<String, String>();
        }
        return this.sheetTitleMap;
    }

    public void setSheetTitleMap(Map<String, String> sheetTitleMap) {
        this.sheetTitleMap = sheetTitleMap;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return this.colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public List<List<CellObj>> getAllOldCellObjList() {
        if (this.sheetList != null) {
            this.allOldCellObjList = new ArrayList<List<CellObj>>();
            for (BaseSheet baseSheet : this.sheetList) {
                this.allOldCellObjList.add(baseSheet.getOldCellObjList());
            }
        }
        return this.allOldCellObjList;
    }

    public void setAllOldCellObjList(List<List<CellObj>> allOldCellObjList) {
        this.allOldCellObjList = allOldCellObjList;
    }

    public int getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }
}

