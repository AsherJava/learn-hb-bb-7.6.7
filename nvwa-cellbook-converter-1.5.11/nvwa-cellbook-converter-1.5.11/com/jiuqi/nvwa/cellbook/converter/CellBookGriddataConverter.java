/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellDataProperty
 *  com.jiuqi.bi.grid.CellDataPropertyIntf
 *  com.jiuqi.bi.grid.CellEdger
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.GridFieldList
 *  com.jiuqi.bi.grid.NumberCellProperty
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.cellbook.constant.CellBorderStyle
 *  com.jiuqi.nvwa.cellbook.constant.CellStyleModel
 *  com.jiuqi.nvwa.cellbook.constant.FillPatternType
 *  com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment
 *  com.jiuqi.nvwa.cellbook.constant.StringUtils
 *  com.jiuqi.nvwa.cellbook.constant.VerticalAlignment
 *  com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellColor
 *  com.jiuqi.nvwa.cellbook.model.CellMerge
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  org.json.JSONException
 */
package com.jiuqi.nvwa.cellbook.converter;

import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.CellEdger;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.util.Html;
import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.converter.ICellBookGridDataConverterProvider;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import java.util.List;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBookGriddataConverter {
    private static final Logger logger = LoggerFactory.getLogger(CellBookGriddataConverter.class);

    public static GridData cellBookToGridData(CellSheet cellSheet, GridData gridData) {
        return CellBookGriddataConverter.cellBookToGridData(cellSheet, gridData, null);
    }

    public static GridData cellBookToGridData(CellSheet cellSheet, GridData gridData, ICellBookGridDataConverterProvider provider) {
        if (provider != null) {
            cellSheet = provider.beforeCellSheetToGridData(gridData, cellSheet);
        }
        int columnCount = cellSheet.getColumnCount();
        int rowCount = cellSheet.getRowCount();
        gridData.setColCount(columnCount + 1);
        gridData.setRowCount(rowCount + 1);
        gridData.setScrollTopCol(cellSheet.getHeaderColCount() + 1);
        gridData.setScrollTopRow(cellSheet.getHeaderRowCount() + 1);
        gridData.setScrollBottomCol(cellSheet.getFooterColCount() + 1);
        gridData.setScrollBottomRow(cellSheet.getFooterRowCount() + 1);
        for (int i = 0; i < rowCount; ++i) {
            gridData.setRowHeights(i + 1, cellSheet.getRowHeight(i));
            gridData.setRowVisible(i + 1, !cellSheet.getRowHidden(i));
            gridData.setRowAutoSize(i + 1, cellSheet.getRowAutoHeight(i));
            for (int j = 0; j < columnCount; ++j) {
                if (i == 0) {
                    gridData.setColWidths(j + 1, cellSheet.getColWide(j));
                    gridData.setColVisible(j + 1, !cellSheet.getColHidden(j));
                    gridData.setColAutoSize(j + 1, cellSheet.getColAutoWide(j));
                }
                GridCell gridCell = gridData.getCellEx(j + 1, i + 1);
                Cell cell = cellSheet.getCell(i, j);
                if (provider != null) {
                    cell = provider.beforeCellToGridCell(gridCell, cell);
                }
                CellBookGriddataConverter.toGridCell(cell, gridCell);
                CellBookGriddataConverter.toGridCellBorder(cell, gridCell);
                if (provider != null) {
                    gridCell = provider.afterCellToGridCell(gridCell, cell);
                }
                gridData.setCell(gridCell);
            }
        }
        List cellMerges = cellSheet.getMerges();
        if (cellMerges != null) {
            for (CellMerge cellMerge : cellMerges) {
                int left = cellMerge.getColumnIndex();
                int top = cellMerge.getRowIndex();
                int right = cellMerge.getColumnIndex() + cellMerge.getColumnSpan();
                int bottom = cellMerge.getRowIndex() + cellMerge.getRowSpan();
                if (left <= -1 || top <= -1 || right <= left || bottom <= top || right >= gridData.getColCount() || bottom >= gridData.getRowCount()) continue;
                gridData.mergeCells(left + 1, top + 1, right, bottom);
            }
        }
        GridFieldList merges = gridData.merges();
        for (int i = 0; i < merges.count(); ++i) {
            CellField cellField = merges.get(i);
            CellEdger cellEdger = CellEdger.at((GridData)gridData, (int)cellField.left, (int)cellField.top);
            CellBookGriddataConverter.fillMergeCellLeftEdge(cellField, cellEdger, cellSheet);
            CellBookGriddataConverter.fillMergeCellRightEdge(cellField, cellEdger, cellSheet);
            CellBookGriddataConverter.fillMergeCellTopEdge(cellField, cellEdger, cellSheet);
            CellBookGriddataConverter.fillMergeCellBottomEdge(cellField, cellEdger, cellSheet);
            cellEdger.apply();
        }
        if (provider != null) {
            gridData = provider.afterCellSheetToGridData(gridData, cellSheet);
        }
        return gridData;
    }

    public static CellSheet gridDataToCellBook(GridData gridData, CellBook cellBook, String cellSheetCode) {
        return CellBookGriddataConverter.gridDataToCellBook(gridData, cellBook, cellSheetCode, null);
    }

    public static CellSheet gridDataToCellBook(GridData gridData, CellBook cellBook, String cellSheetCode, String cellSheetTitle) {
        return CellBookGriddataConverter.gridDataToCellBook(gridData, cellBook, cellSheetCode, cellSheetTitle, null);
    }

    public static CellSheet gridDataToCellBook(GridData gridData, CellBook cellBook, String cellSheetCode, String cellSheetTitle, ICellBookGridDataConverterProvider provider) {
        int columnCount = gridData.getColCount();
        int rowCount = gridData.getRowCount();
        CellSheet cellSheet = cellBook.createSheet(StringUtils.isEmpty((String)cellSheetTitle) ? cellSheetCode : cellSheetTitle, cellSheetCode, rowCount - 1, columnCount - 1);
        if (provider != null) {
            gridData = provider.beforeGridDataToCellSheet(gridData, cellSheet);
        }
        cellSheet.setHeaderColCount(gridData.getScrollTopCol() - 1);
        cellSheet.setHeaderRowCount(gridData.getScrollTopRow() - 1);
        cellSheet.setFooterColCount(gridData.getScrollBottomCol() - 1);
        cellSheet.setFooterRowCount(gridData.getScrollBottomRow() - 1);
        for (int i = 1; i < rowCount; ++i) {
            cellSheet.setRowHeight(i - 1, gridData.getRowHeights(i));
            cellSheet.setRowHidden(i - 1, !gridData.getRowVisible(i));
            cellSheet.setRowAutoHeight(i - 1, gridData.getRowAutoSize(i));
            for (int j = 1; j < columnCount; ++j) {
                if (i == 1) {
                    cellSheet.setColWide(j - 1, gridData.getColWidths(j));
                    cellSheet.setColHidden(j - 1, !gridData.getColVisible(j));
                    cellSheet.setColAutoWide(j - 1, gridData.getColAutoSize(j));
                }
                GridCell gridCell = gridData.getCellEx(j, i);
                Cell cell = cellSheet.getCell(i - 1, j - 1);
                if (provider != null) {
                    gridCell = provider.beforeGridCellToCell(gridCell, cell);
                }
                CellBookGriddataConverter.gridCellToCell(cell, gridCell);
                if (provider == null) continue;
                provider.afterGridCellToCell(gridCell, cell);
            }
        }
        GridFieldList gfl = gridData.merges();
        CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                cellSheet.mergeCells(cf.top - 1, cf.left - 1, cf.bottom - cf.top + 1, cf.right - cf.left + 1);
                CellEdger cellEdger = CellEdger.at((GridData)gridData, (int)cf.left, (int)cf.top);
                for (int col = cf.left; col < cf.right + 1; ++col) {
                    Cell topCell = cellSheet.getCell(cf.top - 1, col - 1);
                    topCell.getCellStyle().setTopBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(cellEdger.getTopStyle(col - cf.left)));
                    topCell.getCellStyle().setTopBorderColor(CellBookGriddataConverter.getCellColor(cellEdger.getTopColor(col - cf.left)).getHexString());
                    if (cf.top > 1) {
                        Cell topTopCell = cellSheet.getCell(cf.top - 2, col - 1);
                        topTopCell.getCellStyle().setBottomBorderStyle(topCell.getCellStyle().getTopBorderStyle());
                        topTopCell.getCellStyle().setBottomBorderColor(topCell.getCellStyle().getTopBorderColor());
                    }
                    Cell bottomCell = cellSheet.getCell(cf.bottom - 1, col - 1);
                    bottomCell.getCellStyle().setBottomBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(cellEdger.getBottomStyle(col - cf.left)));
                    bottomCell.getCellStyle().setBottomBorderColor(CellBookGriddataConverter.getCellColor(cellEdger.getBottomColor(col - cf.left)).getHexString());
                    if (cf.bottom >= gridData.getRowCount() - 1) continue;
                    Cell bottomBottomCell = cellSheet.getCell(cf.bottom, col - 1);
                    bottomBottomCell.getCellStyle().setTopBorderStyle(bottomCell.getCellStyle().getBottomBorderStyle());
                    bottomBottomCell.getCellStyle().setTopBorderColor(bottomCell.getCellStyle().getBottomBorderColor());
                }
                for (int row = cf.top; row < cf.bottom + 1; ++row) {
                    Cell leftCell = cellSheet.getCell(row - 1, cf.left - 1);
                    leftCell.getCellStyle().setLeftBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(cellEdger.getLeftStyle(row - cf.top)));
                    leftCell.getCellStyle().setLeftBorderColor(CellBookGriddataConverter.getCellColor(cellEdger.getLeftColor(row - cf.top)).getHexString());
                    if (cf.left > 1) {
                        Cell leftLeftCell = cellSheet.getCell(row - 1, cf.left - 2);
                        leftLeftCell.getCellStyle().setRightBorderStyle(leftCell.getCellStyle().getLeftBorderStyle());
                        leftLeftCell.getCellStyle().setRightBorderColor(leftCell.getCellStyle().getLeftBorderColor());
                    }
                    Cell rightCell = cellSheet.getCell(row - 1, cf.right - 1);
                    rightCell.getCellStyle().setRightBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(cellEdger.getRightStyle(row - cf.top)));
                    rightCell.getCellStyle().setRightBorderColor(CellBookGriddataConverter.getCellColor(cellEdger.getRightColor(row - cf.top)).getHexString());
                    if (cf.right >= gridData.getColCount() - 1) continue;
                    Cell rightRightCell = cellSheet.getCell(row - 1, cf.right);
                    rightRightCell.getCellStyle().setLeftBorderStyle(rightCell.getCellStyle().getRightBorderStyle());
                    rightRightCell.getCellStyle().setLeftBorderColor(rightCell.getCellStyle().getRightBorderColor());
                }
            }
        }
        if (provider != null) {
            cellSheet = provider.afterGridDataToCellSheet(gridData, cellSheet);
        }
        return cellSheet;
    }

    private static void gridCellToCell(Cell c, GridCell d) {
        if (d.getBackColor() != -1 && 0x1FFFFFFF != d.getBackColor()) {
            c.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            c.setBackGroundColor(CellBookGriddataConverter.getCellColor(d.getBackColor(), d.getBackAlpha()));
        }
        if (d.getFontColor() != -1) {
            c.setFontColor(CellBookGriddataConverter.getCellColor(d.getFontColor()));
        }
        if (d.getReadOnly()) {
            c.setModel(CellStyleModel.READONLY);
        }
        c.setFontSize((float)((double)d.getFontSizeF() * 1.3333333));
        c.setFontName(d.getFontName());
        c.setBold(d.getFontBold());
        c.setItalic(d.getFontItalic());
        c.setInline(d.getFontStrikeOut());
        c.setUnderline(d.getFontUnderLine());
        if (d.getTEdgeColor() != -1) {
            c.setTopBorderColor(CellBookGriddataConverter.getCellColor(d.getTEdgeColor()));
        }
        c.setTopBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(d.getTEdgeStyle()));
        if (d.getLEdgeColor() != -1) {
            c.setLeftBorderColor(CellBookGriddataConverter.getCellColor(d.getLEdgeColor()));
        }
        c.setLeftBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(d.getLEdgeStyle()));
        if (d.getREdgeColor() != -1) {
            c.setRightBorderColor(CellBookGriddataConverter.getCellColor(d.getREdgeColor()));
        }
        c.setRightBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(d.getREdgeStyle()));
        if (d.getBEdgeColor() != -1) {
            c.setBottomBorderColor(CellBookGriddataConverter.getCellColor(d.getBEdgeColor()));
        }
        c.setBottomBorderStyle(CellBookGriddataConverter.toCellBookBorderStyle(d.getBEdgeStyle()));
        c.setWrapLine(d.getWrapLine());
        c.setIndent(d.getIndent());
        int horzAlign = d.getHorzAlign();
        if (horzAlign == 0) {
            c.setHorizontalAlignment(HorizontalAlignment.GENERAL);
        } else if (horzAlign == 1) {
            c.setHorizontalAlignment(HorizontalAlignment.LEFT);
        } else if (horzAlign == 2) {
            c.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        } else if (horzAlign == 3) {
            c.setHorizontalAlignment(HorizontalAlignment.CENTER);
        } else if (horzAlign == 4 || horzAlign == 5) {
            c.setHorizontalAlignment(HorizontalAlignment.DISTRIBUTED);
        }
        int vertAlign = d.getVertAlign();
        if (vertAlign == 0) {
            c.setVerticalAlignment(VerticalAlignment.AUTO);
        } else if (vertAlign == 1) {
            c.setVerticalAlignment(VerticalAlignment.TOP);
        } else if (vertAlign == 2) {
            c.setVerticalAlignment(VerticalAlignment.BOTTOM);
        } else if (vertAlign == 3) {
            c.setVerticalAlignment(VerticalAlignment.CENTER);
        } else if (vertAlign == 4 || vertAlign == 5) {
            c.setVerticalAlignment(VerticalAlignment.DISTRIBUTED);
        }
        String editText = d.getEditText();
        String showText = d.getShowText();
        if (StringUtils.isEmpty((String)editText)) {
            editText = d.getShowText();
        }
        c.setValue(editText);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)showText) && !showText.equals(editText)) {
            c.setShowText(showText);
        }
        CellBookGriddataConverter.toCellBookDataType(d.getDataType(), c);
        if (d.getDataType() == 9) {
            c.setValue(CellBookGriddataConverter.getLinkShowText(d));
            c.setShowText(null);
        }
        c.setFitFontSize(d.getFitFontSize());
        try {
            if (StringUtils.isNotEmpty((String)d.getScript())) {
                c.setPersistenceData(d.getScript());
            }
        }
        catch (JSONException e) {
            logger.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8f6c\u6362\u5931\u8d25\uff01", e);
        }
    }

    private static void toGridCell(Cell cell, GridCell gridCell) {
        String formatter;
        CellStyleModel model;
        if (null != cell.getBackGroundColor()) {
            CellColor backGroundColor = cell.getBackGroundColor();
            gridCell.setBackColor(CellBookGriddataConverter.getGridColor(backGroundColor));
            gridCell.setBackAlpha(CellBookGriddataConverter.getGridAlpha(backGroundColor));
        }
        if (null != cell.getFontColor()) {
            CellColor fontColor = cell.getFontColor();
            gridCell.setFontColor(CellBookGriddataConverter.getGridColor(fontColor));
        }
        if (CellStyleModel.READONLY == (model = cell.getModel())) {
            gridCell.setCanModify(false);
            gridCell.setCanWrite(false);
        }
        gridCell.setFontName(cell.getFontName());
        gridCell.setFontSizeF((float)((double)cell.getFontSizeF() * 0.75));
        gridCell.setFontName(cell.getFontName());
        gridCell.setFontBold(cell.isBold());
        gridCell.setFontItalic(cell.isItalic());
        gridCell.setFontStrikeOut(cell.isInline());
        gridCell.setFontUnderLine(cell.isUnderline());
        gridCell.setWrapLine(cell.isWrapLine());
        gridCell.setIndent(cell.getIndent());
        HorizontalAlignment horizontalAlignment = cell.getHorizontalAlignment();
        if (HorizontalAlignment.GENERAL == horizontalAlignment) {
            gridCell.setHorzAlign(0);
        } else if (HorizontalAlignment.LEFT == horizontalAlignment) {
            gridCell.setHorzAlign(1);
        } else if (HorizontalAlignment.RIGHT == horizontalAlignment) {
            gridCell.setHorzAlign(2);
        } else if (HorizontalAlignment.CENTER == horizontalAlignment) {
            gridCell.setHorzAlign(3);
        } else if (HorizontalAlignment.DISTRIBUTED == horizontalAlignment) {
            gridCell.setHorzAlign(4);
        }
        VerticalAlignment verticalAlignment = cell.getVerticalAlignment();
        if (VerticalAlignment.AUTO == verticalAlignment) {
            gridCell.setVertAlign(0);
        } else if (VerticalAlignment.TOP == verticalAlignment) {
            gridCell.setVertAlign(1);
        } else if (VerticalAlignment.BOTTOM == verticalAlignment) {
            gridCell.setVertAlign(2);
        } else if (VerticalAlignment.CENTER == verticalAlignment) {
            gridCell.setVertAlign(3);
        } else if (VerticalAlignment.DISTRIBUTED == verticalAlignment) {
            gridCell.setVertAlign(4);
        }
        CommonCellDataType commonDataType = cell.getCommonDataType();
        gridCell.setEditText(cell.getValue());
        gridCell.setDataType(CellBookGriddataConverter.toGridDataType(cell));
        if (CommonCellDataType.NUMBER == commonDataType && !StringUtils.isEmpty((String)(formatter = cell.getFormatter()))) {
            String[] formatters = formatter.split("\\.");
            if (formatters.length == 2) {
                int length = formatters[1].length();
                if (formatters[1].endsWith("%")) {
                    length = formatters[1].length() + 1;
                }
                CellDataProperty cdp = new CellDataProperty(gridCell.getDataType(), gridCell.getDataFlag(), gridCell.getEditMode(), gridCell.getDataFormat(), gridCell.getDataProperty());
                NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                numberCellProperty.setDecimal(length);
                gridCell.setDataFlag((int)numberCellProperty.getCellDataProperty().getDataFlag());
                if (formatters[0].contains(",")) {
                    numberCellProperty.setThoundsMarks(true);
                }
                if (formatters[1].endsWith("%")) {
                    numberCellProperty.setIsPercent(true);
                }
                gridCell.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
            } else {
                NumberCellProperty numberCellProperty;
                CellDataProperty cdp;
                if (formatters[0].contains(",")) {
                    cdp = new CellDataProperty(gridCell.getDataType(), gridCell.getDataFlag(), gridCell.getEditMode(), gridCell.getDataFormat(), gridCell.getDataProperty());
                    numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setThoundsMarks(true);
                    gridCell.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                }
                if (formatters[0].endsWith("%")) {
                    cdp = new CellDataProperty(gridCell.getDataType(), gridCell.getDataFlag(), gridCell.getEditMode(), gridCell.getDataFormat(), gridCell.getDataProperty());
                    numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setIsPercent(true);
                    gridCell.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                }
            }
        }
        gridCell.setFitFontSize(cell.isFitFontSize());
        try {
            if (null != cell.getPersistenceData()) {
                gridCell.setScript(cell.getPersistenceData().toString());
            }
        }
        catch (JSONException e) {
            logger.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8f6c\u6362\u5931\u8d25\uff01", e);
        }
    }

    private static void toGridCellBorder(Cell cell, GridCell gridCell) {
        if (gridCell.getRowNum() < cell.getCellSheet().getRowCount() && cell.getCellStyle().getBottomBorderStyle() == CellBorderStyle.NONE) {
            Cell bottomCell = cell.getCellSheet().getCell(cell.getRowIndex() + 1, cell.getColIndex());
            gridCell.setBEdgeStyle(CellBookGriddataConverter.toGridBorderStyle(bottomCell.getTopBorderStyle()));
            if (null != bottomCell.getTopBorderColor()) {
                gridCell.setBEdgeColor(CellBookGriddataConverter.getGridColor(bottomCell.getTopBorderColor()));
            }
        } else {
            gridCell.setBEdgeStyle(CellBookGriddataConverter.toGridBorderStyle(cell.getBottomBorderStyle()));
            if (null != cell.getBottomBorderColor()) {
                gridCell.setBEdgeColor(CellBookGriddataConverter.getGridColor(cell.getBottomBorderColor()));
            }
        }
        if (gridCell.getColNum() < cell.getCellSheet().getColumnCount() && cell.getCellStyle().getRightBorderStyle() == CellBorderStyle.NONE) {
            Cell rightCell = cell.getCellSheet().getCell(cell.getRowIndex(), cell.getColIndex() + 1);
            gridCell.setREdgeStyle(CellBookGriddataConverter.toGridBorderStyle(rightCell.getLeftBorderStyle()));
            if (null != rightCell.getLeftBorderColor()) {
                gridCell.setREdgeColor(CellBookGriddataConverter.getGridColor(rightCell.getLeftBorderColor()));
            }
        } else {
            gridCell.setREdgeStyle(CellBookGriddataConverter.toGridBorderStyle(cell.getRightBorderStyle()));
            if (null != cell.getRightBorderColor()) {
                gridCell.setREdgeColor(CellBookGriddataConverter.getGridColor(cell.getRightBorderColor()));
            }
        }
    }

    private static CellBorderStyle toCellBookBorderStyle(int gridBorderStyle) {
        if (gridBorderStyle == 1) {
            return CellBorderStyle.NONE;
        }
        if (gridBorderStyle == 0 || gridBorderStyle == 2) {
            return CellBorderStyle.THIN;
        }
        if (gridBorderStyle == 3) {
            return CellBorderStyle.DASHED;
        }
        if (gridBorderStyle == 4) {
            return CellBorderStyle.DOTTED;
        }
        if (gridBorderStyle == 5) {
            return CellBorderStyle.DASH_DOT;
        }
        if (gridBorderStyle == 6) {
            return CellBorderStyle.DASH_DOT_DOT;
        }
        if (gridBorderStyle == 7) {
            return CellBorderStyle.THICK;
        }
        if (gridBorderStyle == 8) {
            return CellBorderStyle.MEDIUM_DASHED;
        }
        if (gridBorderStyle == 9) {
            return CellBorderStyle.MEDIUM_DASH_DOT;
        }
        if (gridBorderStyle == 10) {
            return CellBorderStyle.DOUBLE;
        }
        if (gridBorderStyle == 11) {
            return CellBorderStyle.DASHED;
        }
        if (gridBorderStyle == 12) {
            return CellBorderStyle.DOTTED;
        }
        if (gridBorderStyle == 13) {
            return CellBorderStyle.MEDIUM;
        }
        if (gridBorderStyle == 14) {
            return CellBorderStyle.MEDIUM_DASHED;
        }
        if (gridBorderStyle == 15) {
            return CellBorderStyle.MEDIUM_DASH_DOT;
        }
        return CellBorderStyle.THIN;
    }

    private static int toGridBorderStyle(CellBorderStyle cellBorderStyle) {
        if (CellBorderStyle.NONE == cellBorderStyle) {
            return 1;
        }
        if (CellBorderStyle.THIN == cellBorderStyle) {
            return 2;
        }
        if (CellBorderStyle.MEDIUM == cellBorderStyle) {
            return 13;
        }
        if (CellBorderStyle.DASHED == cellBorderStyle) {
            return 3;
        }
        if (CellBorderStyle.DOTTED == cellBorderStyle) {
            return 4;
        }
        if (CellBorderStyle.DASH_DOT == cellBorderStyle) {
            return 5;
        }
        if (CellBorderStyle.DASH_DOT_DOT == cellBorderStyle) {
            return 6;
        }
        if (CellBorderStyle.THICK == cellBorderStyle) {
            return 7;
        }
        if (CellBorderStyle.DASHED == cellBorderStyle) {
            return 3;
        }
        if (CellBorderStyle.MEDIUM_DASHED == cellBorderStyle) {
            return 14;
        }
        if (CellBorderStyle.MEDIUM_DASH_DOT == cellBorderStyle) {
            return 15;
        }
        if (CellBorderStyle.DOUBLE == cellBorderStyle) {
            return 10;
        }
        return 2;
    }

    private static void toCellBookDataType(int dataType, Cell c) {
        String dataTypeId = "";
        CommonCellDataType commonCellDataType = null;
        if (dataType == 1) {
            commonCellDataType = CommonCellDataType.STRING;
        } else if (dataType == 2) {
            commonCellDataType = CommonCellDataType.NUMBER;
        } else if (dataType == 4) {
            commonCellDataType = CommonCellDataType.BOOLEAN;
        } else if (dataType == 5) {
            commonCellDataType = CommonCellDataType.DATE;
        } else if (dataType == 0) {
            dataTypeId = "auto";
        } else if (dataType == 3) {
            dataTypeId = "7";
            commonCellDataType = CommonCellDataType.STRING;
        } else if (dataType == 9) {
            dataTypeId = "9";
            commonCellDataType = CommonCellDataType.STRING;
        } else if (dataType == 6) {
            dataTypeId = "10";
            commonCellDataType = CommonCellDataType.STRING;
        } else {
            commonCellDataType = CommonCellDataType.STRING;
        }
        c.setDataTypeId(dataTypeId);
        c.setCommonDataType(commonCellDataType);
    }

    private static int toGridDataType(Cell cell) {
        String dataTypeId = cell.getDataTypeId();
        CommonCellDataType commonCellDataType = cell.getCommonDataType();
        if (StringUtils.isEmpty((String)dataTypeId) && commonCellDataType == null) {
            return 0;
        }
        if (!StringUtils.isEmpty((String)dataTypeId)) {
            if (dataTypeId.equals("0")) {
                return 1;
            }
            if (dataTypeId.equals("1")) {
                return 2;
            }
            if (dataTypeId.equals("2")) {
                return 4;
            }
            if (dataTypeId.equals("3")) {
                return 5;
            }
            if (dataTypeId.equals("auto")) {
                return 0;
            }
            if (dataTypeId.equals("7")) {
                return 3;
            }
            if (dataTypeId.equals("9")) {
                return 9;
            }
            if (dataTypeId.equals("10")) {
                return 6;
            }
            return 1;
        }
        if (commonCellDataType == CommonCellDataType.STRING) {
            return 1;
        }
        if (commonCellDataType == CommonCellDataType.NUMBER) {
            return 2;
        }
        if (commonCellDataType == CommonCellDataType.BOOLEAN) {
            return 4;
        }
        if (commonCellDataType == CommonCellDataType.DATE) {
            return 5;
        }
        return 1;
    }

    private static CellColor getCellColor(int colorValue) {
        if (Html.isTransparent((int)colorValue)) {
            CellColor cellcolor = new CellColor(0, 0, 0, 255);
            return cellcolor;
        }
        CellColor cellcolor = new CellColor(colorValue);
        return cellcolor;
    }

    private static CellColor getCellColor(int colorValue, int alpha) {
        if (Html.isTransparent((int)colorValue)) {
            CellColor cellcolor = new CellColor(0, 0, 0, 255);
            return cellcolor;
        }
        alpha = (int)Math.round((double)(alpha * 255) / 100.0);
        CellColor cellcolor = new CellColor(colorValue, alpha ^ 0xFF);
        return cellcolor;
    }

    private static int getGridColor(CellColor cellColor) {
        if (cellColor.getAlpha() == 255) {
            return 0x1FFFFFFF;
        }
        return cellColor.getValue();
    }

    private static int getGridAlpha(CellColor cellColor) {
        int alpha = cellColor.getAlpha() ^ 0xFF;
        alpha = Math.round((float)(alpha * 100) / 255.0f);
        return alpha;
    }

    private static String getLinkShowText(GridCell gridCell) {
        String[] linkInfos = gridCell.getLinkInformation();
        if (linkInfos == null || linkInfos.length < 3) {
            return "";
        }
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            return Html.encodeText((String)linkInfos[0]);
        }
        return "";
    }

    private static void fillMergeCellLeftEdge(CellField cellField, CellEdger cellEdger, CellSheet cellSheet) {
        for (int row = cellField.top; row < cellField.bottom + 1; ++row) {
            Cell cell = cellSheet.getCell(row - 1, cellField.left - 1);
            if (null != cell.getLeftBorderColor()) {
                cellEdger.setLeftColor(row - cellField.top, CellBookGriddataConverter.getGridColor(cell.getLeftBorderColor()));
            }
            cellEdger.setLeftStyle(row - cellField.top, CellBookGriddataConverter.toGridBorderStyle(cell.getLeftBorderStyle()));
        }
    }

    private static void fillMergeCellRightEdge(CellField cellField, CellEdger cellEdger, CellSheet cellSheet) {
        for (int row = cellField.top; row < cellField.bottom + 1; ++row) {
            Cell cell = cellSheet.getCell(row - 1, cellField.right - 1);
            if (null != cell.getRightBorderColor()) {
                cellEdger.setRightColor(row - cellField.top, CellBookGriddataConverter.getGridColor(cell.getRightBorderColor()));
            }
            cellEdger.setRightStyle(row - cellField.top, CellBookGriddataConverter.toGridBorderStyle(cell.getRightBorderStyle()));
        }
    }

    private static void fillMergeCellTopEdge(CellField cellField, CellEdger cellEdger, CellSheet cellSheet) {
        for (int col = cellField.left; col < cellField.right + 1; ++col) {
            Cell cell = cellSheet.getCell(cellField.top - 1, col - 1);
            if (null != cell.getTopBorderColor()) {
                cellEdger.setTopColor(col - cellField.left, CellBookGriddataConverter.getGridColor(cell.getTopBorderColor()));
            }
            cellEdger.setTopStyle(col - cellField.left, CellBookGriddataConverter.toGridBorderStyle(cell.getTopBorderStyle()));
        }
    }

    private static void fillMergeCellBottomEdge(CellField cellField, CellEdger cellEdger, CellSheet cellSheet) {
        for (int col = cellField.left; col < cellField.right + 1; ++col) {
            Cell cell = cellSheet.getCell(cellField.bottom - 1, col - 1);
            if (null != cell.getBottomBorderColor()) {
                cellEdger.setBottomColor(col - cellField.left, CellBookGriddataConverter.getGridColor(cell.getBottomBorderColor()));
            }
            cellEdger.setBottomStyle(col - cellField.left, CellBookGriddataConverter.toGridBorderStyle(cell.getBottomBorderStyle()));
        }
    }
}

