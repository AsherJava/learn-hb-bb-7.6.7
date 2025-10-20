/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellEdger
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.GridFieldList
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellAddedData
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.bi.grid.CellEdger;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.util.Html;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellAddedData;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.impl.ImageDescriptorImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class GridDataTransform {
    public static Grid2Data gridDataToGrid2Data(GridData n, Grid2Data o) {
        if (null == o) {
            o = new Grid2Data();
        }
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
                GridDataTransform.copyCellData(n.getCell(j, i), o.getGridCellData(j, i));
            }
        }
        GridFieldList gfl = n.merges();
        CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
                CellEdger at = CellEdger.at((GridData)n, (int)cf.left, (int)cf.top);
                if (null == at) continue;
                for (int r = cf.left; r <= cf.right; ++r) {
                    o.getGridCellData(r, cf.bottom).setBottomBorderStyle(GridDataTransform.fromEdgeStyle(at.getBottomStyle(r - cf.left)));
                    o.getGridCellData(r, cf.bottom).setBottomBorderColor(at.getBottomColor(r - cf.left));
                }
                for (int c = cf.top; c <= cf.bottom; ++c) {
                    o.getGridCellData(cf.right, c).setRightBorderStyle(GridDataTransform.fromEdgeStyle(at.getRightStyle(c - cf.top)));
                    o.getGridCellData(cf.right, c).setRightBorderColor(at.getRightColor(c - cf.top));
                }
            }
        }
        return o;
    }

    private static void copyCellData(GridCell gridCell, GridCellData d) {
        byte[] imageDataBytes;
        d.setBackGroundColor(gridCell.getBackColor());
        d.setBackGroundStyle(gridCell.getBackStyle());
        d.setForeGroundColor(gridCell.getFontColor());
        d.setFontSize(gridCell.getFontSize() * 96 / 72);
        d.setFontName(gridCell.getFontName());
        int style = 0;
        if (gridCell.getFontBold()) {
            style |= 2;
        }
        if (gridCell.getFontItalic()) {
            style |= 4;
        }
        if (gridCell.getFontStrikeOut()) {
            style |= 0x10;
        }
        if (gridCell.getFontUnderLine()) {
            style |= 8;
        }
        d.setFontStyle(style);
        d.setRightBorderColor(gridCell.getREdgeColor());
        if (Html.isTransparent((int)gridCell.getREdgeColor()) || gridCell.getREdgeStyle() == 1) {
            d.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getValue());
        } else if (gridCell.getREdgeStyle() == 0 || gridCell.getREdgeStyle() == 2) {
            d.setRightBorderStyle(gridCell.getREdgeStyle() - 1);
        } else if (gridCell.getREdgeStyle() == 4 || gridCell.getREdgeStyle() == 10) {
            d.setRightBorderStyle(gridCell.getREdgeStyle() - 2);
        } else if (gridCell.getREdgeStyle() == 7) {
            d.setRightBorderStyle(gridCell.getREdgeStyle() - 3);
        } else if (gridCell.getREdgeStyle() == 13) {
            d.setRightBorderStyle(4);
        }
        d.setBottomBorderColor(gridCell.getBEdgeColor());
        if (Html.isTransparent((int)gridCell.getBEdgeColor()) || gridCell.getBEdgeStyle() == 1) {
            d.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getValue());
        } else if (gridCell.getBEdgeStyle() == 0 || gridCell.getBEdgeStyle() == 2) {
            d.setBottomBorderStyle(gridCell.getBEdgeStyle() - 1);
        } else if (gridCell.getBEdgeStyle() == 4 || gridCell.getBEdgeStyle() == 10) {
            d.setBottomBorderStyle(gridCell.getBEdgeStyle() - 2);
        } else if (gridCell.getBEdgeStyle() == 7) {
            d.setBottomBorderStyle(gridCell.getBEdgeStyle() - 3);
        } else if (gridCell.getBEdgeStyle() == 13) {
            d.setBottomBorderStyle(4);
        }
        d.setSelectable(gridCell.getCanSelect());
        d.setEditable(gridCell.getCanModify());
        d.setWrapLine(gridCell.getWrapLine());
        d.setIndent(gridCell.getIndent());
        d.setVertAlign(gridCell.getVertAlign());
        d.setHorzAlign(gridCell.getHorzAlign());
        d.setVertText(gridCell.getVertText());
        d.setShowText(gridCell.getShowText());
        d.setEditText(gridCell.getCssClass());
        d.setDataType(gridCell.getDataType());
        d.setSilverHead(gridCell.getSilverHead());
        d.setMultiLine(gridCell.getMultiLine());
        d.setFitFontSize(gridCell.getFitFontSize());
        if (gridCell.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Graphic) && null != (imageDataBytes = gridCell.getImageData())) {
            String imageType = gridCell.getImageType();
            ImageDescriptorImpl imageDescriptorImpl = new ImageDescriptorImpl();
            ImageData imageData = new ImageData(imageDataBytes);
            imageDescriptorImpl.setImageData(imageData);
            imageDescriptorImpl.setImageId("grid\u8f6c\u6362\u65e0\u540d\u79f0." + imageType.toLowerCase());
            d.setCellData("IMG_DATA", (Object)imageDescriptorImpl);
        }
        GridCellAddedData data = null;
        String addDataStr = gridCell.getScript();
        JSONObject obj = null;
        if (addDataStr != null) {
            try {
                data = new GridCellAddedData(addDataStr);
            }
            catch (JSONException jSONException) {
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
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
    }

    public static Grid2Data Grid2DataTextFilter(Grid2Data grid2Data) {
        for (int i = 0; i < grid2Data.getRowCount(); ++i) {
            for (int j = 0; j < grid2Data.getColumnCount(); ++j) {
                if (grid2Data.getGridCellData(j, i).getShowText() == null) {
                    grid2Data.getGridCellData(j, i).setShowText("");
                }
                if (grid2Data.getGridCellData(j, i).getEditText() != null) continue;
                grid2Data.getGridCellData(j, i).setEditText("");
            }
        }
        return grid2Data;
    }

    private static int fromEdgeStyle(int style) {
        switch (style) {
            case 1: {
                return 0;
            }
            default: {
                return 1;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 9;
            }
            case 6: {
                return 11;
            }
            case 7: {
                return 5;
            }
            case 10: {
                return 6;
            }
            case 13: {
                return 4;
            }
            case 14: 
        }
        return 8;
    }
}

