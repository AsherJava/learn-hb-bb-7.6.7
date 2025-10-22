/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.univocity.parsers.csv.CsvFormat
 *  com.univocity.parsers.csv.CsvWriter
 *  com.univocity.parsers.csv.CsvWriterSettings
 */
package com.jiuqi.nr.table.io.csv;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.io.DataWriter;
import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriterRegistry;
import com.jiuqi.nr.table.io.csv.CsvWriteOptions;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriterSettings;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter
implements DataWriter<CsvWriteOptions> {
    private static final CsvWriter INSTANCE = new CsvWriter();
    private static final String NULL_VALUE = "";

    public static void register(WriterRegistry registry) {
        registry.registerExtension("csv", INSTANCE);
        registry.registerOptions(CsvWriteOptions.class, INSTANCE);
    }

    @Override
    public void write(DataFrame<?> df, Destination dest) throws IOException {
        this.write(df, CsvWriteOptions.builder(dest).build());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(DataFrame<?> df, CsvWriteOptions options) throws IOException {
        CsvWriterSettings settings = CsvWriter.createSettings(options);
        com.univocity.parsers.csv.CsvWriter csvWriter = null;
        try {
            csvWriter = new com.univocity.parsers.csv.CsvWriter(options.destination().createWriter(), settings);
            Index rows = df.rows();
            Index columns = df.columns();
            ArrayList<Object> rowLevels = new ArrayList<Object>(rows.levels());
            ArrayList<Object> colLevels = new ArrayList<Object>(columns.levels());
            int colSize = columns.size();
            if (options.header()) {
                ArrayList<Object> header = new ArrayList<Object>();
                if (options.colHeader()) {
                    String[] rowNames = rows.getNames();
                    colSize += rowNames.length;
                    for (Object object : rowNames) {
                        header.add(object);
                    }
                }
                for (Object e : colLevels) {
                    header.add(e.toString());
                }
                csvWriter.writeHeaders(header);
            }
            for (int r = 0; r < df.rowSize(); ++r) {
                int c;
                String[] entries = new String[colSize];
                Object e = rowLevels.get(r);
                Format format = null;
                if (options.colHeader()) {
                    if (rows.isMultIndex()) {
                        IKey iKey = (IKey)e;
                        Object[] elements = iKey.getElements();
                        for (c = 0; c < elements.length; ++c) {
                            this.writeIndexValues(options, rows, entries, c, elements[c]);
                        }
                    } else {
                        this.writeIndexValues(options, rows, entries, c, e);
                        ++c;
                    }
                }
                List<?> list = df.row(r);
                for (int j = 0; j < colLevels.size(); ++j) {
                    if (options.usePrintFormatters()) {
                        format = columns.getFormat(colLevels.get(j));
                    }
                    this.writeValues(format, entries, j + c, list.get(j));
                }
                csvWriter.writeRow(entries);
            }
        }
        finally {
            if (csvWriter != null) {
                csvWriter.flush();
                if (options.autoClose()) {
                    csvWriter.close();
                }
            }
        }
    }

    private void writeIndexValues(CsvWriteOptions options, Index rows, String[] entries, int c, Object key) {
        Format format = null;
        if (options.usePrintFormatters()) {
            format = rows.getNameFormat(rows.getName(c));
        }
        entries[c] = this.formatValue(format, key);
    }

    private void writeValues(Format format, String[] entries, int c, Object cell) {
        entries[c] = this.formatValue(format, cell);
    }

    private String formatValue(Format format, Object cell) {
        if (cell != null) {
            String value = cell.toString();
            if (null != format) {
                value = format.format(cell);
            }
            return value;
        }
        return null;
    }

    protected static CsvWriterSettings createSettings(CsvWriteOptions options) {
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setNullValue(NULL_VALUE);
        if (options.separator() != null) {
            ((CsvFormat)settings.getFormat()).setDelimiter(options.separator().charValue());
        }
        if (options.quoteChar() != null) {
            ((CsvFormat)settings.getFormat()).setQuote(options.quoteChar().charValue());
        }
        if (options.escapeChar() != null) {
            ((CsvFormat)settings.getFormat()).setQuoteEscape(options.escapeChar().charValue());
        }
        if (options.lineEnd() != null) {
            ((CsvFormat)settings.getFormat()).setLineSeparator(options.lineEnd());
        }
        settings.setIgnoreLeadingWhitespaces(options.ignoreLeadingWhitespaces());
        settings.setIgnoreTrailingWhitespaces(options.ignoreTrailingWhitespaces());
        settings.setSkipEmptyLines(false);
        settings.setQuoteAllFields(options.quoteAllFields());
        return settings;
    }
}

