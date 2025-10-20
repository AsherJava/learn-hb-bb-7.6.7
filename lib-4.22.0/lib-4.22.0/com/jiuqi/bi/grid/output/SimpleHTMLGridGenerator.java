/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid.output;

import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.output.CurrencyBoxHTMLGenerator;
import com.jiuqi.bi.grid.output.HTMLGridGenerator;
import com.jiuqi.bi.grid.output.IPropertySet;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.util.Iterator;

public class SimpleHTMLGridGenerator {
    private GridData gridData;
    protected static final Object NULL_PROP_VALUE = new Object();

    public SimpleHTMLGridGenerator(GridData gridData) {
        this.setGridData(gridData);
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
        if (gridData != null) {
            for (int row = 1; row < gridData.getRowCount() && !gridData.getRowVisible(row); ++row) {
            }
        }
    }

    public final void generate(StringBuffer output) {
        if (this.gridData == null) {
            return;
        }
        for (int row = 0; row < this.gridData.getRowCount(); ++row) {
            output.append("<tr ");
            String styleStr = "";
            if (!this.gridData.getRowVisible(row) || row == 0) {
                styleStr = "display:none;";
            }
            GridCell cell0 = this.gridData.getCell(0, row);
            if (!this.gridData.getRowAutoSize(row)) {
                styleStr = styleStr + " height:" + cell0.getCellHeight() + "px;";
            }
            if (styleStr.length() > 0) {
                output.append(" style=\"").append(styleStr).append("\"");
            }
            output.append(">");
            for (int col = 0; col < this.gridData.getColCount(); ++col) {
                GridCell cell = this.gridData.getCell(col, row);
                HTMLGridGenerator.PropertySet cellProps = new HTMLGridGenerator.PropertySet();
                this.getCellTDProperties(cell, cellProps);
                HTMLGridGenerator.PropertySet styleProps = new HTMLGridGenerator.PropertySet();
                this.getStyleProperties(cell, styleProps);
                String innerHTML = this.genTDContent(cell);
                output.append("<td");
                this.printAttributes(output, cellProps);
                this.printStyleAttributes(output, styleProps);
                output.append(">");
                output.append(innerHTML);
                output.append("</td>");
            }
            output.append("</tr>\n");
        }
    }

    protected void getCellTDProperties(GridCell cell, IPropertySet cellProps) {
        int i;
        int visibleNumber;
        if (!this.gridData.getColAutoSize(cell.getColNum()) && (cell.getRowNum() == 0 || cell.getSilverHead())) {
            cellProps.put("width", new Integer(cell.getCellWidth()));
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
        if (!cell.getWrapLine()) {
            cellProps.put(" nowrap", NULL_PROP_VALUE);
        }
        this.getInlineStyle(cell, cellProps);
    }

    protected void getStyleProperties(GridCell cell, IPropertySet styleProps) {
        CellDataProperty cellProp;
        byte dtype;
        if (cell.getSilverHead()) {
            int horzAlign = cell.getHorzAlign();
            if (horzAlign == 3) {
                styleProps.put("text-align", "center");
            } else if (horzAlign == 2) {
                styleProps.put("text-align", "right");
            } else if (horzAlign == 5) {
                styleProps.put("text-align", "justify");
            }
            String bgColor = Html.rgb2HtmlColor(cell.getBackColor());
            styleProps.put("background-color", bgColor);
            if (cell.getFontBold()) {
                styleProps.put("font-weight", "bold");
            }
        }
        if ((dtype = (cellProp = new CellDataProperty(cell)).getDataType()) == 2) {
            styleProps.put("text-align", "right");
        }
    }

    private void getInlineStyle(GridCell cell, IPropertySet cellProps) {
        HTMLGridGenerator.PropertySet inlineStyles = new HTMLGridGenerator.PropertySet();
        if (cell.getColNum() == 0) {
            inlineStyles.put("display", "none");
        }
        CellField field = cell.getCellField();
        if (field.left != cell.getColNum() || field.top != cell.getRowNum()) {
            inlineStyles.put("display", "none");
        }
        if (!this.gridData.getColVisible(cell.getColNum())) {
            inlineStyles.put("display", "none");
        }
        if (!inlineStyles.isEmpty()) {
            String styles = this.printInlineStyles(inlineStyles);
            cellProps.put("style", styles);
        }
    }

    protected static String htmlEncoding(String inString) {
        return Html.encodeText(inString);
    }

    protected String getCellTDContent(GridCell cell) {
        return cell.getShowText();
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
                    if (StringUtils.isEmpty(data)) {
                        data = "0";
                    }
                    CurrencyBoxHTMLGenerator boxGen = new CurrencyBoxHTMLGenerator(currency.getDecimal(), currency.getPrecision(), data, showType, currency.getUnitString());
                    innerHTML = boxGen.generate();
                    break;
                }
                innerHTML = SimpleHTMLGridGenerator.htmlEncoding(currency.getShowText(cell.getCellData()));
                break;
            }
            default: {
                innerHTML = cell.getColNum() == 0 && cell.getRowNum() == 0 ? "" : (cell.getColNum() == 0 ? Integer.toString(cell.getRowNum()) : (cell.getRowNum() == 0 ? SimpleHTMLGridGenerator.getABCValue(cell.getColNum()) : this.getCellTDContent(cell)));
                if (innerHTML == null || innerHTML.length() <= 0) break;
                innerHTML = SimpleHTMLGridGenerator.htmlEncoding(innerHTML);
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
            returnStr = SimpleHTMLGridGenerator.getABCValue(divValue - 1) + returnStr;
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
        result.append("/>");
        return result.toString();
    }

    private String printInlineStyles(IPropertySet inlineStyles) {
        StringBuffer buffer = new StringBuffer();
        Iterator<IPropertySet.IEntry> i = inlineStyles.iterator();
        while (i.hasNext()) {
            IPropertySet.IEntry entry = i.next();
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

    private String getCellHotLinkHTML(GridCell cell) {
        String[] linkInfos = cell.getLinkInformation();
        if (linkInfos == null || linkInfos.length != 3) {
            return "";
        }
        if (linkInfos[1] == null || linkInfos[1].length() == 0) {
            if (linkInfos[0] == null || linkInfos[0].length() == 0) {
                return null;
            }
            return SimpleHTMLGridGenerator.htmlEncoding(linkInfos[0]);
        }
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            return SimpleHTMLGridGenerator.htmlEncoding(linkInfos[0]);
        }
        return "&nbsp;";
    }

    private void printAttributes(StringBuffer output, IPropertySet props) {
        Iterator<IPropertySet.IEntry> propItr = props.iterator();
        while (propItr.hasNext()) {
            IPropertySet.IEntry curProp = propItr.next();
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

    private void printStyleAttributes(StringBuffer output, IPropertySet props) {
        Iterator<IPropertySet.IEntry> propItr = props.iterator();
        output.append(" style='");
        while (propItr.hasNext()) {
            IPropertySet.IEntry curProp = propItr.next();
            String key = curProp.getKey();
            Object value = curProp.getValue();
            output.append(key).append(":").append(value).append(";");
        }
        output.append("'");
    }
}

