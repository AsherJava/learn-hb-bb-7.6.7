/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.io.font.FontMetrics
 *  com.itextpdf.kernel.colors.Color
 *  com.itextpdf.kernel.colors.DeviceRgb
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.layout.IPropertyContainer
 *  com.itextpdf.layout.borders.Border
 *  com.itextpdf.layout.element.Cell
 *  com.itextpdf.layout.element.Paragraph
 *  com.jiuqi.va.print.adapt.PdfHandler
 */
package com.jiuqi.va.query.util;

import com.itextpdf.io.font.FontMetrics;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.jiuqi.va.print.adapt.PdfHandler;
import com.jiuqi.va.query.print.BorderDTO;
import com.jiuqi.va.query.print.GridCellProp;
import com.jiuqi.va.query.print.QueryPrintConst;
import com.jiuqi.va.query.print.TableCellConst;
import com.jiuqi.va.query.print.TableCellData;
import com.jiuqi.va.query.print.domain.QueryPrintThreadLocal;
import com.jiuqi.va.query.print.domain.TablePrintDrawCellContext;
import com.jiuqi.va.query.print.domain.tablle.CustomCell;
import com.jiuqi.va.query.print.domain.tablle.TableNewPageMergeCellDTO;
import com.jiuqi.va.query.util.QueryPrintUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class QueryPrintCellUtils {
    private QueryPrintCellUtils() {
    }

    public static Paragraph getAutoHeightParagraph(Cell cell, String content, GridCellProp cellProp) {
        float fontSize = cellProp.getFont().getSize();
        String fontFamily = cellProp.getFont().getFontFamily();
        float availableWidth = PdfHandler.getWidth((IPropertyContainer)cell);
        List<String> stringList = QueryPrintUtil.customSplitTxt(fontFamily, content, fontSize, availableWidth);
        int size = (stringList = stringList.stream().filter(StringUtils::hasText).collect(Collectors.toList())).size();
        if (size == 0) {
            size = 1;
        }
        PdfFont pdfFont = QueryPrintUtil.changeToPDFFont(cellProp.getFont().getFontFamily());
        float value = "Microsoft YaHei".equalsIgnoreCase(fontFamily) ? fontSize * (float)stringList.size() + 13.5f * (float)stringList.size() : QueryPrintUtil.getFontHeight(pdfFont, fontSize) * (float)size;
        cell.setHeight((float)Math.ceil(value));
        ArrayList<String> tempList = new ArrayList<String>();
        for (String tempStr : stringList) {
            String str = QueryPrintConst.BLANK_SPACE_PATTERN.matcher(tempStr).replaceAll("\u00a0");
            tempList.add(str);
        }
        String lineFeedContent = String.join((CharSequence)"\n", tempList);
        return QueryPrintUtil.renderParagraph(cellProp, lineFeedContent, fontSize);
    }

    public static float getRetractWidth(String content, GridCellProp cellProp, PdfFont font, float fontSize, boolean ptWidthFlag) {
        PdfFont tempFont;
        float size;
        char ch;
        if (!StringUtils.hasText(content)) {
            return 0.0f;
        }
        if (content.isEmpty() || content.charAt(0) != ' ') {
            return 0.0f;
        }
        int length = content.length();
        int num = 0;
        for (int i = 0; i < length && (ch = content.charAt(i)) == ' '; ++i) {
            ++num;
        }
        if (num == 0) {
            return 0.0f;
        }
        if (Objects.nonNull(cellProp)) {
            size = cellProp.getFont().getSize();
            String fontFamily = cellProp.getFont().getFontFamily();
            tempFont = QueryPrintUtil.changeToPDFFont(fontFamily);
        } else {
            size = fontSize;
            tempFont = font;
        }
        if (Objects.isNull(tempFont)) {
            return 0.0f;
        }
        float currentCharWidth = tempFont.getWidth(32, size);
        if (ptWidthFlag) {
            float v = (float)Math.floor(QueryPrintUtil.millimetersToPoints(currentCharWidth));
            return v * (float)num;
        }
        return (float)num * currentCharWidth;
    }

    public static float getFontLeadingHeight(PdfFont font, float fontSize) {
        FontMetrics fontMetrics = font.getFontProgram().getFontMetrics();
        float leading = fontMetrics.getTypoAscender() - fontMetrics.getTypoDescender() + fontMetrics.getLineGap();
        return leading *= fontSize / (float)fontMetrics.getUnitsPerEm();
    }

    public static Paragraph getAutoFoldParagraph(Cell cell, String content, GridCellProp cellProp) {
        float availableWidth = PdfHandler.getWidth((IPropertyContainer)cell);
        float cellHeight = PdfHandler.getHeight((IPropertyContainer)cell);
        float fontSize = cellProp.getFont().getSize();
        String fontFamily = cellProp.getFont().getFontFamily();
        PdfFont font = null;
        int unitsPerEm = 1;
        int typoAscender = 1;
        int typoDescender = 1;
        int lineGap = 0;
        while (true) {
            float leading;
            float stringWidth;
            double strRows;
            float descent;
            float ascent;
            float lineHeight;
            if (Objects.isNull(font)) {
                font = QueryPrintUtil.changeToPDFFont(fontFamily);
                FontMetrics fontMetrics = font.getFontProgram().getFontMetrics();
                unitsPerEm = fontMetrics.getUnitsPerEm();
                typoAscender = fontMetrics.getTypoAscender();
                typoDescender = fontMetrics.getTypoDescender();
                lineGap = fontMetrics.getLineGap();
            }
            if (!((lineHeight = (float)((double)((ascent = (float)typoAscender * fontSize / (float)unitsPerEm) - (descent = (float)typoDescender * fontSize / (float)unitsPerEm)) * (strRows = Math.ceil((stringWidth = font.getWidth(content, fontSize)) / availableWidth)) + (double)(leading = (float)((double)((float)(typoAscender - typoDescender + lineGap) * (fontSize / (float)unitsPerEm)) * strRows)))) > cellHeight)) break;
            fontSize = (float)((double)fontSize - 1.08);
        }
        float finalFontSize = fontSize;
        List<String> cellString = QueryPrintUtil.splitString(font, content, fontSize, availableWidth);
        String tempStr = String.join((CharSequence)"\n", cellString);
        Paragraph paragraph = QueryPrintUtil.renderParagraph(cellProp, tempStr, finalFontSize);
        paragraph.setMultipliedLeading(1.35f);
        return paragraph;
    }

    public static Paragraph getZoomParagraph(Cell cell, String content, GridCellProp cellProp) {
        float stringWidth;
        float availableWidth = PdfHandler.getWidth((IPropertyContainer)cell);
        float fontSize = cellProp.getFont().getSize();
        while (!((stringWidth = QueryPrintUtil.getStringWidth(cellProp.getFont().getFontFamily(), content, fontSize)) <= availableWidth)) {
            fontSize -= 1.0f;
        }
        fontSize += 1.0f;
        while (!((stringWidth = QueryPrintUtil.getStringWidth(cellProp.getFont().getFontFamily(), content, fontSize)) <= availableWidth)) {
            fontSize = (float)((double)fontSize - 0.1);
        }
        if (fontSize > PdfHandler.getHeight((IPropertyContainer)cell)) {
            fontSize = PdfHandler.getHeight((IPropertyContainer)cell) - 1.0f;
        }
        Paragraph paragraph = new Paragraph(content);
        paragraph.setFontSize(fontSize);
        if (cellProp.isUnderlined()) {
            paragraph.setUnderline();
        }
        if (cellProp.isRemovelined()) {
            paragraph.setLineThrough();
        }
        PdfHandler.setPercentHeight((IPropertyContainer)paragraph, (float)100.0f);
        PdfHandler.setPercentWidth((IPropertyContainer)paragraph, (float)100.0f);
        PdfHandler.setTextAlignment((IPropertyContainer)paragraph, (String)cellProp.getTextAlignment());
        PdfHandler.setVerticalAlignment((IPropertyContainer)paragraph, (String)cellProp.getVerticalAlignment());
        PdfHandler.setHorizontalAlignment((IPropertyContainer)paragraph, (String)cellProp.getHorizontalAlignment());
        QueryPrintUtil.setCellFontWithoutSize(paragraph, cellProp.getFont());
        paragraph.setMultipliedLeading(1.35f);
        return paragraph;
    }

    public static void setTableCellBorder(Cell cell) {
        cell.setBorder((Border)TableCellConst.DASHED_SOLID_BORDER);
    }

    public static float getCellHeightWithPadding(Cell cell) {
        if (Objects.isNull(cell)) {
            return 0.0f;
        }
        float paddingTop = PdfHandler.getPaddingTop((IPropertyContainer)cell);
        float paddingBottom = PdfHandler.getPaddingBottom((IPropertyContainer)cell);
        float top = 0.0f;
        float bottom = 0.0f;
        if (Objects.nonNull(Float.valueOf(paddingTop))) {
            top = paddingTop;
        }
        if (Objects.nonNull(Float.valueOf(paddingBottom))) {
            bottom = paddingBottom;
        }
        float heightUnitValue = PdfHandler.getHeight((IPropertyContainer)cell);
        float height = 0.0f;
        if (Objects.nonNull(Float.valueOf(heightUnitValue))) {
            height = heightUnitValue;
        }
        return height + top + bottom;
    }

    private static boolean needAddEmptyCell(List<? extends TableCellData> tableCellDataList, List<CustomCell> currentRowCells, int columnSize) {
        if (CollectionUtils.isEmpty(currentRowCells)) {
            return false;
        }
        if (columnSize == currentRowCells.size()) {
            return false;
        }
        int colSpanTotal = 0;
        for (TableCellData tableCellData : tableCellDataList) {
            int colspan = tableCellData.getSpanColumn();
            colSpanTotal += colspan;
        }
        return colSpanTotal != columnSize;
    }

    private static GridCellProp getFirstCellWithSpanRow(int rowIndex, int colIndex, Map<String, ? extends GridCellProp> cellPropMap) {
        if (Objects.isNull(cellPropMap)) {
            return null;
        }
        String key = rowIndex + "###" + colIndex;
        GridCellProp gridCellProp = cellPropMap.get(key);
        if (Objects.nonNull(gridCellProp)) {
            int spanRow = gridCellProp.getSpanRow();
            if (spanRow == 0) {
                return QueryPrintCellUtils.getFirstCellWithSpanRow(rowIndex - 1, colIndex, cellPropMap);
            }
            if (spanRow > 1) {
                return gridCellProp;
            }
        }
        return null;
    }

    public static TableCellData createCellData(GridCellProp cellProp, int row, int col) {
        TableCellData tableCellData = new TableCellData();
        tableCellData.setRowIndex(row);
        tableCellData.setColumnIndex(col);
        tableCellData.setSpanColumn(cellProp.getSpanColumn());
        tableCellData.setSpanRow(cellProp.getSpanRow());
        return tableCellData;
    }

    public static void initTablePrintDrawCellThreadLocal() {
        Map<String, BorderDTO> rowColBorderMap;
        Map<String, Float> everyColumnMaxWidthMap;
        TableNewPageMergeCellDTO tableNewPageMergeCellDTO;
        TablePrintDrawCellContext tablePrintDrawCellContext = QueryPrintThreadLocal.getTablePrintDrawCellContext();
        if (Objects.isNull(tablePrintDrawCellContext)) {
            tablePrintDrawCellContext = new TablePrintDrawCellContext();
            QueryPrintThreadLocal.setTableCellThreadLocal(tablePrintDrawCellContext);
        }
        if (Objects.isNull(tableNewPageMergeCellDTO = tablePrintDrawCellContext.getTableNewPageMergeCellDTO())) {
            tableNewPageMergeCellDTO = new TableNewPageMergeCellDTO();
            tablePrintDrawCellContext.setTableNewPageMergeCellDTO(tableNewPageMergeCellDTO);
        }
        if (Objects.isNull(everyColumnMaxWidthMap = tableNewPageMergeCellDTO.getEveryColumnMaxWidthMap())) {
            tableNewPageMergeCellDTO.setEveryColumnMaxWidthMap(new HashMap<String, Float>());
        }
        if (Objects.isNull(rowColBorderMap = tableNewPageMergeCellDTO.getRowColBorderMap())) {
            tableNewPageMergeCellDTO.setRowColBorderMap(new HashMap<String, BorderDTO>());
        }
    }

    public static void setCellBackGroundColor(Cell cell, GridCellProp cellProp) {
        String backgroundColor = cellProp.getBackgroundColor();
        if (!StringUtils.hasText(backgroundColor)) {
            return;
        }
        if (backgroundColor.length() != 7) {
            return;
        }
        int colorValue = QueryPrintUtil.colorValue(cellProp.getBackgroundColor());
        DeviceRgb deviceRgb = new DeviceRgb(new Color(colorValue));
        cell.setBackgroundColor((com.itextpdf.kernel.colors.Color)deviceRgb);
    }

    public static void setCellInfoInToThreadLocal(String key, float columnWidthWithSpanCol, BorderDTO borderDTO) {
        Map<String, BorderDTO> rowColBorderMap;
        Map<String, Float> everyColumnMaxWidthMap;
        TableNewPageMergeCellDTO tableNewPageMergeCellDTO;
        TablePrintDrawCellContext tablePrintDrawCellContext = QueryPrintThreadLocal.getTablePrintDrawCellContext();
        if (Objects.isNull(tablePrintDrawCellContext)) {
            tablePrintDrawCellContext = new TablePrintDrawCellContext();
            QueryPrintThreadLocal.setTableCellThreadLocal(tablePrintDrawCellContext);
        }
        if (Objects.isNull(tableNewPageMergeCellDTO = tablePrintDrawCellContext.getTableNewPageMergeCellDTO())) {
            tableNewPageMergeCellDTO = new TableNewPageMergeCellDTO();
            tablePrintDrawCellContext.setTableNewPageMergeCellDTO(tableNewPageMergeCellDTO);
        }
        if (Objects.isNull(everyColumnMaxWidthMap = tableNewPageMergeCellDTO.getEveryColumnMaxWidthMap())) {
            tableNewPageMergeCellDTO.setEveryColumnMaxWidthMap(new HashMap<String, Float>());
        }
        if (Objects.isNull(rowColBorderMap = tableNewPageMergeCellDTO.getRowColBorderMap())) {
            tableNewPageMergeCellDTO.setRowColBorderMap(new HashMap<String, BorderDTO>());
        }
        tableNewPageMergeCellDTO.getRowColBorderMap().put(key, borderDTO);
        tableNewPageMergeCellDTO.getEveryColumnMaxWidthMap().put(key, Float.valueOf((float)Math.floor(columnWidthWithSpanCol)));
    }

    public static List<CustomCell> addCellInCurrentRowWhileMergeCell(int columnSize, int row, List<CustomCell> currentRowCells, List<TableCellData> tableCellDataList, Map<String, ? extends GridCellProp> cellPropMap) {
        if (CollectionUtils.isEmpty(currentRowCells) || CollectionUtils.isEmpty(cellPropMap)) {
            return currentRowCells;
        }
        HashMap<Integer, CustomCell> map = new HashMap<Integer, CustomCell>();
        for (int i = 0; i < columnSize; ++i) {
            GridCellProp cellProp;
            boolean flag = true;
            for (TableCellData tableCellData : tableCellDataList) {
                int columnIndex = tableCellData.getColumnIndex();
                int tempRowIndex = tableCellData.getRowIndex();
                if (i != columnIndex || row != tempRowIndex) continue;
                flag = false;
                break;
            }
            if (!flag || !QueryPrintCellUtils.needAddEmptyCell(tableCellDataList, currentRowCells, columnSize) || !Objects.nonNull(cellProp = QueryPrintCellUtils.getFirstCellWithSpanRow(row, i, cellPropMap))) continue;
            int rowIndex = cellProp.getRowIndex();
            int spanRow = cellProp.getSpanRow();
            int tempSpanRow = rowIndex + spanRow - row;
            int tempSpanCol = cellProp.getSpanColumn();
            if (tempSpanRow < 0 || tempSpanCol < 0) continue;
            TableCellData tableCellData = new TableCellData();
            tableCellData.setRowIndex(row);
            tableCellData.setColumnIndex(i);
            tableCellData.setSpanRow(tempSpanRow);
            tableCellData.setSpanColumn(tempSpanCol);
            tableCellDataList.add(tableCellData);
            CustomCell cell = new CustomCell(tempSpanRow, tempSpanCol);
            String key = cellProp.getRowIndex() + "###" + cellProp.getColumnIndex();
            QueryPrintCellUtils.setCellWidthBorderFromThreadLocal(key, cell);
            map.put(i, cell);
        }
        int currentRowCellSize = currentRowCells.size();
        ArrayList<CustomCell> list = new ArrayList<CustomCell>();
        if (CollectionUtils.isEmpty(map)) {
            return currentRowCells;
        }
        for (int col = 0; col < columnSize; ++col) {
            CustomCell cell = null;
            int cellDataSize = tableCellDataList.size();
            for (int i = 0; i < cellDataSize; ++i) {
                TableCellData tableCellData = tableCellDataList.get(i);
                int columnIndex = tableCellData.getColumnIndex();
                if (col != columnIndex) continue;
                if (currentRowCellSize <= i) break;
                cell = currentRowCells.get(i);
                break;
            }
            if (cell == null) {
                cell = (CustomCell)((Object)map.get(col));
            }
            if (!Objects.nonNull(cell)) continue;
            list.add(cell);
        }
        return list;
    }

    private static void setCellWidthBorderFromThreadLocal(String key, Cell cell) {
        Map<String, BorderDTO> rowColBorderMap;
        Float cellWidth;
        TablePrintDrawCellContext tablePrintDrawCellContext = QueryPrintThreadLocal.getTablePrintDrawCellContext();
        if (Objects.isNull(tablePrintDrawCellContext)) {
            return;
        }
        TableNewPageMergeCellDTO tableNewPageMergeCellDTO = tablePrintDrawCellContext.getTableNewPageMergeCellDTO();
        if (Objects.isNull(tableNewPageMergeCellDTO)) {
            return;
        }
        Map<String, Float> everyColumnMaxWidthMap = tableNewPageMergeCellDTO.getEveryColumnMaxWidthMap();
        if (!CollectionUtils.isEmpty(everyColumnMaxWidthMap) && Objects.nonNull(cellWidth = everyColumnMaxWidthMap.get(key)) && 0.0f != cellWidth.floatValue()) {
            cell.setWidth(cellWidth.floatValue());
        }
        if (!CollectionUtils.isEmpty(rowColBorderMap = tableNewPageMergeCellDTO.getRowColBorderMap())) {
            BorderDTO borderDTO = rowColBorderMap.get(key);
            if (Objects.nonNull(borderDTO)) {
                Border leftBorder = borderDTO.getLeftBorder();
                Border rightBorder = borderDTO.getRightBorder();
                if (Objects.nonNull(leftBorder)) {
                    cell.setBorderLeft(borderDTO.getLeftBorder());
                }
                if (Objects.nonNull(rightBorder)) {
                    cell.setBorderLeft(borderDTO.getRightBorder());
                }
            } else {
                QueryPrintCellUtils.setTableCellBorder(cell);
            }
        }
    }
}

