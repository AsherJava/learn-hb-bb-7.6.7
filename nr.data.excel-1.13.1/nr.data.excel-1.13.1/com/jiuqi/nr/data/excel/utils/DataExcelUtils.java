/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.CurrencyCellProperty
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.GridFieldList
 *  com.jiuqi.bi.grid.NumberCellProperty
 *  com.jiuqi.bi.grid.NumberCellPropertyIntf
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.np.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.np.grid2.GridEnums;
import com.jiuqi.nr.data.excel.utils.RegionNumber;
import com.jiuqi.nr.data.excel.utils.RegionNumberManager;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DataExcelUtils {
    public static final Integer SHEET_LENGTH = 31;

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
                DataExcelUtils.copyCellData(n.getCell(j, i), o.getGridCellData(j, i));
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
        return o;
    }

    private static void copyCellData(GridCell bigc, GridCellData gc) {
        if (0xFFFFFF != bigc.getBackColor() && !Html.isTransparent((int)bigc.getBackColor())) {
            String hexBack = Html.rgb2HtmlColor((int)bigc.getBackColor());
            gc.setBackGroundColor(DataExcelUtils.hex2RGB(hexBack));
        }
        gc.setBackGroundStyle(bigc.getBackStyle());
        gc.setForeGroundColor(bigc.getFontColor());
        gc.setFontSize(bigc.getFontSize() * 96 / 72);
        if (!"Calibri".equalsIgnoreCase(bigc.getFontName())) {
            gc.setFontName(bigc.getFontName());
        }
        int style = 0;
        if (bigc.getFontBold()) {
            style |= 2;
        }
        if (bigc.getFontItalic()) {
            style |= 4;
        }
        if (bigc.getFontStrikeOut()) {
            style |= 0x10;
        }
        if (bigc.getFontUnderLine()) {
            style |= 8;
        }
        gc.setFontStyle(style);
        if (0xC0C0C0 != bigc.getREdgeColor() && !Html.isTransparent((int)bigc.getREdgeColor())) {
            String hex = Html.rgb2HtmlColor((int)bigc.getREdgeColor());
            gc.setRightBorderColor(DataExcelUtils.hex2RGB(hex));
        }
        if (bigc.getREdgeStyle() == 1 || Html.isTransparent((int)bigc.getREdgeColor())) {
            gc.setRightBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
        } else if (bigc.getREdgeStyle() == 0 || bigc.getREdgeStyle() == 2) {
            gc.setRightBorderStyle(GridEnums.GridBorderStyle.AUTO.getStyle());
        } else if (bigc.getREdgeStyle() == 4 || bigc.getREdgeStyle() == 10) {
            gc.setRightBorderStyle(GridEnums.GridBorderStyle.DASH.getStyle());
        } else if (bigc.getREdgeStyle() == 7) {
            gc.setRightBorderStyle(GridEnums.GridBorderStyle.BOLD.getStyle());
        }
        if (0xC0C0C0 != bigc.getREdgeColor() && !Html.isTransparent((int)bigc.getBEdgeColor())) {
            String hex1 = Html.rgb2HtmlColor((int)bigc.getBEdgeColor());
            gc.setBottomBorderColor(DataExcelUtils.hex2RGB(hex1));
        }
        if (bigc.getBEdgeStyle() == 1 || Html.isTransparent((int)bigc.getBEdgeColor())) {
            gc.setBottomBorderStyle(GridEnums.GridBorderStyle.NONE.getStyle());
        } else if (bigc.getBEdgeStyle() == 0 || bigc.getBEdgeStyle() == 1 || bigc.getBEdgeStyle() == 2) {
            gc.setBottomBorderStyle(GridEnums.GridBorderStyle.AUTO.getStyle());
        } else if (bigc.getBEdgeStyle() == 4 || bigc.getBEdgeStyle() == 10) {
            gc.setBottomBorderStyle(GridEnums.GridBorderStyle.DASH.getStyle());
        } else if (bigc.getBEdgeStyle() == 7) {
            gc.setBottomBorderStyle(GridEnums.GridBorderStyle.BOLD.getStyle());
        }
        gc.setWrapLine(bigc.getWrapLine());
        gc.setIndent(bigc.getIndent());
        gc.setVertAlign(bigc.getVertAlign());
        gc.setHorzAlign(bigc.getHorzAlign());
        gc.setVertText(bigc.getVertText());
        gc.setShowText(bigc.getShowText());
        gc.setDataType(bigc.getDataType());
        if (gc.getDataType() == 2) {
            gc.setFormatter(DataExcelUtils.getNumberFormatter(bigc));
            NumberCellProperty ncp = new NumberCellProperty(bigc);
            gc.setCellData("decimalCount", (Object)ncp.getDecimal());
        }
        gc.setSilverHead(bigc.getSilverHead());
        gc.setMultiLine(bigc.getMultiLine());
        gc.setFitFontSize(bigc.getFitFontSize());
        String[] linkInformation = bigc.getLinkInformation();
        if (linkInformation.length == 3) {
            for (String info : linkInformation) {
                if (!StringUtils.isEmpty((String)info)) continue;
                return;
            }
        }
        DataExcelUtils.linkBiToNvwa(gc, linkInformation);
    }

    private static void linkBiToNvwa(GridCellData gc, String[] linkInformation) {
        String info = linkInformation[1];
        Map<String, String> linkInfoMap = DataExcelUtils.linkInfoFormat(info);
        if (null != linkInfoMap.get("reject")) {
            String SBZT = linkInfoMap.get("SBZT");
            if (!SBZT.equals("tsk_audit")) {
                gc.setShowText("");
                return;
            }
        } else if (null != linkInfoMap.get("check_result")) {
            String cwgs = linkInfoMap.get("CWGS");
            try {
                double i = Double.parseDouble(cwgs);
                if (i == 0.0) {
                    gc.setShowText("\u5ba1\u6838\u901a\u8fc7");
                    return;
                }
            }
            catch (NumberFormatException e) {
                gc.setShowText("\u53c2\u6570\u6709\u8bef");
                return;
            }
        }
        gc.setCellMode(Grid2DataConst.Cell_MODE.Cell_MODE_HTML.getIndex());
        gc.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink));
        int style = 0;
        gc.setFontStyle(style |= 8);
        StringBuffer html = new StringBuffer();
        String title = "";
        if (linkInformation.length > 1) {
            title = linkInformation[1];
        }
        String href = "javascript:void(0);";
        StringBuffer aStyle = new StringBuffer();
        aStyle.append("font-family:");
        aStyle.append(gc.getFontName());
        aStyle.append(";");
        aStyle.append("font-size:");
        aStyle.append(gc.getFontSize());
        aStyle.append("px;");
        aStyle.append("color:");
        aStyle.append(8431551);
        aStyle.append(";");
        if ((gc.getFontStyle() & 2) > 0) {
            aStyle.append("font-weight:bold;");
        }
        if ((gc.getFontStyle() & 4) > 0) {
            aStyle.append("font-style:italic;");
        }
        aStyle.append("position:absolute;");
        int horzAlign = gc.getHorzAlign();
        int vertAlign = gc.getVertAlign();
        if ((vertAlign == 0 || vertAlign == 3) && horzAlign == 3) {
            aStyle.append("top:50%;");
            aStyle.append("right:50%;");
            aStyle.append("transform:translateY(-50%)translateX(50%);");
        } else if (vertAlign == 2 && horzAlign == 3) {
            aStyle.append("top:100%;");
            aStyle.append("right:50%;");
            aStyle.append("transform:translateY(-100%)translateX(50%);");
        } else {
            if (vertAlign == 0 || vertAlign == 3) {
                aStyle.append("top:50%;");
                aStyle.append("transform:translateY(-50%)");
            }
            if (vertAlign == 2) {
                aStyle.append("top:100%;");
                aStyle.append("transform:translateY(-100%)");
            }
            if (horzAlign == 2) {
                aStyle.append("right:0%;");
            }
            if (horzAlign == 3) {
                aStyle.append("right:50%;");
                aStyle.append("transform:translateX(50%);");
            }
        }
        String onClick = "document.getElementById('gridDiv').dispatchEvent(new CustomEvent('gridHotLink', {'detail':{'link':'" + title + "'}}));";
        String trimText = "";
        if (linkInformation.length > 0) {
            trimText = linkInformation[0];
        }
        if (trimText.contains(" ")) {
            char[] charArray = trimText.toCharArray();
            StringBuffer b = new StringBuffer();
            for (char c : charArray) {
                if (' ' == c) {
                    b.append("&nbsp;");
                    continue;
                }
                b.append(c);
            }
            trimText = b.toString();
        }
        String spanStyle = "";
        if ((gc.getFontStyle() & 8) > 0) {
            spanStyle = spanStyle + "text-decoration:underline;";
        }
        if ((gc.getFontStyle() & 0x10) > 0) {
            spanStyle = spanStyle + "text-decoration:line-through;";
        }
        html.append("<a title=\"").append(title).append("\" href=\"").append(href).append("\" style=\"").append(aStyle).append("\" onclick=\"").append(onClick).append("\"><span style=\"").append(spanStyle).append("\">").append(trimText).append("</span></a>");
        gc.setShowText(html.toString());
        gc.setEditText(trimText);
    }

    private static String getNumberFormatter(GridCell tempGridCell) {
        if (tempGridCell.getDataType() == 2) {
            NumberCellProperty ncp = new NumberCellProperty(tempGridCell);
            if (ncp.getIsPercent()) {
                String formatStr = "0" + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)ncp) + "%";
                return formatStr;
            }
            if (tempGridCell.getShowText() != null && tempGridCell.getShowText().endsWith("\u2030")) {
                String numStr = "0";
                String colorStr = "";
                numStr = ncp.getThoundsMark() ? "#,##0" + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)ncp) : numStr + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)ncp);
                if (ncp.getWarningNegative()) {
                    colorStr = "[Red]";
                }
                String formatStr = ncp.getBracketNegative() ? numStr + ";" + colorStr + "(" + numStr + ")" : numStr + ";" + colorStr + "-" + numStr;
                return formatStr;
            }
            String formatStr = "0";
            formatStr = ncp.getThoundsMark() ? "#,##0" + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)ncp) : formatStr + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)ncp);
            if (ncp.getBracketNegative()) {
                StringBuilder builder = new StringBuilder();
                builder.append(formatStr).append(";").append("(").append(formatStr).append(")");
                formatStr = builder.toString();
            }
            return formatStr;
        }
        if (tempGridCell.getDataType() == 3) {
            CurrencyCellProperty curCellProperty = new CurrencyCellProperty(tempGridCell);
            String currencyFormat = DataExcelUtils.makeCurFotmat(curCellProperty);
            return currencyFormat;
        }
        return "";
    }

    private static String buildDecimal(NumberCellPropertyIntf numberCellProperty) {
        int decimalCount = numberCellProperty.getDecimal();
        if (decimalCount > 0) {
            StringBuilder decimal = new StringBuilder(".");
            for (int i = 0; i < decimalCount; ++i) {
                decimal.append("0");
            }
            return decimal.toString();
        }
        return "";
    }

    private static String makeCurFotmat(CurrencyCellProperty curCellProperty) {
        String currencyFormat = "#,##0";
        String colorStr = "";
        currencyFormat = currencyFormat + DataExcelUtils.buildDecimal((NumberCellPropertyIntf)curCellProperty);
        int idx = curCellProperty.getUnitIndex();
        if (curCellProperty.getWarningNegative()) {
            colorStr = "[Red]";
        }
        boolean isBracketNegative = curCellProperty.getBracketNegative();
        switch (curCellProperty.getUnitShowType()) {
            case 0: {
                String signStr = CurrencyCellProperty.CURRENCY_UNIT_SIGNS[idx];
                if (signStr.length() == 1) {
                    currencyFormat = isBracketNegative ? signStr + currencyFormat + ";" + colorStr + signStr + "(" + currencyFormat + ")" : signStr + currencyFormat + ";" + colorStr + signStr + "-" + currencyFormat;
                    break;
                }
                if (signStr.length() <= 1) break;
                currencyFormat = isBracketNegative ? "[$" + signStr + "]" + currencyFormat + ";" + colorStr + "[$" + signStr + "](" + currencyFormat + ")" : "[$" + signStr + "]" + currencyFormat + ";" + colorStr + "[$" + signStr + "]-" + currencyFormat;
                break;
            }
            case 1: {
                String nameStr = CurrencyCellProperty.CURRENCY_UNIT_NAMES[idx];
                currencyFormat = isBracketNegative ? "[$" + nameStr + "] " + currencyFormat + ";" + colorStr + "[$" + nameStr + "] (" + currencyFormat + ")" : "[$" + nameStr + "] " + currencyFormat + ";" + colorStr + "[$" + nameStr + "] -" + currencyFormat;
                break;
            }
            case 2: {
                String nameStr = CurrencyCellProperty.CURRENCY_UNIT_NAMES[idx];
                currencyFormat = isBracketNegative ? currencyFormat + " [$" + nameStr + "];" + colorStr + "(" + currencyFormat + ") [$" + nameStr + "]" : currencyFormat + " [$" + nameStr + "];" + colorStr + "-" + currencyFormat + " [$" + nameStr + "]";
                break;
            }
            case 3: {
                String titleStr = CurrencyCellProperty.CURRENCY_UNIT_TITLES[idx];
                currencyFormat = isBracketNegative ? "[$" + titleStr + "] " + currencyFormat + ";" + colorStr + "[$" + titleStr + "] (" + currencyFormat + ")" : "[$" + titleStr + "] " + currencyFormat + ";" + colorStr + "[$" + titleStr + "] -" + currencyFormat;
                break;
            }
        }
        return currencyFormat;
    }

    private static Map<String, String> linkInfoFormat(String linkInfoString) {
        HashMap<String, String> linkInfo = new HashMap<String, String>();
        try {
            String[] information = linkInfoString.split("\\?");
            linkInfo.put(information[0], "true");
            if (information.length > 1) {
                String[] singerInfo;
                for (String s : singerInfo = information[1].split("&")) {
                    String[] split = s.split("=");
                    linkInfo.put(split[0], split[1]);
                }
            }
        }
        catch (Exception e) {
            linkInfo.put("LinkInfoError", "\u94fe\u63a5\u8f6c\u6362\u5f02\u5e38");
        }
        return linkInfo;
    }

    private static int hex2RGB(String hexStr) {
        int rgb = 0xC0C0C0;
        if (StringUtils.isNotEmpty((String)hexStr) && hexStr.length() == 7) {
            rgb = Integer.parseInt(hexStr.substring(1, 7), 16);
        }
        return rgb;
    }

    public static String getEnumLinkStyle(DataLinkDefine dataLinkDefine, DataField fieldDefine) {
        StringBuilder sb = new StringBuilder();
        EnumDisplayMode displayMode = dataLinkDefine.getDisplayMode();
        if (displayMode == null) {
            displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
        }
        sb.append(displayMode.getValue());
        if ((dataLinkDefine.getAllowMultipleSelect() || fieldDefine.getAllowMultipleSelect() != null) && fieldDefine.getAllowMultipleSelect().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(0);
        if (dataLinkDefine.getAllowNotLeafNodeRefer()) {
            sb.append("0");
        } else {
            sb.append("1");
        }
        sb.append("1");
        sb.append("1");
        sb.append("1");
        if (dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (displayMode != null && displayMode.equals((Object)EnumDisplayMode.DISPLAY_MODE_IN_CELL)) {
            sb.append(dataLinkDefine.getEnumCount());
        }
        return sb.toString();
    }

    public static RegionNumberManager getRegionNumberManager(IRunTimeViewController runtimeView, String regionKey) {
        RegionSettingDefine regionSettingDefine = runtimeView.getRegionSetting(regionKey);
        if (null != regionSettingDefine) {
            return new RegionNumberManager(new RegionNumber((RowNumberSetting)regionSettingDefine.getRowNumberSetting().get(0)));
        }
        return null;
    }

    public static String sheetNameVolidate(String sheetName) {
        if (sheetName.length() > SHEET_LENGTH) {
            sheetName = sheetName.substring(0, SHEET_LENGTH - 3);
        }
        if (sheetName.startsWith("'")) {
            sheetName = "_" + sheetName.substring(1);
        }
        if (sheetName.endsWith("'")) {
            sheetName = sheetName.substring(0, sheetName.length() - 1) + "_";
        }
        if (sheetName.contains(":")) {
            sheetName = sheetName.replace(":", "_");
        }
        if (sheetName.contains("\\")) {
            sheetName = sheetName.replace("\\", "_");
        }
        if (sheetName.contains("/")) {
            sheetName = sheetName.replace("/", "_");
        }
        if (sheetName.contains("?")) {
            sheetName = sheetName.replace("?", "_");
        }
        if (sheetName.contains("*")) {
            sheetName = sheetName.replace("*", "_");
        }
        if (sheetName.contains("[")) {
            sheetName = sheetName.replace("[", "_");
        }
        if (sheetName.contains("]")) {
            sheetName = sheetName.replace("]", "_");
        }
        return sheetName;
    }

    public static Map<String, String> sheetNameToCompanyName(Sheet sheet) {
        HashMap<String, String> resMap = new HashMap<String, String>();
        for (Row row : sheet) {
            int x = row.getRowNum();
            if (x == 0) continue;
            Cell cellCode = row.getCell(1);
            String code = cellCode.getStringCellValue();
            Cell cellName = row.getCell(2);
            String name = cellName.getStringCellValue();
            resMap.put(code, name);
        }
        return resMap;
    }

    public static String getEnumLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        StringBuilder sb = new StringBuilder();
        EnumDisplayMode displayMode = dataLinkDefine.getDisplayMode();
        if (displayMode == null) {
            displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
        }
        sb.append(displayMode.getValue());
        if (dataLinkDefine.getAllowMultipleSelect() || fieldDefine.getAllowMultipleSelect().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(0);
        if (dataLinkDefine.getAllowNotLeafNodeRefer()) {
            sb.append("0");
        } else {
            sb.append("1");
        }
        sb.append("1");
        sb.append("1");
        sb.append("1");
        if (dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (displayMode != null && displayMode.equals((Object)EnumDisplayMode.DISPLAY_MODE_IN_CELL)) {
            sb.append(dataLinkDefine.getEnumCount());
        }
        return sb.toString();
    }

    public static String getEnumLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        StringBuilder sb = new StringBuilder();
        EnumDisplayMode displayMode = dataLinkDefine.getDisplayMode();
        if (displayMode == null) {
            displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
        }
        sb.append(displayMode.getValue());
        if (dataLinkDefine.getAllowMultipleSelect() || columnModelDefine.isMultival()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(0);
        if (dataLinkDefine.getAllowNotLeafNodeRefer()) {
            sb.append("0");
        } else {
            sb.append("1");
        }
        sb.append("1");
        sb.append("1");
        sb.append("1");
        if (dataLinkDefine.getAllowUndefinedCode().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (displayMode != null && displayMode.equals((Object)EnumDisplayMode.DISPLAY_MODE_IN_CELL)) {
            sb.append(dataLinkDefine.getEnumCount());
        }
        return sb.toString();
    }

    public static String getStringLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        int precision = fieldDefine.getSize();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(precision);
    }

    public static String getStringLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        int precision = columnModelDefine.getPrecision();
        DecimalFormat df = new DecimalFormat("0000");
        return df.format(precision);
    }

    public static String getNumberLinkStyle(NumberFormatParser numberFormatParser, FieldDefine fieldDefine, boolean noFormat) {
        String currencyMark;
        int decimal;
        int precision;
        StringBuilder sb = new StringBuilder();
        if (noFormat || numberFormatParser.isThousands() != null && numberFormatParser.isThousands().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(numberFormatParser.getNegativeStyle() != null ? numberFormatParser.getNegativeStyle().getValue() : "0");
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (numberFormatParser.isPercentage() != null && numberFormatParser.isPercentage().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        int n = precision = fieldDefine == null ? 0 : fieldDefine.getSize();
        if (precision < 10) {
            sb.append("0");
        }
        sb.append(precision);
        int n2 = decimal = fieldDefine == null ? 0 : fieldDefine.getFractionDigits();
        if (decimal < 10) {
            sb.append("0");
        }
        sb.append(decimal);
        sb.append("0");
        String string = currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        if (StringUtils.isEmpty((String)currencyMark)) {
            sb.append(" ");
        } else {
            sb.append(currencyMark);
        }
        sb.append("0");
        sb.append("1");
        sb.append(",");
        int displayDigits = 0;
        if (noFormat) {
            displayDigits = decimal;
        } else {
            int n3 = displayDigits = numberFormatParser.getDisplayDigits() != null ? numberFormatParser.getDisplayDigits() : decimal;
        }
        if (displayDigits < 10) {
            sb.append("0");
        }
        sb.append(displayDigits);
        sb.append(numberFormatParser.getFixMode() != null ? numberFormatParser.getFixMode().getValue() : "0");
        if (numberFormatParser.isThousandPer()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        return sb.toString();
    }

    public static String getNumberLinkStyle(NumberFormatParser numberFormatParser, ColumnModelDefine columnModelDefine, boolean noFormat) {
        String currencyMark;
        StringBuilder sb = new StringBuilder();
        if (noFormat || numberFormatParser.isThousands() != null && numberFormatParser.isThousands().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        sb.append(numberFormatParser.getNegativeStyle() != null ? numberFormatParser.getNegativeStyle().getValue() : "0");
        sb.append("0");
        sb.append("0");
        sb.append("0");
        if (numberFormatParser.isPercentage() != null && numberFormatParser.isPercentage().booleanValue()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        int precision = columnModelDefine.getPrecision();
        if (precision < 10) {
            sb.append("0");
        }
        sb.append(precision);
        int decimal = columnModelDefine.getDecimal();
        if (decimal < 10) {
            sb.append("0");
        }
        sb.append(decimal);
        sb.append("0");
        String string = currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
        if (StringUtils.isEmpty((String)currencyMark)) {
            sb.append(" ");
        } else {
            sb.append(currencyMark);
        }
        sb.append("0");
        sb.append("1");
        sb.append(",");
        int displayDigits = 0;
        if (!noFormat) {
            int n = displayDigits = numberFormatParser.getDisplayDigits() != null ? numberFormatParser.getDisplayDigits() : 0;
        }
        if (displayDigits < 10) {
            sb.append("0");
        }
        sb.append(displayDigits);
        sb.append(numberFormatParser.getFixMode() != null ? numberFormatParser.getFixMode().getValue() : "0");
        if (numberFormatParser.isThousandPer()) {
            sb.append("1");
        } else {
            sb.append("0");
        }
        return sb.toString();
    }

    public static String getOtherLinkStyle(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties == null && fieldDefine != null) {
            formatProperties = fieldDefine.getFormatProperties();
        }
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.toString();
        }
        return showFormat;
    }

    public static String getOtherLinkStyle(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        String showFormat = "";
        if (formatProperties != null) {
            showFormat = formatProperties.toString();
        }
        return showFormat;
    }
}

