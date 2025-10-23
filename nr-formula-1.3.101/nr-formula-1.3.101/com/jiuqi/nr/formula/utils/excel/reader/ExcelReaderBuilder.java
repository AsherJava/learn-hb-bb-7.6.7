/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.reader.ExcelReader;
import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReaderBuilder {
    private final ReadWorkBook readWorkBook = new ReadWorkBook();

    public ExcelReaderBuilder inputStream(InputStream inputStream) {
        this.readWorkBook.setInputStream(inputStream);
        return this;
    }

    public ExcelReaderBuilder charset(Charset charset) {
        this.readWorkBook.setCharset(charset);
        return this;
    }

    public ExcelReaderBuilder headClass(Class<? extends ExcelEntity> zlass) {
        this.readWorkBook.setHeadClass(zlass);
        return this;
    }

    public ExcelReader build() {
        return new ExcelReader(this.readWorkBook);
    }

    public ExcelReaderBuilder file(MultipartFile file) {
        this.readWorkBook.setFile(file);
        try {
            this.readWorkBook.setInputStream(file.getInputStream());
        }
        catch (IOException e) {
            throw new FormulaResourceException(e);
        }
        return this;
    }

    public ExcelReaderBuilder rowClass(Class<?> rowClass) {
        this.readWorkBook.setRowClass(rowClass);
        return this;
    }
}

