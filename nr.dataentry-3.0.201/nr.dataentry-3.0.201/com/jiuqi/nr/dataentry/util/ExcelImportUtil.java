/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.steam.xlsx.StreamingReader
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.util.DateUtils
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.np.office.excel2.steam.xlsx.StreamingReader;
import com.jiuqi.nr.dataentry.excelUpload.ExcelDateTypeConstants;
import com.jiuqi.nr.dataentry.excelUpload.Sheet2GridAdapter;
import com.jiuqi.nr.dataentry.service.IUploadExcelFilterRowService;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.util.DateUtils;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelImportUtil {
    public static final String LAST_COL_NUM = "LAST_COL_NUM";
    public static final String LAST_ROW_NUM = "LAST_ROW_NUM";

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

    public static Sheet2GridAdapter getSheetCells(Sheet sheet, IUploadExcelFilterRowService iUploadExcelFilterRowService, FormDefine formDefine) {
        HashMap<String, String> sheetCellMap = new HashMap<String, String>();
        HashMap<String, String> sheetMergeds = new HashMap<String, String>();
        int theRow = 0;
        int lastCol = 0;
        for (Object row : sheet) {
            boolean isSumRow = false;
            if (iUploadExcelFilterRowService != null && iUploadExcelFilterRowService.filterRow((Row)row, formDefine)) {
                isSumRow = true;
            }
            int theCol = 0;
            Iterator<Cell> iterator = row.iterator();
            while (iterator.hasNext()) {
                Cell cell = iterator.next();
                if (!isSumRow) {
                    sheetCellMap.put(theRow + "," + cell.getColumnIndex(), ExcelImportUtil.getStringCellValue(cell));
                    theCol = cell.getColumnIndex();
                    continue;
                }
                sheetCellMap.put(theRow + "," + cell.getColumnIndex(), "");
                theCol = cell.getColumnIndex();
            }
            if (theCol > lastCol) {
                lastCol = theCol;
            }
            ++theRow;
        }
        sheetCellMap.put(LAST_ROW_NUM, theRow + "");
        sheetCellMap.put(LAST_COL_NUM, lastCol + 1 + "");
        List<CellRangeAddress> rangeAddress = ExcelImportUtil.getRangeAddress(sheet);
        for (CellRangeAddress region : rangeAddress) {
            int firstColumn = region.getFirstColumn();
            int lastColumn = region.getLastColumn();
            int firstRow = region.getFirstRow();
            int lastRow = region.getLastRow();
            for (int beginRow = firstRow; beginRow <= lastRow; ++beginRow) {
                for (int beginCol = firstColumn; beginCol <= lastColumn; ++beginCol) {
                    String key = beginRow + "," + beginCol;
                    sheetCellMap.put(key, (String)sheetCellMap.get(firstRow + "," + firstColumn));
                    sheetMergeds.put(key, "1");
                }
            }
        }
        Sheet2GridAdapter sheet2GridAdapter = new Sheet2GridAdapter(sheetCellMap, sheetMergeds);
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

