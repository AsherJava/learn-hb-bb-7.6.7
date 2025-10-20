/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.h5.H5GridCell
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.h5;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.h5.H5GridCell;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.engine.result.FoldingInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.h5.IReportH5GridOptionProvider;
import com.jiuqi.bi.quickreport.html.ConditionStyleUtil;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportH5GridCell
extends H5GridCell {
    public static final int BACK_POSITION_FORE = 0;
    public static final int BACK_POSITION_CENTER = 1;
    public static final int BACK_POSITION_BACK = 2;
    public static final int BACK_POSITION_VERTICAL_CENTER = 3;
    public static final int BACK_IMAGE_STYLE_REPEAT = 1;
    public static final int BACK_IMAGE_STYLE_REPEAT_X = 2;
    public static final int BACK_IMAGE_STYLE_REPEAT_Y = 3;
    public static final int BACK_IMAGE_STYLE_STRETCH = 4;
    public static final int BACK_IMAGE_STYLE_POSITION = 5;
    public static final int BACK_IMAGE_STYLE_BOUNDS = 6;
    public static final int BORDER_AUTO = -1;
    public static final int BORDER_NONE = 0;
    public static final int BORDER_SOLID = 1;
    public static final int BORDER_DASH = 2;
    public static final int BORDER_BOLD = 4;
    public static final int BORDER_DOUBLE = 8;
    public static final int BORDER_DOTTED = 16;
    public static final String TAG_BORDER = "border";
    public static final String TAG_FOLDING_INFO = "foldingInfo";
    private final IReportH5GridOptionProvider provider;
    private final GridData gridData;

    public ReportH5GridCell(GridCell gridCell, GridData gridData, IReportH5GridOptionProvider provider) {
        super(gridCell, null, gridData);
        this.gridData = gridData;
        this.provider = provider;
    }

    public IReportH5GridOptionProvider getProvider() {
        return this.provider;
    }

    public int getCellMode() {
        CellResultInfo cellResultInfo;
        FoldingInfo foldingInfo;
        Object cellObj = this.getGridCell().getCellObj();
        if (cellObj instanceof CellResultInfo && (foldingInfo = (cellResultInfo = (CellResultInfo)cellObj).getFoldingInfo()) != null) {
            return 2;
        }
        return super.getCellMode();
    }

    public String getBackColor() {
        if (this.provider != null && this.provider.isTransparent(this.getColIndex(), this.getRowIndex())) {
            return "transparent";
        }
        return super.getBackColor();
    }

    public List<Integer> getBorders() {
        Integer[] borders = new Integer[]{0, 0, 0, 0};
        if (this.gridCell != null) {
            borders[0] = this.gridCell.getRowNum() == 0 && this.gridCell.getBEdgeStyle() == 1 ? 0 : ReportH5GridCell.getH5EdgeStyle(this.gridCell.getREdgeStyle());
            borders[1] = this.gridCell.getColNum() == 0 && this.gridCell.getREdgeStyle() == 1 ? 0 : ReportH5GridCell.getH5EdgeStyle(this.gridCell.getBEdgeStyle());
            if (this.gridCell.getColNum() == 0 && this.gridCell.getRowNum() == 0 && this.gridData.getColCount() > 1 && this.gridData.getRowCount() > 1) {
                GridCell cell_1_0 = this.gridData.getCellEx(1, 0);
                GridCell cell_0_1 = this.gridData.getCellEx(0, 1);
                borders[0] = cell_1_0.getBEdgeStyle() == 1 ? 0 : ReportH5GridCell.getH5EdgeStyle(this.gridCell.getREdgeStyle());
                borders[1] = cell_0_1.getREdgeStyle() == 1 ? 0 : ReportH5GridCell.getH5EdgeStyle(this.gridCell.getBEdgeStyle());
            }
        }
        return Arrays.asList(borders);
    }

    static int getH5EdgeStyle(int edgeStyle) {
        switch (edgeStyle) {
            case 1: {
                return 0;
            }
            case 0: 
            case 2: {
                return 1;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                return 2;
            }
            case 7: 
            case 8: 
            case 9: 
            case 13: 
            case 14: 
            case 15: {
                return 4;
            }
            case 10: 
            case 11: 
            case 12: {
                return 8;
            }
        }
        return -1;
    }

    public List<String> getBorderColors() {
        String[] borderColors = new String[]{"", "", "", ""};
        borderColors[0] = Html.rgb2HtmlColor((int)this.gridCell.getREdgeColor());
        borderColors[1] = Html.rgb2HtmlColor((int)this.gridCell.getBEdgeColor());
        if (this.gridCell.getColNum() == 0 && this.gridData.getColCount() > 1) {
            borderColors[1] = borderColors[0];
        }
        if (this.gridCell.getRowNum() == 0 && this.gridData.getRowCount() > 1) {
            borderColors[0] = borderColors[1];
        }
        return Arrays.asList(borderColors);
    }

    public JSONObject toJson() throws JSONException {
        Object cellObj;
        JSONObject json_cell = super.toJson();
        json_cell.put("editable", this.getGridCell().getCanModify());
        json_cell.put("selectable", this.getGridCell().getCanSelect());
        json_cell.put("multiLine", this.getGridCell().getMultiLine());
        json_cell.put("wrapLine", this.getGridCell().getWrapLine());
        json_cell.put("vertText", this.getGridCell().getVertText());
        List<Integer> borders = this.getBorders();
        if (borders.size() == 4) {
            json_cell.put(TAG_BORDER, borders);
        }
        if ((cellObj = this.getGridCell().getCellObj()) instanceof CellResultInfo) {
            FoldingInfo foldingInfo;
            InteractiveInfo info;
            CellResultInfo cellResultInfo = (CellResultInfo)cellObj;
            if (cellResultInfo.getDataBarInfo() != null || StringUtils.isNotEmpty((String)cellResultInfo.getIconName())) {
                String tdContent = StringUtils.isEmpty((String)this.getHtml()) ? this.getShowText() : this.getHtml();
                tdContent = StringUtils.isEmpty((String)tdContent) ? "" : tdContent;
                json_cell.put("html", (Object)this.creatContionStyleHTML(this.getGridCell(), tdContent));
                json_cell.put("cellMode", 4);
            }
            if ((info = cellResultInfo.getInteractiveInfo()) != null && info.isSortable()) {
                StringBuilder buffer = new StringBuilder();
                if (this.provider != null && this.provider.getReportEngine() != null) {
                    buffer = this.provider.buildSortHead(this, info);
                }
                json_cell.put("html", (Object)buffer.toString());
                json_cell.put("cellMode", 4);
            }
            if ((foldingInfo = cellResultInfo.getFoldingInfo()) != null) {
                JSONObject json_foldingInfo = foldingInfo.toJSON();
                json_cell.put(TAG_FOLDING_INFO, (Object)json_foldingInfo);
            }
        }
        return json_cell;
    }

    public String getCellStyleDivPrefix() {
        StringBuilder styleDiv = new StringBuilder("<div style='height:100%;width:100%;display: table;");
        styleDiv.append("text-align:").append(this.getCellHorzAlign()).append(";");
        styleDiv.append("white-space:").append("normal").append(";");
        styleDiv.append("font-size:").append(this.getFontSize()).append("px;");
        styleDiv.append("font-family:").append(this.getFontName()).append(";");
        styleDiv.append("text-indent:").append(this.getIndent()).append("em;");
        if (this.isFontItalic()) {
            styleDiv.append("font-style:italic;");
        }
        if (this.isFontBold()) {
            styleDiv.append("font-weight:bold;");
        }
        styleDiv.append("'>");
        return styleDiv.toString();
    }

    public String getCellStyleDivSuffix() {
        return "</div>";
    }

    private String getCellStyle() {
        StringBuilder sb = new StringBuilder();
        int fontSize = this.getGridCell().getFontSize();
        String fontName = this.getGridCell().getFontName();
        sb.append("font-size:").append(fontSize).append("pt").append(";");
        sb.append("font-family:").append(fontName).append(";");
        if (this.getGridCell().getFontBold()) {
            sb.append("font-weight:bold;");
        }
        sb.append("text-align:").append(this.getCellHorzAlign()).append(";");
        return sb.toString();
    }

    private String creatContionStyleHTML(GridCell cell, String tdContent) {
        try {
            int cellHeight = cell.getCellHeight();
            CellResultInfo cellResultInfo = (CellResultInfo)cell.getCellObj();
            String paddingLeft = "";
            if (StringUtils.isNotEmpty((String)cellResultInfo.getIconName()) && (cell.getHorzAlign() == 1 || cell.getHorzAlign() == 0)) {
                paddingLeft = "padding-left:16px;";
            }
            StringBuilder buffer = new StringBuilder();
            buffer.append("<div class='mainContainer' style='height:").append(cellHeight).append("px;width:100%;position: relative;'>");
            buffer.append("<div class='contentContainer' style='height:100%;left:0;top:0;right:0;position:absolute;z-index:110;").append(paddingLeft).append("'>");
            buffer.append("<table style='width:100%;height:100%' cellpadding='1' cellspacing='0'><tr><td style='").append(this.getCellStyle()).append("' >").append(tdContent).append("</td></tr></table>");
            buffer.append("</div>");
            if (cellResultInfo.getDataBarInfo() != null) {
                DataBarInfo dataBarInfo = cellResultInfo.getDataBarInfo();
                Object rawValue = cellResultInfo.getRawValue();
                String dataBarStyle = ConditionStyleUtil.getBarStyleContent(this.getGridCell(), dataBarInfo, rawValue);
                if (!StringUtils.isEmpty((String)dataBarStyle)) {
                    buffer.append(dataBarStyle);
                }
            } else if (StringUtils.isNotEmpty((String)cellResultInfo.getIconName())) {
                String rootPath = null;
                if (this.provider != null) {
                    rootPath = this.provider.getRootPath();
                }
                buffer.append(ConditionStyleUtil.geySignalLampStyleContent(rootPath, cellResultInfo.getIconName(), tdContent));
            } else {
                return tdContent;
            }
            return buffer.append("</div>").toString();
        }
        catch (Exception e) {
            return tdContent;
        }
    }
}

