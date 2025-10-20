/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.grid2;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridCellProperty;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.GridFieldList;
import com.jiuqi.np.grid.NumberCellProperty;
import com.jiuqi.np.grid2.Grid2CellField;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.Grid2FieldList;
import com.jiuqi.np.grid2.GridCellAddedData;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.GridEnums;
import com.jiuqi.np.grid2.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

public final class GridConverter {
    private static int[][] colors = new int[][]{{-16777211, -16777201, 0xFFFFFF, 0xE0E0E0, 0xC0C0C0, 0x808080, 0x666666, 0x333333}, {0xAA00FF, 0xCC55FF, 0xAAAAFF, 0xAAFFFF, 65535, 43775, 26352, 255}, {0xFF00FF, 0xFF55AA, 0xFFAAAA, 0xFFFFAA, 0x66FFAA, 0x55AAAA, 5596912, 0x5500AA}, {0x800080, 0xAA55AA, 0xFFAAFF, 0xAAFFAA, 57514, 32896, 21930, 128}, {0xFF0055, 0xFF5555, 0xFFAA55, 0xFFFF55, 0x99FF55, 0x55CC55, 0x5566AA, 0x550055}, {0xAA0055, 0xAA5555, 0xAAAA55, 0xCCFF55, 65433, 43622, 21845, 85}, {0xFF0000, 0xFF6600, 0xFFCC00, 0xFFFF00, 0x66FF00, 0x66AA00, 0x555500, 0x550000}, {0x800000, 0xAA5500, 0x808000, 0xAAFF00, 65280, 32768, 21760, 0}};
    private static final int[] borderColors = new int[]{-16777201, -16777211, 0xE0E0E0, 0x808080, 0x666666, 0x333333, 0, 65535, 128, 255, 32768, 65280, 0x800000, 0xFF0000, 0x800080, 0xFF00FF};
    static final int[] border = new int[]{GridEnums.BorderStyle.Auto.ordinal(), GridEnums.BorderStyle.None.ordinal(), GridEnums.BorderStyle.Single.ordinal(), GridEnums.BorderStyle.SingleDash.ordinal(), GridEnums.BorderStyle.SingleDot.ordinal(), GridEnums.BorderStyle.SingleDashDot.ordinal(), GridEnums.BorderStyle.SingleDashDotDot.ordinal(), GridEnums.BorderStyle.Thick.ordinal(), GridEnums.BorderStyle.ThickDash.ordinal(), GridEnums.BorderStyle.ThickDot.ordinal(), GridEnums.BorderStyle.Double.ordinal(), GridEnums.BorderStyle.DoubleDash.ordinal(), GridEnums.BorderStyle.DoubleDot.ordinal(), GridEnums.BorderStyle.Strong.ordinal(), GridEnums.BorderStyle.StrongDash.ordinal(), GridEnums.BorderStyle.StrongDot.ordinal()};

