/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.DateParser;
import com.jiuqi.nr.table.io.ReadOptions;
import java.time.LocalDate;

public class DateColumnType
extends AbstractColumnType {
    public static final int BYTE_SIZE = 4;
    public static final DateParser DEFAULT_PARSER = new DateParser(ColumnType.LOCAL_DATE);
    private static DateColumnType INSTANCE;

    private DateColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static DateColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new DateColumnType(4, "LOCAL_DATE");
        }
        return INSTANCE;
    }

    public AbstractColumnParser<LocalDate> customParser(ReadOptions options) {
        return new DateParser(this, options);
    }

    public static int missingValueIndicator() {
        return Integer.MIN_VALUE;
    }

    public static boolean valueIsMissing(int i) {
        return i == DateColumnType.missingValueIndicator();
    }

    @Override
    public DataType type() {
        return DataType.DATE;
    }
}

