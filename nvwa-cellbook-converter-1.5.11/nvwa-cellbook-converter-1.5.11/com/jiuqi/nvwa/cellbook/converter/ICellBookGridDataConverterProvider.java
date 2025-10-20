/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 */
package com.jiuqi.nvwa.cellbook.converter;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellSheet;

public interface ICellBookGridDataConverterProvider {
    public GridData beforeGridDataToCellSheet(GridData var1, CellSheet var2);

    public CellSheet afterGridDataToCellSheet(GridData var1, CellSheet var2);

    public GridCell beforeGridCellToCell(GridCell var1, Cell var2);

    public Cell afterGridCellToCell(GridCell var1, Cell var2);

    public CellSheet beforeCellSheetToGridData(GridData var1, CellSheet var2);

    public GridData afterCellSheetToGridData(GridData var1, CellSheet var2);

    public Cell beforeCellToGridCell(GridCell var1, Cell var2);

    public GridCell afterCellToGridCell(GridCell var1, Cell var2);
}