    public static void data2Todata(Grid2Data n, GridData o) {
        o.setColCount(n.getColumnCount());
        o.setRowCount(n.getRowCount());
        o.setScrollTopCol(n.getHeaderColumnCount());
        o.setScrollTopRow(n.getHeaderRowCount());
        o.setScrollBottomCol(n.getFooterColumnCount());
        o.setScrollBottomRow(n.getFooterRowCount());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeights(i, n.getRowHeight(i));
            o.setRowVisible(i, !n.isRowHidden(i));
            o.setRowAutoSize(i, n.isRowAutoHeight(i));
            for (int j = 0; j < n.getColumnCount(); ++j) {
                if (i == 0) {
                    o.setColWidths(j, n.getColumnWidth(j));
                    o.setColVisible(j, !n.isColumnHidden(j));
                    o.setColAutoSize(j, n.isColumnAutoWidth(j));
                }
                GridCellProperty cell = o.getCellForChange(j, i);
                GridConverter.copyCellData(cell, n.getGridCellData(j, i), 0);
            }
        }
        Grid2FieldList gfl = n.merges();
        Grid2CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
    }

    public static void dataTodata2(GridData n, Grid2Data o) {
        o.setColumnCount(n.getColCount());
        o.setRowCount(n.getRowCount());
        o.setHeaderColumnCount(n.getScrollTopCol());
        o.setHeaderRowCount(n.getScrollTopRow());
        o.setFooterColumnCount(n.getScrollBottomCol());
        o.setFooterRowCount(n.getScrollBottomRow());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeight(i, n.getRowHeights(i));
            o.setRowHidden(i, !n.getRowVisible(i));
            o.setRowAutoHeight(i, n.getRowAutoSize(i));
            for (int j = 0; j < n.getColCount(); ++j) {
                if (i == 0) {
                    o.setColumnWidth(j, n.getColWidths(j));
                    o.setColumnHidden(j, !n.getColVisible(j));
                    o.setColumnAutoWidth(j, n.getColAutoSize(j));
                }
                GridConverter.copyCellData(n.getCell(j, i), o.getGridCellData(j, i), 1);
            }
        }
        GridFieldList gfl = n.merges();
        CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            String formatter;
            if (d.getBackGroundColor() != -1) {
                c.setBackColor(d.getBackGroundColor());
            }
            c.setBackStyle(d.getBackGroundStyle());
            if (d.getForeGroundColor() != -1) {
                c.setFontColor(d.getForeGroundColor());
            }
            c.setFontSize(d.getFontSize());
            c.setFontName(d.getFontName());
            c.setFontBold((d.getFontStyle() & 2) != 0);
            c.setFontItalic((d.getFontStyle() & 4) != 0);
            c.setFontStrikeOut((d.getFontStyle() & 0x10) != 0);
            c.setFontUnderLine((d.getFontStyle() & 8) != 0);
            c.setSilverHead(d.isSilverHead());
            if (d.getRightBorderColor() != -1) {
                c.setREdgeColor(d.getRightBorderColor());
            }
            if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 1);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 2);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 3);
            }
            if (d.getBottomBorderColor() != -1) {
                c.setBEdgeColor(d.getBottomBorderColor());
            }
            if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 1);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 2);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 3);
            }
            c.setCanSelect(d.isSelectable());
            c.setCanModify(d.isEditable());
            c.setWrapLine(d.isWrapLine());
            c.setIndent(d.getIndent());
            c.setVertAlign(d.getVertAlign());
            c.setHorzAlign(d.getHorzAlign());
            c.setVertText(d.isVertText());
            c.setShowText(d.getShowText());
            c.setCssClass(d.getEditText());
            c.setDataType(d.getDataType());
            if (d.getDataType() == GridEnums.getIntValue(GridEnums.DataType.Number) && !StringUtils.isEmpty((String)(formatter = d.getFormatter()))) {
                String[] formatters = formatter.split("\\.");
                if (formatters.length == 2) {
                    int length = formatters[1].length();
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty(cdp);
                    numberCellProperty.setDecimal(length);
                    c.setDataFlag(numberCellProperty.getCellDataProperty().getDataFlag());
                    if (formatters[0].contains(",")) {
                        numberCellProperty.setThoundsMarks(true);
                    }
                    c.setDataProperty(numberCellProperty.getCellDataProperty().getDataProperty());
                } else if (formatters[0].contains(",")) {
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty(cdp);
                    numberCellProperty.setThoundsMarks(true);
                    c.setDataProperty(numberCellProperty.getCellDataProperty().getDataProperty());
                }
            }
            c.setMultiLine(d.isMultiLine());
            c.setFitFontSize(d.isFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException numberCellProperty) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            try {
                obj = new JSONObject(d.getDataExString());
                data.setDataEx(obj);
            }
            catch (JSONException e) {
                LogUtil.log(e);
            }
            c.setScript(data.toString());
        } else if (direction == 1) {
            d.setBackGroundColor(c.getBackColor());
            d.setBackGroundStyle(c.getBackStyle());
            d.setForeGroundColor(c.getFontColor());
            d.setFontSize(c.getFontSize());
            d.setFontName(c.getFontName());
            int style = 0;
            if (c.getFontBold()) {
                style |= 2;
            }
            if (c.getFontItalic()) {
                style |= 4;
            }
            if (c.getFontStrikeOut()) {
                style |= 0x10;
            }
            if (c.getFontUnderLine()) {
                style |= 8;
            }
            d.setFontStyle(style);
            d.setRightBorderColor(c.getREdgeColor());
            if (c.getREdgeStyle() == 0 || c.getREdgeStyle() == 1 || c.getREdgeStyle() == 2) {
                d.setRightBorderStyle(c.getREdgeStyle() - 1);
            } else if (c.getREdgeStyle() == 4 || c.getREdgeStyle() == 10) {
                d.setRightBorderStyle(c.getREdgeStyle() - 2);
            } else if (c.getREdgeStyle() == 7) {
                d.setRightBorderStyle(c.getREdgeStyle() - 3);
            }
            d.setBottomBorderColor(c.getBEdgeColor());
            if (c.getBEdgeStyle() == 0 || c.getBEdgeStyle() == 1 || c.getBEdgeStyle() == 2) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 1);
            } else if (c.getBEdgeStyle() == 4 || c.getBEdgeStyle() == 10) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 2);
            } else if (c.getBEdgeStyle() == 7) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 3);
            }
            d.setSelectable(c.getCanSelect());
            d.setEditable(c.getCanModify());
            d.setWrapLine(c.getWrapLine());
            d.setIndent(c.getIndent());
            d.setVertAlign(c.getVertAlign());
            d.setHorzAlign(c.getHorzAlign());
            d.setVertText(c.getVertText());
            d.setShowText(c.getShowText());
            d.setEditText(c.getCssClass());
            d.setDataType(c.getDataType());
            d.setSilverHead(c.getSilverHead());
            d.setMultiLine(c.getMultiLine());
            d.setFitFontSize(c.getFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException numberCellProperty) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if ((obj = data.getDataEx()) != null) {
                try {
                    d.setDataExFromString(obj.toString());
                }
                catch (JSONException e) {
                    LogUtil.log(e);
                }
            }
        }
    }

    private static int adjustBorderColor2Old(int borderColor) {
        int min = 100000;
        int nearestValue = 0;
        for (int i = 0; i < borderColors.length; ++i) {
            if (Math.abs(borderColor - borderColors[i]) >= min) continue;
            min = Math.abs(borderColor - borderColors[i]);
            nearestValue = borderColors[i];
        }
        return nearestValue;
    }

    private static int adjustBorderColor2New(int borderColorIndex) {
        return borderColors[borderColorIndex];
    }

    private static int adjustColor2Old(int color) {
        int min = 100000;
        int nearestValue = 0;
        for (int i = 0; i < colors.length; ++i) {
            for (int j = 0; j < colors[i].length; ++j) {
                if (Math.abs(color - colors[i][j]) >= min) continue;
                min = Math.abs(color - colors[i][j]);
                nearestValue = colors[i][j];
            }
        }
        return nearestValue;
    }
}

