/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.NumberToTextConverter
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ImportUtils {
    public static String[] parseTitle(Row row) {
        int physicalNumberOfCells = row.getLastCellNum();
        String[] importCvsColumns = new String[physicalNumberOfCells];
        for (int k = 0; k < physicalNumberOfCells; ++k) {
            Cell cell = row.getCell(k);
            importCvsColumns[k] = cell.getStringCellValue();
        }
        return importCvsColumns;
    }

    public static Workbook parseWorkbook(MultipartFile importData) {
        XSSFWorkbook workbook;
        try {
            try {
                workbook = new XSSFWorkbook(importData.getInputStream());
            }
            catch (Exception ex) {
                workbook = new HSSFWorkbook(importData.getInputStream());
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.util.import.error"), (Throwable)e);
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue = "";
        if (cell.getCellType() == CellType.NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            if (format == 14 || format == 31 || format == 57 || format == 58) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate((double)value);
                cellValue = sdf.format(date);
            } else if (DateUtil.isCellDateFormatted((Cell)cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                cellValue = formater.format(date);
            } else {
                cellValue = NumberToTextConverter.toText((double)cell.getNumericCellValue());
            }
        } else {
            cellValue = cell.getCellType() == CellType.STRING ? cell.getStringCellValue().trim() : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()).trim() : (cell.getCellType() == CellType.FORMULA ? cell.getCellFormula() : (cell.getCellType() == CellType.BLANK ? "" : "")));
        }
        return cellValue;
    }
}

