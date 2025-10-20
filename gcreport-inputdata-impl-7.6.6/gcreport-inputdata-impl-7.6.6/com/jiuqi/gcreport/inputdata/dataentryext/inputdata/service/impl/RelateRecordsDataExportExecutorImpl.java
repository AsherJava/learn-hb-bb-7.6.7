/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
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
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.impl;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetItemAdjustReportService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
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
public class RelateRecordsDataExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    private GcOffSetItemAdjustReportService adjustingEntryReportService;

    public String getName() {
        return "RelateRecordsDataExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map queryCondition = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String reocrdId = (String)queryCondition.get("recordId");
        QueryParamsVO queryParams = (QueryParamsVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(queryCondition.get("queryParamsVO")), QueryParamsVO.class);
        context.getVarMap().put("contentAmt", this.getContentAmtStyle(workbook));
        ExportExcelSheet exportExcelSheet = this.adjustingEntryReportService.exportRelateRecords(context, reocrdId, queryParams);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(exportExcelSheet);
        return exportExcelSheets;
    }

    private CellStyle getContentAmtStyle(Workbook workbook) {
        CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
        return contentAmtStyle;
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        String cellStyleKey = !HorizontalAlignment.RIGHT.equals((Object)cell.getCellStyle().getAlignment()) ? (rowNum == 0 ? "headText" : (rowNum % 2 != 0 ? "contentText" : "intervalColorText")) : (rowNum == 0 ? "headAmount" : (rowNum % 2 != 0 ? "contentAmount" : "intervalColorAmount"));
        cell.setCellStyle(this.getCellStyleMap(workbook).get(cellStyleKey));
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
}

