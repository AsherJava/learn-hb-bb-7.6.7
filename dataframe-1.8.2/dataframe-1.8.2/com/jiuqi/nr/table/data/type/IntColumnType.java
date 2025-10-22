/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.IntParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class IntColumnType
extends AbstractColumnType {
    public static final IntParser DEFAULT_PARSER = new IntParser(ColumnType.INTEGER);
    private static final int BYTE_SIZE = 4;
    private static IntColumnType INSTANCE;

    private IntColumnType(int byteSize, String name, String printerFriendlyName) {
        super(byteSize, name);
    }

    public static IntColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new IntColumnType(4, "INTEGER", "Integer");
        }
        return INSTANCE;
    }

    public IntParser customParser(ReadOptions options) {
        return new IntParser(this, options);
    }

    public static boolean valueIsMissing(int value) {
        return value == IntColumnType.missingValueIndicator();
    }

    public static int missingValueIndicator() {
        return Integer.MIN_VALUE;
    }

    @Override
    public DataType type() {
        return DataType.INT;
    }
}

