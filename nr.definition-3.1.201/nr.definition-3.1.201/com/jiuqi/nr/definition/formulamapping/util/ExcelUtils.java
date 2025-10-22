/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    public static Workbook create() {
        return new XSSFWorkbook();
    }

    public static Workbook create(InputStream input) throws IOException {
        return new XSSFWorkbook(input);
    }

    public static FormulaEvaluator getFormulaEvaluator(Workbook workbook) {
        if (workbook instanceof XSSFWorkbook) {
            return new XSSFFormulaEvaluator((XSSFWorkbook)workbook);
        }
        if (workbook instanceof HSSFWorkbook) {
            return new HSSFFormulaEvaluator((HSSFWorkbook)workbook);
        }
        if (workbook instanceof SXSSFWorkbook) {
            return new SXSSFFormulaEvaluator((SXSSFWorkbook)workbook);
        }
        throw new RuntimeException(String.format("\u5c1a\u672a\u652f\u6301\u83b7\u53d6\u3010%s\u3011\u7684\u516c\u5f0f\u6267\u884c\u5668", workbook.getClass()));
    }

    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        if (cellType == CellType.ERROR) {
            return ErrorEval.getText(cell.getErrorCellValue());
        }
        if (cellType == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        }
        if (cellType == CellType.FORMULA) {
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator formulaEvaluator = ExcelUtils.getFormulaEvaluator(workbook);
            if (null != formulaEvaluator) {
                CellValue value = formulaEvaluator.evaluate(cell);
                return ExcelUtils.getCellValue(value);
            }
            throw new RuntimeException("\u672a\u83b7\u53d6\u5230\u516c\u5f0f\u6267\u884c\u5668");
        }
        if (cellType == CellType.BLANK) {
            return null;
        }
        throw new RuntimeException(String.format("\u83b7\u53d6\u5355\u5143\u683c\u3010%d\uff0c%d\u3011\u65f6\u9047\u5230\u4e3a\u652f\u6301\u7684\u503c\u7c7b\u578b\uff1a%s\u3002", cell.getColumnIndex() + 1, cell.getRowIndex() + 1, cellType.name()));
    }

    private static Object getCellValue(CellValue cell) {
        if (null == cell) {
            return null;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING: {
                return cell.getStringValue();
            }
            case BOOLEAN: {
                return cell.getBooleanValue();
            }
            case NUMERIC: {
                return cell.getNumberValue();
            }
            case ERROR: {
                return ErrorEval.getText(cell.getErrorValue());
            }
        }
        return null;
    }

    public static String getCellText(Cell cell) {
        Object cellValue = ExcelUtils.getCellValue(cell);
        if (null != cellValue) {
            return String.valueOf(cellValue);
        }
        return "";
    }

    public static String getValue(Sheet sheet, int row, int col) {
        Row r = sheet.getRow(row);
        if (null == r) {
            return null;
        }
        return ExcelUtils.getCellText(r.getCell(col));
    }

    public static void setCellValue(Cell cell, Object value) {
        if (null == cell || null == value) {
            return;
        }
        if (value instanceof Double) {
            cell.setCellValue((Double)value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else if (value instanceof String) {
            cell.setCellValue((String)value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar)value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date)value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate)value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime)value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString)value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    public static void setCellValue(Sheet sheet, int row, int col, Object value) {
        Cell cell;
        Row r = sheet.getRow(row);
        if (null == r) {
            r = sheet.createRow(row);
        }
        if (null == (cell = r.getCell(col))) {
            cell = r.createCell(col);
        }
        ExcelUtils.setCellValue(cell, value);
    }

    public static Object[] getRowValues(Row row) {
        int lastCellNum;
        if (null != row && 0 < (lastCellNum = row.getLastCellNum())) {
            Object[] values = new Object[lastCellNum + 1];
            for (int i = 0; i <= lastCellNum; ++i) {
                values[i] = ExcelUtils.getCellValue(row.getCell(i));
            }
            return values;
        }
        return null;
    }

    public static String[] getRowTexts(Row row) {
        if (null != row) {
            int lastCellNum = row.getLastCellNum();
            String[] values = new String[lastCellNum + 1];
            for (int i = 0; i <= lastCellNum; ++i) {
                values[i] = ExcelUtils.getCellText(row.getCell(i));
            }
            return values;
        }
        return null;
    }

    public static Object[] getRowValues(Sheet sheet, int row) {
        if (null != sheet && 0 < row) {
            return ExcelUtils.getRowValues(sheet.getRow(row));
        }
        return null;
    }

    public static String[] getRowTexts(Sheet sheet, int row) {
        if (null != sheet && 0 < row) {
            return ExcelUtils.getRowTexts(sheet.getRow(row));
        }
        return null;
    }

    public static void setRowValues(Row row, Object[] values) {
        if (null == row || null == values || 0 == values.length) {
            return;
        }
        for (int col = 0; col < values.length; ++col) {
            Cell cell = row.getCell(col);
            if (null == cell) {
                cell = row.createCell(col);
            }
            ExcelUtils.setCellValue(cell, values[col]);
        }
    }

    public static void setRowValues(Sheet sheet, int row, Object[] values) {
        if (null == sheet || 0 > row || null == values || 0 == values.length) {
            return;
        }
        Row r = sheet.getRow(row);
        if (null == r) {
            r = sheet.createRow(row);
        }
        ExcelUtils.setRowValues(r, values);
    }

    public static Object[][] getSheetValues(Sheet sheet) {
        if (null != sheet) {
            int lastRowNum = sheet.getLastRowNum();
            Object[][] values = new Object[lastRowNum + 1][];
            for (int row = 0; row <= lastRowNum; ++row) {
                values[row] = ExcelUtils.getRowValues(sheet.getRow(row));
            }
            return values;
        }
        return null;
    }

    public static Object[][] getSheetValues(Sheet sheet, int startIndex) {
        if (null != sheet) {
            int lastRowNum = sheet.getLastRowNum();
            if (startIndex > lastRowNum) {
                return null;
            }
            Object[][] values = new Object[lastRowNum - startIndex + 1][];
            for (int row = startIndex; row <= lastRowNum; ++row) {
                values[row - startIndex] = ExcelUtils.getRowValues(sheet.getRow(row));
            }
            return values;
        }
        return null;
    }

    public static String[][] getSheetTexts(Sheet sheet) {
        if (null != sheet) {
            int lastRowNum = sheet.getLastRowNum();
            String[][] values = new String[lastRowNum + 1][];
            for (int row = 0; row <= lastRowNum; ++row) {
                values[row] = ExcelUtils.getRowTexts(sheet.getRow(row));
            }
            return values;
        }
        return null;
    }

    public static String[][] getSheetTexts(Sheet sheet, int startIndex) {
        if (null != sheet) {
            int lastRowNum = sheet.getLastRowNum();
            if (startIndex > lastRowNum) {
                return null;
            }
            String[][] values = new String[lastRowNum - startIndex + 1][];
            for (int row = startIndex; row <= lastRowNum; ++row) {
                values[row - startIndex] = ExcelUtils.getRowTexts(sheet.getRow(row));
            }
            return values;
        }
        return null;
    }

    public static void setSheetValues(Sheet sheet, List<ISheetRowData> datas) {
        if (null != datas && !datas.isEmpty()) {
            for (int i = 0; i < datas.size(); ++i) {
                ExcelUtils.setRowValues(sheet, i, datas.get(i).getRowDatas());
            }
        }
    }

    public static interface ISheetRowData {
        public Object[] getRowDatas();

        public void setRowDatas(Object[] var1);
    }
}

