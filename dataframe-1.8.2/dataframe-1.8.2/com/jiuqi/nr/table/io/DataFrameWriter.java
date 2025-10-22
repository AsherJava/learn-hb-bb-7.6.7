/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.table.io;

import com.google.common.io.Files;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.DataWriter;
import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriteOptions;
import com.jiuqi.nr.table.io.WriterRegistry;
import com.jiuqi.nr.table.io.csv.CsvWriteOptions;
import com.jiuqi.nr.table.io.csv.CsvWriter;
import com.jiuqi.nr.table.io.json.JsonWriter;
import com.jiuqi.nr.table.io.ncell.NCellWriteOptions;
import com.jiuqi.nr.table.io.ncell.NCellWriter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

public class DataFrameWriter {
    public static final WriterRegistry defaultWriterRegistry = new WriterRegistry();
    private DataFrame<?> df;

    public DataFrameWriter(DataFrame<?> df) {
        this.df = df;
        CsvWriter.register(defaultWriterRegistry);
        JsonWriter.register(defaultWriterRegistry);
    }

    private WriterRegistry getRegistry() {
        return defaultWriterRegistry;
    }

    public void toFile(String file) throws IOException {
        this.toFile(new File(file));
    }

    public void toFile(File file) throws IOException {
        String extension = Files.getFileExtension((String)file.getCanonicalPath());
        DataWriter<?> dataWriter = this.getRegistry().getWriterForExtension(extension);
        dataWriter.write(this.df, new Destination(file));
    }

    public void toStream(OutputStream stream, String extension) throws IOException {
        DataWriter<?> dataWriter = this.getRegistry().getWriterForExtension(extension);
        dataWriter.write(this.df, new Destination(stream));
    }

    public void toWriter(Writer writer, String extension) throws IOException {
        DataWriter<?> dataWriter = this.getRegistry().getWriterForExtension(extension);
        dataWriter.write(this.df, new Destination(writer));
    }

    public <T extends WriteOptions> void usingOptions(T options) throws IOException {
        DataWriter<T> dataWriter = this.getRegistry().getWriterForOptions(options);
        if (dataWriter == null) {
            this._throwUnFindException(options.getClass().getName());
        }
        dataWriter.write(this.df, options);
    }

    public String toString(String extension) throws IOException {
        StringWriter writer = new StringWriter();
        DataWriter<?> dataWriter = this.getRegistry().getWriterForExtension(extension);
        if (dataWriter == null) {
            this._throwUnFindException(extension);
        }
        dataWriter.write(this.df, new Destination(writer));
        return writer.toString();
    }

    private void _throwUnFindException(String msg) {
        throw new RuntimeException("\u672a\u627e\u5230 <" + msg + "> \u5bf9\u5e94\u7684 Writer\uff0c\u9700\u8981\u63d0\u524d\u6ce8\u518c\u3002");
    }

    public String toJson() throws IOException {
        StringWriter writer = new StringWriter();
        JsonWriter dataWriter = new JsonWriter();
        dataWriter.write(this.df, new Destination(writer));
        return writer.toString();
    }

    public void csv(String file) throws IOException {
        CsvWriteOptions options = CsvWriteOptions.builder(file).build();
        new CsvWriter().write(this.df, options);
    }

    public void csv(File file) throws IOException {
        CsvWriteOptions options = CsvWriteOptions.builder(file).build();
        new CsvWriter().write(this.df, options);
    }

    public void csv(CsvWriteOptions options) throws IOException {
        new CsvWriter().write(this.df, options);
    }

    public void csv(OutputStream stream) throws IOException {
        CsvWriteOptions options = CsvWriteOptions.builder(stream).build();
        new CsvWriter().write(this.df, options);
    }

    public void csv(Writer writer) throws IOException {
        CsvWriteOptions options = CsvWriteOptions.builder(writer).build();
        new CsvWriter().write(this.df, options);
    }

    public CellBook ncell(NCellWriteOptions options) {
        NCellWriter nCellWriter = new NCellWriter();
        nCellWriter.write(this.df, options);
        return nCellWriter.getCellBook();
    }
}

