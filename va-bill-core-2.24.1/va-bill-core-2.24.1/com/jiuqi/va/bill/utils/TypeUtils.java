/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.mapper.jdialect.Type
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.mapper.jdialect.Type;
import java.util.HashMap;
import java.util.Map;

public class TypeUtils {
    private static final Map<DataModelType.ColumnType, ValueType> cloumnTypeMap = new HashMap<DataModelType.ColumnType, ValueType>();
    private static final Map<Type, ValueType> typeMap;
    private static final Map<ValueType, Type> valueTypeMap;

    public static ValueType map(Type type) {
        ValueType valueType = typeMap.get(type);
        if (valueType == null) {
            throw new IllegalArgumentException();
        }
        return valueType;
    }

    public static Type map(ValueType valueType) {
        Type type = valueTypeMap.get(valueType);
        if (type == null) {
            throw new IllegalArgumentException();
        }
        return type;
    }

    public static ValueType map(DataModelType.ColumnType type) {
        ValueType valueType = cloumnTypeMap.get(type);
        if (valueType == null) {
            throw new IllegalArgumentException();
        }
        return valueType;
    }

    static {
        cloumnTypeMap.put(DataModelType.ColumnType.NUMERIC, ValueType.DECIMAL);
        cloumnTypeMap.put(DataModelType.ColumnType.UUID, ValueType.IDENTIFY);
        cloumnTypeMap.put(DataModelType.ColumnType.NVARCHAR, ValueType.STRING);
        cloumnTypeMap.put(DataModelType.ColumnType.INTEGER, ValueType.INTEGER);
        cloumnTypeMap.put(DataModelType.ColumnType.DATE, ValueType.DATE);
        cloumnTypeMap.put(DataModelType.ColumnType.TIMESTAMP, ValueType.DATETIME);
        cloumnTypeMap.put(DataModelType.ColumnType.CLOB, ValueType.TEXT);
        typeMap = new HashMap<Type, ValueType>();
        typeMap.put(Type.BIGINT, ValueType.LONG);
        typeMap.put(Type.BINARY, ValueType.BINARY);
        typeMap.put(Type.BIT, ValueType.BOOLEAN);
        typeMap.put(Type.BLOB, ValueType.BINARY);
        typeMap.put(Type.BOOLEAN, ValueType.BOOLEAN);
        typeMap.put(Type.CHAR, ValueType.STRING);
        typeMap.put(Type.CLOB, ValueType.TEXT);
        typeMap.put(Type.DATE, ValueType.DATE);
        typeMap.put(Type.DECIMAL, ValueType.DECIMAL);
        typeMap.put(Type.DOUBLE, ValueType.DOUBLE);
        typeMap.put(Type.FLOAT, ValueType.DOUBLE);
        typeMap.put(Type.INTEGER, ValueType.INTEGER);
        typeMap.put(Type.JAVA_OBJECT, ValueType.BINARY);
        typeMap.put(Type.LONGNVARCHAR, ValueType.STRING);
        typeMap.put(Type.LONGVARBINARY, ValueType.BINARY);
        typeMap.put(Type.LONGVARCHAR, ValueType.TEXT);
        typeMap.put(Type.NCHAR, ValueType.STRING);
        typeMap.put(Type.NCLOB, ValueType.TEXT);
        typeMap.put(Type.NUMERIC, ValueType.DECIMAL);
        typeMap.put(Type.NVARCHAR, ValueType.STRING);
        typeMap.put(Type.OTHER, ValueType.BINARY);
        typeMap.put(Type.REAL, ValueType.DOUBLE);
        typeMap.put(Type.SMALLINT, ValueType.BYTE);
        typeMap.put(Type.TIME, ValueType.TIME);
        typeMap.put(Type.TIMESTAMP, ValueType.DATETIME);
        typeMap.put(Type.TINYINT, ValueType.SHORT);
        typeMap.put(Type.VARBINARY, ValueType.BINARY);
        typeMap.put(Type.VARCHAR, ValueType.STRING);
        valueTypeMap = new HashMap<ValueType, Type>();
        valueTypeMap.put(ValueType.BOOLEAN, Type.BOOLEAN);
        valueTypeMap.put(ValueType.BYTE, Type.TINYINT);
        valueTypeMap.put(ValueType.SHORT, Type.SMALLINT);
        valueTypeMap.put(ValueType.INTEGER, Type.INTEGER);
        valueTypeMap.put(ValueType.LONG, Type.BIGINT);
        valueTypeMap.put(ValueType.DECIMAL, Type.NUMERIC);
        valueTypeMap.put(ValueType.DOUBLE, Type.DOUBLE);
        valueTypeMap.put(ValueType.DATE, Type.DATE);
        valueTypeMap.put(ValueType.DATETIME, Type.TIMESTAMP);
        valueTypeMap.put(ValueType.TIME, Type.TIME);
        valueTypeMap.put(ValueType.IDENTIFY, Type.CHAR);
        valueTypeMap.put(ValueType.STRING, Type.NVARCHAR);
        valueTypeMap.put(ValueType.BINARY, Type.BLOB);
        valueTypeMap.put(ValueType.TEXT, Type.CLOB);
    }
}

