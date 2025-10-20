/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.types;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class DataTypes {
    public static final int ERROR = -1;
    public static final int UNKNOWN = 0;
    public static final int BOOLEAN = 1;
    public static final int DATETIME = 2;
    public static final int DOUBLE = 3;
    public static final int INTEGER = 5;
    public static final int STRING = 6;
    public static final int AUTOINC = 7;
    public static final int LONG = 8;
    public static final int BLOB = 9;
    public static final int BIGDECIMAL = 10;
    public static final int ARRAY = 11;
    public static final int UUID = 33;
    public static final int CLOB = 12;
    public static final int RAW = 13;
    public static final int CUSTOM_FIRST = 5000;
    private static final int ORACLE_BINARY_FLOAT = 100;
    private static final int ORACLE_BINARY_DOUBLE = 101;
    private static final int JDBC_NCLOB = 2011;
    private static final String[] TRUE_VALUES = new String[]{"true", "t", "1"};

    private DataTypes() {
    }

    public static boolean isNumber(int dataType) {
        switch (dataType) {
            case 3: 
            case 5: 
            case 8: 
            case 10: {
                return true;
            }
        }
        return false;
    }

    public static Number convertNumber(Number value, int dataType) {
        switch (dataType) {
            case 3: {
                return value.doubleValue();
            }
            case 5: {
                return value.intValue();
            }
            case 10: {
                return DataTypes.toBigDecimal(value);
            }
        }
        return value;
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        if (value instanceof Double) {
            return new BigDecimal((Double)value);
        }
        if (value instanceof Integer) {
            return new BigDecimal((Integer)value);
        }
        if (value instanceof Long) {
            return new BigDecimal((Long)value);
        }
        if (value instanceof String) {
            return new BigDecimal((String)value);
        }
        return null;
    }

    public static int fromJavaSQLType(int javaSQLType) {
        switch (javaSQLType) {
            case -6: 
            case 4: 
            case 5: {
                return 5;
            }
            case -5: {
                return 8;
            }
            case -7: 
            case 16: {
                return 1;
            }
            case -15: 
            case -9: 
            case -1: 
            case 1: 
            case 12: 
            case 2005: {
                return 6;
            }
            case 2: 
            case 3: 
            case 6: 
            case 7: 
            case 8: 
            case 100: 
            case 101: {
                return 3;
            }
            case 91: 
            case 92: 
            case 93: {
                return 2;
            }
            case -4: 
            case -3: 
            case -2: 
            case 2004: 
            case 2011: {
                return 9;
            }
        }
        return 0;
    }

    public static int fromJavaSQLType(int sqlType, String columnTypeName) {
        if (columnTypeName.toUpperCase().startsWith("NVARCHAR")) {
            return 6;
        }
        return DataTypes.fromJavaSQLType(sqlType);
    }

    public static int fromJavaSQLType(ResultSetMetaData metadata, int index) throws SQLException {
        int sqlType = metadata.getColumnType(index);
        if ((sqlType == 2 || sqlType == 3) && metadata.getScale(index) == 0) {
            int precision = metadata.getPrecision(index);
            if (precision > 0 && precision <= 10) {
                return 5;
            }
            if (precision > 10 && precision <= 19) {
                return 8;
            }
            return DataTypes.fromJavaSQLType(sqlType, metadata.getColumnTypeName(index));
        }
        return DataTypes.fromJavaSQLType(sqlType, metadata.getColumnTypeName(index));
    }

    public static int toJavaSQLType(int dataType) {
        switch (dataType) {
            case 1: {
                return 16;
            }
            case 2: {
                return 91;
            }
            case 3: 
            case 10: {
                return 8;
            }
            case 5: {
                return 4;
            }
            case 6: {
                return 12;
            }
            case 7: {
                return 4;
            }
            case 8: {
                return -5;
            }
            case 9: {
                return 2004;
            }
        }
        return 1111;
    }

    public static String dataTypeToString(int dataType) {
        switch (dataType) {
            case 0: {
                return "unknown";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "datetime";
            }
            case 3: {
                return "double";
            }
            case 5: {
                return "integer";
            }
            case 6: {
                return "string";
            }
            case 7: {
                return "autoinc";
            }
            case 8: {
                return "long";
            }
            case 9: {
                return "blob";
            }
            case 12: {
                return "clob";
            }
            case 10: {
                return "bigdecimal";
            }
            case 13: {
                return "raw";
            }
            case 11: {
                return "array";
            }
            case 33: {
                return "uuid";
            }
            case -1: {
                return "error";
            }
        }
        return null;
    }

    public static Boolean parseBoolean(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        for (int i = 0; i < TRUE_VALUES.length; ++i) {
            if (!TRUE_VALUES[i].equalsIgnoreCase(value)) continue;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}

