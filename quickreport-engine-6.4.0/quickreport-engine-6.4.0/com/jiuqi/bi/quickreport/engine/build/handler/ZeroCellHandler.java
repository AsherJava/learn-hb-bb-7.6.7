/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.bi.syntax.DataType;

public final class ZeroCellHandler
implements ICellContentHandler {
    private final ICellContentHandler nextHandler;
    private final ValueConvertMode mode;

    public ZeroCellHandler(ICellContentHandler nextHandler, ValueConvertMode mode) {
        this.nextHandler = nextHandler;
        this.mode = mode;
    }

    @Override
    public void setCell(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        GridData grid = context.getResultGrid().getGridData();
        if (value.displayValue instanceof Number && DataType.compare((double)((Number)value.displayValue).doubleValue(), (double)0.0) == 0) {
            switch (this.mode) {
                case ASNULL: {
                    grid.setObj(col, row, (Object)value);
                    break;
                }
                case ASBLANK: {
                    GridCell cell = grid.getCellEx(col, row);
                    if (cell.getHorzAlign() == 0) {
                        cell.setHorzAlign(2);
                    }
                    cell.setType(1);
                    cell.setCellData("\u2014");
                    grid.setCell(cell);
                    grid.setObj(col, row, (Object)value);
                    break;
                }
                default: {
                    this.nextHandler.setCell(context, col, row, value);
                    break;
                }
            }
        } else {
            this.nextHandler.setCell(context, col, row, value);
        }
    }
}

