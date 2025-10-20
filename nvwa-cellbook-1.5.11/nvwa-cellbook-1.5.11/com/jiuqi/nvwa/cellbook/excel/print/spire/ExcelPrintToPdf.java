/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.xls.FileFormat
 *  com.spire.xls.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.nvwa.cellbook.excel.print.spire;

import com.jiuqi.nvwa.cellbook.excel.print.spire.SpireInitializer;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPrintToPdf {
    public ExcelPrintToPdf() {
        SpireInitializer.initialize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void excel2pdf(XSSFWorkbook xssfWorkBook, OutputStream pdf_os) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream excel_is = null;
        xssfWorkBook.write((OutputStream)byteArrayOutputStream);
        try {
            excel_is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            Workbook wb = new Workbook();
            wb.loadFromStream((InputStream)excel_is);
            wb.saveToStream(pdf_os, FileFormat.PDF);
        }
        finally {
            if (null != excel_is) {
                excel_is.close();
            }
        }
    }
}

