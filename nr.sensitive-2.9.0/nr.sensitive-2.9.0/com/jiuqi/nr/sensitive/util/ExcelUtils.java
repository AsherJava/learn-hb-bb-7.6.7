/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static Workbook create(InputStream excle) {
        try {
            return WorkbookFactory.create(excle);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static Workbook create(Boolean is07) {
        if (is07.booleanValue()) {
            return new XSSFWorkbook();
        }
        return new HSSFWorkbook();
    }

    public static String getCellText(Cell cell) {
        if (cell == null) {
            return null;
        }
        BaseFormulaEvaluator formulaEvaluator = null;
        if (cell.getCellType() == CellType.FORMULA) {
            Workbook workbook = cell.getSheet().getWorkbook();
            if (workbook instanceof XSSFWorkbook) {
                formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook)workbook);
            } else if (workbook instanceof HSSFWorkbook) {
                formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook)workbook);
            }
        }
        return ExcelUtils.getCellText(cell, formulaEvaluator);
    }

    public static String getCellText(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null) {
            return "";
        }
        String text = "";
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            text = new DecimalFormat("#").format(cell.getNumericCellValue());
        } else if (cellType == CellType.ERROR) {
            text = String.valueOf(cell.getErrorCellValue());
        } else if (cellType == CellType.BLANK) {
            text = "";
        } else if (cellType == CellType.BOOLEAN) {
            text = String.valueOf(cell.getBooleanCellValue());
        } else if (cellType == CellType.STRING) {
            text = cell.getStringCellValue();
        } else if (cellType == CellType.FORMULA) {
            text = ExcelUtils.getCellValueFormula(cell, formulaEvaluator);
        }
        return text;
    }

    private static String getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null || formulaEvaluator == null) {
            return "";
        }
        String test = "";
        if (cell.getCellType() == CellType.FORMULA) {
            CellValue value = formulaEvaluator.evaluate(cell);
            CellType cellType = value.getCellType();
            switch (cellType) {
                case STRING: {
                    return cell.getStringCellValue();
                }
                case BOOLEAN: {
                    return String.valueOf(cell.getBooleanCellValue());
                }
                case FORMULA: {
                    return test;
                }
                case NUMERIC: {
                    return new DecimalFormat("#").format(cell.getNumericCellValue());
                }
            }
            return "";
        }
        return "";
    }

    public static String getValue(Sheet sheet, int row, int col, FormulaEvaluator formulaEvaluator) {
        Row rowDc = sheet.getRow(row);
        if (rowDc == null) {
            return "";
        }
        return ExcelUtils.getCellText(rowDc.getCell(col), formulaEvaluator);
    }
}

