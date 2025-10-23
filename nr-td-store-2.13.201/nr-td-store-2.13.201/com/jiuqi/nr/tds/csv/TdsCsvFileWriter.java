/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVPrinter
 */
package com.jiuqi.nr.tds.csv;

import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nr.tds.FileIOException;
import com.jiuqi.nr.tds.PackageData;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import com.jiuqi.nr.tds.api.DataTableWriter;
import com.jiuqi.nr.tds.api.TdStoreFactory;
import com.jiuqi.nr.tds.impl.BaseWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TdsCsvFileWriter
extends BaseWriter {
    private static final Logger logger = LoggerFactory.getLogger(TdsCsvFileWriter.class);
    private final FileWriter out;
    private final CSVPrinter printer;
    private final TdModel tdModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TdsCsvFileWriter(File dataDir, TdModel tdModel) {
        super(dataDir, tdModel.getName());
        this.tdModel = tdModel;
        Costs.createPathIfNotExists(dataDir.toPath());
        try {
            File modelFile = new File(dataDir, "model.json");
            byte[] bytes = Costs.MAPPER.writeValueAsBytes((Object)tdModel);
            Files.write(modelFile.toPath(), bytes, new OpenOption[0]);
            File csv = new File(dataDir, tdModel.getName() + ".csv");
            File pkFile = new File(dataDir, "package.json");
            PackageData pkg = new PackageData();
            pkg.setVersion(TdStoreFactory.VERSION_0_0_1.toString());
            try {
                Files.write(pkFile.toPath(), Costs.MAPPER.writeValueAsBytes((Object)pkg), new OpenOption[0]);
            }
            catch (IOException e) {
                throw new FileIOException("\u751f\u6210\u6570\u636e\u6587\u4ef6\u5931\u8d25", e);
            }
            ArrayList<String> columns = new ArrayList<String>();
            for (TdColumn column : tdModel.getColumns()) {
                columns.add(column.getName());
            }
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(columns.toArray(new String[0])).build();
            this.out = new FileWriter(csv);
            this.printer = new CSVPrinter((Appendable)this.out, csvFormat);
        }
        catch (IOException e) {
            throw new RuntimeException("\u751f\u6210\u6570\u636e\u6587\u4ef6\u5931\u8d25", e);
        }
    }

    @Override
    public DataTableWriter appendRow(TdRowData rowData) {
        List<TdColumn> columns = this.tdModel.getColumns();
        ArrayList<String> columnValues = new ArrayList<String>();
        for (int i = 0; i < columns.size(); ++i) {
            TdColumn tdColumn = columns.get(i);
            if (rowData.isNull(i)) {
                columnValues.add(null);
                continue;
            }
            String value = this.getStringValue(rowData, tdColumn, i);
            columnValues.add(value);
        }
        try {
            this.printer.printRecord(columnValues);
        }
        catch (IOException e) {
            throw new RuntimeException("\u5199\u5165\u6570\u636e\u5931\u8d25", e);
        }
        return this;
    }

    private String getStringValue(TdRowData rowData, TdColumn tdColumn, int index) {
        int dataType = tdColumn.getDataType();
        switch (dataType) {
            case 6: {
                return rowData.getString(index);
            }
            case 5: {
                return String.valueOf(rowData.getInt(index));
            }
            case 10: {
                return rowData.getBigDecimal(index).toPlainString();
            }
            case 1: {
                return rowData.getBoolean(index) ? "1" : "0";
            }
            case 2: {
                Date date = rowData.getDate(index);
                return this.dateFormat.format(date);
            }
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b: " + dataType);
    }

    private String getStringValue(Object ov, TdColumn tdColumn) {
        int dataType = tdColumn.getDataType();
        switch (dataType) {
            case 5: 
            case 6: {
                return ov.toString();
            }
            case 10: {
                if (ov instanceof BigDecimal) {
                    return ((BigDecimal)ov).toPlainString();
                }
                throw new UnsupportedOperationException("\u6570\u503c\u7c7b\u578b\u53ea\u652f\u6301\u4f20\u5165 java.math.BigDecimal");
            }
            case 1: {
                return Boolean.TRUE.equals(ov) ? "1" : "0";
            }
            case 2: {
                if (ov instanceof Date) {
                    return this.dateFormat.format(ov);
                }
                throw new UnsupportedOperationException("\u65e5\u671f\u7c7b\u578b\u53ea\u652f\u6301 java.util.Date");
            }
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b: " + dataType);
    }

    @Override
    public DataTableWriter appendRow(List<Object> rowData) {
        List<TdColumn> columns = this.tdModel.getColumns();
        ArrayList<String> columnValues = new ArrayList<String>();
        for (int i = 0; i < columns.size(); ++i) {
            TdColumn tdColumn = columns.get(i);
            Object vo = rowData.get(i);
            if (Objects.isNull(vo)) {
                columnValues.add(null);
                continue;
            }
            String value = this.getStringValue(vo, tdColumn);
            columnValues.add(value);
        }
        try {
            this.printer.printRecord(columnValues);
        }
        catch (IOException e) {
            throw new RuntimeException("\u5199\u5165\u6570\u636e\u5931\u8d25", e);
        }
        return this;
    }

    @Override
    public void flush() throws IOException {
        this.printer.flush();
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.printer.close();
        this.out.close();
    }
}

