/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.engine.build.folding;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.result.FoldingInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import java.util.HashMap;
import java.util.Map;

public class FoldingAnalyzer {
    private final EngineWorksheet workSheet;
    private final Map<CellField, FoldingInfo> foldingInfos;

    public FoldingAnalyzer(EngineWorksheet workSheet) {
        this.workSheet = workSheet;
        this.foldingInfos = new HashMap<CellField, FoldingInfo>();
    }

    public FoldingInfo scanFolding(int col, int row) throws ReportBuildException {
        int startRow;
        GridData grid = this.workSheet.getResultGrid().getGridData();
        CellValue cellValue = (CellValue)grid.getObj(col, row);
        if (!cellValue.isFoldable()) {
            return null;
        }
        CellField cellField = grid.expandCell(col, row);
        FoldingInfo foldingInfo = this.foldingInfos.get(cellField);
        if (foldingInfo != null) {
            return foldingInfo;
        }
        AxisDataNode dataValue = cellValue._masterValue;
        int left = cellField.left;
        int nextRow = startRow = cellField.bottom + 1;
        while (nextRow < grid.getRowCount()) {
            AxisDataNode nextData;
            CellValue nextValue;
            CellField nextField = grid.expandCell(left, nextRow);
            if (cellField.left != nextField.left || cellField.right != nextField.right || (nextValue = (CellValue)grid.getObj(left, nextRow)) == null || nextValue._masterValue == null || cellValue._bindingInfo != nextValue._bindingInfo || (nextData = nextValue._masterValue).getLevel() <= dataValue.getLevel()) break;
            nextRow = nextField.bottom + 1;
        }
        foldingInfo = nextRow <= startRow ? new FoldingInfo() : new FoldingInfo(startRow, nextRow - 1);
        int expandingLevel = cellValue._bindingInfo.getCellMap().getHierarchyLevel();
        boolean isAllLevels = expandingLevel == 0 || expandingLevel == -1;
        foldingInfo.setExpanding(isAllLevels || dataValue.getLevel() <= expandingLevel);
        return foldingInfo;
    }
}

