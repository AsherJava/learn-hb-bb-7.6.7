/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelField;
import com.jiuqi.nr.formula.utils.excel.filter.ICellFilter;
import com.jiuqi.nr.formula.utils.excel.writer.WriteSheet;
import com.jiuqi.nr.formula.utils.excel.writer.WriteWorkBook;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class ExcelWriter
implements Closeable {
    private final WriteWorkBook writeWorkBook;

    public ExcelWriter(WriteWorkBook writeWorkBook) {
        this.writeWorkBook = writeWorkBook;
    }

    public void write(Collection<ExcelEntity> datas, WriteSheet writeSheet, ICellFilter filter) throws IllegalAccessException, IOException {
        Cell cell;
        if (this.writeWorkBook == null) {
            return;
        }
        Workbook workbook = this.writeWorkBook.getWorkbook();
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(writeSheet.getSheetIndex(), writeSheet.getSheetName());
        sheet.setDefaultColumnWidth(20);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        Class<? extends ExcelEntity> zlass = writeSheet.getZlass();
        if (zlass == null && (zlass = this.writeWorkBook.getZlass()) == null) {
            throw new RuntimeException("excel\u5bfc\u5165\u5bfc\u51fa\u7f3a\u5c11\u5934\u6587\u4ef6");
        }
        Field[] fields = zlass.getDeclaredFields();
        List collect = Arrays.stream(fields).filter(x -> x.isAnnotationPresent(ExcelField.class)).sorted(Comparator.comparing(x -> x.getAnnotation(ExcelField.class).order(), Integer::compare)).collect(Collectors.toList());
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        for (int i = 0; i < collect.size(); ++i) {
            if (!filter.enable(row.getRowNum(), i)) continue;
            cell = row.createCell(i);
            ExcelField excelField = ((Field)collect.get(i)).getAnnotation(ExcelField.class);
            XSSFRichTextString text = new XSSFRichTextString(excelField.columnName());
            cell.setCellValue(text.toString());
            cell.setCellStyle(cellStyle);
        }
        for (ExcelEntity data : datas) {
            int colNum = 0;
            row = sheet.createRow(rowNum++);
            for (Field field : collect) {
                if (!filter.enable(row.getRowNum(), colNum)) continue;
                cell = row.createCell(colNum++);
                field.setAccessible(true);
                cell.setCellValue(field.get(data) == null ? null : field.get(data).toString());
            }
        }
    }

    @Override
    public void close() throws IOException {
        OutputStream outputStream = this.writeWorkBook.getOutputStream();
        this.writeWorkBook.getWorkbook().write(outputStream);
        if (outputStream != null) {
            outputStream.close();
        }
    }
}

