/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.impl;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrimaryTableSetExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private PrimaryWorkpaperService primaryWorkpaperService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "PrimaryTableSetExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map selectNodeIdMap = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String selectNodeId = (String)selectNodeIdMap.get("selectedNodeId");
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(this.primaryWorkpaperService.exportSet(selectNodeId, this.getCellStyleMap(workbook)));
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        int columnNum = cell.getColumnIndex();
        String cellStyleKey = rowNum == 0 ? "headString" : (columnNum == 0 ? "contentIndexStyle" : "contentString");
        cell.setCellStyle(this.getCellStyleMap(workbook).get(cellStyleKey));
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle contentIndexStyle = this.buildDefaultContentCellStyle(workbook);
            contentIndexStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyleMap.put("contentIndexStyle", contentIndexStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
        }
        return cellStyleMap;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 7680);
        sheet.setColumnWidth(2, 5120);
        sheet.setColumnWidth(3, 5120);
        sheet.setColumnWidth(4, 5120);
        sheet.setColumnWidth(5, 15360);
    }
}

