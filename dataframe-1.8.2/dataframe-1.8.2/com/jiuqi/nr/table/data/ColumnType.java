/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.type.BooleanColumnType;
import com.jiuqi.nr.table.data.type.DateColumnType;
import com.jiuqi.nr.table.data.type.DateTimeColumnType;
import com.jiuqi.nr.table.data.type.DoubleColumnType;
import com.jiuqi.nr.table.data.type.EnumColumnType;
import com.jiuqi.nr.table.data.type.IntColumnType;
import com.jiuqi.nr.table.data.type.LongColumnType;
import com.jiuqi.nr.table.data.type.StringColumnType;
import com.jiuqi.nr.table.data.type.TextColumnType;
import com.jiuqi.nr.table.io.ReadOptions;

public interface ColumnType {
    public static final IntColumnType INTEGER = IntColumnType.instance();
    public static final LongColumnType LONG = LongColumnType.instance();
    public static final BooleanColumnType BOOLEAN = BooleanColumnType.instance();
    public static final StringColumnType STRING = StringColumnType.instance();
    public static final DoubleColumnType DOUBLE = DoubleColumnType.instance();
    public static final DateColumnType LOCAL_DATE = DateColumnType.instance();
    public static final DateTimeColumnType LOCAL_DATE_TIME = DateTimeColumnType.instance();
    public static final TextColumnType TEXT = TextColumnType.instance();
    public static final EnumColumnType ENUM = EnumColumnType.instance();

    public String name();

    public int byteSize();

    public AbstractColumnParser<?> customParser(ReadOptions var1);
}

