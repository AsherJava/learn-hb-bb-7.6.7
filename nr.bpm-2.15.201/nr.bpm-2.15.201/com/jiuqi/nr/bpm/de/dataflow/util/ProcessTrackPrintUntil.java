/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridCellProperty
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.grid.CellDataProperty
 *  com.jiuqi.np.grid.CellDataPropertyIntf
 *  com.jiuqi.np.grid.NumberCellProperty
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellAddedData
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 *  com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridCellProperty;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellDataPropertyIntf;
import com.jiuqi.np.grid.NumberCellProperty;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellAddedData;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl;
import com.jiuqi.util.StringUtils;
import java.io.IOException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTrackPrintUntil {
    private static final Logger logger = LoggerFactory.getLogger(ProcessTrackPrintUntil.class);

    public static GridData grid2DataToGridData(Grid2Data n, GridData o) {
        if (null == o) {
            o = new GridData();
        }
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
                ProcessTrackPrintUntil.copyCellData((GridCell)cell, n.getGridCellData(j, i), 0);
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
        return o;
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            Object object;
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
            if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Number)) {
                String formatter = d.getFormatter();
                if (!StringUtils.isEmpty((String)formatter)) {
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
            } else if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) && null != (object = d.getCellData("IMG_DATA")) && object instanceof ImageDescriptor) {
                ImageDescriptor imageDescriptor = (ImageDescriptor)object;
                String imageName = imageDescriptor.getImageId();
                try {
                    c.setImageData(imageDescriptor.getImageData().getBytes(), imageName.substring(imageName.lastIndexOf(".") + 1, imageName.length()));
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
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
                catch (Exception e) {
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
            catch (Exception e) {
                // empty catch block
            }
            c.setScript(data.toString());
        } else if (direction == 1) {
            byte[] imageDataBytes;
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
            if (c.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) && null != (imageDataBytes = c.getImageData())) {
                String imageType = c.getImageType();
                ImageDescriptorImpl imageDescriptorImpl = new ImageDescriptorImpl();
                ImageData imageData = new ImageData(imageDataBytes);
                imageDescriptorImpl.setImageData(imageData);
                imageDescriptorImpl.setImageId("grid\u8f6c\u6362\u65e0\u540d\u79f0." + imageType.toLowerCase());
                d.setCellData("IMG_DATA", (Object)imageDescriptorImpl);
            }
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (Exception exception) {
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
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

