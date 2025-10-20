/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.grid.h5;

import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid.GridCell;
import org.json.JSONException;
import org.json.JSONObject;

public class H5GridCell {
    private static String TAG_COL_INDEX = "colIndex";
    private static String TAG_ROW_INDEX = "rowIndex";
    private static String TAG_CELL_DATA = "cellData";
    private static String TAG_SHOW_TEXT = "showText";
    private static String TAG_BACK_COLOR = "backColor";
    private static String TAG_FONT_SIZE = "fontSize";
    private static String TAG_FONT_BOLD = "fontBold";
    private static String TAG_FONT_ITALIC = "fontItalic";
    private static String TAG_FONT_COLOR = "fontColor";
    private static String TAG_HORZ_ALIGN = "horzAlign";
    private static String TAG_VERT_ALIGN = "vertAlign";
    private static String TAG_CELL_MODE = "cellMode";
    private static String TAG_HTML = "html";
    private static String TAG_INDENT = "indent";
    private static String TAG_FONT_NAME = "fontName";
    private static String TAG_FIT_FONT_SIZE = "fitFontSize";
    private static final int CELL_MODE_NORMAL = 1;
    private static final int CELL_MODE_HTML = 4;
    private int colIndex;
    private int rowIndex;
    private String cellData;
    private String showText;
    private String backColor;
    private int fontSize = 12;
    private boolean fontBold = false;
    private boolean fontItalic = false;
    private String fontColor;
    private int horzAling = 0;
    private int vertAlign = 3;
    private String html;
    private int indent;
    private int cellMode = 1;
    private String fontName;
    private boolean fitFontSize;

    public H5GridCell() {
    }

    public H5GridCell(GridCell gridCell, int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.cellData = gridCell.getCellData();
        this.backColor = Html.rgb2HtmlColor((int)gridCell.getBackColor());
        this.fontSize = gridCell.getFontSize() * 4 / 3;
        this.fontBold = gridCell.getFontBold();
        this.fontItalic = gridCell.getFontItalic();
        this.fontColor = Html.rgb2HtmlColor((int)(gridCell.getFontColor() < 0 ? 0 : gridCell.getFontColor()));
        this.horzAling = gridCell.getHorzAlign();
        this.vertAlign = gridCell.getVertAlign();
        this.indent = gridCell.getIndent();
        this.fontName = gridCell.getFontName();
        this.fitFontSize = gridCell.getFitFontSize();
        this.buildContent(gridCell);
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getShowText() {
        return this.showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getBackColor() {
        return this.backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isFontBold() {
        return this.fontBold;
    }

    public void setFontBold(boolean fontBold) {
        this.fontBold = fontBold;
    }

    public boolean isFontItalic() {
        return this.fontItalic;
    }

    public void setFontItalic(boolean fontItalic) {
        this.fontItalic = fontItalic;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getHorzAling() {
        return this.horzAling;
    }

    public void setHorzAling(int horzAling) {
        this.horzAling = horzAling;
    }

    public int getVertAlign() {
        return this.vertAlign;
    }

    public void setVertAlign(int vertAlign) {
        this.vertAlign = vertAlign;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getCellMode() {
        return this.cellMode;
    }

    public void setCellMode(int cellMode) {
        this.cellMode = cellMode;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(TAG_COL_INDEX, this.colIndex);
        json.put(TAG_ROW_INDEX, this.rowIndex);
        json.put(TAG_CELL_DATA, (Object)(this.cellData == null ? "" : this.cellData));
        json.put(TAG_SHOW_TEXT, (Object)(this.showText == null ? "" : this.showText));
        json.put(TAG_BACK_COLOR, (Object)(this.backColor == null ? "#FFFFFF" : this.backColor));
        json.put(TAG_FONT_SIZE, this.fontSize);
        json.put(TAG_FONT_BOLD, this.fontBold);
        json.put(TAG_FONT_ITALIC, this.fontItalic);
        json.put(TAG_FONT_COLOR, (Object)(this.fontColor == null ? "#000000" : this.fontColor));
        json.put(TAG_HORZ_ALIGN, this.horzAling);
        json.put(TAG_VERT_ALIGN, this.vertAlign);
        json.put(TAG_HTML, (Object)(this.html == null ? "" : this.html));
        json.put(TAG_CELL_MODE, this.cellMode);
        json.put(TAG_INDENT, this.indent);
        json.put(TAG_FONT_NAME, (Object)this.fontName);
        json.put(TAG_FIT_FONT_SIZE, this.fitFontSize);
        return json;
    }

    private void buildContent(GridCell cell) {
        if (cell.getDataType() == 9) {
            this.html = this.getCellHotLinkHTML(cell);
            if (StringUtils.isNotEmpty((String)this.html)) {
                this.cellMode = 4;
            } else {
                this.showText = cell.getShowText();
            }
        } else {
            this.showText = cell.getShowText();
        }
    }

    private String getCellHotLinkHTML(GridCell cell) {
        String[] linkInfos = cell.getLinkInformation();
        if (linkInfos == null || linkInfos.length != 3) {
            return "";
        }
        if (linkInfos[1] == null || linkInfos[1].length() == 0) {
            if (linkInfos[0] == null || linkInfos[0].length() == 0) {
                return "";
            }
            return Html.encodeText((String)linkInfos[0]);
        }
        StringBuffer result = new StringBuffer("<div style='height:100%;width:100%;display: table;");
        result.append("text-align:").append(H5GridCell.getCellHorzAlign(cell)).append(";vertical-align:").append(H5GridCell.getCellVertAlign(cell)).append(";font-size:").append(this.fontSize).append("px;").append("font-family:").append(this.fontName).append(";").append("text-indent:").append(cell.getIndent()).append("em;");
        if (this.fontItalic) {
            result.append("font-style:italic;");
        }
        if (this.fontBold) {
            result.append("font-weight:bold;");
        }
        result.append("'>");
        String content = StringUtils.replace((String)linkInfos[1], (String)"\"", (String)"'");
        result.append("<a style='display: table-cell;vertical-align: middle;width: 100%;' ");
        if (this.isJavaScriptLink(content)) {
            result.append("href=\"javascript:void(0);\" onclick=\"");
            result.append(content);
        } else {
            result.append("href=\"");
            result.append(content);
        }
        result.append("\"");
        if (linkInfos[2] != null && linkInfos[2].length() != 0) {
            result.append(" target=\"");
            result.append(linkInfos[2]);
            result.append("\"");
        }
        result.append(">");
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            result.append(Html.encodeText((String)linkInfos[0]));
        } else {
            result.append("&nbsp;");
        }
        result.append("</a></div>");
        return result.toString();
    }

    private boolean isJavaScriptLink(String content) {
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
}

