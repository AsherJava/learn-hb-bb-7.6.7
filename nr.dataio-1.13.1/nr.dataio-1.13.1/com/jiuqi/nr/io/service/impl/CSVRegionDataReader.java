/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.io.service.impl;

import com.csvreader.CsvWriter;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVRegionDataReader
implements IRegionDataSetReader {
    private static final Logger log = LoggerFactory.getLogger(CSVRegionDataReader.class);
    private final boolean needRowKey;
    private final CsvWriter csvWriter;

    public CSVRegionDataReader(boolean needRowKey, CsvWriter csvWriter) {
        this.needRowKey = needRowKey;
        this.csvWriter = csvWriter;
    }

    @Override
    public boolean needRowKey() {
        return this.needRowKey;
    }

    @Override
    public void start(List<FieldDefine> fieldDefines) {
    }

    @Override
    public void readRowData(List<Object> dataRow) {
        if (dataRow.isEmpty()) {
            return;
        }
        String[] dataArray = new String[dataRow.size()];
        for (int i = 0; i < dataArray.length; ++i) {
            Object object = dataRow.get(i);
            if (object == null) continue;
            dataArray[i] = object.toString();
        }
        try {
            this.csvWriter.writeRecord(dataArray);
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void finish() {
    }
}

