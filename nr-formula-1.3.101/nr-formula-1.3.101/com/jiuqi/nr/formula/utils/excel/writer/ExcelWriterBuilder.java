/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.writer.ExcelWriter;
import com.jiuqi.nr.formula.utils.excel.writer.WriteWorkBook;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.servlet.ServletOutputStream;

public class ExcelWriterBuilder {
    private final WriteWorkBook writeWorkBook = new WriteWorkBook();

    public ExcelWriterBuilder outputStream(ServletOutputStream outputStream) {
        this.writeWorkBook.setOutputStream((OutputStream)outputStream);
        return this;
    }

    public ExcelWriterBuilder headClass(Class<? extends ExcelEntity> entityClass) {
        this.writeWorkBook.setZlass(entityClass);
        return this;
    }

    public ExcelWriterBuilder charset(Charset charset) {
        this.writeWorkBook.setCharset(charset);
        return this;
    }

    public ExcelWriter build() {
        return new ExcelWriter(this.writeWorkBook);
    }
}

