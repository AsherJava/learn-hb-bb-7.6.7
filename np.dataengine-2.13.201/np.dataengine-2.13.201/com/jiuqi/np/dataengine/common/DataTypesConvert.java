/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.CalendarCache
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.syntax.data.CalendarCache;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DataTypesConvert {
    public static int fieldTypeToDataType(FieldType fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_GENERAL: {
                return 0;
            }
            case FIELD_TYPE_FLOAT: {
                return 3;
            }
            case FIELD_TYPE_STRING: {
                return 6;
            }
            case FIELD_TYPE_INTEGER: {
                return 4;
            }
            case FIELD_TYPE_LOGIC: {
                return 1;
            }
            case FIELD_TYPE_DATE: {
                return 5;
            }
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME_STAMP: {
                return 2;
            }
            case FIELD_TYPE_TIME: {
                return 8;
            }
            case FIELD_TYPE_DECIMAL: {
                return 10;
            }
            case FIELD_TYPE_BINARY: {
                return 37;
            }
            case FIELD_TYPE_FILE: {
                return 36;
            }
            case FIELD_TYPE_PICTURE: {
                return 35;
            }
            case FIELD_TYPE_TEXT: {
                return 34;
            }
            case FIELD_TYPE_UUID: {
                return 33;
            }
        }
        return -1;
    }

    public static int fieldTypeToDataType(ColumnModelType fieldType) {
        switch (fieldType) {
            case DOUBLE: {
                return 3;
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
            case ATTACHMENT: {
                return 36;
            }
            case CLOB: {
                return 34;
            }
            case UUID: {
                return 33;
            }
        }
        return -1;
    }

    public static FieldType DataTypeToFieldType(int dataType) {
        switch (dataType) {
            case 7: {
                return FieldType.FIELD_TYPE_GENERAL;
            }
            case 3: {
                return FieldType.FIELD_TYPE_FLOAT;
            }
            case 6: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case 4: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case 1: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case 5: {
                return FieldType.FIELD_TYPE_DATE;
            }
            case 2: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
            case 8: {
                return FieldType.FIELD_TYPE_TIME;
            }
            case 10: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case 37: {
                return FieldType.FIELD_TYPE_BINARY;
            }
            case 36: {
                return FieldType.FIELD_TYPE_FILE;
            }
            case 35: {
                return FieldType.FIELD_TYPE_PICTURE;
            }
            case 34: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case 33: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case 0: {
                return FieldType.FIELD_TYPE_GENERAL;
            }
        }
        return FieldType.FIELD_TYPE_ERROR;
    }

    public static ColumnModelType DataTypeToColumnType(int dataType) {
        switch (dataType) {
            case 3: {
                return ColumnModelType.DOUBLE;
            }
            case 6: {
                return ColumnModelType.STRING;
            }
            case 4: {
                return ColumnModelType.INTEGER;
            }
            case 1: {
                return ColumnModelType.BOOLEAN;
            }
            case 2: {
                return ColumnModelType.DATETIME;
            }
            case 10: {
                return ColumnModelType.BIGDECIMAL;
            }
            case 37: {
                return ColumnModelType.BLOB;
            }
            case 36: {
                return ColumnModelType.ATTACHMENT;
            }
            case 34: {
                return ColumnModelType.CLOB;
            }
            case 33: {
                return ColumnModelType.UUID;
            }
        }
        return ColumnModelType.UUID;
    }

    public static Object toASTNodeData(Object value, int dataType) {
        if (value == null) {
            return null;
        }
        if (dataType == 10) {
            if (value instanceof Double) {
                value = new BigDecimal((Double)value);
            } else if (value instanceof Integer) {
                value = new BigDecimal((Integer)value);
            } else if (value instanceof Long) {
                value = new BigDecimal((Long)value);
            }
        } else if (dataType == 5 || dataType == 2) {
            if (value instanceof Calendar) {
                return value;
            }
            Calendar c = null;
            if (value instanceof Timestamp) {
                c = Calendar.getInstance();
                c.setTime((Timestamp)value);
            } else if (value instanceof Date) {
                c = Calendar.getInstance();
                c.setTime((Date)value);
            } else if (value instanceof Long) {
                c = CalendarCache.get((long)((Long)value));
            }
            return c;
        }
        return value;
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        Class<?> c = value.getClass();
        if (c == BigDecimal.class) {
            return (BigDecimal)value;
        }
        if (c == Long.class) {
            return BigDecimal.valueOf((Long)value);
        }
        if (c == BigInteger.class) {
            return new BigDecimal((BigInteger)value);
        }
        if (c == String.class) {
            return new BigDecimal((String)value);
        }
        return new BigDecimal(value.toString());
    }

    public static Date periodToDate(PeriodWrapper periodWrapper) {
        PeriodType pt = PeriodType.fromType((int)periodWrapper.getType());
        Calendar calendar = pt.toCalendar(periodWrapper);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static PeriodWrapper dateToPeriod(Date date) {
        PeriodType pt = PeriodType.DAY;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return pt.fromCalendar(calendar);
    }

    public static int gatherTypeToStatKind(FieldGatherType gatherType) {
        if (gatherType == null) {
            return 0;
        }
        switch (gatherType) {
            case FIELD_GATHER_NONE: {
                return 6;
            }
            case FIELD_GATHER_SUM: {
                return 1;
            }
            case FIELD_GATHER_COUNT: {
                return 2;
            }
            case FIELD_GATHER_AVG: {
                return 3;
            }
            case FIELD_GATHER_MIN: {
                return 5;
            }
            case FIELD_GATHER_MAX: {
                return 4;
            }
        }
        return 0;
    }
}

