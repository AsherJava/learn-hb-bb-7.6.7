/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.entity.engine.common;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class DataTypesConvert {
    public static int fieldTypeToDataType(ColumnModelType fieldType) {
        switch (fieldType) {
            case DOUBLE: {
                return 10;
            }
            case STRING: {
                return 6;
            }
            case INTEGER: {
                return 4;
            }
            case BOOLEAN: {
                return 1;
            }
            case DATETIME: {
                return 2;
            }
            case BIGDECIMAL: {
                return 10;
            }
            case BLOB: {
                return 37;
            }
            case CLOB: {
                return 6;
            }
            case UUID: {
                return 33;
            }
        }
        return -1;
    }
}

