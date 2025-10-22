/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 */
package com.jiuqi.nr.table.io;

import com.google.common.io.Files;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.DataReader;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.ReaderRegistry;
import com.jiuqi.nr.table.io.Source;
import com.jiuqi.nr.table.io.csv.CsvReadOptions;
import com.jiuqi.nr.table.io.csv.CsvReader;
import com.jiuqi.nr.table.io.excel.XlsxReader;
import com.jiuqi.nr.table.io.json.JsonReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

public class DataFrameReader {
    public static final ReaderRegistry defaultReaderRegistry = new ReaderRegistry();

    public DataFrameReader() {
        DataFrameReader.autoRegisterReadersAndWriters();
    }

    private static void autoRegisterReadersAndWriters() {
        JsonReader.register(defaultReaderRegistry);
        XlsxReader.register(defaultReaderRegistry);
        CsvReader.register(defaultReaderRegistry);
    }

    private ReaderRegistry getRegistry() {
        return defaultReaderRegistry;
    }

    public DataFrame<?> string(String s, String fileExtension) throws IOException {
        Optional<DataReader<?>> reader = this.getRegistry().getReaderForExtension(fileExtension);
        if (!reader.isPresent()) {
            throw new IllegalArgumentException("No reader registered for extension " + fileExtension);
        }
        return reader.get().read(Source.fromString(s));
    }

    public DataFrame<?> file(String file) throws IOException {
        return this.file(new File(file));
    }

    public DataFrame<?> file(File file) throws IOException {
        String extension = Files.getFileExtension((String)file.getCanonicalPath());
        Optional<DataReader<?>> reader = this.getRegistry().getReaderForExtension(extension);
        if (reader.isPresent()) {
            return reader.get().read(new Source(file));
        }
        throw new IllegalArgumentException("No reader registered for extension " + extension);
    }

    public <T extends ReadOptions> DataFrame<?> usingOptions(T options) throws IOException {
        DataReader<T> reader = this.getRegistry().getReaderForOptions(options);
        return reader.read(options);
    }

    public DataFrame<?> usingOptions(ReadOptions.Builder builder) throws IOException {
        return this.usingOptions(builder.build());
    }

    public DataFrame<?> csv(String file) throws IOException {
        return this.csv(CsvReadOptions.builder(file));
    }

    public DataFrame<?> csv(String contents, String tableName) {
        try {
            return this.csv(CsvReadOptions.builder(new StringReader(contents)).tableName(tableName));
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public DataFrame<?> csv(File file) throws IOException {
        return this.csv(CsvReadOptions.builder(file));
    }

    public DataFrame<?> csv(InputStream stream) throws IOException {
        return this.csv(CsvReadOptions.builder(stream));
    }

    public DataFrame<?> csv(InputStream stream, String name) throws IOException {
        return this.csv(CsvReadOptions.builder(stream).tableName(name));
    }

    public DataFrame<?> csv(Reader reader) throws IOException {
        return this.csv(CsvReadOptions.builder(reader));
    }

    public DataFrame<?> csv(CsvReadOptions.Builder options) throws IOException {
        return this.csv(options.build());
    }

    public DataFrame<?> csv(CsvReadOptions options) throws IOException {
        return new CsvReader().read(options);
    }
}

