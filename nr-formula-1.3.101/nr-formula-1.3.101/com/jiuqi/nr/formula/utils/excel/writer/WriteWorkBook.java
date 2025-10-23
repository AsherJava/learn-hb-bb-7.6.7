/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteWorkBook {
    private Workbook workbook = new XSSFWorkbook();
    private OutputStream outputStream;
    private Charset charset;
    private Class<? extends ExcelEntity> zlass;

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Class<? extends ExcelEntity> getZlass() {
        return this.zlass;
    }

    public void setZlass(Class<? extends ExcelEntity> zlass) {
        this.zlass = zlass;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}

