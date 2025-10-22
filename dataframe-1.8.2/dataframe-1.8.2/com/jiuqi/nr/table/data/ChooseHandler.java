/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.data.type.BooleanColumnType;
import com.jiuqi.nr.table.data.type.DateColumnType;
import com.jiuqi.nr.table.data.type.DateTimeColumnType;
import com.jiuqi.nr.table.data.type.DoubleColumnType;
import com.jiuqi.nr.table.data.type.EnumColumnType;
import com.jiuqi.nr.table.data.type.LongColumnType;
import com.jiuqi.nr.table.data.type.StringColumnType;

public class ChooseHandler {
    public static ColumnType choose(DataType type) {
        switch (type) {
            case BOOL: {
                return BooleanColumnType.instance();
            }
            case INT: 
            case LONG: {
                return LongColumnType.instance();
            }
            case DOUBLE: {
                return DoubleColumnType.instance();
            }
            case DATE: {
                return DateColumnType.instance();
            }
            case DATETIME: {
                return DateTimeColumnType.instance();
            }
            case ENUM: {
                return EnumColumnType.instance();
            }
        }
        return StringColumnType.instance();
    }
}

