/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.unionrule.util.ExcelUtils$ExportColumnTypeEnum
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.onekeymerge.service.GcDiffProcessService;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiffProcessDataExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    GcDiffProcessService diffProcessService;

    public String getName() {
        return "DiffProcessDataExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        GcDiffProcessCondition condition = (GcDiffProcessCondition)JsonUtils.readValue((String)context.getParam(), GcDiffProcessCondition.class);
        ExportExcelSheet exportExcelSheet = this.diffProcessService.exportDiffProcessData(condition);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(exportExcelSheet);
        return exportExcelSheets;
    }

    public Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headTextStyle = this.buildDefaultHeadCellStyle(workbook);
            headTextStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headText", headTextStyle);
            CellStyle headAmountStyle = this.buildDefaultHeadCellStyle(workbook);
            headAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleMap.put("headAmount", headAmountStyle);
            CellStyle contentTextStyle = this.buildDefaultContentCellStyle(workbook);
            contentTextStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentText", contentTextStyle);
            CellStyle contentAmountStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmountStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("contentAmount", contentAmountStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorText", (CellStyle)intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("intervalColorAmount", (CellStyle)intervalColorAmt);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        String cellStyleKey;
        int rowIndex;
        int rowNum = row.getRowNum();
        int n = rowIndex = rowNum == 0 ? 0 : Integer.valueOf(((Object[])excelSheet.getRowDatas().get(rowNum))[0].toString());
        if (cell.getColumnIndex() < 4) {
            cellStyleKey = rowNum == 0 ? "headText" : (rowIndex % 2 != 0 ? "contentText" : "intervalColorText");
        } else {
            if (rowNum != 0 && cellValue != null) {
                cell.setCellType(ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
                cell.setCellValue(Double.valueOf(String.valueOf(cellValue).replace(",", "")).doubleValue());
            }
            cellStyleKey = rowNum == 0 ? "headAmount" : (rowIndex % 2 != 0 ? "contentAmount" : "intervalColorAmount");
        }
        cell.setCellStyle(this.getCellStyleMap(workbook).get(cellStyleKey));
    }
}

