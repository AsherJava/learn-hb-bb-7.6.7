/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$TextAlignment
 *  org.json.JSONException
 */
package com.jiuqi.nvwa.cellbook.converter;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.converter.ICellBookGrid2DataConverterProvider;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.math.BigInteger;
import java.util.List;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBookGrid2dataConverter {
    private static final String BACKGROUND_ALPHA = "bga_cellbook";
    private static final String RIGHTBORDER_STYLE = "rbs_cellbook";
    private static final String BOTTOMBORDER_STYLE = "bbs_cellbook";
    private static final Logger logger = LoggerFactory.getLogger(CellBookGrid2dataConverter.class);

    public static Grid2Data cellBookToGrid2Data(CellSheet cellSheet, Grid2Data grid2Data) {
        return CellBookGrid2dataConverter.cellBookToGrid2Data(cellSheet, grid2Data, null);
    }

    public static Grid2Data cellBookToGrid2Data(CellSheet cellSheet, Grid2Data grid2Data, ICellBookGrid2DataConverterProvider provider) {
        if (provider != null) {
            cellSheet = provider.beforeCellSheetToGrid2Data(grid2Data, cellSheet);
        }
        int columnCount = cellSheet.getColumnCount();
        int rowCount = cellSheet.getRowCount();
        grid2Data.setColumnCount(columnCount);
        grid2Data.setRowCount(rowCount);
        grid2Data.setHeaderColumnCount(cellSheet.getHeaderColCount());
        grid2Data.setHeaderRowCount(cellSheet.getHeaderRowCount());
        grid2Data.setFooterColumnCount(cellSheet.getFooterColCount());
        grid2Data.setFooterRowCount(cellSheet.getFooterRowCount());
        for (int i = 0; i < rowCount; ++i) {
            grid2Data.setRowHeight(i, cellSheet.getRowHeight(i));
            grid2Data.setRowHidden(i, cellSheet.getRowHidden(i));
            grid2Data.setRowAutoHeight(i, cellSheet.getRowAutoHeight(i));
            for (int j = 0; j < columnCount; ++j) {
                if (i == 0) {
                    grid2Data.setColumnWidth(j, cellSheet.getColWide(j));
                    grid2Data.setColumnHidden(j, cellSheet.getColHidden(j));
                    grid2Data.setColumnAutoWidth(j, cellSheet.getColAutoWide(j));
                }
                Cell cell = cellSheet.getCell(i, j);
                GridCellData gridCellData = grid2Data.getGridCellData(j, i);
                if (provider != null) {
                    cell = provider.beforeCellToGridCellData(gridCellData, cell);
                }
                CellBookGrid2dataConverter.copyCellData(cell, gridCellData, 1);
                if (provider == null) continue;
                gridCellData = provider.afterCellToGridCellData(gridCellData, cell);
            }
        }
        List merges = cellSheet.getMerges();
        if (merges != null) {
            for (CellMerge cellMerge : merges) {
                int left = cellMerge.getColumnIndex();
                int top = cellMerge.getRowIndex();
                int right = cellMerge.getColumnIndex() + cellMerge.getColumnSpan() - 1;
                int bottom = cellMerge.getRowIndex() + cellMerge.getRowSpan() - 1;
                grid2Data.mergeCells(left, top, right, bottom);
            }
        }
        if (provider != null) {
            grid2Data = provider.afterCellSheetToGrid2Data(grid2Data, cellSheet);
        }
        return grid2Data;
    }

    public static CellSheet grid2DataToCellBook(Grid2Data grid2Data, CellBook cellBook, String cellSheetCode) {
        return CellBookGrid2dataConverter.grid2DataToCellBook(grid2Data, cellBook, cellSheetCode, null);
    }

    public static CellSheet grid2DataToCellBook(Grid2Data grid2Data, CellBook cellBook, String cellSheetCode, String cellSheetTitle) {
        return CellBookGrid2dataConverter.grid2DataToCellBook(grid2Data, cellBook, cellSheetCode, cellSheetTitle, null);
    }

    public static CellSheet grid2DataToCellBook(Grid2Data grid2Data, CellBook cellBook, ICellBookGrid2DataConverterProvider provider, String cellSheetCode) {
        return CellBookGrid2dataConverter.grid2DataToCellBook(grid2Data, cellBook, cellSheetCode, null, provider);
    }

    public static CellSheet grid2DataToCellBook(Grid2Data grid2Data, CellBook cellBook, String cellSheetCode, String cellSheetTitle, ICellBookGrid2DataConverterProvider provider) {
        int columnCount = grid2Data.getColumnCount();
        int rowCount = grid2Data.getRowCount();
        CellSheet cellSheet = cellBook.createSheet(StringUtils.isEmpty((String)cellSheetTitle) ? cellSheetCode : cellSheetTitle, cellSheetCode, rowCount, columnCount);
        if (provider != null) {
            grid2Data = provider.beforeGrid2DataToCellSheet(grid2Data, cellSheet);
        }
        cellSheet.setHeaderColCount(grid2Data.getHeaderColumnCount());
        cellSheet.setHeaderRowCount(grid2Data.getHeaderRowCount());
        cellSheet.setFooterColCount(grid2Data.getFooterColumnCount());
        cellSheet.setFooterRowCount(grid2Data.getFooterRowCount());
        for (int i = 0; i < rowCount; ++i) {
            cellSheet.setRowHeight(i, grid2Data.getRowHeight(i));
            cellSheet.setRowHidden(i, grid2Data.isRowHidden(i));
            cellSheet.setRowAutoHeight(i, grid2Data.isRowAutoHeight(i));
            for (int j = 0; j < columnCount; ++j) {
                if (i == 0) {
                    cellSheet.setColWide(j, grid2Data.getColumnWidth(j));
                    cellSheet.setColHidden(j, grid2Data.isColumnHidden(j));
                    cellSheet.setColAutoWide(j, grid2Data.isColumnAutoWidth(j));
                }
                Cell cell = cellSheet.getCell(i, j);
                GridCellData gridCellData = grid2Data.getGridCellData(j, i);
                if (provider != null) {
                    gridCellData = provider.beforeGridCellDataToCell(gridCellData, cell);
                }
                CellBookGrid2dataConverter.copyCellData(cell, gridCellData, 0);
                if (provider == null) continue;
                provider.afterGridCellDataToCell(gridCellData, cell);
            }
        }
        Grid2FieldList gfl = grid2Data.merges();
        Grid2CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                cellSheet.mergeCells(cf.top, cf.left, cf.bottom - cf.top + 1, cf.right - cf.left + 1);
            }
        }
        if (provider != null) {
            cellSheet = provider.afterGrid2DataToCellSheet(grid2Data, cellSheet);
        }
        return cellSheet;
    }

    private static String getCellBooKColorFormGrid2(int color) {
        return CellBookGrid2dataConverter.getCellBooKColorFormGrid2(color, "");
    }

    private static String getCellBooKColorFormGrid2(int color, String alpha) {
        String hexString = Integer.toHexString(color);
        if (hexString.length() >= 8) {
            hexString = hexString.substring(2, hexString.length()) + hexString.substring(0, 2);
        } else if (hexString.length() < 6) {
            int count = 6 - hexString.length();
            String temp = "";
            for (int i = 0; i < count; ++i) {
                temp = temp + "0";
            }
            hexString = temp + hexString;
        }
        if (StringUtils.isNotEmpty((String)alpha)) {
            hexString = hexString + alpha;
        }
        return hexString;
    }

    private static String getGrid2ColorAlphaFormCellBooK(String hexString) {
        if (hexString.length() > 6) {
            return hexString.substring(6, hexString.length());
        }
        return "";
    }

    private static int getGrid2ColorFormCellBooK(String hexString) {
        if (hexString.length() > 6) {
            hexString = hexString.substring(0, 6);
        }
        BigInteger bi = new BigInteger(hexString, 16);
        return bi.intValue();
    }

    private static void copyCellData(Cell c, GridCellData d, int direction) {
        if (direction == 0) {
            String bottomBoderStyle;
            String rightBoderStyle;
            if (d.isSilverHead()) {
                c.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                CellColor cellColor = new CellColor("#f1f1f1".substring(1));
                c.setBackGroundColor(cellColor);
            } else if (d.getBackGroundColor() != -1 && d.getBackGroundColor() >= 0) {
                c.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                String alpha = d.getPersistenceData(BACKGROUND_ALPHA);
                c.setBackGroundColor(CellBookGrid2dataConverter.getCellBooKColorFormGrid2(d.getBackGroundColor(), alpha));
            }
            if (d.getForeGroundColor() != -1 && d.getForeGroundColor() != 0) {
                c.setFontColor(new CellColor(CellBookGrid2dataConverter.getCellBooKColorFormGrid2(d.getForeGroundColor())));
            }
            if (!d.isSelectable()) {
                c.setModel(CellStyleModel.UNSELECT);
            } else if (d.isEditable()) {
                c.setModel(CellStyleModel.EDIT);
            } else {
                c.setModel(CellStyleModel.READONLY);
            }
            c.setFontSize(d.getFontSize());
            c.setFontName(d.getFontName());
            c.setBold((d.getFontStyle() & 2) != 0);
            c.setItalic((d.getFontStyle() & 4) != 0);
            c.setInline((d.getFontStyle() & 0x10) != 0);
            c.setUnderline((d.getFontStyle() & 8) != 0);
            if (d.getRightBorderColor() != -1) {
                c.setRightBorderColor(new CellColor(CellBookGrid2dataConverter.getCellBooKColorFormGrid2(d.getRightBorderColor())));
            }
            if (StringUtils.isNotEmpty((String)(rightBoderStyle = d.getPersistenceData(RIGHTBORDER_STYLE)))) {
                c.setRightBorderStyle(CellBorderStyle.valueOf((String)rightBoderStyle));
            } else {
                if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.THIN);
                } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.NONE);
                }
                if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.THIN);
                } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.DOUBLE);
                } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.DASH_DOT);
                } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                    c.setRightBorderStyle(CellBorderStyle.THICK);
                }
            }
            if (d.getBottomBorderColor() != -1) {
                c.setBottomBorderColor(new CellColor(CellBookGrid2dataConverter.getCellBooKColorFormGrid2(d.getBottomBorderColor())));
            }
            if (StringUtils.isNotEmpty((String)(bottomBoderStyle = d.getPersistenceData(BOTTOMBORDER_STYLE)))) {
                c.setBottomBorderStyle(CellBorderStyle.valueOf((String)bottomBoderStyle));
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.THIN);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.NONE);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.THIN);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.DOUBLE);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.DASH_DOT);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setBottomBorderStyle(CellBorderStyle.THICK);
            }
            c.setWrapLine(d.isWrapLine());
            c.setIndent(d.getIndent());
            int horzAlign = d.getHorzAlign();
            if (horzAlign == GridEnums.TextAlignment.Auto.ordinal()) {
                c.setHorizontalAlignment(HorizontalAlignment.GENERAL);
            } else if (horzAlign == GridEnums.TextAlignment.Fore.ordinal()) {
                c.setHorizontalAlignment(HorizontalAlignment.LEFT);
            } else if (horzAlign == GridEnums.TextAlignment.Back.ordinal()) {
                c.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            } else if (horzAlign == GridEnums.TextAlignment.Center.ordinal()) {
                c.setHorizontalAlignment(HorizontalAlignment.CENTER);
            } else if (horzAlign == GridEnums.TextAlignment.Sparse.ordinal() || horzAlign == GridEnums.TextAlignment.Extend.ordinal()) {
                c.setHorizontalAlignment(HorizontalAlignment.DISTRIBUTED);
            } else {
                c.setHorizontalAlignment(HorizontalAlignment.GENERAL);
            }
            int vertAlign = d.getVertAlign();
            if (vertAlign == GridEnums.TextAlignment.Auto.ordinal()) {
                c.setVerticalAlignment(VerticalAlignment.AUTO);
            } else if (vertAlign == GridEnums.TextAlignment.Fore.ordinal()) {
                c.setVerticalAlignment(VerticalAlignment.TOP);
            } else if (vertAlign == GridEnums.TextAlignment.Back.ordinal()) {
                c.setVerticalAlignment(VerticalAlignment.BOTTOM);
            } else if (vertAlign == GridEnums.TextAlignment.Center.ordinal()) {
                c.setVerticalAlignment(VerticalAlignment.CENTER);
            } else if (vertAlign == GridEnums.TextAlignment.Sparse.ordinal() || vertAlign == GridEnums.TextAlignment.Extend.ordinal()) {
                c.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            } else {
                c.setVerticalAlignment(VerticalAlignment.AUTO);
            }
            try {
                if (StringUtils.isNotEmpty((String)d.getDataExString())) {
                    c.setPersistenceData(d.getDataExString());
                }
            }
            catch (JSONException e) {
                logger.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8f6c\u6362\u5931\u8d25\uff01", e);
            }
            String editText = d.getEditText();
            if (StringUtils.isEmpty((String)editText)) {
                editText = d.getShowText();
            }
            if (null != d.getShowText() && d.getShowText().length() > 0) {
                c.setShowText(d.getShowText());
            } else {
                c.setShowText(editText);
            }
            c.setFormatter(d.getFormatter());
            c.setFitFontSize(d.isFitFontSize());
            if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Auto)) {
                c.setValue(editText);
                c.setDataTypeId("auto");
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Text)) {
                c.setValue(editText);
                c.setCommonDataType(CommonCellDataType.STRING);
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Number)) {
                c.setValue(editText);
                c.setCommonDataType(CommonCellDataType.NUMBER);
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean)) {
                c.setValue(editText);
                c.setCommonDataType(CommonCellDataType.BOOLEAN);
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime)) {
                c.setValue(editText);
                c.setCommonDataType(CommonCellDataType.DATE);
            } else {
                GridEnums.DataType enumValue = (GridEnums.DataType)GridEnums.getEnumValue(GridEnums.DataType.class, (int)d.getDataType());
                if (GridEnums.DataType.HotLink == enumValue) {
                    c.setDataTypeId("hyperlink");
                    c.putPersistenceData("hyperlink", d.getShowText());
                    c.setValue(d.getEditText());
                    c.setShowText(d.getEditText());
                } else {
                    c.setDataTypeId(enumValue.name());
                    c.setValue(editText);
                }
                c.setCommonDataType(CommonCellDataType.STRING);
            }
        } else {
            block152: {
                CellStyleModel model;
                CellColor backGroundColor;
                if (null != c.getBackGroundColor()) {
                    backGroundColor = c.getBackGroundColor();
                    d.setBackGroundColor(CellBookGrid2dataConverter.getGrid2ColorFormCellBooK(backGroundColor.getHexString()));
                    String grid2ColorAlphaFormCellBooK = CellBookGrid2dataConverter.getGrid2ColorAlphaFormCellBooK(backGroundColor.getHexString());
                    if (StringUtils.isNotEmpty((String)grid2ColorAlphaFormCellBooK)) {
                        d.setPersistenceData(BACKGROUND_ALPHA, grid2ColorAlphaFormCellBooK);
                    }
                } else {
                    d.setBackGroundColor(-1);
                }
                if (null != c.getFontColor()) {
                    backGroundColor = c.getFontColor();
                    d.setForeGroundColor(CellBookGrid2dataConverter.getGrid2ColorFormCellBooK(backGroundColor.getHexString()));
                }
                if ((model = c.getModel()) == CellStyleModel.UNSELECT) {
                    d.setSelectable(false);
                } else if (model == CellStyleModel.EDIT) {
                    d.setEditable(true);
                } else {
                    d.setEditable(false);
                }
                d.setFontSize((int)c.getFontSizeF());
                d.setFontName(c.getFontName());
                int style = 0;
                if (c.isBold()) {
                    style |= 2;
                }
                if (c.isItalic()) {
                    style |= 4;
                }
                if (c.isInline()) {
                    style |= 0x10;
                }
                if (c.isUnderline()) {
                    style |= 8;
                }
                d.setFontStyle(style);
                if (null != c.getRightBorderColor()) {
                    CellColor rightBorderColor = c.getRightBorderColor();
                    d.setRightBorderColor(CellBookGrid2dataConverter.getGrid2ColorFormCellBooK(rightBorderColor.getHexString()));
                }
                if (CellBorderStyle.NONE == c.getRightBorderStyle()) {
                    d.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
                } else if (CellBorderStyle.THIN == c.getRightBorderStyle()) {
                    d.setRightBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                } else if (CellBorderStyle.DOUBLE == c.getRightBorderStyle()) {
                    d.setRightBorderStyle(GridEnums.GridBorderStyle.DOUBLE.getStyle());
                } else if (CellBorderStyle.DASH_DOT == c.getRightBorderStyle()) {
                    d.setRightBorderStyle(GridEnums.GridBorderStyle.DASH.getStyle());
                } else if (CellBorderStyle.THICK == c.getRightBorderStyle()) {
                    d.setRightBorderStyle(GridEnums.GridBorderStyle.BOLD.getStyle());
                } else {
                    d.setPersistenceData(RIGHTBORDER_STYLE, c.getRightBorderStyle().toString());
                }
                if (null != c.getBottomBorderColor()) {
                    CellColor bottomBorderColor = c.getBottomBorderColor();
                    d.setBottomBorderColor(CellBookGrid2dataConverter.getGrid2ColorFormCellBooK(bottomBorderColor.getHexString()));
                }
                if (CellBorderStyle.NONE == c.getBottomBorderStyle()) {
                    d.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
                } else if (CellBorderStyle.THIN == c.getBottomBorderStyle()) {
                    d.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                } else if (CellBorderStyle.DOUBLE == c.getBottomBorderStyle()) {
                    d.setBottomBorderStyle(GridEnums.GridBorderStyle.DOUBLE.getStyle());
                } else if (CellBorderStyle.DASH_DOT == c.getBottomBorderStyle()) {
                    d.setBottomBorderStyle(GridEnums.GridBorderStyle.DASH.getStyle());
                } else if (CellBorderStyle.THICK == c.getBottomBorderStyle()) {
                    d.setBottomBorderStyle(GridEnums.GridBorderStyle.BOLD.getStyle());
                } else {
                    d.setPersistenceData(BOTTOMBORDER_STYLE, c.getBottomBorderStyle().toString());
                }
                d.setWrapLine(c.isWrapLine());
                d.setIndent(c.getIndent());
                HorizontalAlignment horizontalAlignment = c.getHorizontalAlignment();
                if (HorizontalAlignment.GENERAL == horizontalAlignment) {
                    d.setHorzAlign(GridEnums.TextAlignment.Auto.ordinal());
                } else if (HorizontalAlignment.LEFT == horizontalAlignment) {
                    d.setHorzAlign(GridEnums.TextAlignment.Fore.ordinal());
                } else if (HorizontalAlignment.RIGHT == horizontalAlignment) {
                    d.setHorzAlign(GridEnums.TextAlignment.Back.ordinal());
                } else if (HorizontalAlignment.CENTER == horizontalAlignment) {
                    d.setHorzAlign(GridEnums.TextAlignment.Center.ordinal());
                } else if (HorizontalAlignment.DISTRIBUTED == horizontalAlignment) {
                    d.setHorzAlign(GridEnums.TextAlignment.Sparse.ordinal());
                }
                VerticalAlignment verticalAlignment = c.getVerticalAlignment();
                if (VerticalAlignment.AUTO == verticalAlignment) {
                    d.setVertAlign(GridEnums.TextAlignment.Auto.ordinal());
                } else if (VerticalAlignment.TOP == verticalAlignment) {
                    d.setVertAlign(GridEnums.TextAlignment.Fore.ordinal());
                } else if (VerticalAlignment.BOTTOM == verticalAlignment) {
                    d.setVertAlign(GridEnums.TextAlignment.Back.ordinal());
                } else if (VerticalAlignment.CENTER == verticalAlignment) {
                    d.setVertAlign(GridEnums.TextAlignment.Center.ordinal());
                } else if (VerticalAlignment.JUSTIFY == verticalAlignment) {
                    d.setVertAlign(GridEnums.TextAlignment.Sparse.ordinal());
                }
                CommonCellDataType commonDataType = c.getCommonDataType();
                d.setEditText(c.getValue());
                if (null == c.getShowText() || c.getShowText().length() == 0) {
                    d.setShowText(c.getValue());
                } else {
                    d.setShowText(c.getShowText());
                }
                String dataTypeId = c.getDataTypeId();
                if (null != dataTypeId) {
                    if ("auto".equals(dataTypeId)) {
                        d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Auto));
                    } else {
                        try {
                            if ("hyperlink".equals(dataTypeId)) {
                                d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink));
                                String persistenceData = c.getPersistenceData("hyperlink");
                                if (StringUtils.isEmpty((String)persistenceData)) {
                                    persistenceData = "";
                                }
                                d.setShowText(persistenceData);
                                d.setEditText(c.getValue());
                                break block152;
                            }
                            GridEnums.DataType valueOf = GridEnums.DataType.valueOf((String)dataTypeId);
                            if (null != valueOf) {
                                d.setDataType(GridEnums.getIntValue((Enum)valueOf));
                                break block152;
                            }
                            d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                        }
                        catch (Exception e) {
                            if (commonDataType == CommonCellDataType.STRING) {
                                d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                                break block152;
                            }
                            if (CommonCellDataType.NUMBER == commonDataType) {
                                d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                                break block152;
                            }
                            if (CommonCellDataType.BOOLEAN == commonDataType) {
                                d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
                                break block152;
                            }
                            if (CommonCellDataType.DATE == commonDataType) {
                                d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                                break block152;
                            }
                            d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                        }
                    }
                } else if (commonDataType == CommonCellDataType.STRING) {
                    d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Text));
                } else if (CommonCellDataType.NUMBER == commonDataType) {
                    d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                } else if (CommonCellDataType.BOOLEAN == commonDataType) {
                    d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Boolean));
                } else if (CommonCellDataType.DATE == commonDataType) {
                    d.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.DateTime));
                }
            }
            d.setFormatter(c.getFormatter());
            d.setFitFontSize(c.isFitFontSize());
            try {
                if (null != c.getPersistenceData()) {
                    d.setDataExFromString(c.getPersistenceData().toString());
                }
            }
            catch (JSONException e) {
                logger.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8f6c\u6362\u5931\u8d25\uff01", e);
            }
        }
    }
}

