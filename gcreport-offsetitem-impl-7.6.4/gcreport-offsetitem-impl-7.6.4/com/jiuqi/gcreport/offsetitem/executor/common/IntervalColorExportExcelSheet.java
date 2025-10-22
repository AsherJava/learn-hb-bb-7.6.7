/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.offsetitem.executor.common;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class IntervalColorExportExcelSheet
extends ExportExcelSheet {
    private static final short NUMBER_FORMAT = HSSFDataFormat.getBuiltinFormat("#,##0.00");
    private final Set<Integer> intervalColorRowSet = new HashSet<Integer>();
    private final Set<Integer> intervalFontBoldRowSet = new HashSet<Integer>();
    private final Map<Integer, Set<Integer>> intervalFontBoldCol2RowMap = new HashMap<Integer, Set<Integer>>();
    private final Map<Integer, Set<Integer>> intervalFontBoldColorCol2RowMap = new HashMap<Integer, Set<Integer>>();

    public IntervalColorExportExcelSheet(Integer sheetNo, String sheetName) {
        super(sheetNo, sheetName);
    }

    public IntervalColorExportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize) {
        super(sheetNo, sheetName, sheetHeadSize);
    }

    public void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        if (this.intervalColorRowSet.contains(row.getRowNum())) {
            CellStyle cellStyle = (CellStyle)this.getContentCellStyleCache().get(cell.getColumnIndex());
            if (cellStyle.getDataFormat() == NUMBER_FORMAT) {
                cell.setCellStyle(this.getIntervalCellStyle(context, workbook, cell, "intervalColorAmt"));
            } else {
                cell.setCellStyle(this.getIntervalCellStyle(context, workbook, cell, "intervalColorText"));
            }
        }
        if (this.intervalFontBoldRowSet.contains(row.getRowNum()) || this.intervalFontBoldCol2RowMap.containsKey(cell.getColumnIndex()) && this.intervalFontBoldCol2RowMap.get(cell.getColumnIndex()).contains(row.getRowNum())) {
            cell.setCellStyle(this.getFontBoldCellStyle(context, workbook, cell));
        }
        if (this.intervalFontBoldColorCol2RowMap.containsKey(cell.getColumnIndex()) && this.intervalFontBoldColorCol2RowMap.get(cell.getColumnIndex()).contains(row.getRowNum())) {
            cell.setCellStyle(this.getIntervalColorFontBoldCellStyle(context, workbook, cell));
        }
        super.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }

    private CellStyle getIntervalCellStyle(ExportContext context, Workbook workbook, Cell cell, String cellStyleCacheKey) {
        CellStyle intervalCellStyle = (CellStyle)context.getVarMap().get(cellStyleCacheKey);
        if (null == intervalCellStyle) {
            intervalCellStyle = workbook.createCellStyle();
            intervalCellStyle.cloneStyleFrom(cell.getCellStyle());
            ((XSSFCellStyle)intervalCellStyle).setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            context.getVarMap().put(cellStyleCacheKey, intervalCellStyle);
        }
        return intervalCellStyle;
    }

    private CellStyle getFontBoldCellStyle(ExportContext context, Workbook workbook, Cell cell) {
        CellStyle intervalFontBoldCellStyle = (CellStyle)context.getVarMap().get("fontBold");
        if (null == intervalFontBoldCellStyle) {
            intervalFontBoldCellStyle = workbook.createCellStyle();
            intervalFontBoldCellStyle.cloneStyleFrom(cell.getCellStyle());
            Font font = workbook.createFont();
            font.setBold(true);
            intervalFontBoldCellStyle.setFont(font);
            context.getVarMap().put("fontBold", intervalFontBoldCellStyle);
        }
        return intervalFontBoldCellStyle;
    }

    private CellStyle getIntervalColorFontBoldCellStyle(ExportContext context, Workbook workbook, Cell cell) {
        CellStyle intervalFontBoldCellStyle = (CellStyle)context.getVarMap().get("intervalColorFontBold");
        if (null == intervalFontBoldCellStyle) {
            intervalFontBoldCellStyle = workbook.createCellStyle();
            intervalFontBoldCellStyle.cloneStyleFrom(cell.getCellStyle());
            Font font = workbook.createFont();
            font.setBold(true);
            intervalFontBoldCellStyle.setFont(font);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)intervalFontBoldCellStyle;
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            context.getVarMap().put("intervalColorFontBold", intervalFontBoldCellStyle);
        }
        return intervalFontBoldCellStyle;
    }

    public void appendIntervalColorRow(Integer row) {
        this.intervalColorRowSet.add(row);
    }

    public void appendIntervalFontBoldRow(Integer row) {
        this.intervalFontBoldRowSet.add(row);
    }

    public void setIntervalFontBoldRowAndCol(Integer col, Set<Integer> rows) {
        this.intervalFontBoldCol2RowMap.put(col, rows);
    }

    public void setIntervalFontBoldColorRowAndCol(Integer col, Set<Integer> rows) {
        this.intervalFontBoldColorCol2RowMap.put(col, rows);
    }

    public Set<Integer> getIntervalColorRowSet() {
        return this.intervalColorRowSet;
    }

    public void calcIntervalColorByOneRow() {
        int sheetHeadSize;
        for (int i = sheetHeadSize = this.getSheetHeadSize().intValue(); i < this.getRowDatas().size(); ++i) {
            if ((i - sheetHeadSize) % 2 >= 1) continue;
            this.appendIntervalColorRow(i);
        }
    }

    public void calcIntervalColorByTwoRow() {
        int sheetHeadSize;
        for (int i = sheetHeadSize = this.getSheetHeadSize().intValue(); i < this.getRowDatas().size(); ++i) {
            if ((i - sheetHeadSize) % 4 >= 2) continue;
            this.appendIntervalColorRow(i);
        }
    }
}

