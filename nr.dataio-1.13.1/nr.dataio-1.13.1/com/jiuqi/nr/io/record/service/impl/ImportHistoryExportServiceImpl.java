/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.data.common.service.TaskDataFactory
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 */
package com.jiuqi.nr.io.record.service.impl;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.data.common.service.TaskDataFactory;
import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportLog;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.service.FormStatisticService;
import com.jiuqi.nr.io.record.service.ImportHistoryExportService;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.io.record.service.UnitFailureService;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ImportHistoryExportServiceImpl
implements ImportHistoryExportService {
    @Autowired
    private ImportHistoryService importHistoryService;
    @Autowired
    private UnitFailureService unitFailureService;
    @Autowired
    private TaskDataFactoryManager taskDataFactoryManager;
    @Autowired
    private FormStatisticService formStatisticService;

    @Override
    public void exportLog(String recKey, OutputStream out) {
        ImportHistory importHistory = this.importHistoryService.queryByRecKey(recKey);
        if (importHistory == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u5bfc\u5165\u5386\u53f2" + recKey);
        }
        try (XSSFWorkbook wb = new XSSFWorkbook();){
            Sheet sheet = wb.createSheet("\u603b\u89c8");
            this.exportOverView(sheet, recKey);
            this.exportTypeLogView(wb, recKey);
            wb.write(out);
            out.flush();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void exportOverView(Sheet sheet, String recKey) {
        List<ImportLog> importLogs = this.importHistoryService.getImportLogsByFactory(recKey, "OVERVIEW");
        if (CollectionUtils.isEmpty(importLogs)) {
            return;
        }
        int rowNum = 0;
        ImportLog overview = importLogs.get(0);
        Row headRow = sheet.createRow(rowNum++);
        Cell headRowCell = headRow.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        headRow.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * 2.0f);
        headRowCell.setCellValue(overview.getDesc());
        CellStyle descStyle = ImportHistoryExportServiceImpl.getDescStyle(sheet);
        headRowCell.setCellStyle(descStyle);
        int n = ++rowNum;
        Row tableHeadRow = sheet.createRow(n);
        Cell tableHeadRowCell = tableHeadRow.createCell(0);
        tableHeadRowCell.setCellValue("\u5931\u8d25\u5355\u4f4d\u4fe1\u606f\u53ca\u539f\u56e0");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        CellStyle titleStyle = ImportHistoryExportServiceImpl.getTableTitleStyle(sheet);
        tableHeadRowCell.setCellStyle(titleStyle);
        int n2 = ++rowNum;
        ++rowNum;
        Row tableHeadRow2 = sheet.createRow(n2);
        CellStyle tableHeadStyle = ImportHistoryExportServiceImpl.getTableHeadStyle(sheet);
        Cell tableHeadCell1 = tableHeadRow2.createCell(0);
        Cell tableHeadCell2 = tableHeadRow2.createCell(1);
        Cell tableHeadCell3 = tableHeadRow2.createCell(2);
        tableHeadCell1.setCellStyle(tableHeadStyle);
        tableHeadCell2.setCellStyle(tableHeadStyle);
        tableHeadCell3.setCellStyle(tableHeadStyle);
        tableHeadCell1.setCellValue("\u5355\u4f4d\u6807\u8bc6");
        tableHeadCell2.setCellValue("\u5355\u4f4d\u6807\u9898");
        tableHeadCell3.setCellValue("\u5931\u8d25\u539f\u56e0");
        CellStyle dataStyle = ImportHistoryExportServiceImpl.getDataStyle(sheet);
        int pageSize = 10000;
        Page<UnitFailureRecord> unitFailureRecordPage = this.unitFailureService.queryFailureRecords(recKey, 1, pageSize);
        long count = unitFailureRecordPage.getCount();
        int totalPages = Math.max(0, (int)((count + (long)pageSize - 1L) / (long)pageSize));
        for (int pageNo = 1; pageNo <= totalPages; ++pageNo) {
            if (pageNo > 1) {
                unitFailureRecordPage = this.unitFailureService.queryFailureRecords(recKey, pageNo, pageSize);
            }
            List records = unitFailureRecordPage.getRecords();
            for (UnitFailureRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellStyle(dataStyle);
                cell.setCellValue(record.getDwKey());
                Cell cell1 = row.createCell(1);
                cell1.setCellStyle(dataStyle);
                cell1.setCellValue(record.getDwTitle());
                StringBuilder message = new StringBuilder();
                List<String> subRecords = record.getSubRecords();
                for (String subRecord : subRecords) {
                    message.append(subRecord).append(";");
                }
                message.setLength(message.length() - 1);
                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(dataStyle);
                cell2.setCellValue(message.toString());
            }
        }
        sheet.setColumnWidth(0, 8960);
        sheet.setColumnWidth(1, 8960);
        sheet.setColumnWidth(2, 25600);
    }

    private static CellStyle getDescStyle(Sheet sheet) {
        CellStyle dataStyle = sheet.getWorkbook().createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.LEFT);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setWrapText(true);
        return dataStyle;
    }

    private static CellStyle getDataStyle(Sheet sheet) {
        CellStyle dataStyle = sheet.getWorkbook().createCellStyle();
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setAlignment(HorizontalAlignment.LEFT);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setWrapText(true);
        return dataStyle;
    }

    private static CellStyle getTableHeadStyle(Sheet sheet) {
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        return headerStyle;
    }

    private static CellStyle getTableTitleStyle(Sheet sheet) {
        CellStyle titleStyle = sheet.getWorkbook().createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        titleStyle.setFont(font);
        return titleStyle;
    }

    private void exportTypeLogView(Workbook wb, String recKey) {
        List<ImportLog> importLogs = this.importHistoryService.getImportLogs(recKey);
        for (ImportLog importLog : importLogs) {
            TaskDataFactory factory;
            if ("OVERVIEW".equals(importLog.getFactoryId()) || (factory = this.taskDataFactoryManager.getFactory(importLog.getFactoryId())) == null) continue;
            Sheet sheet = wb.createSheet(factory.getName());
            this.exportTypeLog(sheet, importLog);
        }
    }

    private void exportTypeLog(Sheet sheet, ImportLog importLog) {
        int rowNum = 0;
        Row headRow = sheet.createRow(rowNum++);
        Cell headRowCell = headRow.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        headRow.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * 2.0f);
        headRowCell.setCellValue(importLog.getDesc());
        CellStyle descStyle = ImportHistoryExportServiceImpl.getDescStyle(sheet);
        headRowCell.setCellStyle(descStyle);
        CellStyle tableHeadStyle = ImportHistoryExportServiceImpl.getTableHeadStyle(sheet);
        CellStyle tableTitleStyle = ImportHistoryExportServiceImpl.getTableTitleStyle(sheet);
        CellStyle dataStyle = ImportHistoryExportServiceImpl.getDataStyle(sheet);
        int pageSize = 10000;
        Page<FormStatisticLog> formStatisticLogPage = this.formStatisticService.queryStatisticLogs(importLog.getRecKey(), importLog.getFactoryId(), 1, pageSize);
        long count = formStatisticLogPage.getCount();
        if (count > 0L) {
            int n = ++rowNum;
            Row tableHeadRow = sheet.createRow(n);
            Cell tableHeadRowCell = tableHeadRow.createCell(0);
            tableHeadRowCell.setCellValue("\u62a5\u8868\u6570\u636e\u603b\u89c8");
            tableHeadRowCell.setCellStyle(tableTitleStyle);
            int n2 = ++rowNum;
            ++rowNum;
            Row tableHeadRow3 = sheet.createRow(n2);
            Cell cell1 = tableHeadRow3.createCell(0);
            cell1.setCellValue("\u62a5\u8868\u6807\u8bc6");
            cell1.setCellStyle(tableHeadStyle);
            Cell cell2 = tableHeadRow3.createCell(1);
            cell2.setCellValue("\u62a5\u8868\u6807\u9898");
            cell2.setCellStyle(tableHeadStyle);
            Cell cell3 = tableHeadRow3.createCell(2);
            cell3.setCellValue("\u5bfc\u5165\u8be6\u60c5");
            cell3.setCellStyle(tableHeadStyle);
        }
        int totalPages = Math.max(0, (int)((count + (long)pageSize - 1L) / (long)pageSize));
        for (int pageNo = 1; pageNo <= totalPages; ++pageNo) {
            if (pageNo > 1) {
                formStatisticLogPage = this.formStatisticService.queryStatisticLogs(importLog.getRecKey(), importLog.getFactoryId(), pageNo, pageSize);
            }
            List records = formStatisticLogPage.getRecords();
            for (FormStatisticLog record : records) {
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(record.getFormCode());
                cell.setCellStyle(dataStyle);
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(record.getFormTitle());
                cell2.setCellStyle(dataStyle);
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(record.getDesc());
                cell3.setCellStyle(dataStyle);
            }
        }
        int n = ++rowNum;
        Row tableHeadRow = sheet.createRow(n);
        Cell tableHeadRowCell = tableHeadRow.createCell(0);
        tableHeadRowCell.setCellValue("\u5931\u8d25\u5355\u4f4d\u4fe1\u606f\u53ca\u539f\u56e0");
        tableHeadRowCell.setCellStyle(tableTitleStyle);
        int n3 = ++rowNum;
        ++rowNum;
        Row tableHeadRow2 = sheet.createRow(n3);
        Cell headCell1 = tableHeadRow2.createCell(0);
        Cell headCell2 = tableHeadRow2.createCell(1);
        Cell headCell3 = tableHeadRow2.createCell(2);
        headCell1.setCellValue("\u5355\u4f4d\u6807\u8bc6");
        headCell2.setCellValue("\u5355\u4f4d\u6807\u9898");
        headCell3.setCellValue("\u5931\u8d25\u539f\u56e0");
        headCell1.setCellStyle(tableHeadStyle);
        headCell2.setCellStyle(tableHeadStyle);
        headCell3.setCellStyle(tableHeadStyle);
        Page<UnitFailureRecord> unitFailureRecordPage = this.unitFailureService.queryFailureRecordsByFactory(importLog.getRecKey(), importLog.getFactoryId(), 1, pageSize);
        long count2 = unitFailureRecordPage.getCount();
        int totalPages2 = Math.max(0, (int)((count2 + (long)pageSize - 1L) / (long)pageSize));
        for (int pageNo = 1; pageNo <= totalPages2; ++pageNo) {
            if (pageNo > 1) {
                unitFailureRecordPage = this.unitFailureService.queryFailureRecordsByFactory(importLog.getRecKey(), importLog.getFactoryId(), pageNo, pageSize);
            }
            List records = unitFailureRecordPage.getRecords();
            for (UnitFailureRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(record.getDwKey());
                cell1.setCellStyle(dataStyle);
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(record.getDwTitle());
                cell2.setCellStyle(dataStyle);
                StringBuilder message = new StringBuilder();
                List<String> subRecords = record.getSubRecords();
                for (String subRecord : subRecords) {
                    message.append(subRecord).append(";");
                }
                message.setLength(message.length() - 1);
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(message.toString());
                cell3.setCellStyle(dataStyle);
            }
        }
        unitFailureRecordPage.setRecords(null);
        sheet.setColumnWidth(0, 8960);
        sheet.setColumnWidth(1, 8960);
        sheet.setColumnWidth(2, 25600);
    }
}

