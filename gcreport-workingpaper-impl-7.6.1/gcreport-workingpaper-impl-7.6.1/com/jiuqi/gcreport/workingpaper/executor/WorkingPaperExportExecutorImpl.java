/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 */
package com.jiuqi.gcreport.workingpaper.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQueryTypeEnum;
import com.jiuqi.gcreport.workingpaper.service.WorkingPaperService;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkingPaperExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    private WorkingPaperService workingPaperService;

    public String getName() {
        return "WorkingPaperExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        WorkingPaperQueryCondition queryCondition = (WorkingPaperQueryCondition)JsonUtils.readValue((String)context.getParam(), WorkingPaperQueryCondition.class);
        ExportExcelSheet exportExcelSheet = this.workingPaperService.getExcelVO(context, workbook, queryCondition);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        exportExcelSheets.add(exportExcelSheet);
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        super.callBackSheet(context, excelSheet, workbook, sheet);
        WorkingPaperQueryCondition queryCondition = (WorkingPaperQueryCondition)JsonUtils.readValue((String)context.getParam(), WorkingPaperQueryCondition.class);
        String queryType = queryCondition.getQueryType();
        if (WorkingPaperQueryTypeEnum.MERGE.getCode().equals(queryType)) {
            this.rebuildMergeCellStyle(workbook, sheet);
        }
    }

    private void rebuildMergeCellStyle(Workbook workbook, Sheet sheet) {
        Row row1 = sheet.getRow(0);
        int cellNum = row1.getLastCellNum();
        for (int i = 1; i < cellNum; ++i) {
            Cell cell = row1.getCell(i);
            String cellValue = cell.getStringCellValue();
            if ("\u5408\u5e76\u6570".equals(cellValue)) continue;
            cell.setCellStyle(ExpImpUtils.buildDefaultHeadCellStyle((Workbook)workbook));
            cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        }
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > 2) {
            sheet.getRow(2).getCell(1).getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        }
    }
}

