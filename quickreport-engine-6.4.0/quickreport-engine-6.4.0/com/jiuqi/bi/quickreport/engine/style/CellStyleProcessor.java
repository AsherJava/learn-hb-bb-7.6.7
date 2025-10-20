/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.style;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.StyleValue;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.CellStyle;
import com.jiuqi.bi.syntax.cell.Position;

public final class CellStyleProcessor
extends StyleProcessor<CellStyle> {
    public CellStyleProcessor(IStyleProcessor next) {
        super(next);
    }

    @Override
    protected void applyCellStyle(EngineWorksheet worksheet, Position position, CellStyle style) {
        GridData grid = worksheet.getResultGrid().getGridData();
        boolean backcolored = this.applyStyles(grid, position, style);
        this.applyValue(grid, position, style);
        CellValue cellValue = (CellValue)grid.getObj(position.col(), position.row());
        if (cellValue != null) {
            if (cellValue.styleValue == null) {
                cellValue.styleValue = new StyleValue();
            }
            if (backcolored) {
                cellValue.styleValue.setBackColorChanged(true);
            }
        }
    }

    private void applyValue(GridData grid, Position position, CellStyle style) {
        String value;
        switch (style.getValueMode()) {
            case ASZERO: {
                value = "0";
                break;
            }
            case ASNULL: {
                value = null;
                break;
            }
            case ASBLANK: {
                value = "\u2014";
                break;
            }
            default: {
                return;
            }
        }
        grid.setCellData(position.col(), position.row(), value);
    }

    private boolean applyStyles(GridData grid, Position position, CellStyle style) {
        boolean underline;
        GridCell cell = grid.getCellEx(position.col(), position.row());
        boolean backcolored = this.isBackChanged(style, cell);
        if (style.getForegroundColor() != -1) {
            cell.setFontColor(style.getForegroundColor());
        }
        if (style.getFontBold() >= 0) {
            cell.setFontBold(style.getFontBold() > 0);
        }
        if (underline = style.isUnderline()) {
            cell.setFontUnderLine(true);
        }
        if (style.getBackgroundColor() != -1) {
            cell.setBackColor(style.getBackgroundColor());
            cell.setBackAlpha(style.getBackgroundAlpha());
            cell.setBackStyle(1);
        }
        grid.setCell(cell);
        return backcolored;
    }

    private boolean isBackChanged(CellStyle style, GridCell cell) {
        return style.getBackgroundColor() != -1 && (cell.getBackColor() != style.getBackgroundColor() || cell.getBackAlpha() != style.getBackgroundAlpha());
    }
}

