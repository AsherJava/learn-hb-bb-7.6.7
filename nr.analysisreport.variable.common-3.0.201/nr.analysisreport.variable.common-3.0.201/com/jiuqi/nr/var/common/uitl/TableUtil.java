/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine
 *  com.jiuqi.nr.analysisreport.helper.AnalysisHelper
 *  com.jiuqi.nr.analysisreport.utils.AnaUtils
 *  com.jiuqi.nr.analysisreport.utils.BorderStyle
 *  com.jiuqi.nr.analysisreport.utils.Col
 *  com.jiuqi.nr.analysisreport.utils.IntegerParser
 *  com.jiuqi.nr.analysisreport.utils.Row
 *  com.jiuqi.nr.analysisreport.utils.Table
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.analysisreport.vo.print.PrintStyle
 *  com.jiuqi.nr.analysisreport.vo.print.ReportPrintSettingVO
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  com.jiuqi.util.StringUtils
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.common.uitl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.BorderStyle;
import com.jiuqi.nr.analysisreport.utils.Col;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import com.jiuqi.nr.analysisreport.utils.Row;
import com.jiuqi.nr.analysisreport.utils.Table;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.analysisreport.vo.print.PrintStyle;
import com.jiuqi.nr.analysisreport.vo.print.ReportPrintSettingVO;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.var.common.vo.HtmlTableCellInfo;
import com.jiuqi.nr.var.common.vo.HtmlTableContext;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import com.jiuqi.util.StringUtils;
import java.awt.Font;
import java.awt.FontMetrics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JLabel;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableUtil {
    public static final String EMPTY_JSON = "{}";
    private static Logger logger = LoggerFactory.getLogger(TableUtil.class);

    public static Table geneartorTable(HtmlTableContext htmlTableContext) {
        try {
            Grid2Data grid2Data = htmlTableContext.getGrid2Data();
            if (grid2Data == null) {
                return null;
            }
            Table table = new Table();
            TableUtil.caculateTableWidth(htmlTableContext, table);
            int[] tableStructInfo = new int[3];
            for (int i = 1; i < grid2Data.getRowCount(); ++i) {
                TableUtil.dealRowHeight(htmlTableContext.getCustomRows(), i, grid2Data, table);
                if (grid2Data.isRowHidden(i)) {
                    ((Row)table.getRows().get(table.getRows().size() - 1)).getStyle().append("display: none;");
                }
                int currentColNum = 0;
                int currentCellNum = 0;
                for (int j = 1; j < grid2Data.getColumnCount(); ++j) {
                    Col col = table.getRow(i - 1).addCol();
                    HtmlTableCellInfo htmlTableCellInfo = new HtmlTableCellInfo(i, j, col, grid2Data.getGridCellData(j, i));
                    TableUtil.setCellContent(htmlTableCellInfo);
                    if (TableUtil.checkCellIsShow(htmlTableCellInfo, htmlTableContext.getGrid2Data()).booleanValue()) {
                        TableUtil.applyCellStyle(htmlTableContext, htmlTableCellInfo);
                        currentCellNum += Math.max(htmlTableCellInfo.getColspan(), 1);
                        ++currentColNum;
                        continue;
                    }
                    htmlTableCellInfo.getCol().addStyle("display: none;");
                }
                if (currentColNum > tableStructInfo[0]) {
                    tableStructInfo[0] = currentColNum;
                    tableStructInfo[1] = i;
                }
                tableStructInfo[2] = Math.max(tableStructInfo[2], currentCellNum);
            }
            StringBuffer attribute = new StringBuffer();
            TableUtil.addAttriBute("colLength", tableStructInfo[2], attribute);
            TableUtil.addAttriBute("longestRow", tableStructInfo[1] - 1, attribute);
            table.getAttribute().insert(0, attribute);
            return table;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static void dealRowHeight(JsonNode customRows, int i, Grid2Data grid2Data, Table table) {
        Integer rowHeight = 0;
        rowHeight = customRows != null && customRows.has(String.valueOf(i)) ? Integer.valueOf(customRows.get(String.valueOf(i)).asInt()) : Integer.valueOf(grid2Data.getRowHeight(i));
        TableUtil.addStyle("height", rowHeight + "px", table.addRow().getStyle());
    }

    public static void caculateTableWidth(HtmlTableContext htmlTableContext, Table table) throws Exception {
        int printPageWidth = TableUtil.caculateprintPageWidth(htmlTableContext.getModelKey());
        htmlTableContext.setPrintPaperWidth(printPageWidth);
        int tableWidth = TableUtil.getTableWidth(htmlTableContext.getGrid2Data(), htmlTableContext.getCustomCols());
        htmlTableContext.setTableWidth(tableWidth);
        if (htmlTableContext.getCustomCols() != null) {
            table.setWidth(htmlTableContext.getTableWidth() + "px");
        }
    }

    public static Boolean checkCellIsShow(HtmlTableCellInfo cellInfo, Grid2Data grid2Data) {
        int colspan = cellInfo.getGridCellData().getColSpan();
        Point mergeInfo = grid2Data.getGridCellData(cellInfo.getJ(), cellInfo.getI()).getMergeInfo();
        cellInfo.setColspan(colspan);
        if (colspan > 1) {
            int hiddenCol = 0;
            for (int i = 0; i < colspan; ++i) {
                if (!grid2Data.isColumnHidden(cellInfo.getJ() + i)) continue;
                ++hiddenCol;
            }
            if (hiddenCol == cellInfo.getColspan()) {
                return false;
            }
            cellInfo.setColspan(colspan - hiddenCol);
        } else {
            if (mergeInfo != null && (mergeInfo.x != cellInfo.getJ() || mergeInfo.y != cellInfo.getI())) {
                return false;
            }
            if (grid2Data.isColumnHidden(cellInfo.getJ()) || grid2Data.isRowHidden(cellInfo.getI())) {
                return false;
            }
        }
        return true;
    }

    public static void setCellContent(HtmlTableCellInfo htmlTableCellInfo) {
        String showText = htmlTableCellInfo.getGridCellData().getShowText();
        if (StringUtils.isNotEmpty((String)showText)) {
            showText = showText.replace(" ", "&nbsp;").replace("\n", "<br>");
        }
        htmlTableCellInfo.getCol().setValue((Object)showText);
        htmlTableCellInfo.setShowText(showText);
    }

    public static int caculateprintPageWidth(String modelKey) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AnalysisHelper analysisHelper = (AnalysisHelper)BeanUtil.getBean(AnalysisHelper.class);
        AnalysisReportDefine reportDefine = analysisHelper.getListByKey(modelKey);
        ReportPrintSettingVO reportPrintSettingVO = (ReportPrintSettingVO)objectMapper.readValue(reportDefine.getPrintData(), ReportPrintSettingVO.class);
        PrintStyle printStyle = reportPrintSettingVO.getTemplate();
        int paperWidth = IntegerParser.parseInt((String)(printStyle.getOrientation() == 0 ? printStyle.getPaperWidth() : printStyle.getPaperHeight()));
        int marginLeft = IntegerParser.parseInt((String)printStyle.getMarginLeft());
        int usableWidth = paperWidth - marginLeft * 2;
        return usableWidth * 360000 / 10 / 9525;
    }

    public static void applyFontStyle(HtmlTableCellInfo htmlTableCellInfo, HtmlTableContext htmlTableContext, int colWidth) {
        StringBuffer styleBuffer = htmlTableCellInfo.getCol().getStyle();
        GridCellData gridCellData = htmlTableCellInfo.getGridCellData();
        int fontStyle = gridCellData.getCellStyleData().getFontStyle();
        if (fontStyle == 2) {
            styleBuffer.append("font-weight: bold;");
        }
        int fontSize = gridCellData.getCellStyleData().getFontSize();
        String fontName = gridCellData.getCellStyleData().getFontName();
        String showText = htmlTableCellInfo.getShowText();
        if (StringUtils.isNotEmpty((String)showText)) {
            try {
                JLabel label = new JLabel(showText);
                label.setFont(new Font(fontName, fontStyle, fontSize));
                FontMetrics metrics = label.getFontMetrics(label.getFont());
                int textW = metrics.stringWidth(label.getText());
                float zoom = 0.0f;
                JsonNode customCols = htmlTableContext.getCustomCols();
                zoom = customCols != null && customCols.has(htmlTableCellInfo.getJ()) ? new BigDecimal(colWidth).divide(new BigDecimal(htmlTableContext.getTableWidth()), 2, RoundingMode.HALF_EVEN).floatValue() : (colWidth > 0 ? (float)colWidth / 100.0f : 0.01f);
                int printPaperWidth = htmlTableContext.getPrintPaperWidth();
                if ((float)textW > (float)printPaperWidth * zoom) {
                    fontSize = (int)((float)fontSize * ((float)printPaperWidth * zoom / (float)textW));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        TableUtil.addStyle("font-family", fontName, styleBuffer);
        if ("quickFormVar_Local".equals(htmlTableContext.getVarType())) {
            TableUtil.addStyle("font-size", Math.round(fontSize * 4 / 3) + "px", styleBuffer);
            TableUtil.addStyle("originfontsize", fontSize + "", styleBuffer);
        } else {
            TableUtil.addStyle("font-size", fontSize + "px", styleBuffer);
        }
    }

    public static void applyCellBorderStyle(Grid2Data grid2Data, HtmlTableCellInfo htmlTableCellInfo) {
        int j = htmlTableCellInfo.getJ();
        int i = htmlTableCellInfo.getI();
        StringBuffer styleBuffer = htmlTableCellInfo.getCol().getStyle();
        GridCellStyleData cellStyleData = grid2Data.getGridCellData(j, i).getCellStyleData();
        BorderStyle bottomBorder = BorderStyle.getBorderStyle((int)cellStyleData.getBottomBorderColor(), (int)cellStyleData.getBottomBorderStyle());
        TableUtil.appendBorderStyle(styleBuffer, bottomBorder, "bottom");
        BorderStyle rightBorder = BorderStyle.getBorderStyle((int)cellStyleData.getRightBorderColor(), (int)cellStyleData.getRightBorderStyle());
        TableUtil.appendBorderStyle(styleBuffer, rightBorder, "right");
        try {
            GridCellStyleData leftCellStyle = grid2Data.getGridCellData(j - 1, i).getCellStyleData();
            BorderStyle leftBorder = BorderStyle.getBorderStyle((int)leftCellStyle.getRightBorderColor(), (int)leftCellStyle.getRightBorderStyle());
            if (rightBorder.style == BorderStyle.NONE.style && leftBorder.style == BorderStyle.DEFAULT.style) {
                leftBorder = BorderStyle.getBorderStyle((int)0, (int)BorderStyle.NONE.style);
            } else if (bottomBorder.style == BorderStyle.NONE.style && leftBorder.style == BorderStyle.DEFAULT.style) {
                leftBorder = BorderStyle.getBorderStyle((int)0, (int)BorderStyle.NONE.style);
            }
            TableUtil.appendBorderStyle(styleBuffer, leftBorder, "left");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            GridCellStyleData topCellStyle = grid2Data.getGridCellData(j, i - 1).getCellStyleData();
            BorderStyle topBorder = BorderStyle.getBorderStyle((int)topCellStyle.getBottomBorderColor(), (int)topCellStyle.getBottomBorderStyle());
            if (rightBorder.style == BorderStyle.NONE.style && topBorder.style == BorderStyle.DEFAULT.style) {
                topBorder = BorderStyle.getBorderStyle((int)0, (int)BorderStyle.NONE.style);
            } else if (bottomBorder.style == BorderStyle.NONE.style && topBorder.style == BorderStyle.DEFAULT.style) {
                topBorder = BorderStyle.getBorderStyle((int)0, (int)BorderStyle.NONE.style);
            }
            TableUtil.appendBorderStyle(styleBuffer, topBorder, "top");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void appendBorderStyle(StringBuffer style, BorderStyle border, String borderPosition) {
        TableUtil.addStyle("border-" + borderPosition + "-" + "width", border.border_width, style);
        TableUtil.addStyle("border-" + borderPosition + "-" + "style", border.border_style, style);
        TableUtil.addStyle("border-" + borderPosition + "-" + "color", border.border_color, style);
    }

    public static void applyCellStyle(HtmlTableContext htmlTableContext, HtmlTableCellInfo cellInfo) {
        GridCellData gridCellData = cellInfo.getGridCellData();
        int colWidth = TableUtil.dealAndSetCellWidth(htmlTableContext, cellInfo);
        TableUtil.applyFontStyle(cellInfo, htmlTableContext, colWidth);
        TableUtil.applyCellBorderStyle(htmlTableContext.getGrid2Data(), cellInfo);
        int rowSpan = gridCellData.getRowSpan();
        Col col = cellInfo.getCol();
        StringBuffer attributeBuffer = col.getAttribute();
        if (rowSpan != 0) {
            TableUtil.addAttriBute("rowspan", rowSpan, attributeBuffer);
        }
        if (cellInfo.getColspan() != 0) {
            TableUtil.addAttriBute("colspan", cellInfo.getColspan(), attributeBuffer);
        }
        cellInfo.getCol().getStyle().append("white-space:pre-wrap;");
        StringBuffer style = col.getStyle();
        int horzAlign = gridCellData.getCellStyleData().getHorzAlign();
        if (horzAlign == 3) {
            style.append("text-align: center;");
        } else if (horzAlign == 2) {
            style.append("text-align: right;");
            style.append("padding-right: 2px;");
        } else {
            style.append("padding-left: 2px;");
        }
        int indent = gridCellData.getCellStyleData().getIndent();
        if (indent != 0) {
            TableUtil.addStyle("text-indent", indent, style);
            TableUtil.addStyle("analysisreport_text_indent", indent, style);
        }
        if (gridCellData.getBackGroundColor() != -1) {
            String backgrondcolor = AnaUtils.colorLongToARGB((long)gridCellData.getBackGroundColor());
            TableUtil.addStyle("background", backgrondcolor, style);
        }
        String fontcolor = AnaUtils.colorLongToARGB((long)gridCellData.getForeGroundColor());
        TableUtil.addStyle("color", fontcolor, style);
    }

    public static int dealAndSetCellWidth(HtmlTableContext htmlTableContext, HtmlTableCellInfo htmlTableCellInfo) {
        int finalColWidth = 0;
        int colSpan = htmlTableCellInfo.getGridCellData().getColSpan();
        Grid2Data grid2Data = htmlTableContext.getGrid2Data();
        JsonNode customCols = htmlTableContext.getCustomCols();
        int tableWidth = htmlTableContext.getTableWidth();
        StringBuffer styleBuffer = htmlTableCellInfo.getCol().getStyle();
        int j = htmlTableCellInfo.getJ();
        if (colSpan == 1) {
            if (customCols != null && customCols.has(String.valueOf(j))) {
                TableUtil.addStyle("width", customCols.get(String.valueOf(j)) + "px", styleBuffer);
            } else {
                finalColWidth = new Double(Math.floor((float)grid2Data.getColumnWidth(j) / (float)tableWidth * 10000.0f / 100.0f)).intValue();
                finalColWidth = finalColWidth > 0 ? finalColWidth : 1;
                TableUtil.addStyle("width", finalColWidth + "%", styleBuffer);
            }
        } else {
            for (int l = 0; l < colSpan; ++l) {
                if (grid2Data.isColumnHidden(j + l)) continue;
                int subColumnWidth = grid2Data.getColumnWidth(j + l);
                if (customCols != null && customCols.has(String.valueOf(j + l))) {
                    finalColWidth += customCols.get(String.valueOf(j + l)).asInt();
                    continue;
                }
                int width = new Double(Math.floor((float)subColumnWidth / (float)tableWidth * 10000.0f / 100.0f)).intValue();
                finalColWidth += width > 0 ? width : 1;
            }
            if (customCols != null) {
                TableUtil.addStyle("tempwidth", finalColWidth + "px", styleBuffer);
            } else {
                TableUtil.addStyle("tempwidth", finalColWidth + "%", styleBuffer);
            }
        }
        return finalColWidth;
    }

    public static int getTableWidth(Grid2Data grid2Data, JsonNode customCols) {
        int tableWidth = 0;
        for (int colIndex = 1; colIndex < grid2Data.getColumnCount(); ++colIndex) {
            if (grid2Data.isColumnHidden(colIndex)) continue;
            int rowHeight = 0;
            rowHeight = customCols != null && customCols.has(String.valueOf(colIndex)) ? customCols.get(String.valueOf(colIndex)).asInt() : grid2Data.getColumnWidth(colIndex);
            tableWidth += rowHeight;
        }
        return tableWidth;
    }

    public static void addStyle(String name, Object value, StringBuffer style) {
        style.append(name).append(":").append(value).append(";");
    }

    public static void addAttriBute(String name, Object value, StringBuffer style) {
        style.append(name).append("=").append("\"").append(value).append("\"");
    }

    public static void dealTableCustomSettings(HtmlTableContext htmlTableContext, Element e, ReportVariableParseVO reportVariableParseVO) {
        if (e == null || reportVariableParseVO == null) {
            return;
        }
        Object genDataType = reportVariableParseVO.getExt().get("GEN_DATA_TYPE");
        if ("GEN_WORD_DATA".equals(genDataType.toString())) {
            String varSetting = e.attr("var-setings");
            if (StringUtils.isEmpty((String)varSetting) || EMPTY_JSON.equals(varSetting.trim())) {
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode customSettings = mapper.readTree(varSetting);
                htmlTableContext.setCustomRows(customSettings.get("rows"));
                htmlTableContext.setCustomCols(customSettings.get("columns"));
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}

