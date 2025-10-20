/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.efdcdatacheck.impl.utils.GcEfdcDataCheckUtils;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class EfdcDataCheckExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    public String getName() {
        return "EfdcDataCheckDataExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        GcFormOperationInfo formOperationInfo = (GcFormOperationInfo)JsonUtils.readValue((String)context.getParam(), GcFormOperationInfo.class);
        List<ExportExcelSheet> exportExcelSheets = new EFDCDataCheckImpl().exportExcel(formOperationInfo);
        return exportExcelSheets;
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        String cellStyleKey = cell.getColumnIndex() < 5 ? (rowNum == 0 ? "headText" : (rowNum % 2 != 0 ? "contentText" : "intervalColorText")) : (rowNum == 0 ? "headAmount" : (rowNum % 2 != 0 ? "contentAmount" : "intervalColorAmount"));
        cell.setCellStyle(new GcEfdcDataCheckUtils().getCellStyleMap(workbook).get(cellStyleKey));
    }
}

