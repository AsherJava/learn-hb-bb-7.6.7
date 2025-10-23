/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.utils.excel;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.reader.ExcelReaderBuilder;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheetBuilder;
import com.jiuqi.nr.formula.utils.excel.writer.ExcelWriterBuilder;
import com.jiuqi.nr.formula.utils.excel.writer.WriteSheetBuilder;
import javax.servlet.ServletOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class FormulaExcelFactory {
    public static ExcelWriterBuilder write(ServletOutputStream outputStream) {
        return FormulaExcelFactory.write(outputStream, null);
    }

    public static ExcelWriterBuilder write(ServletOutputStream outputStream, Class<? extends ExcelEntity> entityClass) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.outputStream(outputStream);
        if (entityClass != null) {
            excelWriterBuilder.headClass(entityClass);
        }
        return excelWriterBuilder;
    }

    public static WriteSheetBuilder writeSheet() {
        return FormulaExcelFactory.writerSheet(null, 0, "sheet0");
    }

    public static WriteSheetBuilder writerSheet(String sheetName) {
        return FormulaExcelFactory.writerSheet(null, 0, sheetName);
    }

    public static WriteSheetBuilder writerSheet(Class<? extends ExcelEntity> headClass, int sheetIndex, String sheetName) {
        return new WriteSheetBuilder().sheetIndex(sheetIndex).sheetName(sheetName).sheetHeadClass(headClass);
    }

    public static ExcelReaderBuilder read(MultipartFile file, Class<? extends ExcelEntity> headClass) {
        return new ExcelReaderBuilder().file(file).headClass(headClass);
    }

    public static ReadSheetBuilder readSheet(int no, String sheetName, Class<? extends ExcelEntity> headClass) {
        return ReadSheetBuilder.aReadSheet().sheetIndex(no).sheetName(sheetName).headClass(headClass);
    }
}

