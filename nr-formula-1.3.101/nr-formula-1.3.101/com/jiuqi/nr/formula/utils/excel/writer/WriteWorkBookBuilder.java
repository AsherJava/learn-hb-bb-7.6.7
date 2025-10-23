/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.writer;

import com.jiuqi.nr.formula.utils.excel.writer.WriteWorkBook;
import java.io.OutputStream;
import java.nio.charset.Charset;

public final class WriteWorkBookBuilder {
    private WriteWorkBook writeWorkBook = new WriteWorkBook();

    public WriteWorkBookBuilder outputStream(OutputStream outputStream) {
        this.writeWorkBook.setOutputStream(outputStream);
        return this;
    }

    public WriteWorkBookBuilder charset(Charset charset) {
        this.writeWorkBook.setCharset(charset);
        return this;
    }

    public WriteWorkBook build() {
        return this.writeWorkBook;
    }
}

