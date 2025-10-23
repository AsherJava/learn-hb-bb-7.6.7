/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.csvreader.CsvWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class CsvFileWriter
implements Closeable {
    public static final char CSVDELIMITER = ',';
    public static final String CSVCHARSETNAME = "UTF-8";
    private static final int MAX_BUFFERED_ROW_COUNT = 5000;
    private CsvWriter csvWriter;
    private int bufferedRowCount;
    protected final String[] lineData;

    public CsvFileWriter(File file, int columnCount) {
        this.lineData = new String[columnCount];
        FileUtil.forceCreateNewFile(file);
        this.csvWriter = new CsvWriter(FileUtil.createFileOutputStream(file), ',', Charset.forName(CSVCHARSETNAME));
    }

    public void writeLine(String[] lineData) throws IOException {
        this.csvWriter.writeRecord(lineData);
        ++this.bufferedRowCount;
        this.tryFlush();
    }

    public void writeLine() throws IOException {
        this.csvWriter.writeRecord(this.lineData);
        ++this.bufferedRowCount;
        this.tryFlush();
    }

    private void tryFlush() throws IOException {
        if (this.bufferedRowCount >= 5000) {
            this.csvWriter.flush();
            this.bufferedRowCount = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.csvWriter != null) {
            try {
                this.csvWriter.flush();
                this.csvWriter.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

