/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.apache.commons.csv.CSVRecord
 */
package com.jiuqi.nr.tds.csv;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.tds.TdRowData;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.csv.CSVRecord;

public class CsvRowData
implements TdRowData {
    private final CSVRecord record;
    private final SimpleDateFormat dateFormat;

    public CsvRowData(CSVRecord record, SimpleDateFormat dateFormat) {
        this.record = record;
        this.dateFormat = dateFormat;
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
        return v;
    }

    @Override
    public Object getValue(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        return v;
    }

    @Override
    public int getInt(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return 0;
        }
        return Integer.parseInt(v);
    }

    @Override
    public int getInt(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return 0;
        }
        return Integer.parseInt(v);
    }

    @Override
    public long getLong(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return 0L;
        }
        return Long.parseLong(v);
    }

    @Override
    public long getLong(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return 0L;
        }
        return Long.parseLong(v);
    }

    @Override
    public double getDouble(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return 0.0;
        }
        return Double.parseDouble(v);
    }

    @Override
    public double getDouble(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return 0.0;
        }
        return Double.parseDouble(v);
    }

    @Override
    public BigDecimal getBigDecimal(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(v);
    }

    @Override
    public BigDecimal getBigDecimal(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(v);
    }

    @Override
    public String getString(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        return v;
    }

    @Override
    public String getString(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        return v;
    }

    @Override
    public boolean getBoolean(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return false;
        }
        return Boolean.parseBoolean(v);
    }

    @Override
    public boolean getBoolean(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return false;
        }
        return Boolean.parseBoolean(v);
    }

    @Override
    public Date getDate(int index) {
        String v = this.record.get(index);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        try {
            return this.dateFormat.parse(v);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date getDate(String name) {
        String v = this.record.get(name);
        if (StringUtils.isEmpty((String)v)) {
            return null;
        }
        try {
            return this.dateFormat.parse(v);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

