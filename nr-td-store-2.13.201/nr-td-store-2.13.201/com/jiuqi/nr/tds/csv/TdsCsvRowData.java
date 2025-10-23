/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.apache.commons.csv.CSVRecord
 */
package com.jiuqi.nr.tds.csv;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.tds.DataTypeException;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.TdRowData;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVRecord;

public class TdsCsvRowData
implements TdRowData {
    private final TdModel tdModel;
    private final CSVRecord record;
    private final SimpleDateFormat dateFormat;
    private final Map<String, Integer> colMap;

    public TdsCsvRowData(TdModel tdModel, CSVRecord record, SimpleDateFormat dateFormat) {
        this.tdModel = tdModel;
        this.record = record;
        this.dateFormat = dateFormat;
        this.colMap = new HashMap<String, Integer>();
        int i = 0;
        for (TdColumn column : tdModel.getColumns()) {
            this.colMap.put(column.getName(), i++);
        }
    }

    @Override
    public int length() {
        return this.record.size();
    }

    @Override
    public boolean isNull(int index) {
        String v = this.record.get(index);
        return StringUtils.isEmpty((String)v);
    }

    @Override
    public boolean isNull(String name) {
        String v = this.record.get(name);
        return StringUtils.isEmpty((String)v);
    }

    @Override
    public Object getValue(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        TdColumn column = this.tdModel.getColumns().get(index);
        return this.getValue(column, v);
    }

    @Override
    public Object getValue(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        Integer index = this.colMap.get(name);
        TdColumn column = this.tdModel.getColumns().get(index);
        return this.getValue(column, v);
    }

    private Object getValue(TdColumn column, String v) {
        int dataType = column.getDataType();
        switch (dataType) {
            case 6: {
                return v;
            }
            case 5: {
                return Integer.valueOf(v);
            }
            case 10: {
                return new BigDecimal(v);
            }
            case 1: {
                return "1".equals(v);
            }
            case 2: {
                try {
                    return this.dateFormat.parse(v);
                }
                catch (ParseException e) {
                    throw new DataTypeException(e);
                }
            }
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b: " + dataType);
    }
}

