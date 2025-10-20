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
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.FilterBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportCellNode;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportRegionNode;
import com.jiuqi.bi.quickreport.engine.workbook.WorkbookException;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public abstract class ReportWorksheet
implements IWorksheet {
    public abstract String name();

    public IASTNode findCell(IContext context, Token token, Position pos, int cellOptions) throws CellExcpetion {
        if (!this.isValidPosition(pos)) {
            return null;
        }
        return new ReportCellNode(token, this, pos, cellOptions);
    }

    private boolean isValidPosition(Position pos) {
        GridData grid = this.getGridData();
        return pos.col() >= 0 && pos.col() < grid.getColCount() && pos.col() <= Cells.MAX_COL_NUM && pos.row() >= 0 && pos.row() < grid.getRowCount() && pos.row() <= 65535;
    }

    public IASTNode findRegion(IContext context, Token token, Region region, int cellOptions) throws CellExcpetion {
        if (!this.isValidRegion(region)) {
            return null;
        }
        return new ReportRegionNode(token, this, region, cellOptions);
    }

    public IASTNode findCell(IContext context, Token token, CompatiblePosition pos, int cellOptions, List<IASTNode> restrictions) throws CellExcpetion {
        throw new CellExcpetion(token, "\u4e0d\u652f\u6301\u62a5\u8868\u517c\u5bb9\u6a21\u5f0f\u7684\u516c\u5f0f\u8bed\u6cd5\u3002");
    }

    private boolean isValidRegion(Region region) {
        if (region.isColMode()) {
            return region.left() >= 0 && region.right() <= Cells.MAX_COL_NUM;
        }
        if (region.isRowMode()) {
            return region.top() >= 0 && region.bottom() <= 65535;
        }
        return this.isValidPosition(region.leftTop()) && this.isValidPosition(region.rightBottom());
    }

    public abstract GridData getGridData();

    public CellBindingInfo openCellBinding(CellMap cellMap) throws WorkbookException {
        Position position;
        GridData grid = this.getGridData();
        CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj((position = cellMap.getPosition()).col(), position.row());
        if (bindingInfo == null) {
            if (position.col() < 0 || position.col() >= grid.getColCount() || position.row() < 0 || position.row() >= grid.getRowCount()) {
                throw new WorkbookException("\u8bbf\u95ee\u9875\u7b7e" + this.name() + "!" + position + "\u5355\u5143\u683c\u4f4d\u7f6e\u4e0d\u5b58\u5728\u3002");
            }
            bindingInfo = new CellBindingInfo(this.name(), cellMap);
            grid.setObj(position.col(), position.row(), (Object)bindingInfo);
        } else if (bindingInfo.getCellMap() == null) {
            bindingInfo.setCellMap(cellMap);
        }
        return bindingInfo;
    }

    public CellBindingInfo openCellBinding(Position position) throws WorkbookException {
        GridData grid = this.getGridData();
        CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(position.col(), position.row());
        if (bindingInfo == null) {
            if (position.col() < 0 || position.col() >= grid.getColCount() || position.row() < 0 || position.row() >= grid.getRowCount()) {
                throw new WorkbookException("\u8bbf\u95ee\u9875\u7b7e" + this.name() + "!" + position + "\u5355\u5143\u683c\u4f4d\u7f6e\u4e0d\u5b58\u5728\u3002");
            }
            bindingInfo = new CellBindingInfo(new SheetPosition(this.name(), position), null);
            grid.setObj(position.col(), position.row(), (Object)bindingInfo);
        }
        return bindingInfo;
    }

    public FilterBindingInfo openColFilter(int col) {
        GridData grid = this.getGridData();
        FilterBindingInfo bindingInfo = (FilterBindingInfo)grid.getObj(col, 0);
        if (bindingInfo == null) {
            bindingInfo = new FilterBindingInfo();
            grid.setObj(col, 0, (Object)bindingInfo);
        }
        return bindingInfo;
    }

    public FilterBindingInfo openRowFilter(int row) {
        GridData grid = this.getGridData();
        FilterBindingInfo bindingInfo = (FilterBindingInfo)grid.getObj(0, row);
        if (bindingInfo == null) {
            bindingInfo = new FilterBindingInfo();
            grid.setObj(0, row, (Object)bindingInfo);
        }
        return bindingInfo;
    }

    public String toString() {
        return this.name();
    }
}

