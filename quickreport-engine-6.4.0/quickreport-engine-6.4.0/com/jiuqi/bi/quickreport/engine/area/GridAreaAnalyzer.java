/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.FixedArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.area.RegionKey;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.adjust.PagingBuilder;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class GridAreaAnalyzer {
    private ReportWorkbook workbook;
    private ReportContext context;
    private List<GridArea> areas = new ArrayList<GridArea>();

    public ReportWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(ReportWorkbook workbook) {
        this.workbook = workbook;
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public void analyse() throws ReportAreaExpcetion {
        for (int i = 0; i < this.workbook.sheetSize(); ++i) {
            ReportWorksheet worksheet = this.workbook.getSheet(i);
            try {
                this.workbook.setActiveWorksheet(worksheet.name());
            }
            catch (ReportExpressionException e) {
                throw new ReportAreaExpcetion(e);
            }
            this.scanWorksheet(worksheet);
        }
        if (this.context.getReport().getPageInfo().getPageMode() == PageMode.DATASET) {
            this.scanPagingDataSets();
        }
    }

    public List<GridArea> getAreas() {
        return this.areas;
    }

    private void scanWorksheet(ReportWorksheet worksheet) throws ReportAreaExpcetion {
        LinkedList<CellBindingInfo> sheetCells = new LinkedList<CellBindingInfo>();
        LinkedList<CellBindingInfo> masterCells = new LinkedList<CellBindingInfo>();
        this.collectSheetCells(worksheet, sheetCells, masterCells);
        this.scanTieredCells(worksheet, masterCells);
        List<ExpandingArea> expandingAreas = this.buildExpandingFrame(worksheet, masterCells);
        this.scanCellMaps(worksheet, expandingAreas, sheetCells);
        this.areas.addAll(expandingAreas);
        this.createFixedArea(worksheet, sheetCells);
    }

    private void collectSheetCells(ReportWorksheet worksheet, List<CellBindingInfo> sheetCells, List<CellBindingInfo> masterCells) throws ReportAreaExpcetion {
        HashMap<RegionKey, CellBindingInfo> regionFinder = new HashMap<RegionKey, CellBindingInfo>();
        GridData grid = worksheet.getGridData();
        for (int row = 1; row < grid.getRowCount(); ++row) {
            for (int col = 1; col < grid.getColCount(); ++col) {
                CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(col, row);
                if (bindingInfo == null) continue;
                if (bindingInfo.getCellMap() != null && bindingInfo.getCellMap().getExpandMode() != ExpandMode.NONE) {
                    RegionKey key = new RegionKey(worksheet.name(), bindingInfo.getCellMap().getExpandRegion());
                    CellBindingInfo prev = (CellBindingInfo)regionFinder.get(key);
                    if (prev != null) {
                        throw new ReportAreaExpcetion("\u5728\u9875\u7b7e\u201c" + worksheet.name() + "\u201d\u4e2d\u68c0\u6d4b\u5230\u76f8\u540c\u8303\u56f4\uff08" + bindingInfo.getCellMap().getExpandRegion() + "\uff09\u7684\u6d6e\u52a8\u533a\u57df\uff1a" + prev.getCellMap().getPosition() + ", " + bindingInfo.getCellMap().getPosition());
                    }
                    masterCells.add(bindingInfo);
                    regionFinder.put(key, bindingInfo);
                    continue;
                }
                sheetCells.add(bindingInfo);
            }
        }
    }

    private void scanTieredCells(ReportWorksheet worksheet, List<CellBindingInfo> masterCells) throws ReportAreaExpcetion {
        for (CellBindingInfo masterCell : masterCells) {
            if (masterCell.getCellMap().getHierarchyMode() != HierarchyMode.TIERED) continue;
            if (masterCell.getCellMap().getExpandMode() == ExpandMode.ROWEXPANDING) {
                this.scanTieredRows(worksheet, masterCell);
                continue;
            }
            if (masterCell.getCellMap().getExpandMode() != ExpandMode.COLEXPANDING) continue;
            this.scanTieredCols(worksheet, masterCell);
        }
    }

    private void scanTieredRows(ReportWorksheet worksheet, CellBindingInfo masterCell) throws ReportAreaExpcetion {
        CellMap cellMap = masterCell.getCellMap();
        Region region = cellMap.getExpandRegion();
        CellField field = worksheet.getGridData().expandCell(cellMap.getPosition().col(), cellMap.getPosition().row());
        if (field.top > region.top() || field.bottom < region.bottom()) {
            throw new ReportAreaExpcetion("\u5206\u7ea7\u663e\u793a\u5355\u5143\u683c'" + masterCell.getPosition() + "'\u5fc5\u987b\u5408\u5e76\u6d6e\u52a8\u533a\u57df'" + region + "'\u7684\u884c\u8303\u56f4");
        }
        ArrayList<CellBindingInfo> rowTieredCells = new ArrayList<CellBindingInfo>();
        for (CellBindingInfo rowTieredCell : rowTieredCells) {
            Region prevRegion = rowTieredCell.getCellMap().getExpandRegion();
            if (!prevRegion.contains(region)) continue;
            throw new ReportAreaExpcetion("\u5355\u5143\u683c'" + rowTieredCell.getPosition() + "'\u4e0e'" + masterCell.getPosition() + "'\u7684\u884c\u6d6e\u52a8\u8303\u56f4\u51b2\u7a81\uff0c\u65e0\u6cd5\u540c\u65f6\u8bbe\u7f6e\u5206\u7ea7\u663e\u793a");
        }
        rowTieredCells.add(masterCell);
    }

    private void scanTieredCols(ReportWorksheet worksheet, CellBindingInfo masterCell) throws ReportAreaExpcetion {
        CellMap cellMap = masterCell.getCellMap();
        Region region = cellMap.getExpandRegion();
        CellField field = worksheet.getGridData().expandCell(cellMap.getPosition().col(), cellMap.getPosition().row());
        if (field.left > region.left() || field.right < region.right() || field.top > region.top()) {
            throw new ReportAreaExpcetion("\u5206\u7ea7\u663e\u793a\u5355\u5143\u683c'" + masterCell.getPosition() + "'\u5fc5\u987b\u5408\u5e76\u6d6e\u52a8\u533a\u57df'" + region + "'\u7684\u5217\u8303\u56f4\u5e76\u4e14\u7f6e\u4e8e\u533a\u57df\u7684\u6700\u9876\u90e8\u4f4d\u7f6e");
        }
        ArrayList<CellBindingInfo> colTieredCells = new ArrayList<CellBindingInfo>();
        for (CellBindingInfo colTieredCell : colTieredCells) {
            Region prevRegion = colTieredCell.getCellMap().getExpandRegion();
            if (!prevRegion.contains(region)) continue;
            throw new ReportAreaExpcetion("\u5355\u5143\u683c'" + colTieredCell.getPosition() + "'\u4e0e'" + masterCell.getPosition() + "'\u7684\u680f\u6d6e\u52a8\u8303\u56f4\u51b2\u7a81\uff0c\u65e0\u6cd5\u540c\u65f6\u8bbe\u7f6e\u5206\u7ea7\u663e\u793a");
        }
        colTieredCells.add(masterCell);
    }

    private List<ExpandingArea> buildExpandingFrame(ReportWorksheet worksheet, List<CellBindingInfo> masterCells) throws ReportAreaExpcetion {
        ArrayList<ExpandingArea> expandingAreas = new ArrayList<ExpandingArea>();
        while (!masterCells.isEmpty()) {
            ExpandingArea area = this.createExpandingArea(worksheet, masterCells);
            expandingAreas.add(area);
        }
        return expandingAreas;
    }

    private ExpandingArea createExpandingArea(ReportWorksheet worksheet, List<CellBindingInfo> masterCells) throws ReportAreaExpcetion {
        ExpandingArea expandingArea = new ExpandingArea();
        expandingArea.setSheetName(worksheet.name());
        while (this.findRelated(expandingArea, masterCells)) {
        }
        expandingArea.buildMasterRestrictions();
        return expandingArea;
    }

    private boolean findRelated(ExpandingArea expandingArea, List<CellBindingInfo> masterCells) throws ReportAreaExpcetion {
        boolean found = false;
        Iterator<CellBindingInfo> i = masterCells.iterator();
        while (i.hasNext()) {
            CellBindingInfo bindingInfo = i.next();
            if (!expandingArea.tryAddMaster(this.context, bindingInfo)) continue;
            bindingInfo.setOwnerArea(expandingArea);
            i.remove();
            found = true;
        }
        return found;
    }

    private void scanCellMaps(ReportWorksheet worksheet, List<ExpandingArea> expandingAreas, List<CellBindingInfo> sheetCells) throws ReportAreaExpcetion {
        for (ExpandingArea expandingArea : expandingAreas) {
            Iterator<CellBindingInfo> i = sheetCells.iterator();
            while (i.hasNext()) {
                CellBindingInfo bindingInfo = i.next();
                if (!expandingArea.tryAddCell(bindingInfo)) continue;
                bindingInfo.setOwnerArea(expandingArea);
                i.remove();
            }
            expandingArea.validate();
            this.checkExpandingArea(worksheet, expandingArea);
        }
    }

    private void createFixedArea(ReportWorksheet worksheet, List<CellBindingInfo> sheetCells) {
        if (sheetCells.isEmpty()) {
            return;
        }
        FixedArea fixedArea = new FixedArea();
        fixedArea.setSheetName(worksheet.name());
        for (CellBindingInfo bindingInfo : sheetCells) {
            bindingInfo.setOwnerArea(fixedArea);
            fixedArea.getCells().add(bindingInfo);
        }
        this.areas.add(fixedArea);
    }

    private void checkExpandingArea(ReportWorksheet worksheet, ExpandingArea area) throws ReportAreaExpcetion {
        if (!area.getColAxis().isEmpty()) {
            this.checkExpandingCols(worksheet, area);
        }
        if (!area.getRowAxis().isEmpty()) {
            this.checkExpandingRows(worksheet, area);
        }
        this.checkExpandingFilters(worksheet, area);
    }

    private void checkExpandingCols(ReportWorksheet worksheet, ExpandingArea area) throws ReportAreaExpcetion {
        GridData grid = worksheet.getGridData();
        Region colRegion = area.getColAxis().getRegion();
        for (int col = colRegion.left(); col <= colRegion.right(); ++col) {
            if (grid.getObj(col, 0) == null) continue;
            throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e\u201d" + area.getSheetName() + "\u201c\u8bbe\u7f6e\u9519\u8bef\uff0c" + Position.nameOfCol((int)col) + "\u680f\u53e3\u5f84\u8303\u56f4\u5185\u5b58\u5728\u680f\u6d6e\u52a8\u533a\u57df\uff0c\u65e0\u6cd5\u8fdb\u884c\u9650\u5b9a\u3002");
        }
        Region overflowRegion = this.findOverflowRegion(grid, colRegion);
        if (overflowRegion != null) {
            throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e" + area.getSheetName() + "\u8bbe\u7f6e\u9519\u8bef\uff0c\u5408\u5e76\u5355\u5143\u683c" + overflowRegion + "\u8d85\u51fa\u4e86\u680f\u6d6e\u52a8\u533a\u57df" + colRegion + "\u7684\u8303\u56f4");
        }
    }

    private void checkExpandingRows(ReportWorksheet worksheet, ExpandingArea area) throws ReportAreaExpcetion {
        GridData grid = worksheet.getGridData();
        Region rowRegion = area.getRowAxis().getRegion();
        for (int row = rowRegion.top(); row <= rowRegion.bottom(); ++row) {
            if (grid.getObj(0, row) == null) continue;
            throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e\u201d" + area.getSheetName() + "\u201c\u8bbe\u7f6e\u9519\u8bef\uff0c" + row + "\u884c\u53e3\u5f84\u8303\u56f4\u5185\u5b58\u5728\u884c\u6d6e\u52a8\u533a\u57df\uff0c\u65e0\u6cd5\u8fdb\u884c\u9650\u5b9a\u3002");
        }
        Region overflowRegion = this.findOverflowRegion(grid, rowRegion);
        if (overflowRegion != null) {
            throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e" + area.getSheetName() + "\u8bbe\u7f6e\u9519\u8bef\uff0c\u5408\u5e76\u5355\u5143\u683c" + overflowRegion + "\u8d85\u51fa\u4e86\u884c\u6d6e\u52a8\u533a\u57df" + rowRegion + "\u7684\u8303\u56f4");
        }
    }

    private void checkExpandingFilters(ReportWorksheet worksheet, ExpandingArea area) throws ReportAreaExpcetion {
        GridData grid = worksheet.getGridData();
        for (ExpandingRegion region : area) {
            Position pos = region.getMasterCellPosition();
            if (grid.getObj(pos.col(), 0) != null) {
                throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e\u201d" + area.getSheetName() + "\u201c\u8bbe\u7f6e\u9519\u8bef\uff0c" + Position.nameOfCol((int)pos.col()) + "\u680f\u53e3\u5f84\u8303\u56f4\u5185\u5b58\u5728\u6d6e\u52a8\u533a\u57df\u7684\u4e3b\u63a7\u5355\u5143\u683c\uff0c\u65e0\u6cd5\u8fdb\u884c\u9650\u5b9a\u3002");
            }
            if (grid.getObj(0, pos.row()) == null) continue;
            throw new ReportAreaExpcetion("\u68c0\u67e5\u9875\u7b7e\u201d" + area.getSheetName() + "\u201c\u8bbe\u7f6e\u9519\u8bef\uff0c" + pos.row() + "\u884c\u53e3\u5f84\u8303\u56f4\u5185\u5b58\u5728\u6d6e\u52a8\u533a\u57df\u7684\u4e3b\u63a7\u5355\u5143\u683c\uff0c\u65e0\u6cd5\u8fdb\u884c\u9650\u5b9a\u3002");
        }
    }

    private Region findOverflowRegion(GridData grid, Region region) {
        for (int col = region.left(); col <= region.right(); ++col) {
            for (int row = region.top(); row <= region.bottom(); ++row) {
                CellField field = grid.merges().getMergeRect(col, row);
                if (field == null || field.left != col || field.top != row || region.contains(field.right, field.bottom)) continue;
                return new Region(field.left, field.top, field.right, field.bottom);
            }
        }
        return null;
    }

    private void scanPagingDataSets() throws ReportAreaExpcetion {
        PagingBuilder builder = new PagingBuilder(this.context);
        this.areas.stream().filter(area -> area.getSheetName().equalsIgnoreCase(this.context.getReport().getPrimarySheetName()) && area instanceof ExpandingArea).forEach(area -> builder.getAreas().add((ExpandingArea)area));
        try {
            builder.analyse();
        }
        catch (ReportBuildException e) {
            throw new ReportAreaExpcetion(e);
        }
    }
}

