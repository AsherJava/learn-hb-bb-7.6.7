/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellBuffer;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridCellV1;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.grid.IntList;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.grid.PropList;
import java.util.List;
import java.util.stream.Collectors;

final class FileUpdater {
    private FileUpdater() {
    }

    public static PropList convertTo12(GridData grid, PropList prop20) {
        PropList prop12 = new PropList(20);
        GridCell cell20 = new GridCell();
        GridCellV1 cell12 = new GridCellV1();
        byte[] emptyData = new byte[20];
        for (int i = 0; i < prop20.count(); ++i) {
            byte[] data20 = prop20.get(i);
            cell20.internalInit(grid, 0, 0, data20);
            cell12.internalInit(grid, 0, 0, emptyData);
            FileUpdater.convertToCell12(cell20, cell12);
            prop12.add(cell12.getPropData());
        }
        return prop12;
    }

    public static PropList updateTo20(GridData grid, PropList prop12) {
        FileUpdater.updateToRGB(grid.cellColors());
        FileUpdater.updateToRGB(grid.edgeColors());
        FileUpdater.updateToRGB(grid.fontColors());
        return FileUpdater.updateToProp20(grid, prop12);
    }

    public static void updateTo21(GridData grid) {
        FileUpdater.updateSystemColor(grid.cellColors(), false);
        FileUpdater.updateSystemColor(grid.edgeColors(), false);
        FileUpdater.updateSystemColor(grid.fontColors(), true);
    }

    public static void updateTo30(GridData grid, PropList prop2x) {
        for (int i = 0; i < prop2x.count(); ++i) {
            byte[] prop = prop2x.get(i);
            prop[26] = 100;
        }
        prop2x.rebuild();
    }

    public static void updateTo31(GridData gridData, PropList prop2x) {
        boolean updated = false;
        for (int i = 0; i < prop2x.count(); ++i) {
            CellBuffer buffer = new CellBuffer(prop2x.get(i));
            if (buffer.getByte(19) != 9) continue;
            buffer.setByte(19, 0);
            buffer.setBit(3, 4, true);
            updated = true;
        }
        if (updated) {
            prop2x.rebuild();
        }
    }

    public static void updateTo311(GridData gridData) {
        GridFieldList merges = gridData.merges();
        int colCount = gridData.getColCount();
        int rowCount = gridData.getRowCount();
        List<CellField> fields = merges.getFields().stream().filter(cf -> cf.left >= 1 && cf.top >= 1 && cf.right >= cf.left && cf.bottom >= cf.top && cf.right < colCount && cf.bottom < rowCount).collect(Collectors.toList());
        if (fields.size() < merges.count()) {
            merges.clear();
            fields.forEach(cf -> merges.addMergeRect((CellField)cf));
        }
    }

    public static void updateTo312(GridData gridData, PropList prop2x) {
        GridCell cell = new GridCell();
        for (int i = 0; i < prop2x.count(); ++i) {
            CurrencyCellPropertyIntf currCell;
            cell.internalInit(gridData, 1, 1, prop2x.get(i));
            if (cell.getType() == 2) {
                NumberCellPropertyIntf numCell = cell.toNumberCell();
                if (!numCell.getWarningNegative()) continue;
                numCell.setWarningNegative(false);
                prop2x.set(i, cell.getPropData());
                continue;
            }
            if (cell.getType() != 3 || !(currCell = cell.toCurrencyCell()).getWarningNegative()) continue;
            currCell.setWarningNegative(false);
            prop2x.set(i, cell.getPropData());
        }
    }

    private static void updateToRGB(IntList palette) {
        for (int i = 0; i < palette.count(); ++i) {
            int bgr = palette.get(i);
            int rgb = GridColor.BGR2RGB(bgr);
            palette.set(i, rgb);
        }
    }

    private static PropList updateToProp20(GridData grid, PropList prop12) {
        PropList prop20 = new PropList(32);
        GridCellV1 cell12 = new GridCellV1();
        GridCell cell2x = new GridCell();
        byte[] emptyData = new byte[32];
        for (int i = 0; i < prop12.count(); ++i) {
            byte[] data12 = prop12.get(i);
            cell12.internalInit(grid, 1, 1, data12);
            cell2x.internalInit(grid, 1, 1, emptyData);
            FileUpdater.updateToCell20(cell12, cell2x);
            prop20.add(cell2x.getPropData());
        }
        return prop20;
    }

