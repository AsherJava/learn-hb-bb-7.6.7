/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Base64
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.grid.ncell;

import com.jiuqi.bi.grid.CellEdger;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.CurrencyCellPropertyIntf;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.grid.TextCellProperty;
import com.jiuqi.bi.grid.TextCellPropertyIntf;
import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridDataConverter {
    private static final String TAG_ROW = "row";
    private static final String TAG_ROWS = "rows";
    private static final String TAG_ROW_HEADER = "rowHeader";
    private static final String TAG_COLUMN = "column";
    private static final String TAG_COLUMNS = "columns";
    private static final String TAG_COLUMN_HEADER = "columnHeader";
    private static final String TAG_HEADER = "header";
    private static final String TAG_DATA = "data";
    private static final String TAG_STYLES = "styles";
    private static final String TAG_OPTIONS = "options";
    private static final String TAG_MERGE_INFO = "mergeInfo";
    private static final String TAG_AUTO = "auto";
    private static final String TAG_HIDDEN = "hidden";
    private static final String TAG_SIZE = "size";
    private static final String TAG_CELL_VALUE = "v";
    private static final String TAG_CELL_DATA_TYPE = "t";
    private static final String TAG_CELL_STYLE = "s";
    private static final String TAG_CELL_SHOW_TEXT = "showText";
    private static final String TAG_CELL_DATA_FORMATTER = "formatter";
    private static final String TAG_CELL_DATA_FORMATTER_MAX_SIZE = "maxSize";
    private static final String TAG_CELL_DATA_FORMATTER_DECIMAL = "decimal";
    private static final String TAG_CELL_DATA_FORMATTER_FORM = "form";
    private static final String TAG_CELL_DATA_FORMATTER_SHOW_SEPARATOR = "showSeparator";
    private static final String TAG_CELL_DATA_FORMATTER_SHOW_AS_CHINESE = "showAsChinese";
    private static final String TAG_CELL_DATA_FORMATTER_SHOW_CAPITAL_CHAR = "showCapitalChar";
    private static final String TAG_CELL_DATA_FORMATTER_WARNING_NEGATIVE = "warningNegative";
    private static final String TAG_CELL_DATA_FORMATTER_BRACKET_NEGATIVE = "bracketNegative";
    private static final String TAG_CELL_DATA_FORMATTER_CURRENCY_TYPE = "currencyType";
    private static final String TAG_CELL_DATA_FORMATTER_CURRENCY_SHOW_TYPE = "currencyShowType";
    private static final String TAG_CELL_DATA_FORMATTER_FORMAT = "format";
    private static final String TAG_CELL_DATA_FORMATTER_FORM_DEFAULT = "default";
    private static final String TAG_CELL_DATA_FORMATTER_FORM_PERCENT = "percent";
    private static final String TAG_CELL_DATA_FORMATTER_FORM_PERMILLAGE = "permillage";
    private static final String TAG_COUNT = "count";
    private static final String TAG_CELL_BACKGROUND_COLOR = "backGroundColor";
    private static final String TAG_CELL_STYLE_BACKGROUND_IMAGE = "backGroundImg";
    private static final String TAG_CELL_TOP_BORDER_COLOR = "topBorderColor";
    private static final String TAG_CELL_TOP_BORDER_STYLE = "topBorderStyle";
    private static final String TAG_CELL_BOTTOM_BORDER_COLOR = "bottomBorderColor";
    private static final String TAG_CELL_BOTTOM_BORDER_STYLE = "bottomBorderStyle";
    private static final String TAG_CELL_LEFT_BORDER_COLOR = "leftBorderColor";
    private static final String TAG_CELL_LEFT_BORDER_STYLE = "leftBorderStyle";
    private static final String TAG_CELL_RIGHT_BORDER_COLOR = "rightBorderColor";
    private static final String TAG_CELL_RIGHT_BORDER_STYLE = "rightBorderStyle";
    private static final String TAG_CELL_BOLD = "bold";
    private static final String TAG_CELL_ITALIC = "italic";
    private static final String TAG_CELL_FIT_FONT_SIZE = "fitFontSize";
    private static final String TAG_CELL_FONT_COLOR = "fontColor";
    private static final String TAG_CELL_FONT_NAME = "fontName";
    private static final String TAG_CELL_FONT_SIZE = "fontSize";
    private static final String TAG_CELL_HORIZONTAL_ALIGNMENT = "horizontalAlignment";
    private static final String TAG_CELL_VERTICAL_ALIGNMENT = "verticalAlignment";
    private static final String TAG_CELL_VERTICAL_TEXT = "verticalText";
    private static final String TAG_CELL_INDENT = "indent";
    private static final String TAG_CELL_WRAP_LINE = "wrapLine";
    private static final String TAG_CELL_INLINE = "inline";
    private static final String TAG_CELL_UNDERLINE = "underline";
    private static final String TAG_CELL_PADDING = "padding";
    private static final String TAG_CELL_MODE = "mode";
    private static final String TAG_ROW_INDEX = "rowIndex";
    private static final String TAG_COLUMN_INDEX = "columnIndex";
    private static final String TAG_ROW_SPAN = "rowSpan";
    private static final String TAG_COLUMN_SPAN = "columnSpan";
    private static final String TAG_HYPERLINK_DISPLAY = "display";
    private static final String TAG_HYPERLINK_URLTARGET = "urlTarget";
    private static final String TAG_HYPERLINK_OPENTYPE = "openType";
    private static final String TAG_ENTER_NEXT = "enterNext";
    private static final String TAG_SELECT_MODE = "selectMode";
    private static final String TAG_RESIZE = "resize";
    private static final String TRANSPARENT = "transparent";

    public static GridData buildGridData(JSONObject json) {
        int i;
        JSONObject header;
        JSONArray rows = json.optJSONArray(TAG_ROWS);
        JSONArray cols = json.optJSONArray(TAG_COLUMNS);
        JSONObject options = json.optJSONObject(TAG_OPTIONS);
        int colSize = cols.length() + 1;
        int rowSize = rows.length() + 1;
        GridData gridData = new GridData(colSize, rowSize);
        if (options != null && (header = options.optJSONObject(TAG_HEADER)) != null) {
            gridData.setScrollTopCol(header.optInt(TAG_COLUMN_HEADER, -1) + 1);
            gridData.setScrollTopRow(header.optInt(TAG_ROW_HEADER, -1) + 1);
        }
        for (i = 1; i < colSize; ++i) {
            JSONObject col = cols.optJSONObject(i - 1);
            gridData.setColAutoSize(i, col.optBoolean(TAG_AUTO));
            gridData.setColVisible(i, !col.optBoolean(TAG_HIDDEN));
            gridData.setColWidths(i, col.optInt(TAG_SIZE));
        }
        for (i = 1; i < rowSize; ++i) {
            JSONObject row = rows.optJSONObject(i - 1);
            gridData.setRowAutoSize(i, row.optBoolean(TAG_AUTO));
            gridData.setRowVisible(i, !row.optBoolean(TAG_HIDDEN));
            gridData.setRowHeights(i, row.optInt(TAG_SIZE));
        }
        GridDataConverter.fillGridCells(gridData, json);
        JSONArray mergeInfos = json.optJSONArray(TAG_MERGE_INFO);
        if (mergeInfos != null) {
            for (int i2 = 0; i2 < mergeInfos.length(); ++i2) {
                JSONObject mergeInfo = mergeInfos.optJSONObject(i2);
                int top = mergeInfo.optInt(TAG_ROW_INDEX);
                int left = mergeInfo.optInt(TAG_COLUMN_INDEX);
                int rowSpan = mergeInfo.optInt(TAG_ROW_SPAN, 1);
                int colSpan = mergeInfo.optInt(TAG_COLUMN_SPAN, 1);
                if (left <= -1 || top <= -1 || colSpan <= 0 || rowSpan <= 0 || left + colSpan >= gridData.getColCount() || top + rowSpan >= gridData.getRowCount()) continue;
                gridData.mergeCells(left + 1, top + 1, left + colSpan, top + rowSpan);
            }
        }
        GridFieldList merges = gridData.merges();
        for (int i3 = 0; i3 < merges.count(); ++i3) {
            CellField cellField = merges.get(i3);
            JSONArray cellDatas = json.optJSONArray(TAG_DATA);
            JSONArray cellStyles = json.optJSONArray(TAG_STYLES);
            CellEdger cellEdger = CellEdger.at(gridData, cellField.left, cellField.top);
            GridDataConverter.fillMergeCellLeftEdge(cellDatas, cellStyles, cellField, cellEdger);
            GridDataConverter.fillMergeCellRightEdge(cellDatas, cellStyles, cellField, cellEdger);
            GridDataConverter.fillMergeCellTopEdge(cellDatas, cellStyles, cellField, cellEdger);
            GridDataConverter.fillMergeCellBottomEdge(cellDatas, cellStyles, cellField, cellEdger);
            cellEdger.apply();
        }
        return gridData;
    }

    public static JSONObject buildNCellData(GridData gridData, boolean isDesign) {
        JSONObject ncellJson = new JSONObject();
        JSONArray rowJsons = new JSONArray();
        for (int row = 1; row < gridData.getRowCount(); ++row) {
            JSONObject json = new JSONObject();
            json.put(TAG_SIZE, gridData.getRowHeights(row));
            json.put(TAG_AUTO, gridData.getRowAutoSize(row));
            json.put(TAG_HIDDEN, !gridData.getRowVisible(row));
            rowJsons.put((Object)json);
        }
        JSONArray colJsons = new JSONArray();
        for (int col = 1; col < gridData.getColCount(); ++col) {
            JSONObject json = new JSONObject();
            json.put(TAG_SIZE, gridData.getColWidths(col));
            json.put(TAG_AUTO, gridData.getColAutoSize(col));
            json.put(TAG_HIDDEN, !gridData.getColVisible(col));
            colJsons.put((Object)json);
        }
        ArrayList<CellStyle> cellStyles = new ArrayList<CellStyle>();
        JSONArray dataJsons = new JSONArray();
        for (int row = 1; row < gridData.getRowCount(); ++row) {
            JSONArray rowJson = new JSONArray();
            for (int col = 1; col < gridData.getColCount(); ++col) {
                GridCell gridCell = gridData.getCell(col, row);
                JSONObject cellDataJson = GridDataConverter.gridCellToJson(gridCell, cellStyles, isDesign);
                rowJson.put((Object)cellDataJson);
            }
            dataJsons.put((Object)rowJson);
        }
        JSONArray mergerInfos = new JSONArray();
        GridFieldList merges = gridData.merges();
        for (int i = 0; i < merges.count(); ++i) {
            CellField merge = merges.get(i);
            JSONObject mergeInfo = new JSONObject();
            mergeInfo.put(TAG_ROW_INDEX, merge.top - 1);
            mergeInfo.put(TAG_COLUMN_INDEX, merge.left - 1);
            mergeInfo.put(TAG_ROW_SPAN, merge.bottom - merge.top + 1);
            mergeInfo.put(TAG_COLUMN_SPAN, merge.right - merge.left + 1);
            mergerInfos.put((Object)mergeInfo);
            CellEdger cellEdger = CellEdger.at(gridData, merge.left, merge.top);
            JSONArray topRow = dataJsons.optJSONArray(merge.top - 1);
            JSONArray topTopRow = null;
            if (merge.top > 1) {
                topTopRow = dataJsons.optJSONArray(merge.top - 2);
            }
            for (int col = merge.left; col < merge.right + 1; ++col) {
                JSONObject topCell = topRow.optJSONObject(col - 1);
                int topStyle = GridDataConverter.fromEdgeStyle(cellEdger.getTopStyle(col - merge.left));
                String topColor = GridDataConverter.rgbToHtmlColor(cellEdger.getTopColor(col - merge.left));
                GridDataConverter.updateCellStyle(cellStyles, topCell, topStyle, topColor, 1);
                if (topTopRow == null) continue;
                JSONObject topTopCell = topTopRow.optJSONObject(col - 1);
                GridDataConverter.updateCellStyle(cellStyles, topTopCell, topStyle, topColor, 3);
            }
            JSONArray bottomRow = dataJsons.optJSONArray(merge.bottom - 1);
            JSONArray bottomBottomRow = null;
            if (merge.bottom < gridData.getRowCount() - 1) {
                bottomBottomRow = dataJsons.optJSONArray(merge.bottom);
            }
            for (int col = merge.left; col < merge.right + 1; ++col) {
                JSONObject bottomCell = bottomRow.optJSONObject(col - 1);
                int bottomStyle = GridDataConverter.fromEdgeStyle(cellEdger.getBottomStyle(col - merge.left));
                String bottomColor = GridDataConverter.rgbToHtmlColor(cellEdger.getBottomColor(col - merge.left));
                GridDataConverter.updateCellStyle(cellStyles, bottomCell, bottomStyle, bottomColor, 3);
                if (bottomBottomRow == null) continue;
                JSONObject bottomBottomCell = bottomBottomRow.optJSONObject(col - 1);
                GridDataConverter.updateCellStyle(cellStyles, bottomBottomCell, bottomStyle, bottomColor, 1);
            }
            for (int row = merge.top; row < merge.bottom + 1; ++row) {
                JSONArray rowData = dataJsons.optJSONArray(row - 1);
                JSONObject leftCell = rowData.optJSONObject(merge.left - 1);
                int leftStyle = GridDataConverter.fromEdgeStyle(cellEdger.getLeftStyle(row - merge.top));
                String leftColor = GridDataConverter.rgbToHtmlColor(cellEdger.getLeftColor(row - merge.top));
                GridDataConverter.updateCellStyle(cellStyles, leftCell, leftStyle, leftColor, 4);
                if (merge.left > 1) {
                    JSONObject leftLeftCell = rowData.optJSONObject(merge.left - 2);
                    GridDataConverter.updateCellStyle(cellStyles, leftLeftCell, leftStyle, leftColor, 2);
                }
                JSONObject rightCell = rowData.optJSONObject(merge.right - 1);
                int rightStyle = GridDataConverter.fromEdgeStyle(cellEdger.getRightStyle(row - merge.top));
                String rightColor = GridDataConverter.rgbToHtmlColor(cellEdger.getRightColor(row - merge.top));
                GridDataConverter.updateCellStyle(cellStyles, rightCell, rightStyle, rightColor, 2);
                if (merge.right >= gridData.getColCount() - 1) continue;
                JSONObject rightRightCell = rowData.optJSONObject(merge.right);
                GridDataConverter.updateCellStyle(cellStyles, rightRightCell, rightStyle, rightColor, 4);
            }
        }
        JSONObject headerJson = new JSONObject();
        headerJson.put(TAG_ROW_HEADER, gridData.getScrollTopRow() > 0 ? gridData.getScrollTopRow() - 1 : 0);
        headerJson.put(TAG_COLUMN_HEADER, gridData.getScrollTopCol() > 0 ? gridData.getScrollTopCol() - 1 : 0);
        JSONObject resizeJson = new JSONObject();
        resizeJson.put(TAG_ROW, true);
        resizeJson.put(TAG_COLUMN, true);
        JSONObject serialNumberHeaderJson = new JSONObject();
        serialNumberHeaderJson.put("rbackGroundColor", (Object)"#efefef");
        serialNumberHeaderJson.put("cbackGroundColor", (Object)"#efefef");
        serialNumberHeaderJson.put("rfontName", (Object)"\u5b8b\u4f53");
        serialNumberHeaderJson.put("rfontSize", 12);
        serialNumberHeaderJson.put("cfontName", (Object)"\u5b8b\u4f53");
        serialNumberHeaderJson.put("cfontSize", 12);
        serialNumberHeaderJson.put("rHeaderHeight", 25);
        serialNumberHeaderJson.put(TAG_CELL_PADDING, 5);
        serialNumberHeaderJson.put("cHeaderWidth", 35);
        JSONObject optionJson = new JSONObject();
        optionJson.put(TAG_ENTER_NEXT, 1);
        optionJson.put(TAG_SELECT_MODE, 0);
        optionJson.put(TAG_HEADER, (Object)headerJson);
        optionJson.put(TAG_RESIZE, (Object)resizeJson);
        optionJson.put("hiddenSerialNumberHeader", !isDesign);
        optionJson.put("serialNumberHeader", (Object)serialNumberHeaderJson);
        ncellJson.put(TAG_ROWS, (Object)rowJsons);
        ncellJson.put(TAG_COLUMNS, (Object)colJsons);
        ncellJson.put(TAG_DATA, (Object)dataJsons);
        ncellJson.put(TAG_OPTIONS, (Object)optionJson);
        ncellJson.put(TAG_MERGE_INFO, (Object)mergerInfos);
        JSONArray cellStyleJsons = new JSONArray();
        for (CellStyle cellStyle : cellStyles) {
            cellStyleJsons.put((Object)cellStyle.toJson());
        }
        ncellJson.put(TAG_STYLES, (Object)cellStyleJsons);
        return ncellJson;
    }

    private static void updateCellStyle(List<CellStyle> cellStyles, JSONObject cellData, int edgeStyle, String edgeColor, int position) {
        int styleIndex = cellData.optInt(TAG_CELL_STYLE);
        if (styleIndex > -1) {
            CellStyle cellStyle = cellStyles.get(styleIndex);
            boolean needUpdateStyleIndex = false;
            if (cellStyle.count > 1) {
                cellStyle.count--;
                cellStyle = (CellStyle)cellStyle.clone();
                cellStyle.count = 1;
                needUpdateStyleIndex = true;
            }
            switch (position) {
                case 1: {
                    cellStyle.tEdgeStyle = edgeStyle;
                    cellStyle.tEdgeColor = edgeColor;
                    break;
                }
                case 2: {
                    cellStyle.rEdgeStyle = edgeStyle;
                    cellStyle.rEdgeColor = edgeColor;
                    break;
                }
                case 3: {
                    cellStyle.bEdgeStyle = edgeStyle;
                    cellStyle.bEdgeColor = edgeColor;
                    break;
                }
                case 4: {
                    cellStyle.lEdgeStyle = edgeStyle;
                    cellStyle.lEdgeColor = edgeColor;
                    break;
                }
            }
            if (needUpdateStyleIndex) {
                int index = GridDataConverter.mergeStyle(cellStyles, cellStyle);
                cellData.put(TAG_CELL_STYLE, index);
            }
        }
    }

    private static int mergeStyle(List<CellStyle> cellStyles, CellStyle cellStyle) {
        int index = GridDataConverter.findCellStyle(cellStyles, cellStyle);
        if (index < 0) {
            cellStyles.add(cellStyle);
            index = cellStyles.size() - 1;
        } else {
            cellStyles.get(index).count++;
        }
        return index;
    }

    private static JSONObject dataFormatToJson(GridCell gridCell) {
        JSONObject dataFormat = new JSONObject();
        if (gridCell.isHyperlink()) {
            return dataFormat;
        }
        switch (gridCell.getType()) {
            case 1: {
                TextCellPropertyIntf property = gridCell.toTextCell();
                dataFormat.put(TAG_CELL_DATA_FORMATTER_MAX_SIZE, property.getMaxLength());
                break;
            }
            case 2: {
                dataFormat = GridDataConverter.doCellDataTypesNumber(gridCell);
                break;
            }
            case 3: {
                dataFormat = GridDataConverter.doCellDataTypesCurrency(gridCell);
                break;
            }
            case 4: 
            case 5: {
                dataFormat.put(TAG_CELL_DATA_FORMATTER_FORMAT, gridCell.getDataProperty());
                break;
            }
        }
        return dataFormat;
    }

    private static JSONObject doCellDataTypesNumber(GridCell gridCell) {
        NumberCellPropertyIntf property = gridCell.toNumberCell();
        return GridDataConverter.doCellDataTypesNumber(property);
    }

    private static JSONObject doCellDataTypesNumber(NumberCellPropertyIntf property) {
        JSONObject dataFormat = new JSONObject();
        dataFormat.put(TAG_CELL_DATA_FORMATTER_DECIMAL, property.getDecimal());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_BRACKET_NEGATIVE, property.getBracketNegative());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_WARNING_NEGATIVE, property.getWarningNegative());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_SHOW_AS_CHINESE, property.getChineseNumber());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_SHOW_CAPITAL_CHAR, property.getBigChineseChar());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_SHOW_SEPARATOR, property.getThoundsMark());
        String formType = TAG_CELL_DATA_FORMATTER_FORM_DEFAULT;
        if (property.getThoundsMark() && property.getIsPercent()) {
            formType = TAG_CELL_DATA_FORMATTER_FORM_PERMILLAGE;
        } else if (property.getIsPercent()) {
            formType = TAG_CELL_DATA_FORMATTER_FORM_PERCENT;
        }
        dataFormat.put(TAG_CELL_DATA_FORMATTER_FORM, (Object)formType);
        return dataFormat;
    }

    private static JSONObject doCellDataTypesCurrency(GridCell gridCell) {
        CurrencyCellPropertyIntf property = gridCell.toCurrencyCell();
        JSONObject dataFormat = GridDataConverter.doCellDataTypesNumber(property);
        dataFormat.put(TAG_CELL_DATA_FORMATTER_CURRENCY_TYPE, property.getUnitIndex());
        dataFormat.put(TAG_CELL_DATA_FORMATTER_CURRENCY_SHOW_TYPE, property.getUnitShowType());
        return dataFormat;
    }

    private static void dataFormatFromJson(GridCell gridCell, JSONObject dataFormatJson) {
        if (gridCell.isHyperlink()) {
            String cellData = gridCell.getCellData();
            if (cellData.startsWith("LINK:")) {
                cellData = cellData.substring(5);
                JSONObject object = new JSONObject(cellData);
                gridCell.setLinkInformation(object.optString(TAG_HYPERLINK_DISPLAY), object.optString(TAG_HYPERLINK_URLTARGET), object.optString(TAG_HYPERLINK_OPENTYPE));
            }
            return;
        }
        if (dataFormatJson != null) {
            int format = dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_FORMAT);
            switch (gridCell.getType()) {
                case 1: {
                    TextCellProperty property = new TextCellProperty(gridCell);
                    property.setMaxLength(dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_MAX_SIZE));
                    break;
                }
                case 2: {
                    GridDataConverter.cellDataTypesNumber(gridCell, dataFormatJson);
                    break;
                }
                case 3: {
                    GridDataConverter.cellDataTypesCurrency(gridCell, dataFormatJson);
                    break;
                }
                case 5: {
                    GridDataConverter.cellDataTypesDateTime(gridCell, format);
                    break;
                }
                case 4: {
                    gridCell.setDataProperty((short)format);
                    break;
                }
            }
        }
    }

    private static void cellDataTypesNumber(GridCell gridCell, JSONObject dataFormatJson) {
        NumberCellProperty property = new NumberCellProperty(gridCell);
        property.setDecimal(dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_DECIMAL));
        property.setBracketNegative(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_BRACKET_NEGATIVE));
        property.setWarningNegative(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_WARNING_NEGATIVE));
        property.setChineseNumber(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_AS_CHINESE));
        property.setBigChineseChar(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_CAPITAL_CHAR));
        String formType = dataFormatJson.optString(TAG_CELL_DATA_FORMATTER_FORM, TAG_CELL_DATA_FORMATTER_FORM_DEFAULT);
        property.setIsPercent(!TAG_CELL_DATA_FORMATTER_FORM_DEFAULT.equals(formType));
        property.setThoundsMarks(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_SEPARATOR));
    }

    private static void cellDataTypesCurrency(GridCell gridCell, JSONObject dataFormatJson) {
        CurrencyCellProperty property = new CurrencyCellProperty(gridCell);
        property.setDecimal(dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_DECIMAL));
        property.setBracketNegative(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_BRACKET_NEGATIVE));
        property.setWarningNegative(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_WARNING_NEGATIVE));
        property.setChineseNumber(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_AS_CHINESE));
        property.setBigChineseChar(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_CAPITAL_CHAR));
        String formType = dataFormatJson.optString(TAG_CELL_DATA_FORMATTER_FORM, TAG_CELL_DATA_FORMATTER_FORM_DEFAULT);
        property.setIsPercent(!TAG_CELL_DATA_FORMATTER_FORM_DEFAULT.equals(formType));
        property.setThoundsMarks(dataFormatJson.optBoolean(TAG_CELL_DATA_FORMATTER_SHOW_SEPARATOR));
        property.setUnitIndex(dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_CURRENCY_TYPE));
        property.setUnitShowType(dataFormatJson.optInt(TAG_CELL_DATA_FORMATTER_CURRENCY_SHOW_TYPE));
    }

    private static void cellDataTypesDateTime(GridCell gridCell, int format) {
        DateCellProperty property = new DateCellProperty(gridCell);
        property.setDateShowType((short)format);
    }

    private static JSONObject gridCellToJson(GridCell gridCell, List<CellStyle> cellStyles, boolean isDesign) {
        JSONObject cellDataJson = new JSONObject();
        if (isDesign) {
            switch (gridCell.getDataType()) {
                case 9: {
                    cellDataJson = GridDataConverter.getJsonCellDataTypesHotLink(gridCell);
                    break;
                }
                case 6: {
                    cellDataJson = GridDataConverter.getJsonCellDataTypesGraphic(gridCell);
                    break;
                }
                default: {
                    cellDataJson = GridDataConverter.getJsonCellDataTypesDefault(gridCell);
                    break;
                }
            }
        } else {
            cellDataJson.put(TAG_CELL_VALUE, (Object)(gridCell.getShowText() == null ? "" : gridCell.getShowText()));
        }
        cellDataJson.put(TAG_CELL_DATA_TYPE, gridCell.getDataType());
        if (isDesign) {
            cellDataJson.put(TAG_CELL_DATA_FORMATTER, (Object)GridDataConverter.dataFormatToJson(gridCell));
        }
        CellStyle cellStyle = GridDataConverter.cellStyleToJson(gridCell);
        if (!isDesign) {
            cellStyle.mode = 1;
        } else {
            cellStyle.mode = gridCell.getCanModify() ? 0 : 1;
        }
        int index = GridDataConverter.mergeStyle(cellStyles, cellStyle);
        cellDataJson.put(TAG_CELL_STYLE, index);
        return cellDataJson;
    }

    private static JSONObject getJsonCellDataTypesHotLink(GridCell gridCell) {
        String cellValue;
        JSONObject cellDataJson = new JSONObject();
        String[] linkInfos = gridCell.getLinkInformation();
        if (linkInfos.length < 3) {
            cellValue = "";
        } else if (linkInfos[1] == null || linkInfos[1].length() == 0) {
            cellValue = linkInfos[0] == null || linkInfos[0].length() == 0 ? "" : Html.encodeText(linkInfos[0]);
        } else {
            JSONObject object = new JSONObject();
            object.put(TAG_HYPERLINK_DISPLAY, (Object)linkInfos[0]);
            object.put(TAG_HYPERLINK_URLTARGET, (Object)linkInfos[1]);
            object.put(TAG_HYPERLINK_OPENTYPE, (Object)linkInfos[2]);
            cellValue = "LINK:" + object.toString();
        }
        cellDataJson.put(TAG_CELL_VALUE, (Object)cellValue);
        cellDataJson.put(TAG_CELL_SHOW_TEXT, (Object)GridDataConverter.getLinkShowText(gridCell));
        return cellDataJson;
    }

    private static JSONObject getJsonCellDataTypesGraphic(GridCell gridCell) {
        JSONObject cellDataJson = new JSONObject();
        byte[] imageData = gridCell.getImageData();
        if (imageData != null) {
            String base64 = Base64.byteArrayToBase64((byte[])imageData);
            String imageType = gridCell.getImageType();
            base64 = "data:image/" + imageType + ";base64," + base64;
            cellDataJson.put(TAG_CELL_VALUE, (Object)base64);
        }
        cellDataJson.put(TAG_CELL_SHOW_TEXT, (Object)"");
        return cellDataJson;
    }

    private static JSONObject getJsonCellDataTypesDefault(GridCell gridCell) {
        JSONObject cellDataJson = new JSONObject();
        cellDataJson.put(TAG_CELL_VALUE, (Object)(gridCell.getCellData() == null ? "" : gridCell.getCellData()));
        String showText = gridCell.getShowText();
        if (StringUtils.isNotEmpty(showText) && !showText.equals(gridCell.getCellData())) {
            cellDataJson.put(TAG_CELL_SHOW_TEXT, (Object)showText);
        }
        return cellDataJson;
    }

    private static String getLinkShowText(GridCell gridCell) {
        String[] linkInfos = gridCell.getLinkInformation();
        if (linkInfos.length < 3) {
            return "";
        }
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            return linkInfos[0];
        }
        return "";
    }

    private static int findCellStyle(List<CellStyle> cellStyles, CellStyle cellStyle) {
        for (int i = 0; i < cellStyles.size(); ++i) {
            CellStyle style = cellStyles.get(i);
            if (!style.equals(cellStyle)) continue;
            return i;
        }
        return -1;
    }

    private static void gridCellFromJson(GridData gridData, GridCell gridCell, JSONObject cellData, JSONObject cellStyle, JSONArray cellDatas, JSONArray cellStyles) {
        gridCell.setDataType(cellData.optInt(TAG_CELL_DATA_TYPE, 0));
        if (gridCell.getType() == 6) {
            String base64 = cellData.optString(TAG_CELL_VALUE);
            if (StringUtils.isNotEmpty(base64) && base64.startsWith("data:image")) {
                int end = base64.indexOf(";");
                String imageType = base64.substring(11, end);
                int start = base64.indexOf(",");
                base64 = base64.substring(start + 1);
                gridCell.setImageData(Base64.base64ToByteArray((String)base64), imageType);
            }
        } else {
            gridCell.setCellData(cellData.optString(TAG_CELL_VALUE));
        }
        JSONObject dataFormat = cellData.optJSONObject(TAG_CELL_DATA_FORMATTER);
        GridDataConverter.dataFormatFromJson(gridCell, dataFormat);
        if (cellStyle != null) {
            String backColor = cellStyle.optString(TAG_CELL_BACKGROUND_COLOR);
            if (StringUtils.isNotEmpty(backColor)) {
                int bColor = GridDataConverter.colorHtmlToRGB(backColor);
                gridCell.setBackColor(bColor);
                if (bColor <= 0xFFFFFFF) {
                    gridCell.setBackStyle(1);
                }
                int bColorAlpha = GridDataConverter.colorHtmlToA(backColor);
                gridCell.setBackAlpha(bColorAlpha);
            }
            if (gridCell.getRowNum() == 1) {
                GridDataConverter.setFirstTEdge(gridCell, cellStyle);
            }
            if (gridCell.getColNum() == 1) {
                GridDataConverter.setFirstLEdge(gridCell, cellStyle);
            }
            GridDataConverter.setBEdge(gridData, gridCell, cellStyle, cellDatas, cellStyles);
            GridDataConverter.setREdge(gridData, gridCell, cellStyle, cellDatas, cellStyles);
            GridDataConverter.setFont(gridCell, cellStyle);
            gridCell.setCanModify(cellStyle.optInt(TAG_CELL_MODE, 0) == 0);
        }
    }

    private static JSONObject getCellStyle(JSONObject cellData, JSONArray cellStyles) {
        int cellStyleIndex = cellData.optInt(TAG_CELL_STYLE);
        if (cellStyleIndex > -1 && cellStyles != null && cellStyleIndex < cellStyles.length()) {
            return cellStyles.optJSONObject(cellStyleIndex);
        }
        return null;
    }

    private static JSONObject getCellStyle(JSONArray rowData, JSONArray cellStyles, int col) {
        if (rowData == null) {
            return null;
        }
        JSONObject cellData = rowData.optJSONObject(col);
        return GridDataConverter.getCellStyle(cellData, cellStyles);
    }

    private static void fillMergeCellLeftEdge(JSONArray cellDatas, JSONArray cellStyles, CellField cellField, CellEdger cellEdger) {
        for (int row = cellField.top; row < cellField.bottom + 1; ++row) {
            JSONObject leftCellStyle;
            JSONArray rowData = cellDatas.optJSONArray(row - 1);
            JSONObject curCellStyle = GridDataConverter.getCellStyle(rowData, cellStyles, cellField.left - 1);
            if (curCellStyle != null) {
                int style = curCellStyle.optInt(TAG_CELL_LEFT_BORDER_STYLE);
                byte edgeStyle = GridDataConverter.toEdgeStyle(style);
                String color = curCellStyle.optString(TAG_CELL_LEFT_BORDER_COLOR);
                if (StringUtils.isNotEmpty(color)) {
                    cellEdger.setLeftColor(row - cellField.top, GridDataConverter.colorHtmlToRGB(color));
                }
                cellEdger.setLeftStyle(row - cellField.top, GridDataConverter.toEdgeStyle(style));
                if (edgeStyle != 1) continue;
            }
            if (cellField.left <= 1 || (leftCellStyle = GridDataConverter.getCellStyle(rowData, cellStyles, cellField.left - 2)) == null) continue;
            String color = leftCellStyle.optString(TAG_CELL_RIGHT_BORDER_COLOR);
            int style = leftCellStyle.optInt(TAG_CELL_RIGHT_BORDER_STYLE);
            if (StringUtils.isNotEmpty(color)) {
                cellEdger.setLeftColor(row - cellField.top, GridDataConverter.colorHtmlToRGB(color));
            }
            cellEdger.setLeftStyle(row - cellField.top, GridDataConverter.toEdgeStyle(style));
        }
    }

    private static void fillMergeCellRightEdge(JSONArray cellDatas, JSONArray cellStyles, CellField cellField, CellEdger cellEdger) {
        for (int row = cellField.top; row < cellField.bottom + 1; ++row) {
            JSONObject rightCellStyle;
            JSONArray rowData = cellDatas.optJSONArray(row - 1);
            JSONObject curCellStyle = GridDataConverter.getCellStyle(rowData, cellStyles, cellField.right - 1);
            if (curCellStyle != null) {
                int style = curCellStyle.optInt(TAG_CELL_RIGHT_BORDER_STYLE);
                byte edgeStyle = GridDataConverter.toEdgeStyle(style);
                String color = curCellStyle.optString(TAG_CELL_RIGHT_BORDER_COLOR);
                if (StringUtils.isNotEmpty(color)) {
                    cellEdger.setRightColor(row - cellField.top, GridDataConverter.colorHtmlToRGB(color));
                }
                cellEdger.setRightStyle(row - cellField.top, GridDataConverter.toEdgeStyle(style));
                if (edgeStyle != 1) continue;
            }
            if (cellField.right >= rowData.length() || (rightCellStyle = GridDataConverter.getCellStyle(rowData, cellStyles, cellField.right)) == null) continue;
            String color = rightCellStyle.optString(TAG_CELL_LEFT_BORDER_COLOR);
            int style = rightCellStyle.optInt(TAG_CELL_LEFT_BORDER_STYLE);
            if (StringUtils.isNotEmpty(color)) {
                cellEdger.setRightColor(row - cellField.top, GridDataConverter.colorHtmlToRGB(color));
            }
            cellEdger.setRightStyle(row - cellField.top, GridDataConverter.toEdgeStyle(style));
        }
    }

    private static void fillMergeCellTopEdge(JSONArray cellDatas, JSONArray cellStyles, CellField cellField, CellEdger cellEdger) {
        JSONArray curRowData = cellDatas.optJSONArray(cellField.top - 1);
        JSONArray topRowData = null;
        if (cellField.top > 1) {
            topRowData = cellDatas.optJSONArray(cellField.top - 2);
        }
        for (int col = cellField.left; col < cellField.right + 1; ++col) {
            JSONObject topCellStyle;
            JSONObject curCellStyle = GridDataConverter.getCellStyle(curRowData, cellStyles, col - 1);
            if (curCellStyle != null) {
                int style = curCellStyle.optInt(TAG_CELL_TOP_BORDER_STYLE);
                byte edgeStyle = GridDataConverter.toEdgeStyle(style);
                String color = curCellStyle.optString(TAG_CELL_TOP_BORDER_COLOR);
                if (StringUtils.isNotEmpty(color)) {
                    cellEdger.setTopColor(col - cellField.left, GridDataConverter.colorHtmlToRGB(color));
                }
                cellEdger.setTopStyle(col - cellField.left, GridDataConverter.toEdgeStyle(style));
                if (edgeStyle != 1) continue;
            }
            if ((topCellStyle = GridDataConverter.getCellStyle(topRowData, cellStyles, col - 1)) == null) continue;
            String color = topCellStyle.optString(TAG_CELL_BOTTOM_BORDER_COLOR);
            int style = topCellStyle.optInt(TAG_CELL_BOTTOM_BORDER_STYLE);
            if (StringUtils.isNotEmpty(color)) {
                cellEdger.setTopColor(col - cellField.left, GridDataConverter.colorHtmlToRGB(color));
            }
            cellEdger.setTopStyle(col - cellField.left, GridDataConverter.toEdgeStyle(style));
        }
    }

    private static void fillMergeCellBottomEdge(JSONArray cellDatas, JSONArray cellStyles, CellField cellField, CellEdger cellEdger) {
        JSONArray curRowData = cellDatas.optJSONArray(cellField.bottom - 1);
        JSONArray bottomRowData = null;
        if (cellField.bottom < cellDatas.length()) {
            bottomRowData = cellDatas.optJSONArray(cellField.bottom);
        }
        for (int col = cellField.left; col < cellField.right + 1; ++col) {
            JSONObject cellStyle;
            JSONObject curCellStyle = GridDataConverter.getCellStyle(curRowData, cellStyles, col - 1);
            if (curCellStyle != null) {
                int style = curCellStyle.optInt(TAG_CELL_BOTTOM_BORDER_STYLE);
                byte edgeStyle = GridDataConverter.toEdgeStyle(style);
                String color = curCellStyle.optString(TAG_CELL_BOTTOM_BORDER_COLOR);
                if (StringUtils.isNotEmpty(color)) {
                    cellEdger.setBottomColor(col - cellField.left, GridDataConverter.colorHtmlToRGB(color));
                }
                cellEdger.setBottomStyle(col - cellField.left, GridDataConverter.toEdgeStyle(style));
                if (edgeStyle != 1) continue;
            }
            if ((cellStyle = GridDataConverter.getCellStyle(bottomRowData, cellStyles, col - 1)) == null) continue;
            String color = cellStyle.optString(TAG_CELL_TOP_BORDER_COLOR);
            int style = cellStyle.optInt(TAG_CELL_TOP_BORDER_STYLE);
            if (StringUtils.isNotEmpty(color)) {
                cellEdger.setBottomColor(col - cellField.left, GridDataConverter.colorHtmlToRGB(color));
            }
            cellEdger.setBottomStyle(col - cellField.left, GridDataConverter.toEdgeStyle(style));
        }
    }

    private static CellStyle cellStyleToJson(GridCell gridCell) {
        CellStyle cellStyle = new CellStyle();
        cellStyle.backcolor = GridDataConverter.rgbToHtmlColor(gridCell.getBackColor());
        int backColorAlpha = gridCell.getBackAlpha();
        if (backColorAlpha < 100 && !TRANSPARENT.equals(cellStyle.backcolor)) {
            String alpha = Integer.toString((int)Math.round((double)backColorAlpha * 2.55), 16);
            if (alpha.length() < 2) {
                alpha = "0" + alpha;
            }
            CellStyle cellStyle2 = cellStyle;
            cellStyle2.backcolor = cellStyle2.backcolor + alpha;
        }
        cellStyle.tEdgeStyle = GridDataConverter.fromEdgeStyle(gridCell.getTEdgeStyle());
        cellStyle.tEdgeColor = GridDataConverter.rgbToHtmlColor(gridCell.getTEdgeColor());
        cellStyle.bEdgeStyle = GridDataConverter.fromEdgeStyle(gridCell.getBEdgeStyle());
        cellStyle.bEdgeColor = GridDataConverter.rgbToHtmlColor(gridCell.getBEdgeColor());
        cellStyle.lEdgeStyle = GridDataConverter.fromEdgeStyle(gridCell.getLEdgeStyle());
        cellStyle.lEdgeColor = GridDataConverter.rgbToHtmlColor(gridCell.getLEdgeColor());
        cellStyle.rEdgeStyle = GridDataConverter.fromEdgeStyle(gridCell.getREdgeStyle());
        cellStyle.rEdgeColor = GridDataConverter.rgbToHtmlColor(gridCell.getREdgeColor());
        cellStyle.fitFontSize = gridCell.getFitFontSize();
        cellStyle.fontBold = gridCell.getFontBold();
        cellStyle.fontItalic = gridCell.getFontItalic();
        cellStyle.fontName = gridCell.getFontName();
        cellStyle.fontSize = gridCell.getFontSize();
        cellStyle.fontColor = GridDataConverter.rgbToHtmlColor(gridCell.getFontColor());
        cellStyle.underLine = gridCell.getFontUnderLine();
        cellStyle.inLine = gridCell.getFontStrikeOut();
        cellStyle.horzAlign = GridDataConverter.fromHorzTextAlignment(gridCell.getHorzAlign());
        cellStyle.vertAlign = GridDataConverter.fromVertTextAlignment(gridCell.getVertAlign());
        cellStyle.indent = gridCell.getIndent();
        cellStyle.vertText = gridCell.getVertText();
        cellStyle.wrapLine = gridCell.getWrapLine();
        return cellStyle;
    }

    private static int colorHtmlToRGB(String color) {
        if (TRANSPARENT.equals(color)) {
            return 0x1FFFFFFF;
        }
        if (!color.startsWith("#")) {
            color = "#FFFFFF";
        } else if (color.length() == 9 || color.length() == 8) {
            String filter;
            int alphaLength = 2;
            String alpha0 = "00";
            if (color.length() == 8) {
                alphaLength = 1;
                alpha0 = "0";
            }
            color = alpha0.equals(filter = color.substring(color.length() - alphaLength)) ? "1FFFFFFF" : color.substring(0, color.length() - alphaLength);
        }
        return Integer.parseInt(color.replace("#", ""), 16);
    }

    private static int colorHtmlToA(String backColor) {
        if (TRANSPARENT.equals(backColor)) {
            return 0;
        }
        if (backColor.length() == 9) {
            String filter = backColor.substring(backColor.length() - 2);
            int i = Integer.parseInt(filter, 16);
            return (int)Math.round((double)i / 2.55);
        }
        return 100;
    }

    private static String rgbToHtmlColor(int color) {
        if (color < 0) {
            return "#000000";
        }
        return Html.rgb2HtmlColor(color);
    }

    private static void setBEdge(GridData gridData, GridCell gridCell, JSONObject cellStyle, JSONArray cellDatas, JSONArray cellStyles) {
        String color = null;
        int style = 0;
        if (gridCell.getRowNum() < gridData.getRowCount() - 1 && cellStyle.optInt(TAG_CELL_BOTTOM_BORDER_STYLE) == 0) {
            JSONArray bottomRow = cellDatas.optJSONArray(gridCell.getRowNum());
            JSONObject bottomCellData = bottomRow.optJSONObject(gridCell.getColNum() - 1);
            JSONObject bottomCellStyle = GridDataConverter.getCellStyle(bottomCellData, cellStyles);
            if (bottomCellStyle != null) {
                color = bottomCellStyle.optString(TAG_CELL_TOP_BORDER_COLOR);
                style = bottomCellStyle.optInt(TAG_CELL_TOP_BORDER_STYLE);
            }
        } else {
            color = cellStyle.optString(TAG_CELL_BOTTOM_BORDER_COLOR);
            style = cellStyle.optInt(TAG_CELL_BOTTOM_BORDER_STYLE);
        }
        GridDataConverter.setBEdge(gridCell, color, style);
    }

    private static void setBEdge(GridCell gridCell, String color, int style) {
        if (StringUtils.isNotEmpty(color)) {
            gridCell.setBEdgeColor(GridDataConverter.colorHtmlToRGB(color));
        }
        gridCell.setBEdgeStyle(GridDataConverter.toEdgeStyle(style));
    }

    private static void setREdge(GridData gridData, GridCell gridCell, JSONObject cellStyle, JSONArray cellDatas, JSONArray cellStyles) {
        String color = null;
        int style = 0;
        if (gridCell.getColNum() < gridData.getColCount() - 1 && cellStyle.optInt(TAG_CELL_RIGHT_BORDER_STYLE) == 0) {
            JSONArray rightRow = cellDatas.optJSONArray(gridCell.getRowNum() - 1);
            JSONObject rightCellData = rightRow.optJSONObject(gridCell.getColNum());
            JSONObject rightStyle = GridDataConverter.getCellStyle(rightCellData, cellStyles);
            if (rightStyle != null) {
                color = rightStyle.optString(TAG_CELL_LEFT_BORDER_COLOR);
                style = rightStyle.optInt(TAG_CELL_LEFT_BORDER_STYLE);
            }
        } else {
            color = cellStyle.optString(TAG_CELL_RIGHT_BORDER_COLOR);
            style = cellStyle.optInt(TAG_CELL_RIGHT_BORDER_STYLE);
        }
        if (color != null && !color.isEmpty()) {
            gridCell.setREdgeColor(GridDataConverter.colorHtmlToRGB(color));
        }
        gridCell.setREdgeStyle(GridDataConverter.toEdgeStyle(style));
    }

    private static void setFirstLEdge(GridCell gridCell, JSONObject cellStyle) {
        if (gridCell.getColNum() > 1) {
            return;
        }
        int style = cellStyle.optInt(TAG_CELL_LEFT_BORDER_STYLE);
        byte borderStyle = GridDataConverter.toEdgeStyle(style);
        gridCell.setLEdgeStyle(borderStyle);
        String color = cellStyle.optString(TAG_CELL_LEFT_BORDER_COLOR);
        if (StringUtils.isNotEmpty(color)) {
            gridCell.setLEdgeColor(GridDataConverter.colorHtmlToRGB(color));
        }
    }

    private static void setFirstTEdge(GridCell gridCell, JSONObject cellStyle) {
        if (gridCell.getRowNum() > 1) {
            return;
        }
        int style = cellStyle.optInt(TAG_CELL_TOP_BORDER_STYLE);
        byte borderStyle = GridDataConverter.toEdgeStyle(style);
        gridCell.setTEdgeStyle(borderStyle);
        String color = cellStyle.optString(TAG_CELL_TOP_BORDER_COLOR);
        if (StringUtils.isNotEmpty(color)) {
            gridCell.setTEdgeColor(GridDataConverter.colorHtmlToRGB(color));
        }
    }

    private static void setFont(GridCell gridCell, JSONObject cellStyle) {
        gridCell.setFitFontSize(cellStyle.optBoolean(TAG_CELL_FIT_FONT_SIZE));
        gridCell.setWrapLine(cellStyle.optBoolean(TAG_CELL_WRAP_LINE));
        gridCell.setFontBold(cellStyle.optBoolean(TAG_CELL_BOLD));
        gridCell.setFontItalic(cellStyle.optBoolean(TAG_CELL_ITALIC));
        gridCell.setFontName(cellStyle.optString(TAG_CELL_FONT_NAME, "\u5fae\u8f6f\u96c5\u9ed1"));
        gridCell.setFontSize((int)Math.round(cellStyle.optDouble(TAG_CELL_FONT_SIZE, 14.0) * 3.0 / 4.0));
        String color = cellStyle.optString(TAG_CELL_FONT_COLOR);
        if (StringUtils.isNotEmpty(color)) {
            gridCell.setFontColor(GridDataConverter.colorHtmlToRGB(color));
        }
        gridCell.setFontUnderLine(cellStyle.optBoolean(TAG_CELL_UNDERLINE));
        gridCell.setFontStrikeOut(cellStyle.optBoolean(TAG_CELL_INLINE));
        gridCell.setHorzAlign(GridDataConverter.toHorzTextAlignments(cellStyle.optInt(TAG_CELL_HORIZONTAL_ALIGNMENT)));
        gridCell.setVertAlign(GridDataConverter.toVertTextAlignments(cellStyle.optInt(TAG_CELL_VERTICAL_ALIGNMENT)));
        gridCell.setIndent(cellStyle.optInt(TAG_CELL_INDENT));
        gridCell.setVertText(cellStyle.optBoolean(TAG_CELL_VERTICAL_TEXT));
    }

    private static byte toEdgeStyle(int style) {
        switch (style) {
            case 0: {
                return 1;
            }
            case 2: {
                return 13;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 7;
            }
            case 6: {
                return 10;
            }
            case 8: {
                return 14;
            }
            case 9: {
                return 5;
            }
            case 11: {
                return 6;
            }
        }
        return 2;
    }

    private static int fromEdgeStyle(int style) {
        switch (style) {
            case 1: {
                return 0;
            }
            case 13: {
                return 2;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 7: {
                return 5;
            }
            case 10: {
                return 6;
            }
            case 14: {
                return 8;
            }
            case 5: {
                return 9;
            }
            case 6: {
                return 11;
            }
        }
        return 1;
    }

    private static int toHorzTextAlignments(int align) {
        switch (align) {
            case 1: 
            case 5: {
                return 1;
            }
            case 2: {
                return 3;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 5;
            }
            case 7: {
                return 4;
            }
        }
        return 0;
    }

    private static int fromHorzTextAlignment(int align) {
        switch (align) {
            case 1: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 2: {
                return 3;
            }
            case 5: {
                return 4;
            }
            case 4: {
                return 7;
            }
        }
        return 0;
    }

    private static int toVertTextAlignments(int align) {
        switch (align) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 4;
            }
        }
        return 3;
    }

    private static int fromVertTextAlignment(int align) {
        switch (align) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 3;
            }
            case 4: {
                return 4;
            }
        }
        return 2;
    }

    private static void fillGridCells(GridData gridData, JSONObject json) {
        JSONArray cellDatas = json.optJSONArray(TAG_DATA);
        JSONArray cellStyles = json.optJSONArray(TAG_STYLES);
        for (int row = 1; row < gridData.getRowCount(); ++row) {
            JSONArray rowData = cellDatas.optJSONArray(row - 1);
            for (int col = 1; col < gridData.getColCount(); ++col) {
                GridCell cell = gridData.getCellEx(col, row);
                GridDataConverter.fillGridCell(gridData, cell, rowData, cellDatas, cellStyles);
            }
        }
    }

    private static void fillGridCell(GridData gridData, GridCell cell, JSONArray rowData, JSONArray cellDatas, JSONArray cellStyles) {
        int col = cell.getColNum();
        JSONObject cellData = rowData.optJSONObject(col - 1);
        JSONObject cellStyle = GridDataConverter.getCellStyle(cellData, cellStyles);
        GridDataConverter.gridCellFromJson(gridData, cell, cellData, cellStyle, cellDatas, cellStyles);
        gridData.setCell(cell);
    }

    private static class CellStyle
    implements Cloneable {
        private String backcolor;
        private String backImage;
        private String tEdgeColor;
        private int tEdgeStyle = 0;
        private String bEdgeColor;
        private int bEdgeStyle = 0;
        private String lEdgeColor;
        private int lEdgeStyle = 0;
        private String rEdgeColor;
        private int rEdgeStyle = 0;
        private boolean fitFontSize = false;
        private boolean wrapLine = false;
        private boolean fontBold = false;
        private boolean fontItalic = false;
        private String fontName;
        private int fontSize = 9;
        private String fontColor;
        private boolean underLine = false;
        private boolean inLine = false;
        private boolean vertText = false;
        private int horzAlign = 1;
        private int vertAlign = 1;
        private int indent = 0;
        private int mode = 0;
        private int count = 1;

        private CellStyle() {
        }

        public boolean equals(CellStyle cellStyle) {
            return this.stringEquals(this.backcolor, cellStyle.backcolor) && this.stringEquals(this.backImage, cellStyle.backImage) && this.stringEquals(this.tEdgeColor, cellStyle.tEdgeColor) && this.tEdgeStyle == cellStyle.tEdgeStyle && this.stringEquals(this.bEdgeColor, cellStyle.bEdgeColor) && this.bEdgeStyle == cellStyle.bEdgeStyle && this.stringEquals(this.lEdgeColor, cellStyle.lEdgeColor) && this.lEdgeStyle == cellStyle.lEdgeStyle && this.stringEquals(this.rEdgeColor, cellStyle.rEdgeColor) && this.rEdgeStyle == cellStyle.rEdgeStyle && this.fitFontSize == cellStyle.fitFontSize && this.wrapLine == cellStyle.wrapLine && this.fontBold == cellStyle.fontBold && this.fontItalic == cellStyle.fontItalic && this.stringEquals(this.fontName, cellStyle.fontName) && this.fontSize == cellStyle.fontSize && this.stringEquals(this.fontColor, cellStyle.fontColor) && this.underLine == cellStyle.underLine && this.inLine == cellStyle.inLine && this.vertText == cellStyle.vertText && this.horzAlign == cellStyle.horzAlign && this.vertAlign == cellStyle.vertAlign && this.indent == cellStyle.indent && this.mode == cellStyle.mode;
        }

        public Object clone() {
            CellStyle clone;
            try {
                clone = (CellStyle)super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return clone;
        }

        public JSONObject toJson() {
            JSONObject styleJson = new JSONObject();
            styleJson.put(GridDataConverter.TAG_CELL_BACKGROUND_COLOR, (Object)this.backcolor);
            styleJson.put(GridDataConverter.TAG_CELL_STYLE_BACKGROUND_IMAGE, (Object)this.backImage);
            styleJson.put(GridDataConverter.TAG_CELL_TOP_BORDER_COLOR, (Object)this.tEdgeColor);
            styleJson.put(GridDataConverter.TAG_CELL_TOP_BORDER_STYLE, this.tEdgeStyle);
            styleJson.put(GridDataConverter.TAG_CELL_BOTTOM_BORDER_COLOR, (Object)this.bEdgeColor);
            styleJson.put(GridDataConverter.TAG_CELL_BOTTOM_BORDER_STYLE, this.bEdgeStyle);
            styleJson.put(GridDataConverter.TAG_CELL_LEFT_BORDER_COLOR, (Object)this.lEdgeColor);
            styleJson.put(GridDataConverter.TAG_CELL_LEFT_BORDER_STYLE, this.lEdgeStyle);
            styleJson.put(GridDataConverter.TAG_CELL_RIGHT_BORDER_COLOR, (Object)this.rEdgeColor);
            styleJson.put(GridDataConverter.TAG_CELL_RIGHT_BORDER_STYLE, this.rEdgeStyle);
            styleJson.put(GridDataConverter.TAG_CELL_FIT_FONT_SIZE, this.fitFontSize);
            styleJson.put(GridDataConverter.TAG_CELL_WRAP_LINE, this.wrapLine);
            styleJson.put(GridDataConverter.TAG_CELL_BOLD, this.fontBold);
            styleJson.put(GridDataConverter.TAG_CELL_ITALIC, this.fontItalic);
            styleJson.put(GridDataConverter.TAG_CELL_FONT_NAME, (Object)this.fontName);
            styleJson.put(GridDataConverter.TAG_CELL_FONT_SIZE, (double)this.fontSize * 4.0 / 3.0);
            styleJson.put(GridDataConverter.TAG_CELL_FONT_COLOR, (Object)this.fontColor);
            styleJson.put(GridDataConverter.TAG_CELL_UNDERLINE, this.underLine);
            styleJson.put(GridDataConverter.TAG_CELL_INLINE, this.inLine);
            styleJson.put(GridDataConverter.TAG_CELL_HORIZONTAL_ALIGNMENT, this.horzAlign);
            styleJson.put(GridDataConverter.TAG_CELL_VERTICAL_ALIGNMENT, this.vertAlign);
            styleJson.put(GridDataConverter.TAG_CELL_INDENT, this.indent);
            styleJson.put(GridDataConverter.TAG_CELL_VERTICAL_TEXT, this.vertText);
            styleJson.put(GridDataConverter.TAG_CELL_PADDING, (Object)new Integer[]{1, 2, 1, 2});
            styleJson.put(GridDataConverter.TAG_CELL_MODE, this.mode);
            styleJson.put(GridDataConverter.TAG_COUNT, this.count);
            return styleJson;
        }

        private boolean stringEquals(String str1, String str2) {
            if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)) {
                return true;
            }
            return str1 != null && str1.equals(str2);
        }
    }
}

