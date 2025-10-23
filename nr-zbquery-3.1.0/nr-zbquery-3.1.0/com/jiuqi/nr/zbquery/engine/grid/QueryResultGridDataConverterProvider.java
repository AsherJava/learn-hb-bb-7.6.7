/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.result.CellResultInfo
 *  com.jiuqi.bi.quickreport.engine.result.FoldingInfo
 *  com.jiuqi.nvwa.cellbook.converter.ICellBookGridDataConverterProvider
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.engine.grid;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.FoldingInfo;
import com.jiuqi.nr.zbquery.engine.grid.TreeInfo;
import com.jiuqi.nvwa.cellbook.converter.ICellBookGridDataConverterProvider;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import org.json.JSONObject;

public class QueryResultGridDataConverterProvider
implements ICellBookGridDataConverterProvider {
    private final JSONObject treeInfoJson = new JSONObject();

    public GridData beforeGridDataToCellSheet(GridData gridData, CellSheet cellSheet) {
        return gridData;
    }

    public CellSheet afterGridDataToCellSheet(GridData gridData, CellSheet cellSheet) {
        return cellSheet;
    }

    public GridCell beforeGridCellToCell(GridCell gridCell, Cell cell) {
        if (gridCell.getType() == 4) {
            gridCell.setCanModify(true);
            gridCell.setCanWrite(true);
        } else {
            gridCell.setCanModify(false);
        }
        return gridCell;
    }

    public Cell afterGridCellToCell(GridCell gridCell, Cell cell) {
        CellResultInfo cellResultInfo;
        FoldingInfo foldingInfo;
        Object cellObj = gridCell.getCellObj();
        if (cellObj instanceof CellResultInfo && (foldingInfo = (cellResultInfo = (CellResultInfo)cellObj).getFoldingInfo()) != null) {
            this.afterGridCellToTreeCell(cellResultInfo, gridCell, cell);
        }
        return cell;
    }

    public CellSheet beforeCellSheetToGridData(GridData gridData, CellSheet cellSheet) {
        return cellSheet;
    }

    public GridData afterCellSheetToGridData(GridData gridData, CellSheet cellSheet) {
        return gridData;
    }

    public Cell beforeCellToGridCell(GridCell gridCell, Cell cell) {
        return cell;
    }

    public GridCell afterCellToGridCell(GridCell gridCell, Cell cell) {
        return gridCell;
    }

    private void afterGridCellToTreeCell(CellResultInfo cellResultInfo, GridCell gridCell, Cell cell) {
        FoldingInfo foldingInfo = cellResultInfo.getFoldingInfo();
        if (foldingInfo != null && !foldingInfo.isLeaf()) {
            TreeInfo treeInfo = new TreeInfo();
            treeInfo.setExpand(foldingInfo.isExpanding());
            treeInfo.setLeaf(foldingInfo.isLeaf());
            treeInfo.setStartRow(foldingInfo.getStartNum());
            treeInfo.setEndRow(foldingInfo.getEndNum());
            treeInfo.setColIndex(cell.getColIndex());
            cell.setDataTypeId("customTree");
            this.treeInfoJson.put(String.valueOf(cell.getRowIndex()), (Object)treeInfo.toJson());
            cell.setPersistenceData(treeInfo.toJson().toString());
        }
    }

    public JSONObject getTreeInfo() {
        return this.treeInfoJson;
    }
}