    private static void convertToCell12(GridCell cell20, GridCellV1 cell12) {
        cell12.setBackColor(cell20.getBackColor());
        cell12.setBackStyle(cell20.getBackStyle());
        cell12.setBackLines(2, cell20.getBackLines(2));
        cell12.setBackLines(3, cell20.getBackLines(3));
        cell12.setBackLines(0, cell20.getBackLines(0));
        cell12.setBackLines(1, cell20.getBackLines(1));
        cell12.setFontColor(cell20.getFontColor());
        cell12.setFontName(cell20.getFontName());
        cell12.setFontSize(cell20.getFontSize());
        cell12.setFontBold(cell20.getFontBold());
        cell12.setFontItalic(cell20.getFontItalic());
        cell12.setFontUnderLine(cell20.getFontUnderLine());
        cell12.setFontStrikeOut(cell20.getFontStrikeOut());
        cell12.setFitFontSize(cell20.getFitFontSize());
        cell12.setWrapLine(cell20.getWrapLine());
        cell12.setMultiLine(cell20.getMultiLine());
        cell12.setVertText(cell20.getVertText());
        cell12.setHorzAlign(cell20.getHorzAlign());
        cell12.setVertAlign(cell20.getVertAlign());
        cell12.setIndent(cell20.getIndent());
        cell12.setSilverHead(cell20.getSilverHead());
        cell12.setEmptyCell(cell20.getEmptyCell());
        cell12.setCanModify(cell20.getCanModify());
        cell12.setCanInput(cell20.getCanInput());
        cell12.setCanSelect(cell20.getCanSelect());
        cell12.setCanRead(cell20.getCanRead());
        cell12.setCanWrite(cell20.getCanWrite());
        cell12.setEditJumpNext(cell20.getEditJumpNext());
        cell12.setREdgeStyle(cell20.getREdgeStyle());
        cell12.setREdgeColor(cell20.getREdgeColor());
        cell12.setBEdgeStyle(cell20.getBEdgeStyle());
        cell12.setBEdgeColor(cell20.getBEdgeColor());
        cell12.setDataType(cell20.getDataType());
        cell12.setDataFlag(cell20.getDataFlag());
        cell12.setDataFormat(cell20.getDataFormat());
        cell12.setDataProperty(cell20.getDataProperty());
        cell12.setEditMode(cell20.getEditMode());
        cell12.setImeMode(cell20.getImeMode());
        cell12.setOutputKind(cell20.getOutputKind());
    }

    private static void updateToCell20(GridCellV1 cell12, GridCell cell20) {
        cell20.setBackColor(cell12.getBackColor());
        cell20.setBackStyle(cell12.getBackStyle());
        cell20.setBackLines(2, cell12.getBackLines(2));
        cell20.setBackLines(3, cell12.getBackLines(3));
        cell20.setBackLines(0, cell12.getBackLines(0));
        cell20.setBackLines(1, cell12.getBackLines(1));
        cell20.setFontColor(cell12.getFontColor());
        cell20.setFontName(cell12.getFontName());
        cell20.setFontSize(cell12.getFontSize());
        cell20.setFontBold(cell12.getFontBold());
        cell20.setFontItalic(cell12.getFontItalic());
        cell20.setFontUnderLine(cell12.getFontUnderLine());
        cell20.setFontStrikeOut(cell12.getFontStrikeOut());
        cell20.setFitFontSize(cell12.getFitFontSize());
        cell20.setWrapLine(cell12.getWrapLine());
        cell20.setMultiLine(cell12.getMultiLine());
        cell20.setVertText(cell12.getVertText());
        cell20.setHorzAlign(cell12.getHorzAlign());
        cell20.setVertAlign(cell12.getVertAlign());
        cell20.setIndent(cell12.getIndent());
        cell20.setSilverHead(cell12.getSilverHead());
        cell20.setEmptyCell(cell12.getEmptyCell());
        cell20.setCanModify(cell12.getCanModify());
        cell20.setCanInput(cell12.getCanInput());
        cell20.setCanSelect(cell12.getCanSelect());
        cell20.setCanRead(cell12.getCanRead());
        cell20.setCanWrite(cell12.getCanWrite());
        cell20.setEditJumpNext(cell12.getEditJumpNext());
        cell20.setREdgeStyle(cell12.getREdgeStyle());
        cell20.setREdgeColor(cell12.getREdgeColor());
        cell20.setBEdgeStyle(cell12.getBEdgeStyle());
        cell20.setBEdgeColor(cell12.getBEdgeColor());
        cell20.setDataType(cell12.getDataType());
        cell20.setDataFlag(cell12.getDataFlag());
        cell20.setDataFormat(cell12.getDataFormat());
        cell20.setDataProperty(cell12.getDataProperty());
        cell20.setEditMode(cell12.getEditMode());
        cell20.setImeMode(cell12.getImeMode());
        cell20.setOutputKind(cell12.getOutputKind());
    }

    private static void updateSystemColor(IntList colors, boolean isFont) {
        for (int i = 0; i < colors.count(); ++i) {
            int color = colors.get(i);
            if (color >= 0) continue;
            int rgb = isFont && color == GridColor.WINDOW.value() ? 0 : GridColor.getColorValue(color);
            colors.set(i, rgb);
        }
    }

    public static IntList convertToBGR(IntList palette) {
        IntList bgrs = new IntList();
        for (int i = 0; i < palette.count(); ++i) {
            int rgb = palette.get(i);
            int bgr = GridColor.RGB2BGR(rgb);
            bgrs.add(bgr);
        }
        return bgrs;
    }
}

