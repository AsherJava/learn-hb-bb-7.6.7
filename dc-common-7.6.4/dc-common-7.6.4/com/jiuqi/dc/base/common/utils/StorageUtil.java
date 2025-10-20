/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.va.mapper.jdialect.model.ColumnModel;

public class StorageUtil {
    public static final String VARCHAR_DEFAULT_VALUE = "'#'";
    public static final String INTEGER_DEFAULT_VALUE = "0";
    public static final String NUMERIC_DEFAULT_VALUE = "0.00";
    public static final String DATE_DEFAULT_VALUE = "1970-01-01";

    private StorageUtil() {
        throw new IllegalStateException();
    }

    public static final ColumnModel columnNumericField(String field) {
        return StorageUtil.columnNumericPrecisionField(field, 2);
    }

    public static final ColumnModel columnAmountField(String field) {
        return StorageUtil.columnNumericPrecisionField(field, 6);
    }

    public static final ColumnModel columnNumericPrecisionField(String field, int precision) {
        ColumnModel column = new ColumnModel(field).NUMERIC(new Integer[]{19, precision});
        column.setNullable(Boolean.valueOf(false));
        column.setDefaultValue(NUMERIC_DEFAULT_VALUE);
        return column;
    }

    public static final ColumnModel columnNumericPrecisionField(String field, int length, int precision) {
        ColumnModel column = new ColumnModel(field).NUMERIC(new Integer[]{length, precision});
        column.setNullable(Boolean.valueOf(false));
        column.setDefaultValue(NUMERIC_DEFAULT_VALUE);
        return column;
    }
}

