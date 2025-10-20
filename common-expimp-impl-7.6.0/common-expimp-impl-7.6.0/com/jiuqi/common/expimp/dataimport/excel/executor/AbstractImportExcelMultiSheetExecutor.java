/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.poi.hssf.usermodel.HSSFDataFormatter
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Row$MissingCellPolicy
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.expimp.dataimport.excel.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractImportExcelMultiSheetExecutor
extends AbstractImportExcelExecutor {
    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractImportExcelMultiSheetExecutor.class);

    @Override
    public Object dataImport(MultipartFile importFile, ImportContext context) throws Exception {
        try (InputStream inputStream = importFile.getInputStream();){
            Object result;
            Workbook workbook = this.getWorkbook(inputStream, importFile.getOriginalFilename());
            Object object = result = this.dataImport(workbook, context);
            return object;
        }
    }

    @Override
    public Object dataImport(Workbook workbook, ImportContext context) {
        ArrayList<ImportExcelSheet> excelSheets = new ArrayList<ImportExcelSheet>();
        int[] readSheetNos = this.getReadSheetNos();
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); ++sheetIndex) {
            if (readSheetNos != null && !ArrayUtils.contains((int[])readSheetNos, (int)sheetIndex)) continue;
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            ImportExcelSheet excelSheetInfo = this.getSheetDatas(context, workbook, sheetIndex, sheet);
            excelSheets.add(excelSheetInfo);
        }
        Object result = this.importExcelSheets(context, excelSheets);
        return result;
    }

    protected abstract Object importExcelSheets(ImportContext var1, List<ImportExcelSheet> var2);

    protected ImportExcelSheet getSheetDatas(ImportContext context, Workbook workbook, int sheetIndex, Sheet sheet) {
        ImportExcelSheet excelSheetInfo = new ImportExcelSheet(sheetIndex, sheet.getSheetName());
        ExpImpConverter converter = this.getImportExcelSheetConverter(context, excelSheetInfo);
        excelSheetInfo.setConverter(converter);
        List<ImportExcelSheet.ImportExcelSheetRowData> excelSheetDatas = this.getSheetRowDatas(context, workbook, sheet, excelSheetInfo);
        this.fileEndBlankFilter(excelSheetDatas);
        excelSheetInfo.setExcelSheetRowDatas(excelSheetDatas);
        return excelSheetInfo;
    }

    protected void fileEndBlankFilter(List<ImportExcelSheet.ImportExcelSheetRowData> excelSheetDatas) {
        Object[] rowData;
        List rowDatas = excelSheetDatas.stream().map(ImportExcelSheet.ImportExcelSheetRowData::getRowData).collect(Collectors.toList());
        if (rowDatas == null || rowDatas.size() == 0) {
            return;
        }
        for (int i = rowDatas.size() - 1; i >= 0 && this.isEmptyObjectArray(rowData = (Object[])rowDatas.get(i)); --i) {
            rowDatas.remove(i);
        }
    }

    protected boolean isEmptyObjectArray(Object[] rowData) {
        if (rowData == null) {
            return true;
        }
        for (Object item : rowData) {
            if (item == null || StringUtils.isEmpty((CharSequence)item.toString())) continue;
            return false;
        }
        return true;
    }

    protected ExpImpConverter getImportExcelSheetConverter(ImportContext context, ImportExcelSheet excelSheetInfo) {
        return new DefaultExpImpConverter();
    }

    protected List<ImportExcelSheet.ImportExcelSheetRowData> getSheetRowDatas(ImportContext context, Workbook workbook, Sheet sheet, ImportExcelSheet excelSheetInfo) {
        ArrayList<ImportExcelSheet.ImportExcelSheetRowData> excelSheetDatas = new ArrayList<ImportExcelSheet.ImportExcelSheetRowData>();
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        int rowDataLength = this.getRowDataLength(sheet, excelSheetInfo);
        for (int j = firstRowNum; j <= lastRowNum; ++j) {
            ImportExcelSheet.ImportExcelSheetRowData rowData;
            boolean checkValid;
            Row row = sheet.getRow(j);
            if (row == null || !(checkValid = this.checkExcelRowData(context, excelSheetInfo, workbook, sheet, row, rowData = this.getSheetRowData(context, excelSheetInfo, workbook, sheet, row, rowDataLength)))) continue;
            excelSheetDatas.add(rowData);
        }
        return excelSheetDatas;
    }

    private int getRowDataLength(Sheet sheet, ImportExcelSheet excelSheetInfo) {
        int rowSize = 0;
        Integer sheetHeadSize = excelSheetInfo.getSheetHeadSize() == null ? 1 : excelSheetInfo.getSheetHeadSize();
        for (int i = 0; i < sheetHeadSize; ++i) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            int currentRowSize = row.getLastCellNum() - row.getFirstCellNum();
            rowSize = rowSize > currentRowSize ? rowSize : currentRowSize;
        }
        return rowSize;
    }

    protected boolean checkExcelRowData(ImportContext context, ImportExcelSheet excelSheetInfo, Workbook workbook, Sheet sheet, Row row, ImportExcelSheet.ImportExcelSheetRowData rowData) {
        return true;
    }

    protected ImportExcelSheet.ImportExcelSheetRowData getSheetRowData(ImportContext context, ImportExcelSheet excelSheetInfo, Workbook workbook, Sheet sheet, Row row, int rowDataLength) {
        int firstCellNum = 0;
        int lastCellNum = row.getLastCellNum();
        Object[] rowData = new String[rowDataLength];
        for (int y = firstCellNum; y < lastCellNum; ++y) {
            Object cellValue;
            if (y - firstCellNum >= rowDataLength) continue;
            Cell cell = row.getCell(y, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            rowData[y - firstCellNum] = cellValue = this.getSheetCellData(context, excelSheetInfo, workbook, sheet, row, cell);
        }
        ImportExcelSheet.ImportExcelSheetRowData excelSheetDataRow = new ImportExcelSheet.ImportExcelSheetRowData();
        excelSheetDataRow.setRowIndex(row.getRowNum());
        excelSheetDataRow.setRowData(rowData);
        return excelSheetDataRow;
    }

    protected Object getSheetCellData(ImportContext context, ImportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell) {
        if (cell == null) {
            return null;
        }
        boolean isMergedRegion = AbstractImportExcelMultiSheetExecutor.isMergedRegion(sheet, cell.getRowIndex(), cell.getColumnIndex());
        String cellValue = isMergedRegion ? AbstractImportExcelMultiSheetExecutor.getMergedRegionValue(sheet, cell.getRowIndex(), cell.getColumnIndex()) : this.getCellValue(cell);
        Object formatValue = null;
        try {
            ExpImpConverter expImpConverter = excelSheet.getConverter();
            if (expImpConverter == null) {
                expImpConverter = new DefaultExpImpConverter();
                excelSheet.setConverter(expImpConverter);
            }
            formatValue = expImpConverter.convertExcelToJavaData(context, excelSheet, row.getRowNum(), cell.getColumnIndex(), cellValue);
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u5bfc\u5165\u83b7\u53d6\u683c\u5f0f\u5316\u5de5\u5177\u5f02\u5e38\u3002", e);
        }
        return formatValue;
    }

    private String getCellValue(Cell cell) {
        String cellValue = null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted((Cell)cell)) {
            cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate((double)cell.getNumericCellValue()));
        }
        if (ObjectUtils.isEmpty(cellValue)) {
            cellValue = new HSSFDataFormatter().formatCellValue(cell);
        }
        return cellValue;
    }

    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row < firstRow || row > lastRow || column < firstColumn || column > lastColumn) continue;
            return true;
        }
        return false;
    }

    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row < firstRow || row > lastRow || column < firstColumn || column > lastColumn) continue;
            Row fRow = sheet.getRow(firstRow);
            Cell fCell = fRow.getCell(firstColumn);
            String cellValue = null;
            if (fCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted((Cell)fCell)) {
                cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate((double)fCell.getNumericCellValue()));
            }
            if (ObjectUtils.isEmpty(cellValue)) {
                cellValue = new HSSFDataFormatter().formatCellValue(fCell);
            }
            return cellValue;
        }
        return null;
    }

    public final Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        HSSFWorkbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_XLS.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);
        } else if (EXCEL_XLSX.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new BusinessRuntimeException("\u89e3\u6790\u7684\u6587\u4ef6\u683c\u5f0f\u6709\u8bef\uff01");
        }
        return wb;
    }

    protected abstract int[] getReadSheetNos();
}

