/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.common.io;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

public class SheetUtils {
    private SheetUtils() {
    }

    public static <O> O write(IRowData<O> obj, Map<Integer, Object> data) {
        return obj.write(data);
    }

    public static void write(IRowData<?> obj, Row row) {
        Map<Integer, Object> datas = obj.read();
        for (int index : datas.keySet()) {
            Cell cell = row.getCell(index);
            if (null == cell) {
                cell = row.createCell(index);
            }
            SheetUtils.setCellValue(cell, datas.get(index));
        }
    }

    @Deprecated
    public static void autoMerge(Sheet sheet) {
    }

    public static void trans(Sheet sheet, List<IRowData<?>> rowDatas) {
        if (null == sheet || null == rowDatas || rowDatas.isEmpty()) {
            return;
        }
        for (int i = 0; i < rowDatas.size(); ++i) {
            Row row = sheet.createRow(i);
            SheetUtils.write(rowDatas.get(i), row);
        }
    }

    public static <O> List<O> trans(Sheet sheet, Predicate<Row> predicate, Supplier<IRowData<O>> supplier) {
        ArrayList objs = new ArrayList();
        if (null != sheet) {
            sheet.forEach(row -> {
                if (predicate.test((Row)row)) {
                    IRowData excelRowData = (IRowData)supplier.get();
                    Object obj = excelRowData.write((Row)row);
                    objs.add(obj);
                }
            });
        }
        return objs;
    }

    private static FormulaEvaluator getFormulaEvaluator(Workbook workbook) {
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
            FormulaEvaluator formulaEvaluator = SheetUtils.getFormulaEvaluator(workbook);
            CellValue value = formulaEvaluator.evaluate(cell);
            return SheetUtils.getCellValue(value);
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

    public static interface IRowData<T> {
        public Map<Integer, Object> read();

        public T write(Map<Integer, Object> var1);

        default public T write(Row row) {
            HashMap<Integer, Object> data = new HashMap<Integer, Object>();
            if (null != row) {
                row.forEach(cell -> {
                    if (null != cell) {
                        data.put(cell.getColumnIndex(), SheetUtils.getCellValue(cell));
                    }
                });
            }
            return this.write(data);
        }

        @Deprecated
        default public String toCSVString() {
            Map<Integer, Object> read = this.read();
            if (null == read || read.isEmpty()) {
                return "";
            }
            return "";
        }
    }
}

