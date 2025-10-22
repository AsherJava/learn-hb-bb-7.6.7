/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.StringParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class StringColumnType
extends AbstractColumnType {
    public static final int BYTE_SIZE = 4;
    public static final StringParser DEFAULT_PARSER = new StringParser(ColumnType.STRING);
    private static StringColumnType INSTANCE;

    private StringColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static StringColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new StringColumnType(4, "STRING");
        }
        return INSTANCE;
    }

    public static boolean valueIsMissing(String string) {
        return StringColumnType.missingValueIndicator().equals(string);
    }

    public StringParser customParser(ReadOptions options) {
        return new StringParser(this, options);
    }

    public static String missingValueIndicator() {
        return "";
    }

    @Override
    public DataType type() {
        return DataType.STRING;
    }
}

