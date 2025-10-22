/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.CharStreams
 *  com.univocity.parsers.common.AbstractParser
 *  com.univocity.parsers.common.Format
 *  com.univocity.parsers.csv.CsvFormat
 *  com.univocity.parsers.csv.CsvParser
 *  com.univocity.parsers.csv.CsvParserSettings
 *  javax.annotation.concurrent.Immutable
 */
package com.jiuqi.nr.table.io.csv;

import com.google.common.io.CharStreams;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.DataReader;
import com.jiuqi.nr.table.io.FileReader;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.ReaderRegistry;
import com.jiuqi.nr.table.io.Source;
import com.jiuqi.nr.table.io.csv.CsvReadOptions;
import com.jiuqi.nr.table.util.Pair;
import com.univocity.parsers.common.AbstractParser;
import com.univocity.parsers.common.Format;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.IOException;
import java.io.Reader;
import javax.annotation.concurrent.Immutable;

@Immutable
public class CsvReader
extends FileReader
implements DataReader<CsvReadOptions> {
    private static final CsvReader INSTANCE = new CsvReader();

    public static void register(ReaderRegistry registry) {
        registry.registerExtension("csv", INSTANCE);
        registry.registerMimeType("text/csv", INSTANCE);
        registry.registerOptions(CsvReadOptions.class, INSTANCE);
    }

    @Override
    public DataFrame<?> read(CsvReadOptions options) throws IOException {
        return this.read(options, false);
    }

    @Override
    public DataFrame<?> read(Source source) throws IOException {
        return this.read(CsvReadOptions.builder(source).build());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataFrame<?> read(CsvReadOptions options, boolean headerOnly) throws IOException {
        Pair<Reader, ReadOptions.ColumnTypeReadOptions> pair = this.getReaderAndColumnTypes(options.source(), options);
        Reader reader = pair.getKey();
        ReadOptions.ColumnTypeReadOptions columnTypeReadOptions = pair.getValue();
        CsvParser parser = this.csvParser(options);
        try {
            DataFrame<?> dataFrame = this.parseRows(options, headerOnly, reader, columnTypeReadOptions, (AbstractParser<?>)parser, options.sampleSize());
            return dataFrame;
        }
        finally {
            if (options.source().reader() == null) {
                parser.stopParsing();
                reader.close();
            }
        }
    }

    private Pair<Reader, ReadOptions.ColumnTypeReadOptions> getReaderAndColumnTypes(Source source, CsvReadOptions options) throws IOException {
        byte[] bytesCache;
        ReadOptions.ColumnTypeReadOptions columnTypeReadOptions;
        block28: {
            boolean need2ParseFile;
            columnTypeReadOptions = options.columnTypeReadOptions();
            bytesCache = null;
            boolean bl = need2ParseFile = !columnTypeReadOptions.hasColumnTypeForAllColumns() && (!options.header() || !columnTypeReadOptions.hasColumnTypeForAllColumnsIfHavingColumnNames());
            if (need2ParseFile) {
                try (Reader reader = source.createReader(null);){
                    if (source.file() == null) {
                        String s = CharStreams.toString((Readable)reader);
                        bytesCache = source.getCharset() != null ? s.getBytes(source.getCharset()) : s.getBytes();
                        try (Reader reader2 = source.createReader(bytesCache);){
                            ColumnType[] detectedColumnTypes = this.detectColumnTypes(reader2, options);
                            if (detectedColumnTypes.length > 0) {
                                columnTypeReadOptions = ReadOptions.ColumnTypeReadOptions.of(detectedColumnTypes);
                            }
                            break block28;
                        }
                    }
                    ColumnType[] detectedColumnTypes = this.detectColumnTypes(reader, options);
                    if (detectedColumnTypes.length > 0) {
                        columnTypeReadOptions = ReadOptions.ColumnTypeReadOptions.of(detectedColumnTypes);
                    }
                }
            }
        }
        return Pair.create(source.createReader(bytesCache), columnTypeReadOptions);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ColumnType[] detectColumnTypes(Reader reader, CsvReadOptions options) {
        boolean header = options.header();
        CsvParser parser = this.csvParser(options);
        try {
            String[] columnNames = null;
            if (header) {
                parser.beginParsing(reader);
                columnNames = this.getColumnNames(options, options.columnTypeReadOptions(), (AbstractParser<?>)parser);
            }
            ColumnType[] columnTypeArray = this.getColumnTypes(reader, options, 0, (AbstractParser<?>)parser, columnNames);
            return columnTypeArray;
        }
        finally {
            parser.stopParsing();
        }
    }

    private CsvParser csvParser(CsvReadOptions options) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setLineSeparatorDetectionEnabled(options.lineSeparatorDetectionEnabled());
        settings.setFormat((Format)this.csvFormat(options));
        settings.setMaxCharsPerColumn(options.maxCharsPerColumn());
        if (options.maxNumberOfColumns() != null) {
            settings.setMaxColumns(options.maxNumberOfColumns().intValue());
        }
        return new CsvParser(settings);
    }

    private CsvFormat csvFormat(CsvReadOptions options) {
        CsvFormat format = new CsvFormat();
        if (options.quoteChar() != null) {
            format.setQuote(options.quoteChar().charValue());
        }
        if (options.escapeChar() != null) {
            format.setQuoteEscape(options.escapeChar().charValue());
        }
        if (options.separator() != null) {
            format.setDelimiter(options.separator().charValue());
        }
        if (options.lineEnding() != null) {
            format.setLineSeparator(options.lineEnding());
        }
        if (options.commentPrefix() != null) {
            format.setComment(options.commentPrefix().charValue());
        }
        return format;
    }
}

