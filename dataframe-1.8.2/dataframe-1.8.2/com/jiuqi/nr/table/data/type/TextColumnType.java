/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.StringParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class TextColumnType
extends AbstractColumnType {
    public static final int BYTE_SIZE = 4;
    public static final StringParser DEFAULT_PARSER = new StringParser(ColumnType.STRING);
    private static TextColumnType INSTANCE;

    protected TextColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static TextColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new TextColumnType(4, "TEXT");
        }
        return INSTANCE;
    }

    @Override
    public AbstractColumnParser<?> customParser(ReadOptions options) {
        return new StringParser(this, options);
    }

    @Override
    public DataType type() {
        return DataType.STRING;
    }
}

