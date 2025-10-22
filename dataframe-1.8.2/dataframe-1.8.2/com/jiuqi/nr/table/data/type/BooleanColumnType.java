/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.BooleanFormat;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.BooleanParser;
import com.jiuqi.nr.table.df.Options;
import com.jiuqi.nr.table.io.ReadOptions;
import java.text.Format;

public class BooleanColumnType
extends AbstractColumnType {
    public static final BooleanParser DEFAULT_PARSER = new BooleanParser(ColumnType.BOOLEAN);
    public static final byte MISSING_VALUE = BooleanColumnType.missingValueIndicator();
    public static final byte BYTE_TRUE = 1;
    public static final byte BYTE_FALSE = 0;
    private static final byte BYTE_SIZE = 1;
    private static BooleanColumnType INSTANCE;

    private BooleanColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static BooleanColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new BooleanColumnType(1, "BOOLEAN");
        }
        return INSTANCE;
    }

    public BooleanParser customParser(ReadOptions readOptions) {
        return new BooleanParser(this, readOptions);
    }

    public static byte missingValueIndicator() {
        return -128;
    }

    public static boolean valueIsMissing(byte value) {
        return value == BooleanColumnType.missingValueIndicator();
    }

    @Override
    public DataType type() {
        return DataType.BOOL;
    }

    @Override
    public Format format(Options options) {
        return new BooleanFormat();
    }
}

