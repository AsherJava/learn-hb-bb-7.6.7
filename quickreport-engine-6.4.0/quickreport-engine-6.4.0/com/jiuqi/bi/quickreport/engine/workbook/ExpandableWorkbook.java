/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExpandableWorkbook
implements ICellProvider {
    private final ReportWorkbook workbook;
    private Map<ReportWorksheet, ExpandableWorksheet> finder;

    public ExpandableWorkbook(ReportWorkbook workbook) {
        this.workbook = workbook;
        this.finder = new HashMap<ReportWorksheet, ExpandableWorksheet>();
    }

    public IWorksheet activeWorksheet(IContext context) throws CellExcpetion {
        ReportWorksheet activeSheet = (ReportWorksheet)this.workbook.activeWorksheet(context);
        if (activeSheet == null) {
            return null;
        }
        return this.openWrapper(activeSheet);
    }

    private IWorksheet openWrapper(ReportWorksheet sheet) {
        ExpandableWorksheet wrapper = this.finder.get(sheet);
        if (wrapper == null) {
            wrapper = new ExpandableWorksheet(sheet);
            this.finder.put(sheet, wrapper);
        }
        return wrapper;
    }

    public IWorksheet find(IContext context, String sheetName) throws CellExcpetion {
        ReportWorksheet sheet = (ReportWorksheet)this.workbook.find(context, sheetName);
        if (sheet == null) {
            return null;
        }
        return this.openWrapper(sheet);
    }

    private static final class ExpandableWorksheet
    implements IWorksheet {
        private final ReportWorksheet worksheet;

        public ExpandableWorksheet(ReportWorksheet worksheet) {
            this.worksheet = worksheet;
        }

        public String name() {
            return this.worksheet.name();
        }

        public IASTNode findCell(IContext context, Token token, Position pos, int cellOptions) throws CellExcpetion {
            this.checkCol(pos.col());
            this.checkRow(pos.row());
            return this.worksheet.findCell(context, token, pos, cellOptions);
        }

        public IASTNode findCell(IContext context, Token token, CompatiblePosition pos, int cellOptions, List<IASTNode> restrictions) throws CellExcpetion {
            throw new CellExcpetion(token, "\u4e0d\u652f\u6301\u62a5\u8868\u517c\u5bb9\u6a21\u5f0f\u7684\u5355\u5143\u683c\u8bed\u6cd5\u3002");
        }

        private void checkCol(int col) {
            GridData grid = this.worksheet.getGridData();
            if (col <= Cells.MAX_COL_NUM && col >= grid.getColCount()) {
                grid.setColCount(col + 1);
            }
        }

        private void checkRow(int row) {
            GridData grid = this.worksheet.getGridData();
            if (row <= 65535 && row >= grid.getRowCount()) {
                grid.setRowCount(row + 1);
            }
        }

        public IASTNode findRegion(IContext context, Token token, Region region, int cellOptions) throws CellExcpetion {
            this.checkCol(region.right());
            this.checkRow(region.bottom());
            return this.worksheet.findRegion(context, token, region, cellOptions);
        }
    }
}

