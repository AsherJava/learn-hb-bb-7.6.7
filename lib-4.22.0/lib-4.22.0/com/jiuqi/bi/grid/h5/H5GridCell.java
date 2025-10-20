/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.grid.h5;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.h5.H5GridStyle;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class H5GridCell {
    private static String TAG_COL_INDEX = "colIndex";
    private static String TAG_ROW_INDEX = "rowIndex";
    private static String TAG_SCRIPT = "script";
    private static String TAG_SHOW_TEXT = "showText";
    private static String TAG_BACK_COLOR = "backColor";
    private static String TAG_FONT_SIZE = "fontSize";
    private static String TAG_FONT_BOLD = "fontBold";
    private static String TAG_FONT_ITALIC = "fontItalic";
    private static String TAG_FONT_COLOR = "fontColor";
    private static String TAG_FONT_NAME = "fontName";
    private static String TAG_FONT_UNDER_LINE = "fontUnderLine";
    private static String TAG_FIT_FONT_SIZE = "fitFontSize";
    private static String TAG_BORDER_COLOR = "borderColor";
    private static String TAG_HORZ_ALIGN = "horzAlign";
    private static String TAG_VERT_ALIGN = "vertAlign";
    private static String TAG_CELL_MODE = "cellMode";
    private static String TAG_WRAP_LINE = "wrapLine";
    private static String TAG_HTML = "html";
    private static String TAG_INDENT = "indent";
    protected static final int CELL_MODE_NORMAL = 1;
    protected static final int CELL_MODE_TREE = 2;
    protected static final int CELL_MODE_HTML = 4;
    protected GridData gridData;
    protected GridCell gridCell;
    protected H5GridStyle gridStyle;
    private int cellMode = 1;
    private String html;
    private String showText;

    public H5GridCell(GridCell gridCell) {
        this(gridCell, null, null);
    }

    public H5GridCell(GridCell gridCell, H5GridStyle gridStyle) {
        this(gridCell, gridStyle, null);
    }

    public H5GridCell(GridCell gridCell, H5GridStyle gridStyle, GridData gridData) {
        this.gridCell = gridCell;
        this.gridStyle = gridStyle;
        this.gridData = gridData;
    }

    protected GridCell getGridCell() {
        return this.gridCell;
    }

    public int getColIndex() {
        return this.gridCell.getColNum();
    }

    public int getRowIndex() {
        return this.gridCell.getRowNum();
    }

    public String getCellData() {
        return this.gridCell.getCellData();
    }

    public String getShowText() {
        return this.showText;
    }

    public String getBackColor() {
        if (this.gridCell.getColNum() == 0 || this.gridCell.getRowNum() == 0) {
            return "transparent";
        }
        if (this.gridStyle != null) {
            return this.gridCell.getSilverHead() ? this.gridStyle.getHeaderBackColor() : this.gridStyle.getBackColor();
        }
        return Html.rgb2HtmlColor(this.gridCell.getBackColor());
    }

    public int getFontSize() {
        if (this.gridStyle != null) {
            return this.gridStyle.getFontSize();
        }
        return this.gridCell.getFontSize() * 4 / 3;
    }

    public boolean isFontBold() {
        return this.gridCell.getFontBold();
    }

    public boolean isFontItalic() {
        return this.gridCell.getFontItalic();
    }

    public String getFontColor() {
        if (this.gridStyle != null) {
            return this.gridCell.getSilverHead() ? this.gridStyle.getHeaderFontColor() : this.gridStyle.getFontColor();
        }
        return Html.rgb2HtmlColor(Math.max(this.gridCell.getFontColor(), 0));
    }

    public int getHorzAling() {
        if (this.gridCell.getType() == 2 || this.gridCell.getType() == 3 ? this.gridCell.getHorzAlign() == 0 : this.gridCell.getType() == 0 && this.gridCell.getHorzAlign() == 0 && this.isNumber(this.showText)) {
            return 2;
        }
        return this.gridCell.getHorzAlign();
    }

    public int getVertAlign() {
        return this.gridCell.getVertAlign();
    }

    public String getHtml() {
        return this.html;
    }

    public int getCellMode() {
        return this.cellMode;
    }

    public int getIndent() {
        return this.gridCell.getIndent();
    }

    public boolean getWrapLine() {
        return this.gridCell.getWrapLine();
    }

    public String getFontName() {
        return this.gridCell.getFontName();
    }

    public boolean isFitFontSize() {
        return this.gridCell.getFitFontSize();
    }

    public List<String> getBorderColors() {
        if (this.gridStyle == null) {
            return new ArrayList<String>(0);
        }
        String c = this.gridStyle.getBorderColor();
        return Arrays.asList(c, c, c, c);
    }

    public JSONObject toJson() throws JSONException {
        List<String> borderColors;
        this.buildContent(this.gridCell);
        int colIndex = this.getColIndex();
        int rowIndex = this.getRowIndex();
        String backColor = this.getBackColor() == null ? "#FFFFFF" : this.getBackColor();
        int fontSize = this.getFontSize();
        String fontColor = this.getFontColor() == null ? "#000000" : this.getFontColor();
        int horzAling = this.getHorzAling();
        int vertAlign = this.getVertAlign();
        String fontName = this.getFontName();
        JSONObject json = new JSONObject();
        json.put(TAG_COL_INDEX, colIndex);
        json.put(TAG_ROW_INDEX, rowIndex);
        if (StringUtils.isNotEmpty(this.gridCell.getScript())) {
            json.put(TAG_SCRIPT, (Object)this.gridCell.getScript());
        }
        json.put(TAG_SHOW_TEXT, (Object)(this.showText == null ? "" : this.showText));
        json.put(TAG_BACK_COLOR, (Object)backColor);
        json.put(TAG_FONT_SIZE, fontSize);
        if (this.gridCell.getFontBold()) {
            json.put(TAG_FONT_BOLD, this.gridCell.getFontBold());
        }
        if (this.gridCell.getFontItalic()) {
            json.put(TAG_FONT_ITALIC, this.gridCell.getFontItalic());
        }
        json.put(TAG_FONT_COLOR, (Object)fontColor);
        json.put(TAG_HORZ_ALIGN, horzAling);
        json.put(TAG_VERT_ALIGN, vertAlign);
        if (StringUtils.isNotEmpty(this.html)) {
            json.put(TAG_HTML, (Object)this.html);
        }
        json.put(TAG_CELL_MODE, this.getCellMode());
        if (this.gridCell.getWrapLine()) {
            json.put(TAG_WRAP_LINE, this.gridCell.getWrapLine());
        }
        if (this.gridCell.getIndent() > 0) {
            json.put(TAG_INDENT, this.gridCell.getIndent());
        }
        json.put(TAG_FONT_NAME, (Object)fontName);
        if (this.gridCell.getFitFontSize()) {
            json.put(TAG_FIT_FONT_SIZE, this.gridCell.getFitFontSize());
        }
        if (this.gridCell.getFontUnderLine()) {
            json.put(TAG_FONT_UNDER_LINE, this.gridCell.getFontUnderLine());
        }
        if ((borderColors = this.getBorderColors()).size() == 4) {
            json.put(TAG_BORDER_COLOR, borderColors);
        }
        return json;
    }

    private void buildContent(GridCell cell) {
        if (cell.isHyperlink()) {
            this.html = this.getCellHotLinkHTML();
            if (StringUtils.isNotEmpty(this.html)) {
                this.cellMode = 4;
                this.showText = this.getLinkShowText();
            } else {
                this.showText = cell.getShowText();
            }
        } else {
            this.showText = cell.getShowText();
        }
    }

    private String getCellHotLinkHTML() {
        String[] linkInfos = this.gridCell.getLinkInformation();
        if (linkInfos.length != 3) {
            return "";
        }
        if (linkInfos[1] == null || linkInfos[1].length() == 0) {
            if (linkInfos[0] == null || linkInfos[0].length() == 0) {
                return "";
            }
            return this.getCellHotLinkHTML(linkInfos[0], null, null);
        }
        String content = StringUtils.replace(linkInfos[1], "\"", "'");
        return this.getCellHotLinkHTML(Html.encodeText(linkInfos[0]), content, linkInfos[2]);
    }

    private String getCellHotLinkHTML(String showText, String targetLink, String targetType) {
        StringBuilder result = new StringBuilder("<div style='height:100%;width:100%;display: table;");
        result.append("text-align:").append(this.getCellHorzAlign()).append(";vertical-align:").append(this.getCellVertAlign()).append(";font-size:").append(this.getFontSize()).append("px;").append("font-family:").append(this.getFontName()).append(";").append("text-indent:").append(this.getIndent()).append("em;");
        if (this.isFontItalic()) {
            result.append("font-style:italic;");
        }
        if (this.isFontBold()) {
            result.append("font-weight:bold;");
        }
        result.append("'>");
        result.append(StringUtils.isEmpty(targetLink) ? "<span " : "<a ").append("style='vertical-align:").append(this.getCellVertAlign()).append(";width: 100%;display: table-cell; ");
        if (this.gridCell.getFontColor() != 0) {
            result.append("color:").append(this.getFontColor()).append("; ");
        }
        if (this.gridCell.getFontUnderLine()) {
            result.append("text-decoration:underline;");
        }
        if (this.gridData != null && this.gridCell.getWrapLine() && this.gridData.getRowAutoSize(this.gridCell.getRowNum())) {
            result.append("white-space:pre-line;word-wrap:break-word;word-break:break-all;line-height:").append(this.getFontSize() + 5).append("px;");
        }
        result.append("' ");
        if (StringUtils.isNotEmpty(targetLink)) {
            if (this.isJavaScriptLink(targetLink)) {
                result.append("href=\"javascript:void(0);\" onclick=\"");
                result.append(targetLink);
            } else {
                result.append("href=\"");
                result.append(targetLink);
            }
            result.append("\"");
            if (StringUtils.isNotEmpty(targetType)) {
                result.append(" target=\"");
                result.append(targetType);
                result.append("\"");
            }
        }
        result.append(">");
        if (StringUtils.isNotEmpty(showText)) {
            result.append(showText);
        } else {
            result.append("&nbsp;");
        }
        result.append(StringUtils.isEmpty(targetLink) ? "</span>" : "</a>").append("</div>");
        return result.toString();
    }

    private String getLinkShowText() {
        String[] linkInfos = this.gridCell.getLinkInformation();
        if (linkInfos.length != 3) {
            return "";
        }
        if (linkInfos[0] != null && linkInfos[0].length() != 0) {
            return linkInfos[0];
        }
        return "";
    }

    private boolean isJavaScriptLink(String content) {
        if (content == null || content.length() == 0) {
            return false;
        }
        int pos = content.lastIndexOf(40);
        if (pos <= 0 || pos >= content.length() - 1) {
            return false;
        }
        pos = content.lastIndexOf(41);
        return pos == content.length() - 1;
    }

    public String getCellHorzAlign() {
        switch (this.gridCell.getHorzAlign()) {
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

    public String getCellVertAlign() {
        switch (this.gridCell.getVertAlign()) {
            case 1: {
                return "top";
            }
            case 2: {
                return "bottom";
            }
        }
        return "middle";
    }

    private boolean isNumber(String value) {
        if (StringUtils.isNotEmpty(value)) {
            try {
                Double.parseDouble(value);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}

