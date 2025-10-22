/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Streams
 *  com.univocity.parsers.common.AbstractParser
 */
package com.jiuqi.nr.table.io;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.io.ColumnTypeDetector;
import com.jiuqi.nr.table.io.ReadOptions;
import com.univocity.parsers.common.AbstractParser;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileReader {
    private static final int UNLIMITED_SAMPLE_SIZE = -1;
    private static Logger logger = LoggerFactory.getLogger(FileReader.class);

    public ColumnType[] getColumnTypes(Reader reader, ReadOptions options, int linesToSkip, final AbstractParser<?> parser, String[] columnNames) {
        if (parser.getContext() == null) {
            parser.beginParsing(reader);
        }
        for (int i = 0; i < linesToSkip; ++i) {
            parser.parseNext();
        }
        ColumnTypeDetector detector = new ColumnTypeDetector(options.columnTypesToDetect());
        ColumnType[] columnTypes = detector.detectColumnTypes(new Iterator<String[]>(){
            String[] nextRow;
            {
                this.nextRow = parser.parseNext();
            }

            @Override
            public boolean hasNext() {
                return this.nextRow != null;
            }

            @Override
            public String[] next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                String[] tmp = this.nextRow;
                this.nextRow = parser.parseNext();
                return tmp;
            }
        }, options);
        for (int i = 0; i < columnTypes.length; ++i) {
            boolean hasColumnName = columnNames != null && i < columnNames.length;
            Optional<ColumnType> configuredColumnType = options.columnTypeReadOptions().columnType(i, hasColumnName ? columnNames[i] : null);
            if (!configuredColumnType.isPresent()) continue;
            columnTypes[i] = configuredColumnType.get();
        }
        return columnTypes;
    }

    private String cleanName(String name) {
        return name.trim();
    }

    public String[] getColumnNames(ReadOptions options, ReadOptions.ColumnTypeReadOptions columnTypeReadOptions, AbstractParser<?> parser) {
        if (options.header()) {
            String[] headerNames = parser.parseNext();
            for (int i = 0; i < headerNames.length; ++i) {
                headerNames[i] = headerNames[i] == null ? "C" + i : headerNames[i].trim();
            }
            if (options.allowDuplicateColumnNames()) {
                this.renameDuplicateColumnHeaders(headerNames);
            }
            return headerNames;
        }
        int columnCount = columnTypeReadOptions.columnTypes() != null ? columnTypeReadOptions.columnTypes().length : 0;
        String[] headerNames = new String[columnCount];
        for (int i = 0; i < columnCount; ++i) {
            headerNames[i] = "C" + i;
        }
        return headerNames;
    }

    private void renameDuplicateColumnHeaders(String[] headerNames) {
        HashMap<String, Integer> nameCounter = new HashMap<String, Integer>();
        for (int i = 0; i < headerNames.length; ++i) {
            String name = headerNames[i];
            Integer count = (Integer)nameCounter.get(name);
            if (count == null) {
                nameCounter.put(name, 1);
                continue;
            }
            Integer n = count;
            Integer n2 = count = Integer.valueOf(count + 1);
            nameCounter.put(name, count);
            headerNames[i] = name + "-" + count;
        }
    }

    protected DataFrame<?> parseRows(ReadOptions options, boolean headerOnly, Reader reader, ReadOptions.ColumnTypeReadOptions columnTypeReadOptions, AbstractParser<?> parser) {
        return this.parseRows(options, headerOnly, reader, columnTypeReadOptions, parser, -1);
    }

    protected DataFrame<?> parseRows(ReadOptions options, boolean headerOnly, Reader reader, ReadOptions.ColumnTypeReadOptions columnTypeReadOptions, AbstractParser<?> parser, int sampleSize) {
        parser.beginParsing(reader);
        ArrayList headerRow = Lists.newArrayList((Object[])this.getColumnNames(options, columnTypeReadOptions, parser));
        ColumnType[] types = (ColumnType[])Streams.mapWithIndex(headerRow.stream(), (columnName, idx) -> columnTypeReadOptions.columnType((int)idx, (String)columnName)).filter(Optional::isPresent).map(Optional::get).toArray(ColumnType[]::new);
        int rowIndexCount = options.rowIndexCount();
        String[] rowIndexName = new String[options.rowIndexCount()];
        ArrayList<String> columns = new ArrayList<String>();
        for (int x = 0; x < types.length; ++x) {
            String columnName2 = this.cleanName((String)headerRow.get(x));
            if (Strings.isNullOrEmpty((String)columnName2)) {
                columnName2 = "Column " + columns.size();
            }
            if (x < rowIndexCount) {
                rowIndexName[x] = columnName2;
                continue;
            }
            columns.add(columnName2);
        }
        DataFrame<Object> df = this.addRows(options, parser, rowIndexName, columns, sampleSize, types);
        return df;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DataFrame<Object> addRows(ReadOptions options, AbstractParser<?> reader, String[] rowIndexName, List<String> columns, int sampleSize, ColumnType[] types) {
        String[] nextLine;
        BlockData<Object> bd = new BlockData<Object>();
        Index rowIndex = new Index(rowIndexName);
        Index columnIndex = new Index(columns, "_COLUMN_");
        int rowNumber = options.header() ? 1 : 0;
        while ((nextLine = reader.parseNext()) != null) {
            if (!options.header() || nextLine.length == types.length) {
                if (nextLine.length < types.length) {
                    if (nextLine.length != 1) throw new IndexOutOfBoundsException("Row number " + rowNumber + " contains " + nextLine.length + " columns. " + types.length + " expected.");
                    if (!Strings.isNullOrEmpty((String)nextLine[0])) throw new IndexOutOfBoundsException("Row number " + rowNumber + " contains " + nextLine.length + " columns. " + types.length + " expected.");
                    logger.error("Warning: Invalid file. Row " + rowNumber + " is empty. Continuing.");
                } else {
                    if (nextLine.length > types.length) {
                        throw new IllegalArgumentException("Row number " + rowNumber + " contains " + nextLine.length + " columns. " + types.length + " expected.");
                    }
                    int samplesCount = bd.rowSize();
                    if (sampleSize >= 0) {
                        if (samplesCount >= sampleSize) return new DataFrame<Object>(rowIndex, columnIndex, bd);
                    }
                    ArrayList<Object> rowData = new ArrayList<Object>(nextLine.length);
                    this.addValuesToColumns(options, rowNumber, rowIndex, rowData, columns, types, nextLine);
                    bd.add(rowData);
                }
            }
            ++rowNumber;
        }
        return new DataFrame<Object>(rowIndex, columnIndex, bd);
    }

    private void addValuesToColumns(ReadOptions options, Integer rowNumber, Index rowIndex, List<Object> rowData, List<String> columns, ColumnType[] types, String[] nextLine) {
        int rowIndexCount = options.rowIndexCount();
        ArrayList<Object> rowlevels = new ArrayList<Object>();
        for (int c = 0; c < rowIndexCount + columns.size(); ++c) {
            Object o = this.parseValue(options, types[c], nextLine[c]);
            if (c < rowIndexCount) {
                rowlevels.add(o);
                continue;
            }
            rowData.add(o);
        }
        if (rowlevels.size() == 1) {
            rowIndex.add(rowlevels.get(0));
        } else if (rowlevels.size() > 1) {
            IKey key = IKey.newIKey(rowlevels.toArray());
            rowIndex.add(key);
        } else {
            rowIndex.fill();
        }
    }

    private Object parseValue(ReadOptions options, ColumnType type, String value) {
        AbstractColumnParser<?> parser = type.customParser(options);
        return parser.parse(value);
    }
}

