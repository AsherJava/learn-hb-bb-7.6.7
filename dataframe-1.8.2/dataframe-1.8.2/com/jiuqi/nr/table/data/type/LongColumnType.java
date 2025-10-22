/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.LongParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class LongColumnType
extends AbstractColumnType {
    public static final LongParser DEFAULT_PARSER = new LongParser(ColumnType.LONG);
    private static final int BYTE_SIZE = 8;
    private static LongColumnType INSTANCE;

    private LongColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static LongColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new LongColumnType(8, "LONG");
        }
        return INSTANCE;
    }

    @Override
    public AbstractColumnParser<?> customParser(ReadOptions options) {
        return new LongParser(this, options);
    }

    public static long missingValueIndicator() {
        return Long.MIN_VALUE;
    }

    @Override
    public DataType type() {
        return DataType.LONG;
    }
}

