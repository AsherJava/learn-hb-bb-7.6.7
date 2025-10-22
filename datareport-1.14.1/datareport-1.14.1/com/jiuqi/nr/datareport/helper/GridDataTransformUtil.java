/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellDataProperty
 *  com.jiuqi.bi.grid.CellDataPropertyIntf
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridCellProperty
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.NumberCellProperty
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellAddedData
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.datareport.helper;

import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridCellProperty;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellAddedData;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridDataTransformUtil {
    private static final Logger logger = LoggerFactory.getLogger(GridDataTransformUtil.class);

    public static void data2ToData(Grid2Data n, GridData o) {
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
                GridDataTransformUtil.copyCellData((GridCell)cell, n.getGridCellData(j, i), 0);
            }
        }
        Grid2FieldList gfl = n.merges();
        Grid2CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                if (cf.right < cf.left) {
                    cf.right = cf.left;
                }
                if (cf.bottom < cf.top) {
                    cf.top = cf.bottom;
                }
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            String dataExString;
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
            if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Number) && !StringUtils.isEmpty((String)(formatter = d.getFormatter()))) {
                String[] formatters = formatter.split("\\.");
                if (formatters.length == 2) {
                    int length = formatters[1].length();
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setDecimal(length);
                    c.setDataFlag((int)numberCellProperty.getCellDataProperty().getDataFlag());
                    if (formatters[0].contains(",")) {
                        numberCellProperty.setThoundsMarks(true);
                    }
                    c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                } else if (formatters[0].contains(",")) {
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setThoundsMarks(true);
                    c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                }
            }
            c.setMultiLine(d.isMultiLine());
            c.setFitFontSize(d.isFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException e) {
                    logger.debug(e.getMessage(), e);
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if (StringUtils.isNotEmpty((String)(dataExString = d.getDataExString()))) {
                try {
                    JSONObject obj = new JSONObject(dataExString);
                    data.setDataEx(obj);
                }
                catch (JSONException e) {
                    logger.debug(e.getMessage(), e);
                }
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
                catch (JSONException e) {
                    logger.debug(e.getMessage(), e);
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
                    logger.debug(e.getMessage(), e);
                }
            }
        }
    }
}

