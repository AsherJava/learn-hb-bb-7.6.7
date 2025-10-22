/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FieldValueType {
    FIELD_VALUE_DEFALUT(0),
    FIELD_VALUE_TIME_POINT(1),
    FIELD_VALUE_PERIODIC(2),
    FIELD_VALUE_PERCENTAGE(3),
    FIELD_VALUE_INPUT_ORDER(10),
    FIELD_VALUE_PERIOD_VALUE(11),
    FIELD_VALUE_ROW_VERSION(12),
    FIELD_VALUE_BIZKEY_ORDER(13),
    FIELD_VALUE_VERSION_DATE(14),
    FIELD_VALUE_DATE_VERSION(15),
    FIELD_VALUE_UNIT_NAME(20),
    FIELD_VALUE_UNIT_CODE(21),
    FIELD_VALUE_PARENT_UNIT(22),
    FIELD_VALUE_UNIT_TYPE(23),
    FIELD_VALUE_PERIOD_YEAR(24),
    FIELD_VALUE_PERIOD_TYPE(25),
    FIELD_VALUE_PERIOD_NUMBER(26),
    FIELD_VALUE_RECORD_KEY(27),
    FIELD_VALUE_UNIT_KEY(28),
    FIELD_VALUE_DICTIONARY_CODE(29),
    FIELD_VALUE_DICTIONARY_TITLE(30),
    FIELD_VALUE_DICTIONARY_PARENT(31),
    FIELD_VALUE_UNIT_ORDER(32),
    FIELD_VALUE_PRESSION(33),
    FIELD_VALUE_CN_MOBILE_PHONE(60),
    FIELD_VALUE_CN_ID_NUMBER(61),
    FIELD_VALUE_UNIT_INFO(81),
    FIELD_VALUE_NB_ORDER(90);

    private int intValue;
    private static Map<Integer, FieldValueType> mappings;

    private static Map<Integer, FieldValueType> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FieldValueType.values()).collect(Collectors.toMap(FieldValueType::getValue, f -> f));
        }
        return mappings;
    }

    private FieldValueType(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static FieldValueType forValue(int value) {
        return FieldValueType.getMappings().get(value);
    }
}

