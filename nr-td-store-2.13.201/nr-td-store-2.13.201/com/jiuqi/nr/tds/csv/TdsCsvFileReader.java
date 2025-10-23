/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVRecord
 */
package com.jiuqi.nr.tds.csv;

import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.csv.TdsCsvRowData;
import com.jiuqi.nr.tds.impl.BaseReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class TdsCsvFileReader
extends BaseReader {
    private TdModel tdModel;
    private CSVParser csvParser;
    private Iterator<CSVRecord> csvRecordIterator;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void open(File file, String tableName) throws IOException {
        super.open(file, tableName);
        this.tdModel = (TdModel)Costs.MAPPER.readValue(new File(this.tempDir + "model.json"), TdModel.class);
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(new String[0]).setSkipHeaderRecord(true).build();
        File csvFile = new File(this.tempDir + tableName + ".csv");
        this.csvParser = new CSVParser((Reader)new FileReader(csvFile), format);
        this.csvRecordIterator = this.csvParser.iterator();
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
        return new TdsCsvRowData(this.tdModel, next, this.dateFormat);
    }

    @Override
    public void close() throws IOException {
        this.csvRecordIterator = null;
        if (this.csvParser != null) {
            this.csvParser.close();
        }
    }
}

