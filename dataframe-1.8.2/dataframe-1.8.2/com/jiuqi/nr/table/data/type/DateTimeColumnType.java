/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.DateTimeParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class DateTimeColumnType
extends AbstractColumnType {
    public static final int BYTE_SIZE = 8;
    public static final DateTimeParser DEFAULT_PARSER = new DateTimeParser(ColumnType.LOCAL_DATE_TIME);
    private static DateTimeColumnType INSTANCE = new DateTimeColumnType(8, "LOCAL_DATE_TIME");

    private DateTimeColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static DateTimeColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new DateTimeColumnType(8, "LOCAL_DATE_TIME");
        }
        return INSTANCE;
    }

    public DateTimeParser customParser(ReadOptions options) {
        return new DateTimeParser(this, options);
    }

    public static long missingValueIndicator() {
        return Long.MIN_VALUE;
    }

    public static boolean valueIsMissing(long value) {
        return value == DateTimeColumnType.missingValueIndicator();
    }

    @Override
    public DataType type() {
        return DataType.DATETIME;
    }
}

