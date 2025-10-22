/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 */
package com.jiuqi.gcreport.offsetitem.executor.tab.sheet;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.offsetitem.executor.common.CellStyleExportExcelSheet;
import java.awt.Color;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class OffsetTabExportExcelSheet
extends CellStyleExportExcelSheet {
    private Map<String, CellStyle> cellStyleName2StyleMap;

    public OffsetTabExportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize) {
        super(sheetNo, sheetName, sheetHeadSize);
    }

    @Override
    public void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        super.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
        Set<Integer> intervalColorRowSet = this.getIntervalColorRowSet();
        int rowNum = row.getRowNum();
        int columnIndex = cell.getColumnIndex();
        this.buildDefaultCellStyleMap(workbook);
        if (rowNum != 0 && columnIndex == 0) {
            cell.setCellStyle(this.cellStyleName2StyleMap.get("fontCenter"));
        }
        if (intervalColorRowSet != null && intervalColorRowSet.contains(rowNum) && columnIndex == 0) {
            cell.setCellStyle(this.cellStyleName2StyleMap.get("intervalColorFontCenter"));
        }
    }

    private void buildDefaultCellStyleMap(Workbook workbook) {
        if (this.cellStyleName2StyleMap != null) {
            return;
        }
        this.cellStyleName2StyleMap = new ConcurrentHashMap<String, CellStyle>(16);
        XSSFCellStyle contentFontCenter = (XSSFCellStyle)ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        contentFontCenter.setAlignment(HorizontalAlignment.CENTER);
        this.cellStyleName2StyleMap.put("fontCenter", contentFontCenter);
        XSSFCellStyle intervalColorFontCenter = (XSSFCellStyle)ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        intervalColorFontCenter.setAlignment(HorizontalAlignment.CENTER);
        intervalColorFontCenter.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
        this.cellStyleName2StyleMap.put("intervalColorFontCenter", intervalColorFontCenter);
    }
}

