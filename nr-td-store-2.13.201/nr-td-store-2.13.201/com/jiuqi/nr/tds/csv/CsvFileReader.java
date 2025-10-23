/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVRecord
 */
package com.jiuqi.nr.tds.csv;

import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.csv.CsvRowData;
import com.jiuqi.nr.tds.impl.BaseReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvFileReader
extends BaseReader {
    private TdModel tdModel;
    private CSVParser csvParser;
    private Iterator<CSVRecord> csvRecordIterator;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void open(File csvFile, String tableName) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(new String[0]).setSkipHeaderRecord(true).build();
        this.csvParser = new CSVParser((Reader)new FileReader(csvFile), format);
        this.csvRecordIterator = this.csvParser.iterator();
        this.tdModel = new TdModel();
        this.tdModel.setName(tableName);
        ArrayList<TdColumn> columns = new ArrayList<TdColumn>();
        for (String headerName : this.csvParser.getHeaderNames()) {
            TdColumn column = new TdColumn();
            column.setName(headerName);
            columns.add(column);
        }
        this.tdModel.setColumns(columns);
    }

    @Override
    public List<TdColumn> columns() {
        return this.tdModel.getColumns();
    }

    @Override
    public boolean hasNext() {
        return this.csvRecordIterator.hasNext();
    }

    @Override
    public TdRowData next() {
        CSVRecord next = this.csvRecordIterator.next();
        return new CsvRowData(next, this.dateFormat);
    }

    @Override
    public void close() throws IOException {
        this.csvRecordIterator = null;
        if (this.csvParser != null) {
            this.csvParser.close();
        }
    }
}

