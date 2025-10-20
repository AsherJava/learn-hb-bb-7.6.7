/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.ArrayData$ItemIterator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.util.StringUtils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class DefaultCellHandler
implements ICellContentHandler {
    private final DateFormat dateFormat;

    public DefaultCellHandler(Locale locale) {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
    }

    private String formatCellValue(CellValue value) throws IllegalArgumentException {
        if (value.displayValue == null) {
            return null;
        }
        if (value._bindingInfo != null && value._bindingInfo.getFormat() != null) {
            Object dispVal = value.displayValue instanceof Calendar ? ((Calendar)value.displayValue).getTime() : value.displayValue;
            return value._bindingInfo.getFormat().format(dispVal);
        }
        if (value.displayValue instanceof Calendar) {
            Calendar cal = (Calendar)value.displayValue;
            return this.dateFormat.format(cal.getTime());
        }
        if (value.displayValue instanceof BigDecimal) {
            return ((BigDecimal)value.displayValue).toPlainString();
        }
        if (value.displayValue instanceof Number) {
            Number n = (Number)value.displayValue;
            if (DataType.isInteger((double)n.doubleValue())) {
                return Long.toString(n.longValue());
            }
            return Double.toString(n.doubleValue());
        }
        return value.displayValue.toString();
    }

    @Override
    public void setCell(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        if (value.displayValue instanceof ArrayData) {
            this.setCellArray(context, col, row, value);
        } else {
            this.setCellValue(context, col, row, value);
        }
    }

    private void setCellValue(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        String valStr;
        try {
            valStr = this.formatCellValue(value);
        }
        catch (IllegalArgumentException e) {
            throw new ReportBuildException("\u65e0\u6cd5\u683c\u5f0f\u5316\u5355\u5143\u683c" + value._bindingInfo.getPosition() + "\u5185\u5bb9\uff0c" + e.getMessage(), e);
        }
        GridData grid = context.getResultGrid().getGridData();
        grid.setCellData(col, row, valStr);
        if (value._bindingInfo != null) {
            GridCell cell;
            if (!value._bindingInfo.isMaster() && (value.displayValue instanceof Number || value.displayValue instanceof Calendar)) {
                GridCell cell2 = grid.getCellEx(col, row);
                if (cell2.getHorzAlign() == 0) {
                    cell2.setHorzAlign(2);
                    grid.setCell(cell2);
                }
            } else if (value.displayValue instanceof String) {
                GridCell cell3 = grid.getCellEx(col, row);
                if (cell3.getHorzAlign() == 0) {
                    cell3.setHorzAlign(1);
                    grid.setCell(cell3);
                }
            } else if (value.displayValue instanceof Boolean && (cell = grid.getCellEx(col, row)).getHorzAlign() == 0) {
                cell.setHorzAlign(3);
                grid.setCell(cell);
            }
        }
        grid.setObj(col, row, (Object)value);
    }

    private void setCellArray(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        ArrayData arr = (ArrayData)value.displayValue;
        GridData grid = context.getResultGrid().getGridData();
        if (col + arr.xSize() > grid.getColCount() || row + arr.ySize() > grid.getRowCount()) {
            throw new ReportBuildException("\u8bbe\u7f6e\u5355\u5143\u683c" + value._bindingInfo.getPosition() + "\u6570\u7ec4\u503c\u6ea2\u51fa");
        }
        ArrayData.ItemIterator i = arr.itemIterator();
        while (i.hasNext()) {
            int y;
            Object item = i.next();
            int x = col + i.x();
            int n = y = i.y() < 0 ? row : row + i.y();
            if (!this.isEmpty(context, x, y, value)) {
                throw new ReportBuildException("\u8bbe\u7f6e\u5355\u5143\u683c" + value._bindingInfo.getPosition() + "\u6570\u7ec4\u503c\u6ea2\u51fa");
            }
            CellValue itemValue = this.createItemValue(value, item);
            this.setCellValue(context, x, y, itemValue);
        }
    }

    private boolean isEmpty(IHandlerContext context, int col, int row, CellValue value) {
        CellBindingInfo info;
        if (!StringUtils.isEmpty((String)context.getResultGrid().getGridData().getCellData(col, row))) {
            return false;
        }
        int x = context.getResultGrid().getRawCol(col);
        int y = context.getResultGrid().getRawRow(row);
        Object obj = context.getRawGrid().getObj(x, y);
        return !(obj instanceof CellBindingInfo) || value._bindingInfo == obj || (info = (CellBindingInfo)obj).getCellMap() == null;
    }

    private CellValue createItemValue(CellValue value, Object item) {
        CellValue itemValue = new CellValue(value._bindingInfo);
        itemValue._masterValue = value._masterValue;
        itemValue._restrictions = value._restrictions;
        itemValue.value = item;
        itemValue.displayValue = item;
        return itemValue;
    }
}

