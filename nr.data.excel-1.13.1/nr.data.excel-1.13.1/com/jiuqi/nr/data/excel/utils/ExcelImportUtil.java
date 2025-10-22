/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.steam.xlsx.StreamingReader
 *  com.jiuqi.nr.datacrud.common.DateUtils
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.excel.utils;

import com.jiuqi.np.office.excel2.steam.xlsx.StreamingReader;
import com.jiuqi.nr.data.excel.consts.ExcelDateTypeConstants;
import com.jiuqi.nr.data.excel.exception.ImportContainManyEmptyRowException;
import com.jiuqi.nr.data.excel.param.upload.Sheet2GridAdapter;
import com.jiuqi.nr.data.excel.utils.ExcelMergeCell;
import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelImportUtil {
    public static final String LAST_COL_NUM = "LAST_COL_NUM";
    public static final String LAST_ROW_NUM = "LAST_ROW_NUM";
    private static final int MAX_EMPTY_ROW_COUNT = 1000;

    public static Workbook create(File file) throws EncryptedDocumentException, IOException, InvalidFormatException {
        String name = file.getName();
        if (name.endsWith(".xlsx")) {
            long length = file.length();
            if (length > 0xA00000L) {
                return StreamingReader.builder().rowCacheSize(200).bufferSize(4096).open(file);
            }
            return WorkbookFactory.create(file);
        }
        return WorkbookFactory.create(file);
    }

    public static Sheet2GridAdapter getSheetCells(Sheet sheet, FormDefine formDefine) {
        int rowCount = 0;
        int cloCount = 0;
        HashMap<String, Map<String, Object>> sheetCellMap = new HashMap<String, Map<String, Object>>();
        HashMap<String, Map<String, String>> sheetCellFormatMap = new HashMap<String, Map<String, String>>();
        ArrayList<ExcelMergeCell> sheetMergeds = new ArrayList<ExcelMergeCell>();
        HashMap<Integer, String> tabNames = new HashMap<Integer, String>();
        int theRow = 0;
        int lastCol = 0;
        int emptyRowCount = 0;
        int lastRowOutlineLevel = -1;
        Cell lastRowFristCell = null;
        for (Object row : sheet) {
            boolean isEmptyRow = true;
            int theCol = 0;
            boolean setFirstCell = false;
            if (lastRowOutlineLevel < row.getOutlineLevel() && lastRowFristCell != null && lastRowFristCell.getCellType().equals((Object)CellType.STRING)) {
                tabNames.put(theRow, lastRowFristCell.getStringCellValue());
            }
            lastRowOutlineLevel = row.getOutlineLevel();
            HashMap<String, Object> rowData = new HashMap<String, Object>();
            HashMap<String, String> rowFormat = new HashMap<String, String>();
            Iterator<Cell> iterator = row.iterator();
            while (iterator.hasNext()) {
                Object cellValue;
                Cell cell = iterator.next();
                if (!setFirstCell) {
                    lastRowFristCell = cell;
                    setFirstCell = true;
                }
                if ((cellValue = ExcelImportUtil.getObjectCellValue(cell)) != null && !cellValue.equals("")) {
                    if (isEmptyRow) {
                        isEmptyRow = false;
                    }
                    rowData.put(String.valueOf(cell.getColumnIndex()), cellValue);
                    rowFormat.put(String.valueOf(cell.getColumnIndex()), cell.getCellStyle().getDataFormatString());
                }
                theCol = cell.getColumnIndex();
            }
            sheetCellMap.put(String.valueOf(theRow), rowData);
            sheetCellFormatMap.put(String.valueOf(theRow), rowFormat);
            if (isEmptyRow) {
                ++emptyRowCount;
            }
            if (emptyRowCount >= 1000) {
                throw new ImportContainManyEmptyRowException("Excel\u6587\u4ef6\u5185\u6709\u591a\u4e8e1000\u884c\u7684\u7a7a\u884c\u6570\u636e\uff01\u8bf7\u68c0\u67e5\u6587\u4ef6\u5185\u5bb9\u662f\u5426\u6b63\u786e\u3002");
            }
            if (theCol > lastCol) {
                lastCol = theCol;
            }
            ++theRow;
        }
        rowCount = theRow;
        cloCount = lastCol + 1;
        List<CellRangeAddress> rangeAddress = ExcelImportUtil.getRangeAddress(sheet);
        for (CellRangeAddress region : rangeAddress) {
            int firstColumn = region.getFirstColumn();
            int lastColumn = region.getLastColumn();
            int firstRow = region.getFirstRow();
            int lastRow = region.getLastRow();
            Map firstRowData = (Map)sheetCellMap.get(String.valueOf(firstRow));
            Map firstRowFormat = (Map)sheetCellFormatMap.get(String.valueOf(firstRow));
            Object firstCellValue = firstRowData == null ? null : (Object)firstRowData.get(String.valueOf(firstColumn));
            String firstCellFormat = firstRowFormat == null ? null : (String)firstRowFormat.get(String.valueOf(firstColumn));
            for (int beginRow = firstRow; beginRow <= lastRow; ++beginRow) {
                Map rowData = (Map)sheetCellMap.get(String.valueOf(beginRow));
                Map rowFormat = (Map)sheetCellFormatMap.get(String.valueOf(beginRow));
                for (int beginCol = firstColumn; beginCol <= lastColumn; ++beginCol) {
                    rowData.put(String.valueOf(beginCol), firstCellValue);
                    rowFormat.put(String.valueOf(beginCol), firstCellFormat);
                }
            }
            ExcelMergeCell mergeCell = new ExcelMergeCell(firstRow, firstColumn, lastRow, lastColumn);
            sheetMergeds.add(mergeCell);
        }
        Sheet2GridAdapter sheet2GridAdapter = new Sheet2GridAdapter(rowCount, cloCount, sheetCellMap, sheetCellFormatMap, sheetMergeds);
        sheet2GridAdapter.setTabNames(tabNames);
        return sheet2GridAdapter;
    }

    public static List<CellRangeAddress> getRangeAddress(Sheet sheet) {
        ArrayList<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        int nums = sheet.getNumMergedRegions();
        for (int index = 0; index < nums; ++index) {
            list.add(sheet.getMergedRegion(index));
        }
        return list;
    }

    private static String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return ExcelImportUtil.getDateValue(cell, cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString(), cell.getNumericCellValue());
            }
            return ExcelImportUtil.handleNumericStr(cell.getNumericCellValue(), null);
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue() + "";
        }
        if (cell.getCellType() == CellType.FORMULA) {
            return "";
        }
        return "";
    }

    private static Object getObjectCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return DateUtil.getJavaDate(cell.getNumericCellValue());
            }
            return ExcelImportUtil.handleNumericStr(cell.getNumericCellValue(), null);
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue() + "";
        }
        if (cell.getCellType() == CellType.FORMULA) {
            CellType cellType;
            try {
                cellType = cell.getCachedFormulaResultType();
            }
            catch (Exception e) {
                return "";
            }
            switch (cellType) {
                case NUMERIC: {
                    return ExcelImportUtil.handleNumericStr(cell.getNumericCellValue(), null);
                }
                case STRING: {
                    return cell.getStringCellValue();
                }
                case BOOLEAN: {
                    return cell.getBooleanCellValue();
                }
            }
            return "";
        }
        return "";
    }

    private static String getDateValue(Cell cell, short dataFormat, String dataFormatString, double value) {
        if (!DateUtil.isValidExcelDate(value)) {
            return null;
        }
        Date date = DateUtil.getJavaDate(value);
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_NYRSFM_STRING.contains(dataFormatString)) {
            return DateUtils.dateToStringTime((Date)date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_NYR_STRING.contains(dataFormatString)) {
            return DateUtils.dateToString((Date)date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_NY_STRING.contains(dataFormatString) || ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATA_EXACT_NY.equals(dataFormat)) {
            return DateUtils.dateToStringYYYYMM((Date)date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_YR_STRING.contains(dataFormatString) || ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATA_EXACT_YR.equals(dataFormat)) {
            return DateUtils.dateToStrinMMDD((Date)date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_Y_STRING.contains(dataFormatString)) {
            return DateUtils.dateToStrinMM((Date)date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_XQ_STRING.contains(dataFormatString)) {
            return "\u661f\u671f" + ExcelDateTypeConstants.dateToWeek(date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_DATE_Z_STRING.contains(dataFormatString)) {
            return "\u5468" + ExcelDateTypeConstants.dateToWeek(date);
        }
        if (ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_TIME_STRING.contains(dataFormatString) || ExcelDateTypeConstants.EXCEL_FORMAT_INDEX_TIME_EXACT.contains(dataFormat)) {
            return DateUtils.dateToTime((Date)date);
        }
        return cell.getDateCellValue().toString();
    }

    private static String handleNumericStr(double numericCellValue, String format) {
        if (null != format) {
            DecimalFormat df = new DecimalFormat(format);
            String value = df.format(numericCellValue);
            if (null != value && value.contains(",")) {
                value = value.replace(",", "");
            }
            return value;
        }
        DecimalFormat df = new DecimalFormat("0.####################");
        String value = df.format(numericCellValue);
        BigDecimal bd1 = new BigDecimal(value);
        String valueString = bd1.stripTrailingZeros().toPlainString();
        return valueString;
    }
}

