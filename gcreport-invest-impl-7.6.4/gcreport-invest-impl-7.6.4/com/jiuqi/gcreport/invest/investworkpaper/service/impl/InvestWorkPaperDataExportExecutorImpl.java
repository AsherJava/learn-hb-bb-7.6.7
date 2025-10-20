/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
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
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investworkpaper.service.InvestWorkPaperService;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import java.awt.Color;
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
public class InvestWorkPaperDataExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    InvestWorkPaperService workPaperService;

    public String getName() {
        return "InvestWorkPaperExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        context.getVarMap().putAll(this.getCellStyleMap(workbook));
        QueryCondition queryCondition = (QueryCondition)JsonUtils.readValue((String)context.getParam(), QueryCondition.class);
        return this.workPaperService.exportInvestWorkPaperDatas(queryCondition, context.getVarMap());
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
        int rowNum = row.getRowNum();
        Object[] currRow = (Object[])excelSheet.getRowDatas().get(rowNum);
        if (cell.getColumnIndex() == currRow.length - 1) {
            row.removeCell(cell);
            return;
        }
        if (rowNum >= 2) {
            boolean isInterval = (Boolean)currRow[currRow.length - 1];
            Map cellTypeMap = excelSheet.getHeadCellStyleCache();
            HorizontalAlignment headAlignment = ((CellStyle)cellTypeMap.get(cell.getColumnIndex())).getAlignment();
            String cellStyleKey = headAlignment == HorizontalAlignment.LEFT ? (isInterval ? "intervalColorText" : "contentText") : (isInterval ? "intervalColorAmount" : "contentAmount");
            cell.setCellStyle(this.getCellStyleMap(workbook).get(cellStyleKey));
        }
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        Map cellStyleMap = excelSheet.getHeadCellStyleCache();
        Object[] rowData = (Object[])excelSheet.getRowDatas().get(0);
        for (Map.Entry entry : cellStyleMap.entrySet()) {
            CellStyle cellStyle = (CellStyle)entry.getValue();
            if (HorizontalAlignment.RIGHT == cellStyle.getAlignment()) {
                sheet.setColumnWidth(((Integer)entry.getKey()).intValue(), 7680);
            } else if (HorizontalAlignment.LEFT == cellStyle.getAlignment()) {
                sheet.setColumnWidth(((Integer)entry.getKey()).intValue(), 10240);
            }
            if (!GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.offsetscenario").equals(rowData[(Integer)entry.getKey()])) continue;
            sheet.setColumnWidth(((Integer)entry.getKey()).intValue(), 16640);
        }
    }
}

