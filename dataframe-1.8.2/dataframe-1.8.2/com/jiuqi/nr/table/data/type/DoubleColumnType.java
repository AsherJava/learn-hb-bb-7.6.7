/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.DoubleParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class DoubleColumnType
extends AbstractColumnType {
    private static final int BYTE_SIZE = 8;
    public static final DoubleParser DEFAULT_PARSER = new DoubleParser(ColumnType.DOUBLE);
    private static DoubleColumnType INSTANCE = new DoubleColumnType(8, "DOUBLE");

    public static DoubleColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new DoubleColumnType(8, "DOUBLE");
        }
        return INSTANCE;
    }

    private DoubleColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public DoubleParser customParser(ReadOptions options) {
        return new DoubleParser(this, options);
    }

    public static boolean valueIsMissing(double value) {
        return Double.isNaN(value);
    }

    public static double missingValueIndicator() {
        return Double.NaN;
    }

    @Override
    public DataType type() {
        return DataType.DOUBLE;
    }
}

