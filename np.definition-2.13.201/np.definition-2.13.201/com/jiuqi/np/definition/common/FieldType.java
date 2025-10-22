/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FieldType {
    FIELD_TYPE_GENERAL(0),
    FIELD_TYPE_FLOAT(1),
    FIELD_TYPE_STRING(2),
    FIELD_TYPE_INTEGER(3),
    FIELD_TYPE_LOGIC(4),
    FIELD_TYPE_DATE(5),
    FIELD_TYPE_DATE_TIME(6),
    FIELD_TYPE_TIME(19),
    FIELD_TYPE_UUID(7),
    FIELD_TYPE_DECIMAL(8),
    FIELD_TYPE_TIME_STAMP(9),
    FIELD_TYPE_TEXT(16),
    FIELD_TYPE_PICTURE(17),
    FIELD_TYPE_BINARY(18),
    FIELD_TYPE_FILE(22),
    FIELD_TYPE_ENUM(23),
    FIELD_TYPE_LATITUDE_LONGITUDE(21),
    FIELD_TYPE_QRCODE(20),
    FIELD_TYPE_OBJECT_ARRAY(64),
    FIELD_TYPE_ERROR(127);

    private int intValue;
    private static Map<Integer, FieldType> mappings;

    private static Map<Integer, FieldType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FieldType.values()).collect(Collectors.toMap(FieldType::getValue, f -> f));
        }
        return mappings;
    }

    private FieldType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FieldType forValue(int value) {
        return FieldType.getMappings().get(value);
    }
}

