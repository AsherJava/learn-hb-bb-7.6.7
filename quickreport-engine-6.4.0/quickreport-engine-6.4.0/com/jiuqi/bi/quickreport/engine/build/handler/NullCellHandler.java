/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.bi.syntax.parser.IContext;

public final class NullCellHandler
implements ICellContentHandler {
    private final ICellContentHandler nextHandler;
    private final ValueConvertMode mode;

    public NullCellHandler(ICellContentHandler nextHandler, ValueConvertMode mode) {
        this.nextHandler = nextHandler;
        this.mode = mode;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setCell(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        GridData grid = context.getResultGrid().getGridData();
        if (value.displayValue == null) {
            switch (this.mode) {
                case ASZERO: {
                    GridCell cell = grid.getCellEx(col, row);
                    if (this.isNumberCell(context.getContext(), cell, value)) {
                        cell.setCellData("0");
                        grid.setObj(col, row, (Object)value);
                        if (value._bindingInfo == null || value._bindingInfo.isMaster() || cell.getHorzAlign() != 0) return;
                        cell.setHorzAlign(2);
                        grid.setCell(cell);
                        return;
                    }
                    this.nextHandler.setCell(context, col, row, value);
                    return;
                }
                case ASBLANK: {
                    GridCell cell = grid.getCellEx(col, row);
                    if (cell.getHorzAlign() == 0) {
                        cell.setHorzAlign(this.isNumberCell(context.getContext(), cell, value) ? 2 : 1);
                    }
                    cell.setType(1);
                    cell.setCellData("\u2014");
                    grid.setCell(cell);
                    grid.setObj(col, row, (Object)value);
                    return;
                }
                default: {
                    this.nextHandler.setCell(context, col, row, value);
                    return;
                }
            }
        } else {
            this.nextHandler.setCell(context, col, row, value);
        }
    }

    private boolean isNumberCell(IContext context, GridCell cell, CellValue value) throws ReportBuildException {
        switch (cell.getType()) {
            case 2: 
            case 3: {
                return true;
            }
            case 0: {
                if (value._bindingInfo == null) {
                    return false;
                }
                IReportExpression expr = value._bindingInfo.getDisplay() == null ? value._bindingInfo.getValue() : value._bindingInfo.getDisplay();
                try {
                    return expr != null && expr.getDataType(context) == 3;
                }
                catch (ReportExpressionException e) {
                    throw new ReportBuildException(e);
                }
            }
        }
        return false;
    }
}

