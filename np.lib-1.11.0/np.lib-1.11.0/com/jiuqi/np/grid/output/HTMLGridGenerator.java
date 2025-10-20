/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.grid.output;

import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid.CellField;
import com.jiuqi.np.grid.CurrencyCellProperty;
import com.jiuqi.np.grid.GridCell;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.grid.output.CurrencyBoxHTMLGenerator;
import com.jiuqi.np.grid.output.IPropertySet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTMLGridGenerator {
    private GridData gridData;
    private int gridHeight;
    private int gridWidth;
    private int firstRowNum = 1;
    private boolean printCellScript = true;
    protected static final Object NULL_PROP_VALUE = new Object();
    private static final String CELL_STYLE_PREFIX = "jqgs";
    private static final String CELL_STYLE_TOP = "t";
    private static final String CELL_STYLE_LEFT = "l";
    private static final int CELL_PADDING = 4;
    public static final int BROWSER_IE = 1;
    public static final int BROWSER_FIREFOX = 2;
    public static final int BROWSER_OTHER = 10;
    protected int browserType = 2;
    public static final String SCRIPT_ATTR_NAME = "script";
    private Map<Integer, Boolean> colWidthOutputStatusMap = new HashMap<Integer, Boolean>();

    public HTMLGridGenerator() {
        this(null);
    }

    public HTMLGridGenerator(GridData gridData) {
        this.setGridData(gridData);
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
        this.gridHeight = 0;
        this.gridWidth = 0;
        this.firstRowNum = 1;
        if (gridData != null) {
            for (int row = 1; row < gridData.getRowCount(); ++row) {
                if (!gridData.getRowVisible(row)) continue;
                this.firstRowNum = row;
                break;
            }
        }
    }

    public boolean getPrintCellScript() {
        return this.printCellScript;
    }

    public void setPrintCellScript(boolean value) {
        this.printCellScript = value;
    }

    public final String generate() {
        StringBuffer output = new StringBuffer();
        this.generate(output);
        return output.toString();
    }

    public final void generate(StringBuffer output) {
        if (!this.startGenerate()) {
            return;
        }
        if (this.gridData == null) {
            return;
        }
        this.generateColgroup(output);
        for (int row = 0; row < this.gridData.getRowCount(); ++row) {
            this.startRow(row);
            PropertySet rowProps = new PropertySet();
            this.getRowTRProperties(row, rowProps);
            output.append("<tr");
            this.printAttributes(output, rowProps);
            output.append(">");
            for (int col = 0; col < this.gridData.getColCount(); ++col) {
                this.genTD(col, row, output);
            }
            output.append("</tr>\n");
            this.stopRow(row);
        }
        this.stopGenerate();
    }

    private final void generateColgroup(StringBuffer output) {
        if (this.gridData == null) {
            return;
        }
        StringBuffer colsBuffer = new StringBuffer();
        boolean colGroupAutoWidth = false;
        for (int col = 0; col < this.gridData.getColCount(); ++col) {
            GridCell cell = this.gridData.getCell(col, 0);
            PropertySet cellProps = new PropertySet();
            this.getCellTDProperties(cell, cellProps);
            Iterator itr = cellProps.iterator();
            String styleStr = "";
            boolean colAutoWidth = false;
            while (itr.hasNext()) {
                IPropertySet.IEntry curProp = (IPropertySet.IEntry)itr.next();
                String key = curProp.getKey();
                Object value = curProp.getValue();
                if (!"style".equals(key) || null == value) continue;
                styleStr = value.toString();
            }
            colAutoWidth = this.gridData.getColAutoSize(col);
            colsBuffer.append("<col idx='" + col + "' ");
            if (colAutoWidth) {
                colGroupAutoWidth = true;
                colsBuffer.append(" autowidth='true' ");
            }
            colsBuffer.append("style='").append(styleStr).append("'/>");
        }
        if (colGroupAutoWidth) {
            output.append("<colgroup autowidth='true'>");
        } else {
            output.append("<colgroup>");
        }
        output.append(colsBuffer);
        output.append("</colgroup>");
    }

    public final String getTableWidth() {
        return Integer.toString(this.calcGridWidth());
    }

    public final String getTableHeight() {
        return Integer.toString(this.calcGridHeight());
    }

    public final String getCellStyles() {
        StringBuffer stylesBuffer = new StringBuffer();
        this.getCellStyles(stylesBuffer);
        return stylesBuffer.toString();
    }

    public final void getCellStyles(StringBuffer output) {
        int i;
        if (this.gridData == null || !StringUtils.isEmpty((String)this.gridData.getDataClass())) {
            return;
        }
        this.beforeGetCellStyles(output);
        GridCell[] styleCells = this.gridData.getCellSytles();
        for (i = 0; i < styleCells.length; ++i) {
            output.append(".");
            output.append(CELL_STYLE_PREFIX);
            output.append(i);
            output.append(" {\n");
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], output, this.browserType);
            output.append("}\n");
        }
        styleCells = this.gridData.getCellStyles(2, 1, this.gridData.getColCount() - 1, 1);
        for (i = 0; i < styleCells.length; ++i) {
            if (styleCells[i] == null) continue;
            output.append(".");
            output.append(this.getCellClassName(styleCells[i]));
            output.append(" {\n");
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], output, this.browserType);
            output.append("}\n");
        }
        styleCells = this.gridData.getCellStyles(1, 2, 1, this.gridData.getRowCount() - 1);
        for (i = 0; i < styleCells.length; ++i) {
            if (styleCells[i] == null) continue;
            output.append(".");
            output.append(this.getCellClassName(styleCells[i]));
            output.append(" {\n");
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], output, this.browserType);
            output.append("}\n");
        }
        GridCell a1 = this.gridData.getCell(1, 1);
        output.append(".");
        output.append(this.getCellClassName(a1));
        output.append(" {\n");
        HTMLGridGenerator.getSingleCellStyle(a1, output, this.browserType);
        output.append("}\n");
        this.afterGetCellStyles(output);
    }

    public final void getCellStylesObject(IPropertySet set) {
        PropertySet singleCellStyle;
        int i;
        if (this.gridData == null || !StringUtils.isEmpty((String)this.gridData.getDataClass())) {
            return;
        }
        GridCell[] styleCells = this.gridData.getCellSytles();
        for (i = 0; i < styleCells.length; ++i) {
            singleCellStyle = new PropertySet();
            set.put(CELL_STYLE_PREFIX + i, singleCellStyle);
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], singleCellStyle, this.browserType);
        }
        styleCells = this.gridData.getCellStyles(2, 1, this.gridData.getColCount() - 1, 1);
        for (i = 0; i < styleCells.length; ++i) {
            if (styleCells[i] == null) continue;
            singleCellStyle = new PropertySet();
            set.put(this.getCellClassName(styleCells[i]), singleCellStyle);
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], singleCellStyle, this.browserType);
        }
        styleCells = this.gridData.getCellStyles(1, 2, 1, this.gridData.getRowCount() - 1);
        for (i = 0; i < styleCells.length; ++i) {
            if (styleCells[i] == null) continue;
            singleCellStyle = new PropertySet();
            set.put(this.getCellClassName(styleCells[i]), singleCellStyle);
            HTMLGridGenerator.getSingleCellStyle(styleCells[i], singleCellStyle, this.browserType);
        }
        GridCell a1 = this.gridData.getCell(1, 1);
        singleCellStyle = new PropertySet();
        set.put(this.getCellClassName(a1), singleCellStyle);
        HTMLGridGenerator.getSingleCellStyle(a1, singleCellStyle, this.browserType);
    }

    public int getBrowserType() {
        return this.browserType;
    }

    public void setBrowserType(int type) {
    }

    private static void getSingleCellStyle(GridCell cell, StringBuffer output, int browseType) {
        String fontName;
        String color = HTMLGridGenerator.getHTMLColor(cell.getFontColor());
        String strBackColor = HTMLGridGenerator.getHTMLColor(cell.getBackColor());
        String strBEdgeColor = HTMLGridGenerator.getHTMLColor(cell.getBEdgeColor());
        String strBEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getBEdgeStyle());
        String strREdgeColor = HTMLGridGenerator.getHTMLColor(cell.getREdgeColor());
        String strREdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getREdgeStyle());
        String strLEdgeColor = cell.getColNum() == 1 && cell.getREdgeStyle() == 0 && cell.getLEdgeStyle() == 0 ? HTMLGridGenerator.getHTMLColor(cell.getREdgeColor()) : HTMLGridGenerator.getHTMLColor(cell.getLEdgeColor());
        String strLEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getLEdgeStyle());
        String strTEdgeColor = cell.getRowNum() == 1 && cell.getBEdgeStyle() == 0 && cell.getTEdgeStyle() == 0 ? HTMLGridGenerator.getHTMLColor(cell.getBEdgeColor()) : HTMLGridGenerator.getHTMLColor(cell.getTEdgeColor());
        String strTEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getTEdgeStyle());
        int fontSize = cell.getFontSize();
        int getREdgeWidth = HTMLGridGenerator.getBorderWidth(cell.getREdgeStyle());
        int getBEdgeWidth = HTMLGridGenerator.getBorderWidth(cell.getBEdgeStyle());
        int getLEdgeWidth = HTMLGridGenerator.getLBorderWidth(cell);
        int getTEdgeWidth = HTMLGridGenerator.getTBorderWidth(cell);
        int borderStyle = cell.getBackStyle();
        output.append("");
        if (!(color.equals("#000000") || color.equals("") || "window".equalsIgnoreCase(color))) {
            output.append("color:" + color + ";\n");
        }
        if (cell.getSilverHead()) {
            strTEdgeColor = cell.getRowNum() == 1 ? "#000000" : "#FFFFFF";
            strLEdgeColor = "#000000";
            strREdgeColor = "#000000";
            strBEdgeColor = "#000000";
            getTEdgeWidth = cell.getRowNum() == 1 ? 1 : 0;
            getREdgeWidth = 1;
            getBEdgeWidth = 1;
            getLEdgeWidth = 1;
            strBackColor = "buttonface";
        } else {
            if (cell.getRowNum() != 1) {
                getTEdgeWidth = 0;
            }
            if (cell.getColNum() != 1) {
                getLEdgeWidth = 0;
            }
        }
        output.append("border-top-color:" + strTEdgeColor + ";\n");
        output.append("border-left-color:" + strLEdgeColor + ";\n");
        output.append("border-right-color:" + strREdgeColor + ";\n");
        output.append("border-bottom-color:" + strBEdgeColor + ";\n");
        output.append("border-top-style:" + strTEdgeStyle + ";\n");
        output.append("border-left-style:" + strLEdgeStyle + ";\n");
        output.append("border-right-style:" + strREdgeStyle + ";\n");
        output.append("border-bottom-style:" + strBEdgeStyle + ";\n");
        output.append("border-top-width:" + getTEdgeWidth + "px;\n");
        output.append("border-left-width:" + getLEdgeWidth + "px;\n");
        output.append("border-right-width:" + getREdgeWidth + "px;\n");
        output.append("border-bottom-width:" + getBEdgeWidth + "px;\n");
        output.append("font-size:" + fontSize + "pt;\n");
        if (cell.getFontBold()) {
            output.append("font-weight:bold;\n");
        }
        String halign = HTMLGridGenerator.getCellHorzAlign(cell);
        String valign = HTMLGridGenerator.getCellVertAlign(cell);
        if (cell.getVertText() && browseType == 1) {
            output.append("direction:inherit;\n");
            output.append("writing-mode:tb-rl;\n");
            if (!halign.equals("")) {
                output.append("vertical-align:" + HTMLGridGenerator.halignToValign(halign) + ";\n");
            }
            output.append("text-align:" + HTMLGridGenerator.valignToHalign(valign) + ";\n");
        } else {
            if (!halign.equals("") && !halign.equals("left")) {
                output.append("text-align:" + halign + ";\n");
            }
            output.append("vertical-align:" + valign + ";\n");
        }
        int indent = cell.getIndent();
        if (indent != 0) {
            output.append("text-indent:" + indent + "em;");
        }
        if (cell.getFontItalic()) {
            output.append("font-style:italic;\n");
        }
        if (cell.getFontUnderLine() || cell.getFontStrikeOut()) {
            String temp = "";
            temp = temp + (cell.getFontUnderLine() ? "underline " : "");
            temp = temp + (cell.getFontStrikeOut() ? "line-through " : "");
            output.append("text-decoration:" + temp + ";\n");
        }
        if (!"".equals((fontName = cell.getFontName()).trim())) {
            fontName = StringUtils.replace((String)fontName, (String)"\"", (String)"");
            output.append("font-family:\"" + fontName + "\";\n");
        }
        if (borderStyle == 0) {
            output.append("background-color:#ffffff;\n");
        } else if (!strBackColor.equals("")) {
            output.append("background-color:" + strBackColor + ";\n");
        }
        if (cell.getWrapLine()) {
            output.append("word-wrap:break-word;\n");
            output.append("word-break:break-all;\n");
        }
        output.append("overflow:hidden;\n");
        output.append("padding:0px ").append(4).append("px;\n");
    }

    private static void getSingleCellStyle(GridCell cell, IPropertySet set, int browseType) {
        String fontName;
        String color = HTMLGridGenerator.getHTMLColor(cell.getFontColor());
        String strBackColor = HTMLGridGenerator.getHTMLColor(cell.getBackColor());
        String strBEdgeColor = HTMLGridGenerator.getHTMLColor(cell.getBEdgeColor());
        String strBEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getBEdgeStyle());
        String strREdgeColor = HTMLGridGenerator.getHTMLColor(cell.getREdgeColor());
        String strREdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getREdgeStyle());
        String strLEdgeColor = cell.getColNum() == 1 && cell.getREdgeStyle() == 0 && cell.getLEdgeStyle() == 0 ? HTMLGridGenerator.getHTMLColor(cell.getREdgeColor()) : HTMLGridGenerator.getHTMLColor(cell.getLEdgeColor());
        String strLEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getLEdgeStyle());
        String strTEdgeColor = cell.getRowNum() == 1 && cell.getBEdgeStyle() == 0 && cell.getTEdgeStyle() == 0 ? HTMLGridGenerator.getHTMLColor(cell.getBEdgeColor()) : HTMLGridGenerator.getHTMLColor(cell.getTEdgeColor());
        String strTEdgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getTEdgeStyle());
        int fontSize = cell.getFontSize();
        int getREdgeWidth = HTMLGridGenerator.getBorderWidth(cell.getREdgeStyle());
        int getBEdgeWidth = HTMLGridGenerator.getBorderWidth(cell.getBEdgeStyle());
        int getLEdgeWidth = HTMLGridGenerator.getLBorderWidth(cell);
        int getTEdgeWidth = HTMLGridGenerator.getTBorderWidth(cell);
        int borderStyle = cell.getBackStyle();
        if (!(color.equals("#000000") || color.equals("") || "window".equalsIgnoreCase(color))) {
            set.put("color", color);
        }
        if (cell.getSilverHead()) {
            strTEdgeColor = cell.getRowNum() == 1 ? "#000000" : "#FFFFFF";
            strLEdgeColor = cell.getColNum() == 1 ? "#000000" : "#FFFFFF";
            strREdgeColor = "#000000";
            strBEdgeColor = "#000000";
            getTEdgeWidth = cell.getRowNum() == 1 ? 1 : 0;
            getREdgeWidth = 1;
            getBEdgeWidth = 1;
            getLEdgeWidth = 1;
            strBackColor = "buttonface";
        } else {
            if (cell.getRowNum() != 1) {
                getTEdgeWidth = 0;
            }
            if (cell.getColNum() != 1) {
                getLEdgeWidth = 0;
            }
        }
        set.put("border-top-color", strTEdgeColor);
        set.put("border-left-color", strLEdgeColor);
        set.put("border-right-color", strREdgeColor);
        set.put("border-bottom-color", strBEdgeColor);
        set.put("border-top-style", strTEdgeStyle);
        set.put("border-left-style", strLEdgeStyle);
        set.put("border-right-style", strREdgeStyle);
        set.put("border-bottom-style", strBEdgeStyle);
        set.put("border-top-width", String.valueOf(getTEdgeWidth) + "px");
        set.put("border-left-width", String.valueOf(getLEdgeWidth) + "px");
        set.put("border-right-width", String.valueOf(getREdgeWidth) + "px");
        set.put("border-bottom-width", String.valueOf(getBEdgeWidth) + "px");
        set.put("font-size", fontSize + "pt");
        if (cell.getFontBold()) {
            set.put("font-weight", "bold");
        }
        String halign = HTMLGridGenerator.getCellHorzAlign(cell);
        String valign = HTMLGridGenerator.getCellVertAlign(cell);
        if (cell.getVertText() && browseType == 1) {
            set.put("direction", "inherit");
            set.put("writing-mode", "tb-rl");
            if (!halign.equals("")) {
                set.put("vertical-align", HTMLGridGenerator.halignToValign(halign));
            }
            set.put("text-align", HTMLGridGenerator.valignToHalign(valign));
        } else {
            if (!halign.equals("") && !halign.equals("left")) {
                set.put("text-align", halign);
            }
            set.put("vertical-align", valign);
        }
        int indent = cell.getIndent();
        if (indent != 0) {
            set.put("text-indent", indent + "em");
        }
        if (cell.getFontItalic()) {
            set.put("font-style", "italic");
        }
        if (cell.getFontUnderLine() || cell.getFontStrikeOut()) {
            String temp = "";
            temp = temp + (cell.getFontUnderLine() ? "underline " : "");
            temp = temp + (cell.getFontStrikeOut() ? "line-through " : "");
            set.put("text-decoration", temp);
        }
        if (!"".equals((fontName = cell.getFontName()).trim())) {
            fontName = StringUtils.replace((String)fontName, (String)"\"", (String)"");
            set.put("font-family", fontName);
        }
        if (borderStyle == 0) {
            set.put("background-color", "#ffffff");
        } else if (!strBackColor.equals("")) {
            set.put("background-color", strBackColor);
        }
        if (cell.getWrapLine()) {
            set.put("word-wrap", "break-word");
            set.put("word-break", "break-all");
        }
        set.put("overflow", "hidden");
    }

    private static String halignToValign(String halign) {
        if ("left".equals(halign)) {
            return "bottom";
        }
        if ("center".equals(halign)) {
            return "middle";
        }
        if ("right".equals(halign)) {
            return "top";
        }
        return "";
    }

    private static String valignToHalign(String valign) {
        if ("top".equals(valign)) {
            return "left";
        }
        if ("middle".equals(valign)) {
            return "center";
        }
        if ("bottom".equals(valign)) {
            return "right";
        }
        return "";
    }

    private static String getHTMLColor(int colorValue) {
        return Html.rgb2HtmlColor((int)colorValue);
    }

    private static String getCellHorzAlign(GridCell cell) {
        switch (cell.getHorzAlign()) {
            case 2: {
                return "right";
            }
            case 3: {
                return "center";
            }
            case 1: {
                return "left";
            }
        }
        return "";
    }

    private static String getCellVertAlign(GridCell cell) {
        switch (cell.getVertAlign()) {
            case 1: {
                return "top";
            }
            case 2: {
                return "bottom";
            }
        }
        return "middle";
    }

    private static String getEdgeStyleString(int styleValue) {
        switch (styleValue) {
            case 0: {
                return "solid";
            }
            case 1: {
                return "none";
            }
            case 2: {
                return "solid";
            }
            case 3: {
                return "dashed";
            }
            case 4: 
            case 9: 
            case 15: {
                return "dotted";
            }
            case 7: {
                return "double";
            }
        }
        return styleValue >= 10 ? "double" : "";
    }

    private static int getBorderWidth(int edgeStyle) {
        if (edgeStyle == 10) {
            return 3;
        }
        if (edgeStyle > 13) {
            return 3;
        }
        if (edgeStyle > 4) {
            return 2;
        }
        return 1;
    }

    private static int getLBorderWidth(GridCell cell) {
        if (cell.getColNum() == 1) {
            return HTMLGridGenerator.getBorderWidth(cell.getLEdgeStyle());
        }
        return cell.getBackStyle() == 28 ? 1 : 0;
    }

    private static int getTBorderWidth(GridCell cell) {
        if (cell.getRowNum() == 1) {
            return HTMLGridGenerator.getBorderWidth(cell.getTEdgeStyle());
        }
        return cell.getBackStyle() == 28 ? 1 : 0;
    }

    protected static String htmlEncoding(String inString) {
        return Html.encodeText((String)inString);
    }

    private void genTD(int col, int row, StringBuffer output) {
        GridCell cell = this.gridData.getCell(col, row);
        this.startCell(cell);
        PropertySet cellProps = new PropertySet();
        this.getCellTDProperties(cell, cellProps);
        String innerHTML = this.genTDContent(cell);
        output.append("<td idx='" + col + "' ");
        this.printAttributes(output, cellProps);
        output.append(">");
        output.append(innerHTML);
        output.append("</td>");
        this.stopCell(cell);
    }

    private void printAttributes(StringBuffer output, IPropertySet props) {
        Iterator propItr = props.iterator();
        while (propItr.hasNext()) {
            IPropertySet.IEntry curProp = (IPropertySet.IEntry)propItr.next();
            String key = curProp.getKey();
            Object value = curProp.getValue();
            output.append(" ");
            output.append(key);
            if (value == NULL_PROP_VALUE || value == null) continue;
            output.append("='");
            if (value instanceof Boolean) {
                Boolean boolValue = (Boolean)value;
                output.append(boolValue != false ? "1" : "0");
            } else {
                output.append(value);
            }
            output.append("'");
        }
    }

    protected String genTDContent(GridCell cell) {
        String innerHTML = null;
        switch (cell.getDataType()) {
            case 6: {
                innerHTML = this.getCellImageHTML(cell);
                break;
            }
            case 9: {
                innerHTML = this.getCellHotLinkHTML(cell);
                break;
            }
            case 3: {
                CurrencyCellProperty currency = new CurrencyCellProperty(cell);
                if (currency.getShowCurrencyBox()) {
                    int showType = 0;
                    if (currency.getBigChineseChar()) {
                        showType = 2;
                    } else if (currency.getChineseNumber()) {
                        showType = 1;
                    }
                    String data = cell.getCellData();
                    if (StringUtils.isEmpty((String)data)) {
                        data = "0";
                    }
                    CurrencyBoxHTMLGenerator boxGen = new CurrencyBoxHTMLGenerator(currency.getDecimal(), currency.getPrecision(), data, showType, currency.getUnitString());
                    innerHTML = boxGen.generate();
                    break;
                }
                innerHTML = HTMLGridGenerator.htmlEncoding(currency.getShowText(cell.getCellData()));
                break;
            }
            default: {
                innerHTML = cell.getColNum() == 0 && cell.getRowNum() == 0 ? "" : (cell.getColNum() == 0 ? Integer.toString(cell.getRowNum()) : (cell.getRowNum() == 0 ? HTMLGridGenerator.getABCValue(cell.getColNum()) : this.getCellTDContent(cell)));
                if (innerHTML == null || innerHTML.length() <= 0) break;
                innerHTML = HTMLGridGenerator.htmlEncoding(innerHTML);
            }
        }
        return innerHTML == null || innerHTML.length() == 0 ? "&nbsp;" : innerHTML;
    }

    private static String getABCValue(int value) {
        if (value < 0) {
            return "";
        }
        String returnStr = "";
        int modValue = value % 26;
        char returnInt = (char)("A".charAt(0) + modValue);
        char[] a = new char[]{returnInt};
        returnStr = String.valueOf(a);
        int divValue = value / 26;
        if (divValue >= 1) {
            returnStr = HTMLGridGenerator.getABCValue(divValue - 1) + returnStr;
        }
        return returnStr;
    }

    private String getCellImageHTML(GridCell cell) {
        String imgRef = cell.getImageReference();
        if (imgRef == null || imgRef.length() == 0) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        result.append("<img");
        if (cell.getHorzAlign() == 5 && !this.gridData.getColAutoSize(cell.getColNum())) {
            result.append(" width=\"");
            result.append(cell.getWidth());
            result.append("\"");
        }
        if (cell.getVertAlign() == 5 && !this.gridData.getRowAutoSize(cell.getRowNum())) {
            result.append(" height=\"");
            result.append(cell.getHeight());
            result.append("\"");
        }
        result.append(" src=\"");
        result.append(StringUtils.replace((String)this.getRealLinkAddr(imgRef), (String)"\\", (String)"/"));
        result.append("\"/>");
        return result.toString();
    }

    private String getCellHotLinkHTML(GridCell cell) {
        String[] linkInfos = cell.getLinkInformation();
        if (linkInfos == null || linkInfos.length != 3) {
            return "";
        }
        if (linkInfos[1] == null || linkInfos[1].length() == 0) {
            if (linkInfos[0] == null || linkInfos[0].length() == 0) {
                return null;
            }
            return HTMLGridGenerator.htmlEncoding(linkInfos[0]);
        }
        StringBuffer result = new StringBuffer();
        String content = StringUtils.replace((String)linkInfos[1], (String)"\"", (String)"'");
        if (HTMLGridGenerator.isJavaScriptLink(content)) {
            result.append("<a href=\"javascript:");
            result.append(content);
        } else {
            result.append("<a href=\"");
            result.append(this.getRealLinkAddr(content));
        }
        result.append("\"");
        if (linkInfos[2] != null && linkInfos[2].length() != 0) {
            result.append(" target=\"");
            result.append(linkInfos[2]);
            result.append("\"");
        }
        result.append(">");
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            result.append(HTMLGridGenerator.htmlEncoding(linkInfos[0]));
        } else {
            result.append("&nbsp;");
        }
        result.append("</a>");
        return result.toString();
    }

    private static boolean isJavaScriptLink(String content) {
        if (content == null || content.length() == 0) {
            return false;
        }
        int pos = content.indexOf(40);
        if (pos <= 0 || pos >= content.length() - 1) {
            return false;
        }
        pos = content.indexOf(41);
        return pos == content.length() - 1;
    }

    public static String makeCellID(int col, int row) {
        return "cell_" + Integer.toString(row) + "_" + Integer.toString(col);
    }

    private String getRealLinkAddr(String link) {
        String context = this.getContextPath();
        if (StringUtils.isEmpty((String)context)) {
            return link;
        }
        if (link.toLowerCase().startsWith("http:")) {
            return link;
        }
        return link.startsWith("/") ? context + link : context + "/" + link;
    }

    private int calcGridWidth() {
        if (this.gridWidth == 0 && this.gridData != null) {
            for (int i = 0; i < this.gridData.getColCount(); ++i) {
                if (!this.gridData.getColVisible(i)) continue;
                this.gridWidth += this.gridData.getColWidths(i);
            }
        }
        return this.gridWidth;
    }

    private int calcGridHeight() {
        if (this.gridHeight == 0 && this.gridData != null) {
            for (int i = 0; i < this.gridData.getRowCount(); ++i) {
                if (!this.gridData.getRowVisible(i)) continue;
                this.gridHeight += this.gridData.getRowHeights(i);
            }
        }
        return this.gridHeight;
    }

    protected String getContextPath() {
        return null;
    }

    protected boolean startGenerate() {
        return true;
    }

    protected void stopGenerate() {
    }

    protected void startRow(int row) {
    }

    protected void stopRow(int row) {
    }

    protected void startCell(GridCell cell) {
    }

    protected void stopCell(GridCell cell) {
    }

    protected void getRowTRProperties(int row, IPropertySet rowProps) {
        String styleStr = "";
        if (!this.gridData.getRowVisible(row) || row == 0) {
            styleStr = "display:none;";
        }
        GridCell cell = this.gridData.getCell(0, row);
        if (!this.gridData.getRowAutoSize(row)) {
            styleStr = styleStr + "height:" + cell.getCellHeight() + "px;";
        }
        rowProps.put("style", styleStr);
    }

    protected void getCellTDProperties(GridCell cell, IPropertySet cellProps) {
        String cellScript;
        int i;
        int visibleNumber;
        String className = this.getCellClassName(cell);
        if (StringUtils.isEmpty((String)className) && StringUtils.isEmpty((String)this.gridData.getDataClass())) {
            className = CELL_STYLE_PREFIX + this.gridData.getCellStyleIndex(cell.getColNum(), cell.getRowNum());
        }
        if (!StringUtils.isEmpty((String)className)) {
            cellProps.put("class", className);
        }
        if (cell.getColSpan() > 1) {
            int colSpan = cell.getColSpan();
            visibleNumber = cell.getColSpan();
            for (i = cell.getColNum() + 1; i < cell.getColNum() + colSpan; ++i) {
                if (this.gridData.getColVisible(i)) continue;
                --visibleNumber;
            }
            cellProps.put("colSpan", new Integer(visibleNumber));
        }
        if (cell.getRowSpan() > 1) {
            int rowSpan = cell.getRowSpan();
            visibleNumber = cell.getRowSpan();
            for (i = cell.getRowNum() + 1; i < cell.getRowNum() + rowSpan; ++i) {
                if (this.gridData.getRowVisible(i)) continue;
                --visibleNumber;
            }
            cellProps.put("rowSpan", new Integer(visibleNumber));
        }
        if (this.browserType != 2 && cell.getRowNum() == 0 && !this.gridData.getColAutoSize(cell.getColNum())) {
            cellProps.put("width", new Integer(cell.getCellWidth()));
        }
        if (!cell.getWrapLine()) {
            cellProps.put("nowrap", NULL_PROP_VALUE);
        }
        if (cell.getFitFontSize()) {
            cellProps.put("fft", Integer.toString(1));
        }
        if (this.printCellScript && !StringUtils.isEmpty((String)(cellScript = cell.getScript()))) {
            cellProps.put(SCRIPT_ATTR_NAME, cellScript);
        }
        if (cell.getCellData() != null && cell.getDataType() != 9 && cell.getDataType() != 6) {
            cellProps.put("rV", cell.getCellData());
        }
        this.getInlineStyle(cell, cellProps);
    }

    private void getInlineStyle(GridCell cell, IPropertySet cellProps) {
        String edgeColor;
        int edgeWidth;
        String edgeStyle;
        GridCell rawCell;
        PropertySet inlineStyles = new PropertySet();
        if (cell.getColNum() == 0) {
            inlineStyles.put("display", "none");
        }
        if (!(this.browserType != 2 && cell.getCellWidth() != 0 || this.gridData.getColAutoSize(cell.getColNum()) || cell.getRowNum() != this.firstRowNum && cell.getRowNum() != 0 && Boolean.FALSE != this.colWidthOutputStatusMap.get(cell.getColNum()))) {
            if (cell.getCellField().left != cell.getColNum()) {
                this.colWidthOutputStatusMap.put(cell.getColNum(), Boolean.FALSE);
            } else {
                inlineStyles.put("width", cell.getCellWidth() + "px");
                this.colWidthOutputStatusMap.put(cell.getColNum(), Boolean.TRUE);
            }
        }
        CellField field = cell.getCellField();
        if (field.left != cell.getColNum() || field.top != cell.getRowNum()) {
            inlineStyles.put("display", "none");
            cellProps.put("oCI", cell.getColNum() - cell.getCellLeft() + "");
        }
        if (field.left != field.right && field.left == cell.getColNum()) {
            rawCell = new GridCell();
            rawCell.initRaw(this.gridData, field.left, field.top);
            if (cell.getREdgeStyle() != rawCell.getREdgeStyle()) {
                edgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getREdgeStyle());
                edgeWidth = cell.getSilverHead() ? 1 : HTMLGridGenerator.getBorderWidth(cell.getREdgeColor());
                inlineStyles.put("border-right-style", edgeStyle);
                inlineStyles.put("border-rigth-width", edgeWidth + "px");
            }
            if (cell.getREdgeColor() != rawCell.getREdgeColor()) {
                edgeColor = HTMLGridGenerator.getHTMLColor(cell.getREdgeColor());
                inlineStyles.put("border-right-color", edgeColor);
            }
        }
        if (field.top != field.bottom && field.top == cell.getRowNum()) {
            rawCell = new GridCell();
            rawCell.initRaw(this.gridData, field.left, field.top);
            if (cell.getBEdgeStyle() != rawCell.getBEdgeStyle()) {
                edgeStyle = HTMLGridGenerator.getEdgeStyleString(cell.getBEdgeStyle());
                edgeWidth = cell.getSilverHead() ? 1 : HTMLGridGenerator.getBorderWidth(cell.getBEdgeStyle());
                inlineStyles.put("border-bottom-style", edgeStyle);
                inlineStyles.put("border-bottom-width", edgeWidth + "px");
            }
            if (cell.getBEdgeColor() != rawCell.getBEdgeColor()) {
                edgeColor = HTMLGridGenerator.getHTMLColor(cell.getBEdgeColor());
                inlineStyles.put("border-bottom-color", edgeColor);
            }
        }
        if (!this.gridData.getColVisible(cell.getColNum())) {
            inlineStyles.put("display", "none");
        }
        if (cell.getVertText() && this.browserType == 1) {
            inlineStyles.put("width", cell.getWidth() + "px");
        }
        if (!inlineStyles.isEmpty()) {
            String styles = this.printInlineStyles(inlineStyles);
            cellProps.put("style", styles);
        }
    }

    private String printInlineStyles(IPropertySet inlineStyles) {
        StringBuffer buffer = new StringBuffer();
        Iterator i = inlineStyles.iterator();
        while (i.hasNext()) {
            IPropertySet.IEntry entry = (IPropertySet.IEntry)i.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            buffer.append(key);
            if (value != NULL_PROP_VALUE && value != null) {
                buffer.append(':');
                if (value instanceof Boolean) {
                    buffer.append((Boolean)value != false ? (char)'1' : '0');
                } else {
                    buffer.append(value.toString());
                }
            }
            buffer.append(';');
        }
        return buffer.toString();
    }

    protected String getCellClassName(GridCell cell) {
        String className = cell.getCssClass();
        if (StringUtils.isEmpty((String)className) && StringUtils.isEmpty((String)this.gridData.getDataClass())) {
            if (cell.getColNum() == 1 && cell.getRowNum() == 1) {
                className = "tA1";
            } else {
                className = CELL_STYLE_PREFIX + Integer.toString(this.gridData.getCellStyleIndex(cell.getColNum(), cell.getRowNum()));
                if (cell.getColNum() == 1) {
                    className = className + CELL_STYLE_LEFT;
                } else if (cell.getRowNum() == 1) {
                    className = className + CELL_STYLE_TOP;
                }
            }
        }
        return className;
    }

    protected String getCellTDContent(GridCell cell) {
        return cell.getShowText();
    }

    protected void beforeGetCellStyles(StringBuffer output) {
    }

    protected void afterGetCellStyles(StringBuffer output) {
    }

    protected static final class PropertySet
    implements IPropertySet {
        private Map finder = new HashMap();
        private List entries = new ArrayList();

        protected PropertySet() {
        }

        @Override
        public void put(String key, Object value) {
            Entry entry = (Entry)this.finder.get(key);
            if (entry == null) {
                entry = new Entry(key, value);
                this.finder.put(key, entry);
                this.entries.add(entry);
            } else {
                entry.setValue(value);
            }
        }

        @Override
        public void remove(String key) {
            Entry entry = (Entry)this.finder.remove(key);
            if (entry != null) {
                this.entries.remove(entry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.entries.iterator();
        }

        @Override
        public void clear() {
            this.finder.clear();
            this.entries.clear();
        }

        @Override
        public int size() {
            return this.entries.size();
        }

        @Override
        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        public static final class Entry
        implements IPropertySet.IEntry {
            private String key;
            private Object value;

            public Entry(String key, Object value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public String getKey() {
                return this.key;
            }

            @Override
            public Object getValue() {
                return this.value;
            }

            public void setValue(Object value) {
                this.value = value;
            }
        }
    }
}

