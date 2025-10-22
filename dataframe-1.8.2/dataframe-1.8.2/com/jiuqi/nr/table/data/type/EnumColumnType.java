/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data.type;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.AbstractColumnType;
import com.jiuqi.nr.table.data.type.EnumParser;
import com.jiuqi.nr.table.io.ReadOptions;

public class EnumColumnType
extends AbstractColumnType {
    public static final EnumParser DEFAULT_PARSER = new EnumParser(ColumnType.BOOLEAN);
    public static final byte BYTE_TRUE = 1;
    public static final byte BYTE_FALSE = 0;
    private static final byte BYTE_SIZE = 1;
    private static EnumColumnType INSTANCE;

    private EnumColumnType(int byteSize, String name) {
        super(byteSize, name);
    }

    public static EnumColumnType instance() {
        if (INSTANCE == null) {
            INSTANCE = new EnumColumnType(1, "ENUM");
        }
        return INSTANCE;
    }

    public EnumParser customParser(ReadOptions readOptions) {
        return new EnumParser(this, readOptions);
    }

    @Override
    public DataType type() {
        return DataType.ENUM;
    }
}

