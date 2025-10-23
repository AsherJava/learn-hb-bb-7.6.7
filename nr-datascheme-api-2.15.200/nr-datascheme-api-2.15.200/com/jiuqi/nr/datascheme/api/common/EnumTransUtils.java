/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datascheme.api.common;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class EnumTransUtils {
    private EnumTransUtils() {
    }

    public static TableGatherType valueOf(DataTableGatherType getherType) {
        if (null == getherType) {
            return TableGatherType.TABLE_GATHER_NONE;
        }
        switch (getherType) {
            case CLASSIFY: {
                return TableGatherType.TABLE_GATHER_CLASSIFY;
            }
            case LIST: {
                return TableGatherType.TABLE_GATHER_LIST;
            }
        }
        return TableGatherType.TABLE_GATHER_NONE;
    }

    public static FieldType valueOf(DataFieldType dataFieldType) {
        switch (dataFieldType) {
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case DATE: {
                return FieldType.FIELD_TYPE_DATE;
            }
            case DATE_TIME: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
            case UUID: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case BIGDECIMAL: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case CLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case PICTURE: {
                return FieldType.FIELD_TYPE_PICTURE;
            }
            case FILE: {
                return FieldType.FIELD_TYPE_FILE;
            }
        }
        return FieldType.FIELD_TYPE_GENERAL;
    }

    public static FieldType valueOf(ColumnModelType dataFieldType) {
        switch (dataFieldType) {
            case DOUBLE: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case DATETIME: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
            case UUID: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case BIGDECIMAL: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case CLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case BLOB: {
                return FieldType.FIELD_TYPE_BINARY;
            }
            case ATTACHMENT: {
                return FieldType.FIELD_TYPE_FILE;
            }
        }
        return FieldType.FIELD_TYPE_GENERAL;
    }

    private static FieldValueType valueOf(DataFieldApplyType applyType) {
        if (null == applyType) {
            return FieldValueType.FIELD_VALUE_DEFALUT;
        }
        switch (applyType) {
            case PERIODIC: {
                return FieldValueType.FIELD_VALUE_PERIODIC;
            }
            case TIME_POINT: {
                return FieldValueType.FIELD_VALUE_TIME_POINT;
            }
        }
        return FieldValueType.FIELD_VALUE_DEFALUT;
    }

    public static FieldValueType valueOf(ApplyType applyType) {
        if (null == applyType) {
            return FieldValueType.FIELD_VALUE_DEFALUT;
        }
        switch (applyType) {
            case PERIODIC: {
                return FieldValueType.FIELD_VALUE_PERIODIC;
            }
            case TIME_POINT: {
                return FieldValueType.FIELD_VALUE_TIME_POINT;
            }
        }
        return FieldValueType.FIELD_VALUE_DEFALUT;
    }
}

