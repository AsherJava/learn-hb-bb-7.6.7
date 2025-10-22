/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 */
package com.jiuqi.gcreport.samecontrol.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GcSameCtrlAdjustingEntryExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    @Qualifier(value="sameCtrlOffSetItemServiceImpl")
    private SameCtrlOffSetItemService sameCtrlOffSetItemService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "SameCtrlAdjustingEntryExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        SameCtrlOffsetCond sameCtrlOffsetCond = (SameCtrlOffsetCond)JsonUtils.readValue((String)context.getParam(), SameCtrlOffsetCond.class);
        sameCtrlOffsetCond.setPageNum(-1);
        sameCtrlOffsetCond.setPageSize(-1);
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        List<ExportExcelSheet> exportExcelSheets = this.sameCtrlOffSetItemService.exportSameCtrlOffsetDatas(sameCtrlOffsetCond, cellStyleMap, context);
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        Set rowSet;
        int rowNum = row.getRowNum();
        if (rowNum == 0) {
            return;
        }
        Object intervalColorRows = null;
        switch (excelSheet.getSheetNo()) {
            case 0: {
                intervalColorRows = context.getVarMap().get("offsetIntervalRows");
                break;
            }
            case 1: {
                intervalColorRows = context.getVarMap().get("sameParentEndOffsetIntervalRows");
                break;
            }
        }
        if (intervalColorRows != null && (rowSet = (Set)intervalColorRows).contains(rowNum)) {
            this.setIntervalColor(workbook, cell);
        }
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
            headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("contentAmt", contentAmtStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorString", intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("intervalColorAmt", intervalColorAmt);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    private void setIntervalColor(Workbook workbook, Cell cell) {
        if (cell.getCellStyle() != null && cell.getCellStyle().getAlignment() == HorizontalAlignment.RIGHT) {
            cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorAmt"));
            return;
        }
        cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorString"));
    }
}

