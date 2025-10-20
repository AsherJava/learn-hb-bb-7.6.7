/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFFont
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Color
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 */
package com.jiuqi.nr.designer.excel.importexcel;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.designer.excel.importexcel.FormulaObjImportHelper;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.common.ColorInfo;
import com.jiuqi.nr.designer.excel.importexcel.common.ExcelType;
import com.jiuqi.nr.designer.excel.importexcel.common.FloatRegionInfo;
import com.jiuqi.nr.designer.excel.importexcel.util.FormHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormObjStyleImportHelper {
    @Autowired
    private FormHelper formHelper;
    @Autowired
    private FormulaObjImportHelper formulaObjImportHelper;
    private static final String FONT_NAME = "\u5fae\u8f6f\u96c5\u9ed1";
    private static final String BORDER_COLOR_DEFAULT = "D4D4D4";
    private static final String BACK_COLOR_DEFAULT_HEADER = "EBEBEB";
    private static final String BACK_COLOR_DEFAULT_CELL = "FFFFFF";
    private static final String FONT_COLOR_DEFAULT = "494949";
    private static final int FONT_SIZE = 16;
    private static final String FLOAT_MESS = "floatMess";
    private static final String ROW_COUNT = "rowCount";
    private static final String COL_COUNT = "colCount";
    private static final int MAX_EMPTY = 30;
    private Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public Grid2Data excelPrecessFormStyle(Workbook workbook, ExcelType excelType, Sheet sheet, ExcelImportContext importContext) {
        int rowNum;
        Grid2Data griddata = new Grid2Data();
        int fontWidth = 8;
        Map<String, Integer> rsMap = this.setRowsCountAndFloatRegion(sheet, griddata);
        int rowNumCount = rsMap.get(ROW_COUNT);
        int colNumCount = rsMap.get(COL_COUNT);
        griddata.setHeaderColumnCount(1);
        griddata.setHeaderRowCount(1);
        griddata.setFooterColumnCount(0);
        griddata.setFooterRowCount(0);
        griddata.setRowCount(rowNumCount);
        griddata.setColumnCount(colNumCount);
        int girdRow = 1;
        boolean reduceRow = false;
        for (rowNum = 0; rowNum < rowNumCount; ++rowNum) {
            if (rowNum == 0) {
                griddata.setRowHeight(rowNum, 22);
                griddata.setRowHidden(rowNum, false);
                griddata.setRowAutoHeight(rowNum, false);
                griddata.setRowResizeEnabled(true);
                continue;
            }
            Row row = sheet.getRow(rowNum - 1);
            if (row != null) {
                griddata.setRowHeight(girdRow, row.getHeight() / 20 * 96 / 72);
                griddata.setRowHidden(girdRow, row.getRowStyle() == null ? false : row.getRowStyle().getHidden());
                griddata.setRowAutoHeight(girdRow, row.getZeroHeight());
            }
            ++girdRow;
        }
        for (int colNum = 0; colNum < colNumCount; ++colNum) {
            if (colNum == 0) {
                griddata.setColumnWidth(colNum, 36);
                griddata.setColumnAutoWidth(colNum, false);
                griddata.setColumnHidden(colNum, false);
                griddata.setColumnResizeEnabled(true);
                continue;
            }
            int width = (int)((double)sheet.getColumnWidth(colNum - 1) / 256.0 * (double)fontWidth + 1.0);
            griddata.setColumnWidth(colNum, width);
            griddata.setColumnAutoWidth(colNum, false);
            griddata.setColumnHidden(colNum, sheet.getColumnStyle(colNum - 1) == null ? false : sheet.getColumnStyle(colNum - 1).getHidden());
        }
        girdRow = 0;
        reduceRow = false;
        for (rowNum = 0; rowNum < rowNumCount; ++rowNum) {
            for (int colNum = 0; colNum < colNumCount; ++colNum) {
                Cell cell;
                GridCellData gridCellData = null;
                if (rowNum == 0 || colNum == 0) {
                    gridCellData = this.initHeaderRowOrCol(colNum, rowNum);
                    continue;
                }
                Row row = sheet.getRow(rowNum - 1);
                gridCellData = row == null ? this.initDefaultGridCellData(colNum, girdRow) : ((cell = row.getCell(colNum - 1)) == null ? this.initDefaultGridCellData(colNum, girdRow) : this.initGridCellData(colNum, girdRow, cell, sheet, workbook, excelType, importContext));
                griddata.setGridCellData(gridCellData, gridCellData.getRowIndex(), gridCellData.getColIndex());
            }
            ++girdRow;
        }
        int sheetmergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetmergerCount; ++i) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstC = ca.getFirstColumn() + 1;
            int lastC = ca.getLastColumn() + 1;
            int firstR = ca.getFirstRow() + 1;
            int lastR = ca.getLastRow() + 1;
            griddata.mergeCells(firstC, firstR, lastC, lastR);
        }
        return griddata;
    }

    public void createFloatRegionByOnlyOneRow(Grid2Data grid2Data, ExcelImportContext importContext) {
        GridCellData gridCellData;
        int i;
        if (grid2Data.getRowCount() != 2) {
            return;
        }
        int colCount = grid2Data.getColumnCount();
        for (i = 1; i < colCount; ++i) {
            gridCellData = grid2Data.getGridCellData(i, 1);
            if (!StringUtils.isEmpty((String)gridCellData.getShowText())) continue;
            return;
        }
        grid2Data.setRowCount(3);
        for (i = 0; i < colCount; ++i) {
            gridCellData = null;
            gridCellData = i == 0 ? this.initHeaderRowOrCol(i, 2) : this.initDefaultGridCellData(i, 2);
            grid2Data.setGridCellData(gridCellData, gridCellData.getRowIndex(), gridCellData.getColIndex());
        }
        importContext.getFloatMess().put(1, new FloatRegionInfo(1, 1, 1));
    }

    private int getColNumCount(Sheet sheet, int rowNumCount) {
        short colCount = 0;
        for (int i = 0; i < rowNumCount; ++i) {
            Row row = sheet.getRow(i);
            if (null == row || colCount >= row.getLastCellNum()) continue;
            colCount = row.getLastCellNum();
        }
        return colCount;
    }

    private Map<String, Integer> setRowsCountAndFloatRegion(Sheet sheet, Grid2Data grid2Data) {
        int rowNumCount = sheet.getLastRowNum() + 2;
        int colNumCount = this.getColNumCount(sheet, rowNumCount) + 1;
        int truncationRow = rowNumCount;
        int truncationCol = colNumCount;
        int emptyRowCount = 0;
        for (int rowNum = 0; rowNum < rowNumCount; ++rowNum) {
            Row row = sheet.getRow(rowNum);
            if (null == row) {
                ++emptyRowCount;
                continue;
            }
            Boolean isRowEmpty = true;
            for (int colNum = 0; colNum < colNumCount; ++colNum) {
                Cell cell = row.getCell(colNum);
                if (null == cell) continue;
                String text = "";
                if (cell.getCellType() == CellType.NUMERIC) {
                    text = String.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    text = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    text = String.valueOf(cell.getBooleanCellValue());
                }
                if (!StringUtils.isNotEmpty((String)text)) continue;
                isRowEmpty = false;
                break;
            }
            emptyRowCount = isRowEmpty.booleanValue() ? ++emptyRowCount : 0;
            if (emptyRowCount < 30) continue;
            truncationRow = rowNum - 30 + 3;
            break;
        }
        int emptyColCount = 0;
        for (int colNum = 0; colNum < colNumCount; ++colNum) {
            Boolean isColEmpty = true;
            for (int rowNum = 0; rowNum < rowNumCount; ++rowNum) {
                Cell cell;
                Row row = sheet.getRow(rowNum);
                if (null == row || null == (cell = row.getCell(colNum))) continue;
                String text = "";
                if (cell.getCellType() == CellType.NUMERIC) {
                    text = String.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    text = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    text = String.valueOf(cell.getBooleanCellValue());
                }
                if (!StringUtils.isNotEmpty((String)text)) continue;
                isColEmpty = false;
                break;
            }
            emptyColCount = isColEmpty.booleanValue() ? ++emptyColCount : 0;
            if (emptyColCount < 30) continue;
            truncationCol = colNum - 30 + 3;
            break;
        }
        HashMap<String, Integer> rsMap = new HashMap<String, Integer>();
        rsMap.put(ROW_COUNT, truncationRow);
        rsMap.put(COL_COUNT, truncationCol);
        return rsMap;
    }

    private Map<Integer, FloatRegionInfo> checkFloatRow(Sheet sheet, boolean[] isFloatRowArr) {
        ArrayList<Integer> floatNum = new ArrayList<Integer>();
        HashMap<Integer, Integer> floatMess = new HashMap<Integer, Integer>();
        for (int i = 0; i < isFloatRowArr.length; ++i) {
            if (isFloatRowArr[i]) {
                floatNum.add(i);
                continue;
            }
            if (floatNum.size() > 1) {
                floatMess.put((Integer)floatNum.get(0), floatNum.size());
            }
            floatNum.clear();
        }
        if (floatNum.size() > 0) {
            if (floatNum.size() > 1) {
                floatMess.put((Integer)floatNum.get(0), floatNum.size());
            } else if ((Integer)floatNum.get(0) == isFloatRowArr.length - 1) {
                floatMess.put((Integer)floatNum.get(0), floatNum.size());
            }
            floatNum.clear();
        }
        Map<Integer, FloatRegionInfo> finalfloatRows = this.compareRowAndCol(sheet, floatMess, isFloatRowArr.length);
        return finalfloatRows;
    }

    private Map<Integer, FloatRegionInfo> compareRowAndCol(Sheet sheet, Map<Integer, Integer> floatMess, int rowCount) {
        Map<Integer, HashMap<Integer, GridCellData>> rspanAndCspanMap = this.initRowSpanAndColSpan(sheet);
        HashMap<Integer, FloatRegionInfo> finalfloatRows = new HashMap<Integer, FloatRegionInfo>();
        for (Map.Entry<Integer, Integer> entry : floatMess.entrySet()) {
            int regionTop = entry.getKey();
            int height = entry.getValue();
            if (regionTop + 1 == rowCount && height == 1) {
                finalfloatRows.put(regionTop, new FloatRegionInfo(regionTop, height, 1));
                break;
            }
            HashMap<Integer, GridCellData> spanMap = this.getRowSpanByRegionTop(rspanAndCspanMap.get(regionTop), regionTop);
            int maxRowSpan = 1;
            if (spanMap != null && spanMap.size() > 0) {
                for (GridCellData gridCellData : spanMap.values()) {
                    if (gridCellData.getRowSpan() <= 1 || maxRowSpan >= gridCellData.getRowSpan()) continue;
                    maxRowSpan = gridCellData.getRowSpan();
                }
            } else if (!this.formHelper.mapIsEmpty(rspanAndCspanMap)) {
                boolean isJumpOut = false;
                block2: for (HashMap<Integer, GridCellData> spanCell : rspanAndCspanMap.values()) {
                    for (GridCellData gridCellData : spanCell.values()) {
                        int rowIndex;
                        int rowSpan = gridCellData.getRowSpan();
                        boolean isContainMarginCell = this.isContainMargin(rowSpan, rowIndex = gridCellData.getRowIndex(), regionTop, height);
                        if (!isContainMarginCell || rowIndex == regionTop && rowIndex + rowSpan == regionTop + height) continue;
                        isJumpOut = true;
                        continue block2;
                    }
                }
                if (isJumpOut) continue;
            }
            int equalCount = 1;
            boolean rowIsEqual = true;
            block4: while (maxRowSpan * (equalCount + 1) <= height && rowIsEqual) {
                for (int rowNum = regionTop; rowNum < regionTop + maxRowSpan && rowIsEqual; ++rowNum) {
                    Row currRow = sheet.getRow(rowNum);
                    Row nextRow = sheet.getRow(rowNum + maxRowSpan * equalCount);
                    if (currRow == null && nextRow == null) {
                        if (!rowIsEqual || rowNum != regionTop + maxRowSpan - 1) continue;
                        ++equalCount;
                        continue;
                    }
                    if (currRow == null && nextRow != null || currRow != null && nextRow == null) {
                        if (currRow == null && nextRow != null && !this.formHelper.mapIsEmpty((Map<? extends Object, ? extends Object>)rspanAndCspanMap.get(nextRow.getRowNum())) || currRow != null && !this.formHelper.mapIsEmpty((Map<? extends Object, ? extends Object>)rspanAndCspanMap.get(currRow.getRowNum())) && nextRow == null) {
                            rowIsEqual = false;
                            continue block4;
                        }
                        if (!rowIsEqual || rowNum != regionTop + maxRowSpan - 1) continue;
                        ++equalCount;
                        continue;
                    }
                    if (currRow != null && nextRow != null) {
                        int currColCount = currRow.getLastCellNum();
                        short nextColCount = nextRow.getLastCellNum();
                        if (currColCount == -1 && nextColCount == -1) {
                            if (!rowIsEqual || rowNum != regionTop + maxRowSpan - 1) continue;
                            ++equalCount;
                            continue;
                        }
                        if (currColCount == -1 || nextColCount == -1) {
                            if (currColCount == -1 && nextColCount != -1 && !this.formHelper.mapIsEmpty((Map<? extends Object, ? extends Object>)rspanAndCspanMap.get(nextRow.getRowNum())) || currColCount != -1 && !this.formHelper.mapIsEmpty((Map<? extends Object, ? extends Object>)rspanAndCspanMap.get(currRow.getRowNum())) && nextColCount == -1) {
                                rowIsEqual = false;
                                continue block4;
                            }
                        } else if (currColCount != nextColCount) {
                            rowIsEqual = false;
                            continue block4;
                        }
                        for (int colNum = 0; colNum < currColCount; ++colNum) {
                            GridCellData nextMergeCell;
                            Cell currCell = currRow.getCell(colNum);
                            Cell nextCell = nextRow.getCell(colNum);
                            if (currCell == null && nextCell == null) continue;
                            if (currCell == null && nextCell != null && !this.currCellIsEmpty(nextCell) || currCell != null && !this.currCellIsEmpty(currCell) && nextCell == null) {
                                rowIsEqual = false;
                                break;
                            }
                            if (currCell == null || nextCell == null) continue;
                            GridCellData currMergeCell = rspanAndCspanMap.get(currCell.getRowIndex()) == null ? null : rspanAndCspanMap.get(currCell.getRowIndex()).get(currCell.getColumnIndex());
                            GridCellData gridCellData = nextMergeCell = rspanAndCspanMap.get(nextCell.getRowIndex()) == null ? null : rspanAndCspanMap.get(nextCell.getRowIndex()).get(nextCell.getColumnIndex());
                            if (currMergeCell == null && nextMergeCell == null) continue;
                            if (currMergeCell != null && nextMergeCell == null || currMergeCell == null && nextMergeCell != null) {
                                rowIsEqual = false;
                                break;
                            }
                            if (currMergeCell == null || nextMergeCell == null || currMergeCell.getColSpan() == nextMergeCell.getColSpan() && currMergeCell.getRowSpan() == nextMergeCell.getRowSpan()) continue;
                            rowIsEqual = false;
                            break;
                        }
                    }
                    if (!rowIsEqual || rowNum != regionTop + maxRowSpan - 1) continue;
                    ++equalCount;
                }
            }
            if (equalCount <= 1) continue;
            finalfloatRows.put(regionTop, new FloatRegionInfo(regionTop, maxRowSpan, equalCount));
        }
        return finalfloatRows;
    }

    private HashMap<Integer, GridCellData> getRowSpanByRegionTop(HashMap<Integer, GridCellData> spanMap, int regionTop) {
        HashMap<Integer, GridCellData> rowSpanMap = new HashMap<Integer, GridCellData>();
        if (!this.formHelper.mapIsEmpty(spanMap)) {
            for (Map.Entry<Integer, GridCellData> entry : spanMap.entrySet()) {
                int col = entry.getKey();
                GridCellData spanCell = entry.getValue();
                if (spanCell.getRowSpan() <= 1) continue;
                rowSpanMap.put(col, spanCell);
            }
        }
        return rowSpanMap;
    }

    private boolean isContainMargin(int rowSpan, int rowIndex, int regionTop, int height) {
        HashSet<Integer> set1 = new HashSet<Integer>();
        HashSet<Integer> set2 = new HashSet<Integer>();
        for (int i = rowIndex; i < rowSpan + rowIndex; ++i) {
            set1.add(i);
        }
        for (int k = regionTop; k < regionTop + height; ++k) {
            set2.add(k);
        }
        set1.retainAll(set2);
        return set1.size() > 0;
    }

    private GridCellData initHeaderRowOrCol(int col, int row) {
        GridCellData gridCellData = new GridCellData(col, row);
        if (row == 0) {
            if (col == 0) {
                gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
                gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            } else {
                String colTitle = this.formHelper.getColumnTitle(col);
                gridCellData.setShowText(colTitle);
                gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_HEADER, 16));
                gridCellData.setBackGroundStyle(0);
                gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            }
        } else if (col == 0) {
            gridCellData.setShowText(String.valueOf(row));
            gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_HEADER, 16));
            gridCellData.setBackGroundStyle(0);
            gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
            gridCellData.setFontSize(16);
        }
        gridCellData.setRightBorderStyle(0);
        gridCellData.setBottomBorderStyle(0);
        gridCellData.setFontName(FONT_NAME);
        gridCellData.setHorzAlign(3);
        gridCellData.setVertAlign(3);
        return gridCellData;
    }

    private GridCellData initDefaultGridCellData(int col, int row) {
        GridCellData gridCellData = new GridCellData(col, row);
        gridCellData.setMerged(false);
        gridCellData.setEditable(true);
        gridCellData.setBackGroundColor(Integer.parseInt(BACK_COLOR_DEFAULT_CELL, 16));
        gridCellData.setBackGroundStyle(0);
        gridCellData.setRightBorderStyle(-1);
        gridCellData.setBottomBorderStyle(-1);
        gridCellData.setRightBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
        gridCellData.setBottomBorderColor(Integer.parseInt(BORDER_COLOR_DEFAULT, 16));
        gridCellData.setFontName(FONT_NAME);
        gridCellData.setForeGroundColor(Integer.parseInt(FONT_COLOR_DEFAULT, 16));
        gridCellData.setFontSize(16);
        gridCellData.setWrapLine(true);
        gridCellData.setHorzAlign(0);
        gridCellData.setVertAlign(0);
        gridCellData.setFitFontSize(false);
        return gridCellData;
    }

    private GridCellData initGridCellData(int col, int row, Cell cell, Sheet sheet, Workbook workbook, ExcelType excelType, ExcelImportContext importContext) {
        if (excelType == ExcelType.XLSHXXF) {
            HSSFCellStyle cellStyle = (HSSFCellStyle)cell.getCellStyle();
            HSSFFont font = cellStyle.getFont(workbook);
            font.getHSSFColor((HSSFWorkbook)workbook);
            return this.initGridCellDataByXSSFOrHSSF(workbook, sheet, cell, (CellStyle)cellStyle, (Font)font, col, row, importContext);
        }
        if (excelType == ExcelType.XLSXXSSF) {
            XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
            XSSFFont font = cellStyle.getFont();
            return this.initGridCellDataByXSSFOrHSSF(workbook, sheet, cell, (CellStyle)cellStyle, (Font)font, col, row, importContext);
        }
        return null;
    }

    private GridCellData initGridCellDataByXSSFOrHSSF(Workbook workbook, Sheet sheet, Cell cell, CellStyle cellStyle, Font font, int col, int row, ExcelImportContext importContext) {
        GridCellData gridCellData = new GridCellData(col, row);
        String text = "";
        if (cell.getCellType() == CellType.NUMERIC) {
            if (cell.getNumericCellValue() % 1.0 == 0.0) {
                long codeTmp = (long)cell.getNumericCellValue();
                text = String.valueOf(codeTmp);
            } else {
                text = String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.STRING) {
            text = String.valueOf(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            text = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            this.formulaObjImportHelper.createExcelFormulaCache(cell, importContext);
        }
        Matcher m = this.p.matcher(text);
        if (!StringUtils.isEmpty((String)text)) {
            gridCellData.setShowText(text);
            gridCellData.setEditText(text);
        }
        this.isMergedRegion(sheet, cell, gridCellData);
        gridCellData.setEditable(true);
        if (cellStyle.getFillPattern() == FillPatternType.SOLID_FOREGROUND) {
            gridCellData.setBackGroundColor(this.transBackGroundColor(cellStyle.getFillForegroundColorColor()));
        }
        gridCellData.setRightBorderStyle(this.poiBorderStyleTransGridStyle(cellStyle.getBorderRight()));
        gridCellData.setBottomBorderStyle(this.poiBorderStyleTransGridStyle(cellStyle.getBorderBottom()));
        if (cellStyle instanceof XSSFCellStyle) {
            XSSFCellStyle xSSFCellStyle = (XSSFCellStyle)cellStyle;
            int rightColor = this.transBorderColorByXSSF(cellStyle, (Color)xSSFCellStyle.getRightBorderXSSFColor(), cellStyle.getBorderRight());
            int bottomColor = this.transBorderColorByXSSF(cellStyle, (Color)xSSFCellStyle.getBottomBorderXSSFColor(), cellStyle.getBorderRight());
            if (rightColor == 0xFFFFFF) {
                gridCellData.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            if (bottomColor == 0xFFFFFF) {
                gridCellData.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            gridCellData.setRightBorderColor(rightColor);
            gridCellData.setBottomBorderColor(bottomColor);
        } else {
            int rightColor = this.transBorderColorByHSSF(workbook, cellStyle, cellStyle.getRightBorderColor());
            int bottomColor = this.transBorderColorByHSSF(workbook, cellStyle, cellStyle.getBottomBorderColor());
            if (rightColor == 0xFFFFFF) {
                gridCellData.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            if (bottomColor == 0xFFFFFF) {
                gridCellData.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
            }
            gridCellData.setRightBorderColor(rightColor);
            gridCellData.setBottomBorderColor(bottomColor);
        }
        gridCellData.setFontName(font.getFontName());
        gridCellData.setForeGroundColor(this.transFontColor(workbook, font));
        gridCellData.setFontSize(font.getFontHeightInPoints() * 96 / 72);
        int fontStyle = 0;
        if (font.getBold()) {
            fontStyle ^= 2;
        }
        if (font.getItalic()) {
            fontStyle ^= 4;
        }
        if (font.getUnderline() == 1) {
            fontStyle ^= 8;
        }
        gridCellData.setFontStyle(fontStyle);
        gridCellData.setWrapLine(cellStyle.getWrapText());
        gridCellData.setIndent((int)cellStyle.getIndention());
        if (cellStyle.getAlignment() == HorizontalAlignment.LEFT) {
            gridCellData.setHorzAlign(1);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.CENTER) {
            gridCellData.setHorzAlign(3);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.RIGHT) {
            gridCellData.setHorzAlign(2);
        } else if (cellStyle.getAlignment() == HorizontalAlignment.GENERAL) {
            String checkNumber = "^[0-9]*$";
            if (StringUtils.isNotEmpty((String)text)) {
                if (Pattern.compile(checkNumber).matcher(text).matches()) {
                    gridCellData.setHorzAlign(2);
                } else {
                    gridCellData.setHorzAlign(1);
                }
            }
        }
        if (cellStyle.getVerticalAlignment() == VerticalAlignment.TOP) {
            gridCellData.setVertAlign(1);
        } else if (cellStyle.getVerticalAlignment() == VerticalAlignment.CENTER) {
            gridCellData.setVertAlign(3);
        } else if (cellStyle.getVerticalAlignment() == VerticalAlignment.BOTTOM) {
            gridCellData.setVertAlign(2);
        }
        gridCellData.setMultiLine(cellStyle.getWrapText());
        return gridCellData;
    }

    private int transBackGroundColor(Color color) {
        ColorInfo bgColor = this.excelColor2UOF(color);
        if (bgColor == null) {
            bgColor = new ColorInfo(0, 255, 255, 255);
        }
        if (bgColor.R == 0 && bgColor.G == 0 && bgColor.B == 0) {
            bgColor.R = 255;
            bgColor.G = 255;
            bgColor.B = 255;
        }
        return this.rgbColorToInt(bgColor);
    }

    private void isMergedRegion(Sheet sheet, Cell cell, GridCellData gridCellData) {
        int col = cell.getColumnIndex();
        int row = cell.getRowIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row != firstRow || row > lastRow || col != firstColumn || col > lastColumn) continue;
            gridCellData.setRowSpan(lastRow - firstRow + 1);
            gridCellData.setColSpan(lastColumn - firstColumn + 1);
            gridCellData.setMerged(true);
        }
        gridCellData.setMerged(false);
    }

    private Map<Integer, HashMap<Integer, GridCellData>> initRowSpanAndColSpan(Sheet sheet) {
        HashMap<Integer, HashMap<Integer, GridCellData>> rspanAndCspanMap = new HashMap<Integer, HashMap<Integer, GridCellData>>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            int lastRow;
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            if (firstRow > (lastRow = range.getLastRow()) || firstColumn > lastColumn) continue;
            GridCellData gridCellData = new GridCellData(firstColumn, firstRow);
            gridCellData.setRowSpan(lastRow - firstRow + 1);
            gridCellData.setColSpan(lastColumn - firstColumn + 1);
            gridCellData.setMerged(true);
            HashMap<Integer, GridCellData> cspanMaps = (HashMap<Integer, GridCellData>)rspanAndCspanMap.get(firstRow);
            if (null == cspanMaps) {
                cspanMaps = new HashMap<Integer, GridCellData>();
            }
            cspanMaps.put(firstColumn, gridCellData);
            rspanAndCspanMap.put(firstRow, cspanMaps);
        }
        return rspanAndCspanMap;
    }

    private int poiBorderStyleTransGridStyle(BorderStyle poiBorderStyle) {
        if (poiBorderStyle == BorderStyle.THIN) {
            return GridEnums.GridBorderStyle.SOLID.getStyle();
        }
        if (poiBorderStyle == BorderStyle.DASHED) {
            return GridEnums.GridBorderStyle.DASH.getStyle();
        }
        if (poiBorderStyle == BorderStyle.THICK) {
            return GridEnums.GridBorderStyle.BOLD.getStyle();
        }
        if (poiBorderStyle == BorderStyle.DOUBLE) {
            return GridEnums.GridBorderStyle.DOUBLE.getStyle();
        }
        return GridEnums.GridBorderStyle.AUTO.getStyle();
    }

    private int transBorderColorByXSSF(CellStyle cellStyle, Color color, BorderStyle poiBorderStyle) {
        ColorInfo ci = null;
        if (cellStyle instanceof XSSFCellStyle) {
            ci = this.excelColor2UOF(color);
            if (ci == null) {
                ci = poiBorderStyle == BorderStyle.THIN ? new ColorInfo(0, 0, 0, 0) : new ColorInfo(0, 212, 212, 212);
            } else if (ci.A == -1 && ci.R == 0 && ci.G == 0 && ci.B == 0) {
                ci = new ColorInfo(0, 212, 212, 212);
            }
        }
        return this.rgbColorToInt(ci);
    }

    private int transBorderColorByHSSF(Workbook workbook, CellStyle cellStyle, short borderColor) {
        ColorInfo ci = null;
        if (cellStyle instanceof HSSFCellStyle && (ci = this.excel97Color2UOF(workbook, borderColor)) == null) {
            ci = new ColorInfo(0, 212, 212, 212);
        }
        return this.rgbColorToInt(ci);
    }

    private int rgbColorToInt(ColorInfo color) {
        String colorR = Integer.toHexString(color.R).equals("0") ? "00" : Integer.toHexString(color.R);
        String colorG = Integer.toHexString(color.G).equals("0") ? "00" : Integer.toHexString(color.G);
        String colorB = Integer.toHexString(color.B).equals("0") ? "00" : Integer.toHexString(color.B);
        StringBuffer rgb = new StringBuffer();
        rgb.append(colorR).append(colorG).append(colorB);
        return Integer.parseInt(rgb.toString(), 16);
    }

    private int transFontColor(Workbook workbook, Font font) {
        ColorInfo color = null;
        if (font instanceof XSSFFont) {
            XSSFFont f = (XSSFFont)font;
            color = this.excelColor2UOF((Color)f.getXSSFColor());
            if (color != null) {
                if (color.R == 255 && color.G == 255 && color.B == 255) {
                    color.R = 0;
                    color.G = 0;
                    color.B = 0;
                }
            } else {
                color = new ColorInfo(0, 73, 73, 73);
            }
        } else {
            color = this.excel97Color2UOF(workbook, font.getColor());
        }
        if (color == null) {
            color = new ColorInfo(0, 73, 73, 73);
        }
        return this.rgbColorToInt(color);
    }

    private ColorInfo excelColor2UOF(Color color) {
        HSSFColor hc;
        short[] s;
        if (color == null) {
            return null;
        }
        ColorInfo ci = null;
        if (color instanceof XSSFColor) {
            XSSFColor xc = (XSSFColor)color;
            byte[] b = xc.getARGB();
            if (b != null) {
                if (b.length == 4) {
                    ci = ColorInfo.fromARGB((short)(b[0] & 0xFF), (short)(b[1] & 0xFF), (short)(b[2] & 0xFF), (short)(b[3] & 0xFF));
                } else if (b.length == 3) {
                    ci = ColorInfo.fromARGB((short)(b[0] & 0xFF), (short)(b[1] & 0xFF), (short)(b[2] & 0xFF));
                }
            }
        } else if (color instanceof HSSFColor && (s = (hc = (HSSFColor)color).getTriplet()) != null) {
            ci = ColorInfo.fromARGB(s[0], s[1], s[2]);
        }
        return ci;
    }

    private ColorInfo excel97Color2UOF(Workbook book, short color) {
        HSSFWorkbook hb;
        HSSFColor hc;
        if (book instanceof HSSFWorkbook && (hc = (hb = (HSSFWorkbook)book).getCustomPalette().getColor(color)) != null) {
            return this.excelColor2UOF((Color)hc);
        }
        return null;
    }

    private boolean currRowIsEmpty(Row currRow) {
        int currColCount = currRow.getLastCellNum();
        for (int colNum = 0; colNum < currColCount; ++colNum) {
            Cell currCell = currRow.getCell(colNum);
            if (this.currCellIsEmpty(currCell)) continue;
            return false;
        }
        return true;
    }

    private boolean currCellIsEmpty(Cell cell) {
        String text = "";
        if (cell.getCellType() == CellType.NUMERIC) {
            text = String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.STRING) {
            text = String.valueOf(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            text = String.valueOf(cell.getBooleanCellValue());
        }
        return StringUtils.isEmpty((String)text);
    }
}

