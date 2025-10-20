/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.CellUtil
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCell
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCells
 *  org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.expimp.dataexport.excel.executor;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTMergeCells;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractExportExcelMultiSheetExecutor
extends AbstractExportExcelExecutor {
    private static final Integer MAX_ROWSUM = 1048570;
    private static final Integer ROW_ACCESS_WINDOW_SIZE = 500;

    @Override
    public final Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        SXSSFWorkbook workbook = this.getWorkbook(context);
        if (context.isAsync()) {
            String ossFileId = context.getSn();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write((OutputStream)bos);
            byte[] bytes = FileCopyUtils.copyToByteArray(new ByteArrayInputStream(bos.toByteArray()));
            VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", bytes);
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            ObjectInfo objectInfo = commonFileService.uploadFileToOss("EXPIMP_FILE_OSS", (MultipartFile)multipartFile, ossFileId);
            return "";
        }
        response.setHeader("Charset", "UTF-8");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("\u5bfc\u51fa\u6570\u636e\u6587\u4ef6.xlsx", "UTF-8"));
        response.flushBuffer();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write((OutputStream)outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.dispose();
        return "";
    }

    @Override
    public final SXSSFWorkbook getWorkbook(ExportContext context) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE.intValue());
        workbook.setCompressTempFiles(true);
        List<ExportExcelSheet> excelSheets = this.exportExcelSheets(context, (Workbook)workbook);
        this.processExcelSheets(context, workbook, excelSheets);
        if (!CollectionUtils.isEmpty(excelSheets)) {
            excelSheets.stream().forEach(excelSheet -> this.buildSheet(context, workbook, (ExportExcelSheet)excelSheet));
        }
        this.callBackWorkbook(context, (Workbook)workbook);
        return workbook;
    }

    private void processExcelSheets(ExportContext context, SXSSFWorkbook workbook, List<ExportExcelSheet> excelSheets) {
        excelSheets.stream().forEach(excelSheet -> {
            this.processCellStyleCache(context, workbook, (ExportExcelSheet)excelSheet);
            this.processMergedRegions(context, workbook, (ExportExcelSheet)excelSheet);
        });
    }

    private void processCellStyleCache(ExportContext context, SXSSFWorkbook workbook, ExportExcelSheet excelSheet) {
        CellStyle defaultHeadCellStyle = this.buildDefaultHeadCellStyle((Workbook)workbook);
        CellStyle defaultContentCellStyle = this.buildDefaultContentCellStyle((Workbook)workbook);
        Integer columnMaxIndex = excelSheet.getColumnMaxIndex();
        for (int columnIndex = 0; columnIndex <= columnMaxIndex; ++columnIndex) {
            CellType contentCellType;
            CellStyle headCellStyle;
            CellStyle contentCellStyle = excelSheet.getContentCellStyleCache().get(columnIndex);
            if (contentCellStyle == null) {
                excelSheet.getContentCellStyleCache().put(columnIndex, defaultContentCellStyle);
            }
            if ((headCellStyle = excelSheet.getHeadCellStyleCache().get(columnIndex)) == null) {
                if (contentCellStyle == null) {
                    headCellStyle = defaultHeadCellStyle;
                } else {
                    headCellStyle = workbook.createCellStyle();
                    headCellStyle.cloneStyleFrom(defaultHeadCellStyle);
                    headCellStyle.setAlignment(contentCellStyle.getAlignment());
                }
                excelSheet.getHeadCellStyleCache().put(columnIndex, headCellStyle);
            }
            if ((contentCellType = excelSheet.getContentCellTypeCache().get(columnIndex)) != null) continue;
            excelSheet.getContentCellTypeCache().put(columnIndex, CellType.STRING);
        }
    }

    protected void processMergedRegions(ExportContext context, SXSSFWorkbook workbook, ExportExcelSheet excelSheet) {
        if (excelSheet.isAutoMergeHeadFlag()) {
            Integer sheetHeadSize = excelSheet.getSheetHeadSize();
            Integer columnMaxIndex = excelSheet.getColumnMaxIndex();
            if (columnMaxIndex != -1) {
                LinkedHashMap<Integer, List<String>> index2HeadsMap = new LinkedHashMap<Integer, List<String>>();
                for (int columnIndex = 0; columnIndex <= columnMaxIndex; ++columnIndex) {
                    index2HeadsMap.put(columnIndex, new ArrayList());
                    for (int headRowNum = 0; headRowNum < sheetHeadSize; ++headRowNum) {
                        String headValue = String.valueOf(excelSheet.getRowDatas().get(headRowNum)[columnIndex]);
                        ((List)index2HeadsMap.get(columnIndex)).add(headValue);
                    }
                }
                List<CellRangeAddress> headCellRangeAddresses = this.buildAutoMergeHeadCellRangeAddresses(index2HeadsMap);
                if (!CollectionUtils.isEmpty(headCellRangeAddresses)) {
                    excelSheet.getCellRangeAddresses().addAll(headCellRangeAddresses);
                }
            }
        }
    }

    private void buildSheet(ExportContext context, SXSSFWorkbook workbook, ExportExcelSheet excelSheet) {
        List<Object[]> rowDatas = excelSheet.getRowDatas();
        if (rowDatas.size() > MAX_ROWSUM) {
            throw new BusinessRuntimeException("\u5bfc\u51fa\u6570\u636e\u91cf\u8fc7\u5927\uff0c\u5f53\u524d\u53ea\u652f\u6301\u6700\u5927" + MAX_ROWSUM + "\u6761\u6570\u636e\u3002");
        }
        SXSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(excelSheet.getSheetNo().intValue(), excelSheet.getSheetName());
        sheet.setDefaultColumnWidth(16);
        this.buildSheetDataCells(context, excelSheet, (Workbook)workbook, (Sheet)sheet);
        List<CellRangeAddress> cellRangeAddresses = excelSheet.getCellRangeAddresses();
        if (!CollectionUtils.isEmpty(cellRangeAddresses)) {
            CTWorksheet ctWorksheet = sheet.getWorkbook().getXSSFWorkbook().getSheetAt(excelSheet.getSheetNo().intValue()).getCTWorksheet();
            cellRangeAddresses.stream().forEach(cellRangeAddress -> this.addMergeRegion(ctWorksheet, (CellRangeAddress)cellRangeAddress));
        }
        this.callBackSheet(context, excelSheet, (Workbook)workbook, (Sheet)sheet);
    }

    private void addMergeRegion(CTWorksheet ctWorksheet, CellRangeAddress cellRangeAddress) {
        CTMergeCells ctMergeCells = ctWorksheet.isSetMergeCells() ? ctWorksheet.getMergeCells() : ctWorksheet.addNewMergeCells();
        CTMergeCell ctMergeCell = ctMergeCells.addNewMergeCell();
        ctMergeCell.setRef(cellRangeAddress.formatAsString());
    }

    protected void buildSheetDataCells(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        AtomicInteger rowIndex = new AtomicInteger(0);
        List<Object[]> rowDatas = excelSheet.getRowDatas();
        rowDatas.stream().forEach(rowData -> {
            Row row = sheet.createRow(rowIndex.get());
            int rowNum = row.getRowNum();
            for (int columnIndex = 0; columnIndex < ((Object[])rowData).length; ++columnIndex) {
                CellType cellType;
                CellStyle cellStyle;
                Cell cell = CellUtil.getCell((Row)row, (int)columnIndex);
                if (rowNum < excelSheet.getSheetHeadSize()) {
                    cellStyle = excelSheet.getHeadCellStyleCache().get(columnIndex);
                    cellType = CellType.STRING;
                } else {
                    cellStyle = excelSheet.getContentCellStyleCache().get(columnIndex);
                    cellType = excelSheet.getContentCellTypeCache().get(columnIndex);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellType(cellType);
                Object cellValue = rowData[columnIndex];
                this.writeCellValue(context, excelSheet, workbook, sheet, row, cell, cellValue);
                this.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
            }
            rowIndex.incrementAndGet();
            this.callBackRow(context, excelSheet, workbook, sheet, row);
        });
    }

    protected void writeCellValue(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        int columnIndex = cell.getColumnIndex();
        Object formatCellValue = cellValue;
        if (rowNum >= excelSheet.getSheetHeadSize()) {
            ExpImpConverter converter = excelSheet.getConverter();
            if (converter == null) {
                converter = new DefaultExpImpConverter();
            }
            formatCellValue = converter.convertJavaToExcelData(context, excelSheet, rowNum, columnIndex, cellValue);
        }
        if (formatCellValue == null) {
            cell.setBlank();
            return;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                if ("".equals(formatCellValue)) {
                    cell.setBlank();
                    return;
                }
                try {
                    if (formatCellValue instanceof String) {
                        cell.setCellValue(Double.valueOf(formatCellValue.toString().replaceAll(",", "")).doubleValue());
                        break;
                    }
                    cell.setCellValue(((Number)formatCellValue).doubleValue());
                }
                catch (Exception e) {
                    cell.setCellValue((String)formatCellValue);
                }
                break;
            }
            case BOOLEAN: {
                cell.setCellValue(((Boolean)formatCellValue).booleanValue());
                break;
            }
            case BLANK: {
                cell.setBlank();
                break;
            }
            case FORMULA: {
                cell.setCellFormula(formatCellValue.toString());
                break;
            }
            case ERROR: {
                cell.setCellErrorValue((byte)Integer.valueOf(formatCellValue.toString()).intValue());
                break;
            }
            default: {
                cell.setCellValue(formatCellValue.toString());
            }
        }
    }

    protected abstract List<ExportExcelSheet> exportExcelSheets(ExportContext var1, Workbook var2);

    protected List<CellRangeAddress> buildAutoMergeHeadCellRangeAddresses(Map<Integer, List<String>> index2HeadsMap) {
        if (CollectionUtils.isEmpty(index2HeadsMap)) {
            return Collections.emptyList();
        }
        ArrayList<CellRangeAddress> cellRangeList = new ArrayList<CellRangeAddress>();
        HashSet<String> alreadyRangeSet = new HashSet<String>();
        ArrayList<List<String>> headList = new ArrayList<List<String>>(index2HeadsMap.values());
        for (int colIndex = 0; colIndex < headList.size(); ++colIndex) {
            List headNameList = (List)headList.get(colIndex);
            for (int rowIndex = 0; rowIndex < headNameList.size(); ++rowIndex) {
                if (alreadyRangeSet.contains(colIndex + "-" + rowIndex)) continue;
                alreadyRangeSet.add(colIndex + "-" + rowIndex);
                String headName = (String)headNameList.get(rowIndex);
                int lastColIndex = colIndex;
                int lastRowIndex = rowIndex;
                int colIndex2 = colIndex + 1;
                while (colIndex2 < headList.size() && ((String)((List)headList.get(colIndex2)).get(rowIndex)).equals(headName)) {
                    alreadyRangeSet.add(colIndex2 + "-" + rowIndex);
                    lastColIndex = colIndex2++;
                }
                HashSet<String> tempAlreadyRangeSet = new HashSet<String>();
                int rowIndex2 = rowIndex + 1;
                block3: while (rowIndex2 < headNameList.size()) {
                    for (int colIndex3 = colIndex; colIndex3 <= lastColIndex; ++colIndex3) {
                        if (!((String)((List)headList.get(colIndex3)).get(rowIndex2)).equals(headName)) break block3;
                        tempAlreadyRangeSet.add(colIndex3 + "-" + rowIndex2);
                    }
                    lastRowIndex = rowIndex2++;
                    alreadyRangeSet.addAll(tempAlreadyRangeSet);
                }
                if (rowIndex == lastRowIndex && colIndex == lastColIndex) continue;
                cellRangeList.add(new CellRangeAddress(rowIndex, lastRowIndex, colIndex, lastColIndex));
            }
        }
        return cellRangeList;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        excelSheet.callBackSheet(context, workbook, sheet);
    }

    protected void callBackRow(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row) {
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        excelSheet.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }

    protected CellStyle buildDefaultHeadCellStyle(Workbook workbook) {
        return ExpImpUtils.buildDefaultHeadCellStyle(workbook);
    }

    protected CellStyle buildDefaultContentCellStyle(Workbook workbook) {
        return ExpImpUtils.buildDefaultContentCellStyle(workbook);
    }
}

