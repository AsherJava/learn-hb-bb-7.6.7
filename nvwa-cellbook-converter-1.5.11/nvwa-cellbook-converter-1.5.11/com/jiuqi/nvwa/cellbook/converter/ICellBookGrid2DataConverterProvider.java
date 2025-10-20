/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nvwa.cellbook.converter;

import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;

public interface ICellBookGrid2DataConverterProvider {
    public Grid2Data beforeGrid2DataToCellSheet(Grid2Data var1, CellSheet var2);

    public CellSheet afterGrid2DataToCellSheet(Grid2Data var1, CellSheet var2);

    public GridCellData beforeGridCellDataToCell(GridCellData var1, Cell var2);

    public Cell afterGridCellDataToCell(GridCellData var1, Cell var2);

    public CellSheet beforeCellSheetToGrid2Data(Grid2Data var1, CellSheet var2);

    public Grid2Data afterCellSheetToGrid2Data(Grid2Data var1, CellSheet var2);

    public Cell beforeCellToGridCellData(GridCellData var1, Cell var2);

    public GridCellData afterCellToGridCellData(GridCellData var1, Cell var2);
}

