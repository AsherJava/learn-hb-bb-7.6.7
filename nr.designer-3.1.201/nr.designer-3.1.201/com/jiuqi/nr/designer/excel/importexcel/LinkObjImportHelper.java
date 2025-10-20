/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.excel.importexcel;

import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.designer.excel.importexcel.RegionObjImportHelper;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkObjImportHelper {
    @Autowired
    private RegionObjImportHelper regionObjImportHelper;

    public void reNumberDataCells(ExcelImportContext importContext) {
        ArrayList<Integer> parellelCols = new ArrayList<Integer>();
        ArrayList<Integer> parellelLNum = new ArrayList<Integer>();
        HashSet<Integer> usedHNums = new HashSet<Integer>();
        HashSet<String> numberdCells = new HashSet<String>();
        int dftHNum = 1;
        List<Integer> fDataCols = this.initFDataCols(importContext);
        List<Integer> fDataRows = this.initFDataRows(importContext);
        int colIdx = 0;
        while (colIdx < fDataCols.size()) {
            parellelCols.clear();
            parellelLNum.clear();
            int col = fDataCols.get(colIdx);
            ++colIdx;
            parellelCols.add(col);
            DesignDataLinkDefine currLink = this.getFirstLinkByCol(col, importContext);
            int currLinkUserSetLNum = 0;
            int currLinkUserSetHNum = 0;
            int HNum = dftHNum;
            int LNum = 1;
            parellelLNum.add(LNum);
            while (colIdx < fDataCols.size()) {
                int aCol = fDataCols.get(colIdx);
                currLink = this.getFirstLinkByCol(aCol, importContext);
                if (aCol != col + 1 && currLinkUserSetHNum != HNum && currLinkUserSetLNum <= LNum) break;
                col = aCol;
                LNum = currLinkUserSetLNum > LNum ? currLinkUserSetLNum : ++LNum;
                parellelCols.add(col);
                parellelLNum.add(LNum);
                ++colIdx;
            }
            for (int rowIdx = 0; rowIdx < fDataRows.size(); ++rowIdx) {
                int row = fDataRows.get(rowIdx);
                if (usedHNums.contains(dftHNum)) {
                    int lastHNum = 0;
                    Iterator iterator = usedHNums.iterator();
                    while (iterator.hasNext()) {
                        int element = (Integer)iterator.next();
                        if (lastHNum >= element) continue;
                        lastHNum = element;
                    }
                    dftHNum = lastHNum + 1;
                }
                currLink = null;
                for (int x = 0; x < parellelCols.size() && (currLink = this.regionObjImportHelper.getLinkByPos(col = ((Integer)parellelCols.get(x)).intValue(), row, importContext)) == null; ++x) {
                }
                if (currLink == null) {
                    ++dftHNum;
                    continue;
                }
                HNum = dftHNum;
                boolean hNumConflict = false;
                do {
                    LNum = 0;
                    for (int idx = 0; idx < parellelCols.size(); ++idx) {
                        col = (Integer)parellelCols.get(idx);
                        currLink = this.regionObjImportHelper.getLinkByPos(col, row, importContext);
                        if (currLink == null) continue;
                        if (numberdCells.contains((LNum = currLinkUserSetLNum <= LNum ? ((Integer)parellelLNum.get(idx) > LNum ? (Integer)parellelLNum.get(idx) : LNum++) : currLinkUserSetLNum) + "_" + HNum)) {
                            hNumConflict = true;
                            if (HNum == dftHNum && usedHNums.contains(++dftHNum)) {
                                int lastHNum = 0;
                                Iterator iterator = usedHNums.iterator();
                                while (iterator.hasNext()) {
                                    int element = (Integer)iterator.next();
                                    if (lastHNum >= element) continue;
                                    lastHNum = element;
                                }
                                dftHNum = lastHNum + 1;
                            }
                            HNum = dftHNum;
                            continue;
                        }
                        hNumConflict = false;
                    }
                } while (hNumConflict);
                usedHNums.add(HNum);
                dftHNum = HNum + 1;
                LNum = 0;
                for (int x = 0; x < parellelCols.size(); ++x) {
                    col = (Integer)parellelCols.get(x);
                    currLink = this.regionObjImportHelper.getLinkByPos(col, row, importContext);
                    if (currLink == null) continue;
                    LNum = currLinkUserSetLNum <= LNum ? ((Integer)parellelLNum.get(x) > LNum ? (Integer)parellelLNum.get(x) : LNum++) : currLinkUserSetLNum;
                    numberdCells.add(LNum + "_" + HNum);
                    currLink.setColNum(LNum);
                    currLink.setRowNum(HNum);
                }
            }
        }
    }

    private List<Integer> initFDataCols(ExcelImportContext importContext) {
        ArrayList<Integer> fDataCols = new ArrayList<Integer>();
        Grid2Data grid2Data = importContext.getGrid2Data();
        int colCount = grid2Data.getColumnCount();
        int rowCount = grid2Data.getRowCount();
        block0: for (int col = 1; col <= colCount; ++col) {
            for (int row = 1; row < rowCount; ++row) {
                DesignDataLinkDefine linkObj = this.regionObjImportHelper.getLinkByPos(col, row, importContext);
                if (linkObj == null) continue;
                fDataCols.add(col);
                continue block0;
            }
        }
        return fDataCols;
    }

    private List<Integer> initFDataRows(ExcelImportContext importContext) {
        int row;
        int rowCount;
        ArrayList<Integer> fDataRows = new ArrayList<Integer>();
        Grid2Data grid2Data = importContext.getGrid2Data();
        int colCount = grid2Data.getColumnCount() - 1;
        int minRow = rowCount = grid2Data.getRowCount() - 1;
        block0: for (row = 1; row <= rowCount; ++row) {
            for (int col = 1; col < colCount; ++col) {
                DesignDataLinkDefine linkObj = this.regionObjImportHelper.getLinkByPos(col, row, importContext);
                if (linkObj == null) continue;
                if (minRow <= row) continue block0;
                minRow = row;
                continue block0;
            }
        }
        for (row = minRow; row <= rowCount; ++row) {
            fDataRows.add(row);
        }
        return fDataRows;
    }

    private DesignDataLinkDefine getFirstLinkByCol(int col, ExcelImportContext importContext) {
        Grid2Data grid2Data = importContext.getGrid2Data();
        int rowCount = grid2Data.getRowCount();
        for (int row = 1; row < rowCount; ++row) {
            DesignDataLinkDefine linkObj = this.regionObjImportHelper.getLinkByPos(col, row, importContext);
            if (linkObj == null) continue;
            return linkObj;
        }
        return null;
    }
}

